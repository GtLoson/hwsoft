/**
 * 
 */
package com.hwsoft.util.idcard;

import com.hwsoft.util.validate.IdentityValidate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 * @author tzh
 *
 */
public class IdCardUtil {
	
	private static Log logger = LogFactory.getLog(IdCardUtil.class);
	
	public static IdCardInfo checkIdCard(String idCardNumber,String realName){
		
		try {
			IdCardInfo idCardInfo = new IdCardInfo();

			IdentityValidate identityValidate = new IdentityValidate(idCardNumber);
			idCardInfo.setDate(identityValidate.getBirthday());
			idCardInfo.setGener(identityValidate.getGender());
			idCardInfo.setProvince(identityValidate.getProvince());
			idCardInfo.setRealName(realName);
			idCardInfo.setIdCardNumber(idCardNumber);

			return idCardInfo;


//			return IDCardVerifyTools.verifyIDCard(realName, idCardNumber);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("认证失败："+e);
			logger.error("认证失败，"+e.getMessage());
		}
		
		return null;
	}

//	private void  test(){
//		
//		QueryValidatorServicesProxy proxy = new QueryValidatorServicesProxy();
//		proxy.setEndpoint("http://gboss.id5.cn/services/QueryValidatorServices?wsdl");
//		QueryValidatorServices service = proxy.getQueryValidatorServices();
//		String userName = "username";//用户名
//		String password = "password";//密码
//		System.setProperty("javax.net.ssl.trustStore", "CheckID.keystore");
//		String resultXML = "";
//		String datasource = "1A020201";//数据类型
//		//单条
//		String param = "刘丽萍,210204196501015829";//输入参数
//		try {
//			resultXML = service.querySingle(userName, password, datasource, param);
//			
//			System.out.println(resultXML);
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	public static void main(String[] args) {
		IdCardInfo idCardInfo = checkIdCard("510902198801224652", "dd");
		System.out.println(idCardInfo.getCity());
		System.out.println(idCardInfo.getProvince());
		System.out.println(idCardInfo.getGener());
	}
	
}
