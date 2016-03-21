package com.hwsoft.util.idcard;

import com.nciic.serv.webservice.ServiceInf;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.client.XFireProxy;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.codehaus.xfire.transport.http.CommonsHttpMessageSender;
import org.codehaus.xfire.transport.http.EasySSLProtocolSocketFactory;
import org.codehaus.xfire.util.dom.DOMOutHandler;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.tree.DefaultAttribute;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * NCIIS 验证用户的身份证信息
 *
 * @author wangzhen
 * @since 2013-08-12
 */
public class IDCardVerifyTools {

  /**
   * NCIIS服务地址
   */
  public static final String SERVICE_URL = "https://api.nciic.com.cn/nciic_ws/services/";
  /**
   * 服务的名字
   */
  public static final String SERVICE_NAME = "NciicServices";
  /**
   * 授权文件
   */
  public static final String LICENSE = "license.txt";

  private static Log logger = LogFactory.getLog(IDCardVerifyTools.class);

  /**
   * 与Nciic 通讯
   *
   * @return
   * @throws MalformedURLException
   */
  public static String executeNciicClient(String condition) {
    String result = null;
    ServiceInf service = null;

    try {
      ProtocolSocketFactory easy = new EasySSLProtocolSocketFactory();
      Protocol protocol = new Protocol("https", easy, 443);
      Protocol.registerProtocol("https", protocol);
      Service serviceModel = new ObjectServiceFactory().create(ServiceInf.class, "service", null, null);
      service = (ServiceInf) new XFireProxyFactory().create(serviceModel, SERVICE_URL + SERVICE_NAME);
      Client client = ((XFireProxy) Proxy.getInvocationHandler(service)).getClient();
      client.addOutHandler(new DOMOutHandler());

      //压缩传输
      client.setProperty(CommonsHttpMessageSender.GZIP_ENABLED, Boolean.TRUE);

      //忽略超时
      client.setProperty(CommonsHttpMessageSender.DISABLE_EXPECT_CONTINUE, "1");
      client.setProperty(CommonsHttpMessageSender.HTTP_TIMEOUT, "0");
    } catch (MalformedURLException e) {
      System.out.println("idcard bind erroinfo:网络异常！");
      throw new RuntimeException("网络异常！", null);
    }

    //读取授权文件内容，因为授权文件为加密格式请不要对内容做任何修改
    String licensecode = null;
    BufferedReader in;

    try {
      InputStream is = IDCardVerifyTools.class.getClassLoader().getResourceAsStream("com/hwsoft/util/idcard/" + LICENSE);
      in = new BufferedReader(new InputStreamReader(is));
      licensecode = in.readLine();

      //获取用户的信息
      result = service.nciicCheck(licensecode, condition);
    } catch (Exception e) {
      logger.info("idcard bind erroinfo:本地读取授权文件失败！");
      throw new RuntimeException("本地读取授权文件失败！", null);
    }

    return result;
  }

  /**
   * 解析从Nciicl返回的数据
   *
   * @param backXMl：返回的数据
   * @return
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public static IdCardInfo parseXml(String backXMl, String userName, String idCard) {
    Document doc = null;
    String result_gmsfhm = null;
    String result_xm = null;
    String gmsfhm = null;
    String xm = null;
    String xp = null;
    String errormesage = null;
    IdCardInfo IdCardInfo = null;
    System.out.println("idcard bind Nciic  backInfo:" + backXMl);

    try {
      //将字符串转为XML
      doc = DocumentHelper.parseText(backXMl);

      // 获取根节点
      Element rootElt = doc.getRootElement();

      if ("RESPONSE".equalsIgnoreCase(rootElt.getName())) {
        //获取错误码和错误信息
        List<DefaultAttribute> list = rootElt.attributes();

        for (DefaultAttribute e : list) {
          if ("errorcode".equals(e.getName())) {
            if ("-70".equalsIgnoreCase(e.getValue())) {
              throw new RuntimeException("用户不存在", null);
            } else if ("-71".equalsIgnoreCase(e.getValue())) {
              throw new RuntimeException("用户密码错误", null);
            } else if ("-72".equalsIgnoreCase(e.getValue())) {
              throw new RuntimeException("IP 地址受限", null);
            } else if ("-80".equalsIgnoreCase(e.getValue())) {
              throw new RuntimeException("授权文件格式错误", null);
            }
          }
        }
      } else if ("ROWS".equalsIgnoreCase(rootElt.getName())) {
        Iterator iterForRow = rootElt.elementIterator("ROW");

        while (iterForRow.hasNext()) {
          Element recordEleForRow = (Element) iterForRow.next();

          //身份证中心返回的用户输入的姓名和身份证号
          Iterator itersForInput = recordEleForRow.elementIterator("INPUT");

          while (itersForInput.hasNext()) {
            Element recordEleForInput = (Element) itersForInput.next();
            gmsfhm = recordEleForInput.elementTextTrim("gmsfhm");

            if (!(idCard.equalsIgnoreCase(gmsfhm))) {
              throw new RuntimeException("用户的身份证号不匹配！", null);
            }

            xm = recordEleForInput.elementTextTrim("xm");

            if (!(userName.equalsIgnoreCase(xm))) {
              throw new RuntimeException("用户的姓名不匹配！", null);
            }
          }

          //身份证中心返回的用户输入的姓名和身份证号的匹配结果
          Iterator itersForOutput = recordEleForRow.elementIterator("OUTPUT");

          while (itersForOutput.hasNext()) {
            Element recordEleForSubRow = (Element) itersForOutput.next();
            Iterator itersForItem = recordEleForSubRow.elementIterator("ITEM");

            while (itersForItem.hasNext()) {
              Element recordEleForItem = (Element) itersForItem.next();
              gmsfhm = recordEleForItem.elementTextTrim("gmsfhm");
              xm = recordEleForItem.elementTextTrim("xm");
              xp = recordEleForItem.elementTextTrim("xp");
              errormesage = recordEleForItem.elementTextTrim("errormesage");

              if (gmsfhm != null) {
                result_gmsfhm = recordEleForItem.elementTextTrim("result_gmsfhm");
              }

              if (xm != null) {
                result_xm = recordEleForItem.elementTextTrim("result_xm");
              }

              if (xp != null) {
                xp = recordEleForItem.elementTextTrim("xp");
              }

              if (errormesage != null) {
                errormesage = recordEleForItem.elementTextTrim("errormesage");
                break;
              }
            }
          }

          if (null != errormesage) {
            throw new RuntimeException("库中无此号，请到户籍所在地进行核实!", null);
          } else {
            if (!"一致".equalsIgnoreCase(result_xm)) {
              throw new RuntimeException("输入的姓名不匹配！", null);
            }

            if (!"一致".equalsIgnoreCase(result_gmsfhm)) {
              throw new RuntimeException("输入的身份证号不匹配！", null);
            }

            IdCardInfo = new IdCardInfo();
            IdCardInfo.setIdCardNumber(idCard);
            IdCardInfo.setRealName(userName);
            IdCardInfo.setAvatar(xp);
          }
        }
      }
    } catch (DocumentException e) {
      System.out.println("idcard bind erroinfo:XML解析异常！");
    } catch (Exception e) {
      System.out.println("idcard bind erroinfo:XML解析异常！");
    }
    return IdCardInfo;
  }

  /**
   * 匹配用户的名字和身份证号
   *
   * @return
   * @throws MalformedURLException
   */
  public static IdCardInfo verifyIDCard(String userName, String idcard) {

    String condition = "<?xml version=\'1.0\' encoding=\'UTF-8\' ?><ROWS><INFO><SBM>shshshsh53080</SBM>" +
        "</INFO><ROW><GMSFHM>公民身份号码</GMSFHM><XM>姓名</XM></ROW>" +
        "<ROW FSD=\'4864\' YWLX=\'网络借贷\' ><GMSFHM>" + idcard +
        "</GMSFHM>" + "<XM>" + userName + "</XM></ROW></ROWS>";
    IdCardInfo IdCardInfo = null;

    try {
      //通讯
      String result = executeNciicClient(condition);
      IdCardInfo = parseXml(result, userName, idcard);

      if (null != IdCardInfo) {
        IDVerifierUtil idVerifierUtil = new IDVerifierUtil(idcard);
        String province = "";
        String city = "";
        String region = "";

        if (null != idVerifierUtil.getProvince()) {
          province = idVerifierUtil.getProvince();
        }

        if (null != idVerifierUtil.getCity()) {
          city = idVerifierUtil.getCity();
        }

        if (null != idVerifierUtil.getRegion()) {
          region = idVerifierUtil.getRegion();
        }

        IdCardInfo.setAddress(province + city + region);
        IdCardInfo.setDate(idVerifierUtil.getBirthday());

        if ("男".equalsIgnoreCase(idVerifierUtil.getGender())) {
          IdCardInfo.setGener("MALE");
        }

        if ("女".equalsIgnoreCase(idVerifierUtil.getGender())) {
          IdCardInfo.setGener("FEMALE");
        }

        IdCardInfo.setValiDate(new Date());
      }
    } catch (Exception e) {
      System.out.println("idcard bind erroinfo:" + e.getMessage());
    }

    return IdCardInfo;
  }

  /**
   * 测试
   *
   * @param args
   * @throws MalformedURLException
   */
  public static void main(String[] args) throws MalformedURLException {

//		System.out.println(IDCardVerifyTools.class.getClassLoader().getResourceAsStream("com/hwsoft/util/idcard/"+LICENSE));
    verifyIDCard("邵东营", "370921198704043318");
  }

}

