package com.hwsoft.datamove;

import com.hwsoft.common.bank.UserBankCardBindStatus;
import com.hwsoft.common.customer.AccountStatus;
import com.hwsoft.common.customer.CustomerSource;
import com.hwsoft.common.customer.Gender;
import com.hwsoft.common.order.OrderStatus;
import com.hwsoft.common.order.OrderType;
import com.hwsoft.common.product.*;
import com.hwsoft.common.version.AppOSType;
import com.hwsoft.dao.bank.BankDao;
import com.hwsoft.dao.bank.UserBankCardDao;
import com.hwsoft.dao.customer.CustomerDao;
import com.hwsoft.dao.customer.CustomerInfoDao;
import com.hwsoft.dao.customer.CustomerSubAccountDao;
import com.hwsoft.dao.order.gongming.BuyPlanOrderDao;
import com.hwsoft.dao.product.*;
import com.hwsoft.dao.score.CustomerScoreDao;
import com.hwsoft.model.bank.Bank;
import com.hwsoft.model.bank.UserBankCard;
import com.hwsoft.model.customer.Customer;
import com.hwsoft.model.customer.CustomerInfo;
import com.hwsoft.model.customer.CustomerSubAccount;
import com.hwsoft.model.order.common.BuyPlanOrder;
import com.hwsoft.model.product.*;
import com.hwsoft.model.score.CustomerScore;
import com.hwsoft.util.date.DateTools;
import com.hwsoft.util.enumtype.EnumUtil;
import com.hwsoft.util.idcard.IdCardInfo;
import com.hwsoft.util.idcard.IdCardUtil;
import com.hwsoft.util.string.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("dataMoveService")
public class DataMoveService {

  private static Log logger = LogFactory.getLog(DataMoveService.class);

//	@Autowired
//	private CustomerService customerService;

  @Autowired
  private CustomerDao customerDao;

  @Autowired
  private CustomerInfoDao customerInfoDao;

  @Autowired
  private CustomerSubAccountDao customerSubAccountDao;

  @Autowired
  private BankDao bankDao;

  @Autowired
  private ProductBankDao productBankDao;

  @Autowired
  private ProductAgreementTemplateDao productAgreementTemplateDao;

  @Autowired
  private ProductSaleTimeInfoDao productSaleTimeInfoDao;

  @Autowired
  private ProductDao productDao;

  @Autowired
  private CustomerScoreDao customerScoreDao;

  @Autowired
  private BuyPlanOrderDao buyPlanOrderDao;

  @Autowired
  private ProductAccountDao productAccountDao;

  @Autowired
  private ProductSubAccountDao productSubAccountDao;

  @Autowired
  private UserBankCardDao userBankCardDao;


  public static void main(String[] args) {

    try {
//			List<Map<String, Object>> list = DB.findList("select * from users");
//			if(null != list && list.size() != 0){
//				for(Map<String, Object> map : list){//获取到用户信息
//					//查询用户详细信息
//					Map<String,Object> data = DB.findObject(" select * from customer where userId="+map.get("id"));
//					//查询
//					// System.out.println(map+"-"+data);
//
//
//
//
//				}
//				// System.out.println(list.size());
//			} else {
//				// System.out.println("数据为空");
//			}

      List<Map<String, Object>> orderProductIdList = DB.findList("select productId from orders where userId=" + 1);
      if (null != orderProductIdList && orderProductIdList.size() != 0) {
        for (Map<String, Object> orderProductIdMap : orderProductIdList) {//获取到用户信息
          int orderProductId = Integer.parseInt(orderProductIdMap.get("productId").toString());
          // System.out.println(orderProductId);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      // System.out.println("查询失败");
    }
  }

  private void syncUser() {
    // 查到所有用户
    List<Map<String, Object>> userList = DB.findList("select * from users");
    if (null != userList && userList.size() > 0) {
      logger.info("共有：" + userList.size() + "个用户需要同步数据");
      for (Map<String, Object> userMap : userList) {
        syncUser(userMap);
      }
    }
  }

  private void syncUser(Map<String, Object> userMap) {
    // 原库里的用户id
    Integer userId = (Integer) userMap.get("id");

    logger.info("开始同步用户Id：" + userId + "的用户数据");
    Map<String, Object> customerMap = syncCustomer(userMap, userId);
    syncCustomerInfo(customerMap, userId);
    // 共鸣用户Id，只有开户的用户才有
    Long memberId = (Long) customerMap.get("gongming_userId");

    // 同步之后的用户Id
    Integer customerId = (Integer) customerMap.get("id");
    CustomerSubAccount customerSubAccount = new CustomerSubAccount();
    customerSubAccount.setCustomerId(customerId);//用户id
    customerSubAccount.setMemberId(memberId + "");
    customerSubAccount.setProductChannelId(1);//产品渠道id
    customerSubAccount.setStatus(AccountStatus.USING);//产品渠道状态
    customerSubAccountDao.save(customerSubAccount);

    // 同步用户银行卡
    syncUserBankCard(userId, customerId, customerSubAccount.getId(), memberId);

    // 创建用户产品账户
    createUserProductAccount(userId, customerId, customerSubAccount.getId(), memberId);


  }

  /**
   * @param userId
   * @param customerId
   * @param userSubAccountId
   * @param memberId
   */
  private void createUserProductAccount(Integer userId, Integer customerId, Integer userSubAccountId, Long memberId) {
    // 每个用户所购买的产品的id集合
    List<Map<String, Object>> orderProductIdList = DB.findList("select distinct productId from orders where userId=" + userId);
    if (null != orderProductIdList && orderProductIdList.size() != 0) {
      // System.out.println("用户：" + customerId + "的用户，购买了" + orderProductIdList.size() + "个产品");
      for (Map<String, Object> orderProductIdMap : orderProductIdList) {//获取到用户信息
        int orderProductId = Integer.parseInt(orderProductIdMap.get("productId").toString());

        //  根据用户已经购买的产品的个数添加产品账户
        ProductAccount productAccount = new ProductAccount();
        productAccount.setAvailableAmount(0);
        productAccount.setBuyAmount(0);
        productAccount.setCustomerId(customerId);
        productAccount.setFreezeAmount(0);
        productAccount.setProductBuyerStatus(ProductBuyerStatus.NORMAL);
        productAccount.setProductId(orderProductId);
        productAccount.setTotalInterest(0);
        productAccount.setWaittingPrincipal(0);
        productAccount.setWithdrawalAmount(0);
        productAccount = productAccountDao.add(productAccount);
        Integer productAccountId = productAccount.getId();

        double productAccountDayInterest = 0;
        double productAccountAllInterest = 0;
        double productAccountTotalBuySuccessAmount = 0;


        // 根据产品和用户，获取某一个用户的所有购买订单
        List<Map<String, Object>> orderList = DB.findList("select * from orders where userId=" + userId + " and productId=" + orderProductId);
        //TODO 处理购买订单
        if (null != orderList && orderList.size() != 0) {
          for (Map<String, Object> orderMap : orderList) {//获取到用户信息

            Double buyAmount = Double.parseDouble(orderMap.get("amount").toString());
            // 这里的
            Double buySuccessAmount = Double.parseDouble(orderMap.get("amount").toString());
            Date createTime = null;
            OrderStatus orderStatus = null;

            String statusString = (String) orderMap.get("status");
            Integer orderValue = Integer.valueOf(statusString);
            if (4 == orderValue || 7 == orderValue || 8 == orderValue) {
              orderStatus = OrderStatus.HANDLE_SUCESS;
            } else if (orderValue < 4) {
              orderStatus = OrderStatus.IN_HANDLE;
            } else if (orderValue == 11) {
              orderStatus = OrderStatus.WAITING_HANDLE;
            } else if (orderValue == 12) {
              orderStatus = OrderStatus.REQUEST_FAILED;
            } else {
              orderStatus = OrderStatus.HANDLE_FAILED;
            }
            // 订单的请求id
            String orderFormId = orderMap.get("orderId").toString();
            OrderType orderType = OrderType.BUY_PLAN;
            int productId = Integer.parseInt(orderMap.get("productId").toString());
            // String response = orderMap.get("").toString();
            String thirdProductId = orderMap.get("planId").toString();
            // 订单中没有用户的银行卡的id，这个要单独处理。
            // int userBankCardId = Integer.parseInt(orderMap.get("").toString());
            String userBankCardNumber = orderMap.get("bankCardNumber").toString();
            double dailyInterest = Double.parseDouble(orderMap.get("dayIncome").toString());
            productAccountDayInterest = productAccountDayInterest + dailyInterest;
            double totalInterest = Double.parseDouble(orderMap.get("TotalIncome").toString());
//									double fee = Double.parseDouble(StringUtils.isEmpty(orderMap.get("fee").toString()) ? "0":orderMap.get("fee").toString());
            productAccountAllInterest = productAccountAllInterest + totalInterest;
            String buyTimeString = (String) orderMap.get("logtime");
            Date buyTime = DateTools.stringToDate(buyTimeString, DateTools.DATE_PATTERN_DEFAULT);

            String effectiveDateString = (String) orderMap.get("effectiveTime");
            String endTimeString = (String) orderMap.get("endTime");
            Date effectiveDate = null;
            Date endDate = null;
            try {
              effectiveDate = DateTools.stringToDateDay(effectiveDateString);
              endDate = DateTools.stringToDateDay(endTimeString);
            } catch (Exception e) {
              e.printStackTrace();
            }

            Integer statusValue = Integer.valueOf(statusString);
            ProductBuyerRecordStatus status = ProductBuyerRecordStatus.PURCHASE;
            if (statusValue < ProductBuyerRecordStatus.values().length) {
              status = ProductBuyerRecordStatus.values()[statusValue];
            }

            //TODO 先添加产品子账户
            ProductSubAccount productSubAccount = new ProductSubAccount();
            productSubAccount.setAmount(buyAmount);
            productSubAccount.setBankCardNumber(userBankCardNumber);
            productSubAccount.setBuyTime(buyTime);
            productSubAccount.setCustomerId(customerId);
            productSubAccount.setDailyInterest(dailyInterest);
            productSubAccount.setCustomerSubAccountId(userSubAccountId);
            productSubAccount.setEffectiveDate(effectiveDate);
            productSubAccount.setEndDate(endDate);
            productSubAccount.setFee(0D);
            productSubAccount.setLastInterestUpdateDate(new Date()); // TODO  这里直接用了现在作为收益的更新时间
            productSubAccount.setOrderFormId(orderFormId);
            productSubAccount.setProductBuyerId(productAccount.getId());
            productSubAccount.setProductId(productId);
            productSubAccount.setStatus(status);
            productSubAccount.setSuccessAmount(buyAmount);
            productSubAccount.setTotalInterest(totalInterest);
            productSubAccount.setUserBankCardId(0);
            productSubAccount.setWithdrawalAmount(0);

            productSubAccount = productSubAccountDao.add(productSubAccount);

            //TODO 添加购买订单
            BuyPlanOrder buyPlanOrder = new BuyPlanOrder();
            buyPlanOrder.setBackCode("");
            buyPlanOrder.setBackMsg("");
            buyPlanOrder.setBuyAmount(buyAmount);
            buyPlanOrder.setBuySuccessAmount(buySuccessAmount);
            buyPlanOrder.setCreateTime(buyTime);
            buyPlanOrder.setMemberId(memberId + "");
            buyPlanOrder.setOrderFormId(orderFormId);
            buyPlanOrder.setOrderStatus(orderStatus);
            buyPlanOrder.setOrderType(orderType);
            buyPlanOrder.setProductBuyerRecordId(productSubAccount.getId());
            buyPlanOrder.setProductId(productId);
            buyPlanOrder.setResponse("");
            buyPlanOrder.setThirdProductId(thirdProductId);
            buyPlanOrder.setUserBankCardId(0); // 银行Id信息无法获取，单独处理
            buyPlanOrder.setUserBankCardNumber(userBankCardNumber);
            buyPlanOrder.setUserId(customerId);

            buyPlanOrderDao.add(buyPlanOrder);
          }
        }

        productAccount.setTotalInterest(productAccountAllInterest);
        productAccountDao.update(productAccount);
      }
    }
  }

  private Map<String, Object> syncCustomer(Map<String, Object> userMap, Integer userId) {
    Customer customer = new Customer();
    String phoneNumber = (String) userMap.get("phone_number");
    customer.setMobile(phoneNumber);
    customer.setMobileAuth(true);
    String password = (String) userMap.get("password");
    customer.setPassword(password);

    // 原库的customer表
    Map<String, Object> customerMap = DB.findObject(" select * from customer where userId=" + userId);
    // 原库里的customerId
    Integer customerId = (Integer) customerMap.get("id");
    customer.setId(customerId); // 这里是设置的原库的customerId为新的库的用户Id
    // 原库里的渠道号
    String channelString = (String) customerMap.get("userRegisterChannel");
    if (StringUtils.isEmpty(channelString)) {
      channelString = "0";
    }
    int channelId = Integer.parseInt(channelString);
    customer.setChannelId(channelId);
    // 原库里的支付密码
    String payPassword = (String) customerMap.get("payPassword");
    customer.setPayPassword(payPassword);
    if (StringUtils.isEmpty(payPassword)) {
      customer.setHasPayPassword(false);
    } else {
      customer.setHasPayPassword(true);
    }

    // 身份证
    String idCard = (String) customerMap.get("identifierNumber");
    boolean idCardAuth = false;
    if (!StringUtils.isEmpty(idCard)) {
      idCardAuth = true;
    }
    customer.setIdCardAuth(idCardAuth);
    customer.setIdCard(idCard);
    // 真实姓名
    String realName = (String) customerMap.get("realName");
    customer.setRealName(realName);
    // 系统类型
    String osType = (String) customerMap.get("userRegisterSource");
    if (osType == null) {
      osType = "android";
    }
    AppOSType registerOSType = null;
    if (!StringUtils.isEmpty(osType)) {
      if ("android".equals(osType)) {
        registerOSType = AppOSType.OS_ANDROID;
      } else if ("ios".equals(osType)) {
        registerOSType = AppOSType.OS_IOS;
      }
    }
    customer.setRegisterOSType(registerOSType);
    customer.setCustomerSource(CustomerSource.MOBILE_REGISTER);
    // 注册时间
    String createTime = customerMap.get("logtime").toString();
    Date regTime = null;
    if (StringUtils.isEmpty(createTime)) {
      //TODO 需要设置默认时间
    } else {
      regTime = DateTools.stringToDate(createTime, DateTools.DATE_PATTERN_DEFAULT);
    }
    customer.setRegTime(regTime);
    customerDao.save(customer);
    logger.info("customer:" + customer);
    return customerMap;
  }

  private void syncCustomerInfo(Map<String, Object> customerMap, Integer userId) {
    Integer customerId = (Integer) customerMap.get("id");
    String createTime = customerMap.get("logtime").toString();
    Date regTime = null;
    if (StringUtils.isEmpty(createTime)) {
      //TODO 需要设置默认时间
    } else {
      regTime = DateTools.stringToDate(createTime, DateTools.DATE_PATTERN_DEFAULT);
    }
    // 身份证
    String idCard = (String) customerMap.get("identifierNumber");
    // 真实姓名
    String realName = (String) customerMap.get("realName");
    // 地址
    String address = (String) customerMap.get("adderss");
    // 生日
    Date birthDay = null;
    // 性别
    Gender gender = null;
    // 省份
    String province = "";
    // 城市
    String city = "";
    if (!StringUtils.isEmpty(idCard)) {
      IdCardInfo idCardInfo = IdCardUtil.checkIdCard(idCard, realName);
      if (null != idCardInfo) {
        gender = EnumUtil.getEnumByName(idCardInfo.getGener(), Gender.class);
        birthDay = idCardInfo.getDate();
        city = idCardInfo.getCity();
        province = idCardInfo.getProvince();
      }
    }

    //处理 customerInfo
    CustomerInfo customerInfo = new CustomerInfo();
    customerInfo.setCustomerId(customerId); // 和customer用得同一个Id
    customerInfo.setAddress(address);//地址
    customerInfo.setBirthDay(birthDay);//生日
    customerInfo.setCity(city);//城市
    customerInfo.setCustomerId(userId);//用户id
    customerInfo.setGender(gender);//性别
    customerInfo.setIdCardValidateTime(null);//身份证认证时间
    customerInfo.setProvince(province);//省份
    customerInfo.setRealName(realName);//真实姓名
    customerInfoDao.save(customerInfo);
    logger.info("userInfo:" + customerInfo);
  }

  /**
   * @param customerId      绑卡用户id
   * @param customAccountId 绑卡的渠道账户
   * @param memberId        第三方用户Id
   */
  private void syncUserBankCard(Integer userId, Integer customerId, Integer customAccountId, Long memberId) {
    //处理银行卡
    List<Map<String, Object>> userBankList = DB.findList("select * from bankcard where userId =" + userId);
    if (null != userBankList && userBankList.size() != 0) {
      for (Map<String, Object> userBankMap : userBankList) {//银行处理
        String bankBranchName = userBankMap.get("bankBranchName").toString();
        String bankCardCity = userBankMap.get("bankCardCity").toString();
        String bankCardProvince = userBankMap.get("bankCardProvince").toString();
        String bankCode = userBankMap.get("bankCode").toString();
        String reservePhoneNumber = userBankMap.get("reservePhoneNumber").toString();
        String bankCardNumber = userBankMap.get("bankCardNumber").toString();
        String bankName = userBankMap.get("bankName").toString();
        int bankId = Integer.parseInt(userBankMap.get("bankId").toString());
        Date createTime = null;
        try {
          createTime = DateTools.stringToDateTime(userBankMap.get("logtime").toString());
        } catch (Exception e) {
          e.printStackTrace();
        }
        String enableValue = userBankMap.get("enable").toString();
        boolean enable = false;
        UserBankCardBindStatus status = UserBankCardBindStatus.BINDED_FAILED;
        if ("1".equals(enableValue)) {
          enable = true;
          status = UserBankCardBindStatus.BINDED;
        }

        UserBankCard userBankCard = new UserBankCard();
        userBankCard.setBankBranchName(bankBranchName);
        userBankCard.setBankCardCity(bankCardCity);
        userBankCard.setBankCardNumber(bankCardNumber);
        userBankCard.setBankCardProvince(bankCardProvince);
        userBankCard.setBankCode(bankCode);
        userBankCard.setBankId(bankId);
        userBankCard.setBankName(bankName);
        userBankCard.setCreateTime(createTime);
        userBankCard.setCustomerId(customerId);
        userBankCard.setCustomerSubAccountId(customAccountId);
        userBankCard.setEnable(enable);
        userBankCard.setMemberId(memberId + "");
        userBankCard.setReservePhoneNumber(reservePhoneNumber);
        userBankCard.setStatus(status);
        userBankCardDao.save(userBankCard);

        logger.info("用户的银行卡:" + userBankCard);
      }
    }
  }

  private void processScore() {
    List<Map<String, Object>> scoreList = DB.findList("select * from account ");
    if (null != scoreList && scoreList.size() != 0) {
      for (Map<String, Object> map : scoreList) {//获取到用户信息

        Integer customerId = (Integer) map.get("customerId");

        Double availableScore = (Double) map.get("integral");
        int totalGainScore = 0;
        int totalUsedSocre = 0;

        CustomerScore customerScore = new CustomerScore();
        if (null != availableScore) {
          customerScore.setAvailableScore(availableScore.intValue());
        } else {
          customerScore.setAvailableScore(0);
        }
        customerScore.setCustomerId(customerId);
        customerScore.setTotalGainScore(customerScore.getAvailableScore());
        customerScore.setTotalUsedSocre(totalUsedSocre);
        customerScoreDao.save(customerScore);
      }
    }
  }

  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public void sysData() {
    // 处理银行
    processBank();

    // 处理产品
    processProduct();

    syncUser();

    processScore();

    /*
    // 查到所有的用户
    List<Map<String, Object>> list = DB.findList("select * from users");
    if (null != list && list.size() != 0) {

      for (Map<String, Object> userMap : list) {//获取到用户信息
        Integer userId = (Integer) userMap.get("id");
        //根据用户Id 查询用户详细信息
        Map<String, Object> customerMap = DB.findObject(" select * from customer where userId=" + userId);
        //查询
//				customerService.register(mobile, code, password, passwordAgain, recommendCode, channelId, registerOsType, idfa, mac);
        Customer customer = new Customer();
        Integer customerId = (Integer) customerMap.get("id");
        customer.setId(customerId);
        String channelString = (String) customerMap.get("userRegisterChannel");
        if (StringUtils.isEmpty(channelString)) {
          channelString = "0";
        }
        int channelId = Integer.parseInt(channelString);
        String payPassword = (String) customerMap.get("payPassword");
        boolean hasPayPassword = false;
        if (!StringUtils.isEmpty(payPassword)) {
          hasPayPassword = true;
        }

        String idCard = (String) customerMap.get("identifierNumber");
        boolean idCardAuth = false;
        if (!StringUtils.isEmpty(idCard)) {
          idCardAuth = true;
        }

        String mobile = userMap.get("phone_number").toString();
        boolean mobileAuth = false;
        if (!StringUtils.isEmpty(mobile)) {
          mobileAuth = true;
        }

        String password = userMap.get("password").toString();
        String realName = (String) customerMap.get("realName");
        String osType = (String) customerMap.get("userRegisterSource");
        if (osType == null) {
          osType = "android";
        }
        AppOSType registerOSType = null;
        if (!StringUtils.isEmpty(osType)) {
          if ("android".equals(osType)) {
            registerOSType = AppOSType.OS_ANDROID;
          } else if ("ios".equals(osType)) {
            registerOSType = AppOSType.OS_IOS;
          }
        }

        String logTime = customerMap.get("logtime").toString();
        Date regTime = null;
        if (StringUtils.isEmpty(logTime)) {
          //TODO 需要设置默认时间
        } else {
          regTime = DateTools.stringToDate(logTime, DateTools.DATE_PATTERN_DEFAULT);
        }
        CustomerSource customerSource = CustomerSource.MOBILE_REGISTER;
        String username = mobile;
        customer.setChannelId(channelId);//渠道id
        customer.setCustomerSource(customerSource);//是否可用
        customer.setHasPayPassword(hasPayPassword);//支付密码是否设置
        customer.setIdCard(idCard);//身份证号码
        customer.setIdCardAuth(idCardAuth);//身份证号码是否认证
        customer.setMobile(mobile);//手机号码
        customer.setMobileAuth(mobileAuth);//手机号码是否认证
        customer.setPassword(password);//密码
        customer.setPayPassword(payPassword);//支付密码
        customer.setRealName(realName);//真实姓名
        customer.setRegisterOSType(registerOSType);//注册系统类型
        customer.setRegTime(regTime);//注册时间
        customer.setUsername(username);//用户名
        customerDao.save(customer);

        String address = (String) customerMap.get("adderss");
        Date birthDay = null;
        Gender gender = null;
        String province = "";
        String city = "";
        if (!StringUtils.isEmpty(idCard)) {
          IdCardInfo idCardInfo = IdCardUtil.checkIdCard(idCard, realName);
          if (null != idCardInfo) {
            gender = EnumUtil.getEnumByName(idCardInfo.getGener(), Gender.class);
            birthDay = idCardInfo.getDate();
            city = idCardInfo.getCity();
            province = idCardInfo.getProvince();
          }
        }
        //处理 customerInfo
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setCustomerId(customer.getId());
        customerInfo.setAddress(address);//地址
        customerInfo.setBirthDay(birthDay);//生日
        customerInfo.setCity(city);//城市
        customerInfo.setCustomerId(userId);//用户id
        customerInfo.setGender(gender);//性别
        customerInfo.setIdCardValidateTime(null);//身份证认证时间
        customerInfo.setProvince(province);//省份
        customerInfo.setRealName(realName);//真实姓名
        customerInfoDao.save(customerInfo);

        //处理CustomerSubAccount
        Long memberId = (Long) customerMap.get("gongming_userId");
        int customerSubAccountId = 0; //渠道子账户id
        if (null != memberId) {
          CustomerSubAccount customerSubAccount = new CustomerSubAccount();
          customerSubAccount.setCustomerId(customer.getId());//用户id
          customerSubAccount.setMemberId(memberId + "");
          customerSubAccount.setProductChannelId(1);//产品渠道id
          customerSubAccount.setStatus(AccountStatus.USING);//产品渠道状态
          customerSubAccount = customerSubAccountDao.save(customerSubAccount);
          customerSubAccountId = customerSubAccount.getId();

          //TODO 处理用户绑定的银行卡
          //处理银行卡
          List<Map<String, Object>> userBankList = DB.findList("select * from bankcard");
          if (null != userBankList && userBankList.size() != 0) {
            for (Map<String, Object> userBankMap : userBankList) {//银行处理
              Integer Id = (Integer) userBankMap.get("id");
              String bankBranchName = userBankMap.get("bankBranchName").toString();
              String bankCardCity = userBankMap.get("bankCardCity").toString();
              String bankCardProvince = userBankMap.get("bankCardProvince").toString();
              String bankCode = userBankMap.get("bankCode").toString();
              String reservePhoneNumber = userBankMap.get("reservePhoneNumber").toString();
              String bankCardNumber = userBankMap.get("bankCardNumber").toString();
              int bankId = Integer.parseInt(userBankMap.get("bankId").toString());
              Date createTime = null;
              try {
                createTime = DateTools.stringToDateTime(userBankMap.get("logtime").toString());
              } catch (Exception e) {
                e.printStackTrace();
              }
              String enableValue = userBankMap.get("enable").toString();
              boolean enable = false;
              UserBankCardBindStatus status = UserBankCardBindStatus.BINDED_FAILED;
              if ("1".equals(enableValue)) {
                enable = true;
                status = UserBankCardBindStatus.BINDED;
              }

              UserBankCard userBankCard = new UserBankCard();
              userBankCard.setBankBranchName(bankBranchName);
              userBankCard.setBankCardCity(bankCardCity);
              userBankCard.setBankCardNumber(bankCardNumber);
              userBankCard.setBankCardProvince(bankCardProvince);
              userBankCard.setBankCode(bankCode);
              userBankCard.setBankId(bankId);
              userBankCard.setCreateTime(createTime);
              userBankCard.setCustomerId(customer.getId());
              userBankCard.setCustomerSubAccountId(customerSubAccountId);
              userBankCard.setEnable(enable);
              userBankCard.setMemberId(memberId + "");
              userBankCard.setReservePhoneNumber(reservePhoneNumber);
              userBankCard.setStatus(status);

              userBankCardDao.save(userBankCard);
            }
          }

          // 用户购买过的产品的信息，去掉重复的产品
          List<Map<String, Object>> orderProductIdList = DB.findList("select distinct productId from orders where userId=" + customer.getId());
          if (null != orderProductIdList && orderProductIdList.size() != 0) {
            // System.out.println("用户：" + customer.getId() + "的用户，购买了" + orderProductIdList.size() + "个产品");
            for (Map<String, Object> orderProductIdMap : orderProductIdList) {//获取到用户信息
              int orderProductId = Integer.parseInt(orderProductIdMap.get("productId").toString());

              int productAccountId = 0;

              //  根据用户已经购买的产品的个数添加产品账户
              ProductAccount productAccount = new ProductAccount();
              productAccount.setAvailableAmount(0);
              productAccount.setBuyAmount(0);
              productAccount.setCustomerId(customer.getId());
              productAccount.setFreezeAmount(0);
              productAccount.setProductBuyerStatus(ProductBuyerStatus.NORMAL);
              productAccount.setProductId(orderProductId);
              productAccount.setTotalInterest(0);
              productAccount.setWaittingPrincipal(0);
              productAccount.setWithdrawalAmount(0);
              productAccount = productAccountDao.add(productAccount);


            }
          } else {
            // System.out.println("这个用户没有买理财产品");
          }
        }
        //TODO 处理收益记录
        //TODO 处理购买订单通知状态
      }
      // System.out.println("处理数据个数:" + list.size());
    } else {
      // System.out.println("数据为空");
    }


    //TODO 处理用户积分
    List<Map<String, Object>> scoreList = DB.findList("select * from account ");
    if (null != scoreList && scoreList.size() != 0) {
      for (Map<String, Object> map : scoreList) {//获取到用户信息

        Double availableScore = (Double) map.get("ingegral");
        int totalGainScore = 0;//Integer.parseInt(map.get("").toString());
        int totalUsedSocre = 0;//Integer.parseInt(map.get("").toString());
        int customerId = Integer.parseInt(map.get("customerId").toString());

        CustomerScore customerScore = new CustomerScore();
        if (null != availableScore) {
          customerScore.setAvailableScore(availableScore.intValue());
        } else {
          customerScore.setAvailableScore(0);
        }
        customerScore.setCustomerId(customerId);
        customerScore.setTotalGainScore(totalGainScore);
        customerScore.setTotalUsedSocre(totalUsedSocre);

        customerScoreDao.save(customerScore);
      }
    }

    //TODO 处理积分记录

    //TODO 处理积分兑换记录
*/
  }

  /**
   * 同步银行卡数据
   */
  private void processBank() {
    List<Map<String, Object>> bankList = DB.findList("select * from bank");
    if (null != bankList && bankList.size() != 0) {
      for (Map<String, Object> bankMap : bankList) {//银行处理
        String bankCode = bankMap.get("bankCode").toString();
        Double bankDayLimit = Double.parseDouble(bankMap.get("bankDayLimit").toString());
        String bankName = bankMap.get("bankName").toString();
        String bankPicPath = "bank/" + bankCode.toLowerCase() + "_icon.png";
        Double bankTimeLimit = Double.parseDouble(bankMap.get("bankTimeLimit").toString());
        boolean enable = Boolean.parseBoolean(bankMap.get("enable").toString());

        Bank bank = new Bank();
        bank.setBankCode(bankCode);
        bank.setBankDayLimit(bankDayLimit);
        bank.setBankName(bankName);
        bank.setBankPicPath(bankPicPath);
        bank.setBankTimeLimit(bankTimeLimit);
        bank.setEnable(enable);
        bank.setProductChannelId(1);
        bankDao.saveBank(bank);
      }
    }
  }

  /**
   * 同步产品数据列表
   */
  private void processProduct() {
    List<Map<String, Object>> productList = DB.findList("select * from product");
    if (null != productList && productList.size() != 0) {
      for (Map<String, Object> map : productList) {//产品处理
        // System.out.println("共有" + productList.size() + "个产品需要同步");
        int productId = Integer.parseInt(map.get("id").toString());
        syncProduct(map, productId);
      }
    }
  }

  /**
   * 同步产品
   *
   * @param map
   * @param productId
   */
  private void syncProduct(Map<String, Object> map, int productId) {
    // System.out.println("开始处理产品Id:" + productId + "的数据");

    double baseAmount = Double.parseDouble(map.get("baseAmount").toString());
    Date closedTime = null;
    Date createTime = null;
    try {
      // 没有创建时间
      createTime = DateTools.stringToDateTime((String) map.get("xx"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    Integer dummyBoughtCount = Integer.parseInt(map.get("boughtCount").toString());
    boolean enableDummyBoughtAmount = false;
    boolean enableDummyBoughtCount = false;
    if (null != dummyBoughtCount) {
      enableDummyBoughtCount = true;
    }

    // 计息结束时间没有
    Date endInterestBearingDate = null;//DateTools.stringToDate(map.get("").toString(), DateTools.DATE_PATTERN_DAY);
    double maxInterest = Double.parseDouble(map.get("maxInterest").toString());
    double minInterest = Double.parseDouble(map.get("minInterest").toString());
    double interest = 0;
    ProductInterestType productInterestType = ProductInterestType.FLOATING_INCOME;

    if (maxInterest == minInterest) {
      interest = minInterest;
      productInterestType = ProductInterestType.FIXED_INCOME;
    }

    Integer lockDuration = Integer.parseInt(map.get("lockDuration").toString());
    String lockUnitValue = map.get("lockUnit").toString();
    ProductDateUnit lockUnit = null;
    if (!StringUtils.isEmpty(lockUnitValue)) {
      if ("天".equals(lockUnitValue)) {
        lockUnit = ProductDateUnit.DAY;
      }
      if ("月".equals(lockUnitValue) || "个月".equals(lockUnitValue)) {
        lockUnit = ProductDateUnit.MONTHS;
      }

//      if ("周".equals(lockUnitValue)) {
//        lockUnit = ProductDateUnit.WEEK;
//      }

      if ("年".equals(lockUnitValue)) {
        lockUnit = ProductDateUnit.YEAR;
      }
    }

    Integer maturityDuration = Integer.parseInt(map.get("maturityDuration").toString());
    String maturityUnitValue = map.get("maturityUnit").toString();
    ProductDateUnit maturityUnit = null;
    if (!StringUtils.isEmpty(maturityUnitValue)) {
      if ("天".equals(maturityUnitValue)) {
        maturityUnit = ProductDateUnit.DAY;
      }
      if ("月".equals(maturityUnitValue) || "个月".equals(maturityUnitValue)) {
        maturityUnit = ProductDateUnit.MONTHS;
      }

//      if ("周".equals(maturityUnitValue)) {
//        maturityUnit = ProductDateUnit.WEEK;
//      }

      if ("年".equals(maturityUnitValue)) {
        maturityUnit = ProductDateUnit.YEAR;
      }
    }

    double maxAmount = Double.parseDouble(map.get("maxAmount").toString());
    double minAmount = Double.parseDouble(map.get("minAmount").toString());
    double openAmount = Double.parseDouble(map.get("totalAmount").toString());
    double paidAmount = Double.parseDouble(map.get("paidAmount").toString());


    ProductDateType productDateType = ProductDateType.FIXED_TERM;
    ProductIncomeType productIncomeType = ProductIncomeType.PERIOD_REPAYS_CAPTITAL;//TODO 待确认
    String productInvestTypeValue = map.get("type").toString();

    ProductInvestType productInvestType = EnumUtil.getEnumByValue(productInvestTypeValue, ProductInvestType.class);//

    String productName = map.get("name").toString();
    ProductProfitProgressType productProfitProgressType = ProductProfitProgressType.RETURN_BANK;
    ProductRedeemType productRedeemType = ProductRedeemType.EXPIRE_AUTOMATICALLY_REDEMPTION;
    String productSaleTypeValue = map.get("saleType").toString();

    ProductSaleType productSaleType = EnumUtil.getEnumByValue(productSaleTypeValue, ProductSaleType.class);

    ProductType productType = ProductType.FIXED_TERM_PRODUCT;
    boolean recommend = false;
    Double remainingAmount = Double.parseDouble(map.get("readyRemainingAmount").toString());
    Double saleAmount = Double.parseDouble(map.get("paidAmount").toString());
    Date saleEndTime = null;
    Date saleStartTime = null;
    Date startInterestBearingDate = null;//DateTools.stringToDate(map.get("").toString(), DateTools.DATE_PATTERN_DAY);
    String thirdProductId = map.get("planId").toString();
    Double totalAmount = Double.parseDouble(map.get("totalAmount").toString());
    Double waittingPayAmount = 0D;//Double.parseDouble(map.get("").toString());

    String description = map.get("description").toString();

    Product product = new Product();
    product.setBaseAmount(baseAmount);//投资递进基数
    product.setClosedTime(closedTime);//产品完成时间
    product.setCreateTime(createTime);//创建时间
    product.setDummyBoughtAmount(0D);//虚增金额
    product.setDummyBoughtCount(dummyBoughtCount);//虚增人数
    product.setEnableDummyBoughtAmount(enableDummyBoughtAmount);//是否虚增金额
    product.setEnableDummyBoughtCount(enableDummyBoughtCount);//是否虚增人数
    product.setEndInterestBearingDate(endInterestBearingDate);//计息结束时间
    product.setId(productId);
    product.setProductStatus(ProductStatus.AUDIT);
    product.setInterest(interest * 100);//收益率
    product.setLockDuration(lockDuration);//基础锁定期
    product.setLockUnit(lockUnit);//基础锁定期单位
    product.setMaturityDuration(maturityDuration);//计划到期时间
    product.setMaturityUnit(maturityUnit);//计划到期时间单位
    product.setMaxAmount(maxAmount);//最高投资金额
    product.setMinInterest(minInterest * 100);//预期最低利息
    product.setMaxInterest(maxInterest * 100);//预期最高利息
    product.setMinAmount(minAmount);//最低投资金额
    product.setOpenAmount(openAmount);//放款金额
    product.setPaidAmount(paidAmount);//已经支付金额
    product.setPlatSaleTime(null);//平台销售时间
    product.setProductChannelId(1);
    product.setProductDateType(productDateType);//产品期限类型，固定期限、非固定期限
    product.setProductDesc(description);// 计划描述
    product.setProductIncomeType(productIncomeType);//产品收益方式
    product.setProductInterestType(productInterestType);//收益率类型
    product.setProductInvestType(productInvestType);//产品投资品种类型
    product.setProductName(productName);//产品名称
    product.setProductProfitProgressType(productProfitProgressType);//收益处理方式
    product.setProductRedeemType(productRedeemType);//产品赎回方式
    product.setProductSaleType(productSaleType);//产品销售类型
    // product.setProductStatus(productStatus);//产品状态
    product.setProductType(productType);//产品类型
    product.setRecommend(recommend);//是否推荐
    product.setRemainingAmount(remainingAmount);//产品剩余可售金额
    product.setSaleAmount(saleAmount);//平台实际销售金额
    product.setSaleEndTime(saleEndTime);//销售结束时间
    product.setSaleStartTime(saleStartTime);//销售开始时间
    product.setStartInterestBearingDate(startInterestBearingDate);//起息日
    product.setThirdProductId(thirdProductId);//第三方产品id
    product.setTotalAmount(totalAmount);//产品总金额
    product.setWaittingPayAmount(waittingPayAmount);// 等待支付金额
    product = productDao.save(product);

    // System.out.println("存入数据库之后的产品Id：" + product.getId());

    //TODO 合同模板数据,直接是写死的
    String agreementContent = "";
    int agreemenId = productId;
    ProductAgreementTemplate productAgreementTemplate = new ProductAgreementTemplate();
    productAgreementTemplate.setAgreemenId(agreemenId);// 合同id
    productAgreementTemplate.setAgreementContent(agreementContent);//合同雷人
    productAgreementTemplate.setProductId(productId);//产品id
    productAgreementTemplateDao.save(productAgreementTemplate);

    //TODO 产品销售时间，直接写死的
    ProductSaleTimeInfo productSaleTimeInfo = new ProductSaleTimeInfo();
    productSaleTimeInfo.setProductId(productId);
    productSaleTimeInfo.setEndHour(23);
    productSaleTimeInfo.setEndMinute(0);
    productSaleTimeInfo.setStartHour(1);
    productSaleTimeInfo.setStartMinute(0);
    productSaleTimeInfoDao.save(productSaleTimeInfo);

    List<Map<String, Object>> productBankList = DB.findList("select * from productbank where productId=" + productId);
    if (null != productBankList && productBankList.size() != 0) {
      for (Map<String, Object> mapBank : productBankList) {//产品处理

        int bankId = Integer.parseInt(mapBank.get("bankId").toString());

        //TODO 产品银行数据，先添加银行卡信息，
        ProductBank productBank = new ProductBank();
        productBank.setBankEnable(true);
        productBank.setBankId(bankId);
        productBank.setEnable(true);
//						productBank.setId(id);
        productBank.setProductI(productId);
        productBankDao.save(productBank);
      }
    }
  }


  private void ExitingOrderProcess() {
    List<Map<String, Object>> productAccount = DB.findList("select DISTINCT product_buyer_id  from product_sub_account;");


  }

}
