/**
 *
 */
package com.hwsoft.service.customer.impl;

import com.hwsoft.common.conf.Conf;
import com.hwsoft.common.customer.CustomerSource;
import com.hwsoft.common.customer.Gender;
import com.hwsoft.common.product.ProductChannelType;
import com.hwsoft.common.score.ScoreOperType;
import com.hwsoft.common.validate.ValidateType;
import com.hwsoft.common.version.AppOSType;
import com.hwsoft.dao.customer.CustomerDao;
import com.hwsoft.exception.user.CustomerException;
import com.hwsoft.exception.validate.MobileVlidateException;
import com.hwsoft.model.customer.Customer;
import com.hwsoft.model.customer.CustomerInfo;
import com.hwsoft.model.product.ProductChannel;
import com.hwsoft.service.channel.ChannelService;
import com.hwsoft.service.customer.CustomerInfoService;
import com.hwsoft.service.customer.CustomerRecommendService;
import com.hwsoft.service.customer.CustomerService;
import com.hwsoft.service.customer.CustomerSubAccountService;
import com.hwsoft.service.product.ProductChannelService;
import com.hwsoft.service.score.CustomerScoreService;
import com.hwsoft.service.score.ScoreRecordService;
import com.hwsoft.service.statistics.CustomerStatisticsService;
import com.hwsoft.service.validate.MobileValidateService;
import com.hwsoft.spring.MessageSource;
import com.hwsoft.util.enumtype.EnumUtil;
import com.hwsoft.util.idcard.IdCardInfo;
import com.hwsoft.util.idcard.IdCardUtil;
import com.hwsoft.util.password.AESUtil;
import com.hwsoft.util.password.AESUtil.AESFailedException;
import com.hwsoft.util.password.MD5Util;
import com.hwsoft.util.string.StringUtils;
import com.hwsoft.util.validate.ParamValidate;
import com.hwsoft.vo.customer.SystemCustomerVo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author tzh
 */
@Service
public class CustomerServiceImpl implements CustomerService {

  private static Log log = LogFactory.getLog(CustomerServiceImpl.class);

  @Autowired
  private CustomerDao customerDao;

  @Autowired
  private MessageSource messageSource;

  @Autowired
  private MobileValidateService mobileValidateService;

  @Autowired
  private CustomerInfoService customerInfoService;

  @Autowired
  private CustomerScoreService customerScoreService;

  @Autowired
  private ScoreRecordService scoreRecordService;

  @Autowired
  private CustomerRecommendService customerRecommendService;

  @Autowired
  private ChannelService channelService;
  
  @Autowired
  private CustomerSubAccountService customerSubAccountService;
  
  @Autowired
  private ProductChannelService productChannelService;

  /* (non-Javadoc)
   * @see com.hwsoft.service.user.CusomerService#findById(int)
   */
  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public Customer findById(int customerId) {
    return customerDao.findById(customerId);
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public List<Customer> listAll(String mobile, int from, int pageSize) {
    return customerDao.list(mobile, from, pageSize);
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public long getTotalCount(String mobile) {
    return customerDao.getTotalCount(mobile);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public void enableCustomer(Integer customerId) {
    Customer customer = findById(customerId);
    customer.setEnable(true);
    customerDao.updateCustomer(customer);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public void disableCustomer(Integer customerId) {
    Customer customer = findById(customerId);
    customer.setEnable(false);
    customerDao.updateCustomer(customer);
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.customer.CustomerService#findByUsername(java.lang.String)
   */
  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public Customer findByUsername(String username) {
    return customerDao.findByUsername(username);
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.customer.CustomerService#findByMobile(java.lang.String)
   */
  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public Customer findByMobile(String mobile) {
    return customerDao.findByMobile(mobile);
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public List<Customer> findBuyUserSourceType(CustomerSource customerSource){
    return customerDao.findByUserSourceType(customerSource);
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.customer.CustomerService#updateLoginPassword(int, java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public Customer updateLoginPassword(int customerId, String oldPassword,
                                      String newPassword, String newPasswordAgain) {
    Customer customer = findById(customerId);
    if (null == customer) {
      throw new CustomerException(messageSource.getMessage("customer.not.fund"));
    }

    if (StringUtils.isEmpty(oldPassword)) {
      throw new CustomerException(messageSource.getMessage("old.password.is.not.null"));
    }

    if (!customer.getPassword().equals(MD5Util.digest(oldPassword))) {
      throw new CustomerException(messageSource.getMessage("old.password.is.error"));
    }
    if (oldPassword.equals(newPassword)) {
      throw new CustomerException(messageSource.getMessage("old.password.not.equal.new.password"));
    }
    if (StringUtils.isEmpty(newPassword) || StringUtils.isEmpty(newPasswordAgain)
        || !newPassword.equals(newPasswordAgain)) {
      throw new CustomerException(messageSource.getMessage("password.is.not.match"));
    }
    customer.setPassword(MD5Util.digest(newPassword));
    return customerDao.updateCustomer(customer);
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.customer.CustomerService#setPayPassword(int, java.lang.String)
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public Customer setPayPassword(int customerId, String password, String passwordAgain) {
    Customer customer = findById(customerId);
    if (null == customer) {
      throw new CustomerException(messageSource.getMessage("customer.not.fund"));
    }

    if (StringUtils.isEmpty(password) || StringUtils.isEmpty(passwordAgain)
        || !password.equals(passwordAgain)) {
      throw new CustomerException(messageSource.getMessage("password.is.not.match"));
    }
    if (customer.getHasPayPassword()) {
      throw new CustomerException(messageSource.getMessage("pay.password.has.set"));
    }
    if (customer.getPassword().equals(MD5Util.digest(password))) {
      throw new CustomerException(messageSource.getMessage("password.not.equal.pay.password"));
    }
    if (!customer.isIdCardAuth()) {
      throw new CustomerException(messageSource.getMessage("idcard.is.not.binding"));
    }
    customer.setPayPassword(MD5Util.digest(password));
    customer.setHasPayPassword(true);
    return customerDao.updateCustomer(customer);
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.customer.CustomerService#updatePayPassword(int, java.lang.String, java.lang.String)
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public Customer updatePayPassword(int customerId, String oldPassword, String newPassword, String newPasswordAgain) {

    Customer customer = findById(customerId);
    if (null == customer) {
      throw new CustomerException(messageSource.getMessage("customer.not.fund"));
    }
    if (!customer.getHasPayPassword()) {
      throw new CustomerException(messageSource.getMessage("pay.password.is.not.set"));
    }
    if (StringUtils.isEmpty(oldPassword)) {
      throw new CustomerException(messageSource.getMessage("old.password.is.not.null"));
    }

    if (!customer.getPayPassword().equals(MD5Util.digest(oldPassword))) {
      throw new CustomerException(messageSource.getMessage("old.pay.password.is.error"));
    }
    if (oldPassword.equals(newPassword)) {
      throw new CustomerException(messageSource.getMessage("old.password.not.equal.new.password"));
    }
    if (StringUtils.isEmpty(newPassword) || StringUtils.isEmpty(newPasswordAgain)
        || !newPassword.equals(newPasswordAgain)) {
      throw new CustomerException(messageSource.getMessage("password.is.not.match"));
    }
    customer.setPayPassword(MD5Util.digest(newPassword));
    return customerDao.updateCustomer(customer);
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.customer.CustomerService#resetPayPassword(int, java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public Customer resetPayPassword(int customerId, String idCardNumber, String code,
                                   String password, String passwordAgain) {

    Customer customer = findById(customerId);
    if (null == customer) {
      throw new CustomerException(messageSource.getMessage("customer.not.fund"));
    }

    if (!customer.isIdCardAuth() || !customer.getIdCard().equals(idCardNumber)) {
      throw new CustomerException(messageSource.getMessage("id.card.number.is.error"));
    }

    if (StringUtils.isEmpty(password) || StringUtils.isEmpty(passwordAgain)
        || !password.equals(passwordAgain)) {
      throw new CustomerException(messageSource.getMessage("password.is.not.match"));
    }
    if (!customer.getHasPayPassword()) {
      throw new CustomerException(messageSource.getMessage("pay.password.is.not.set"));
    }
    try {
      mobileValidateService.checkMobileCode(customer.getMobile(), code, ValidateType.RESET_PAY_PASSWORD);
    } catch (MobileVlidateException e) {
      throw new CustomerException(e.getMessage());
    }
    customer.setPayPassword(MD5Util.digest(password));
    return customerDao.updateCustomer(customer);
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.customer.CustomerService#resetLoginPassword(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public Customer resetLoginPassword(String mobile,
                                     String code, String password, String passwordAgain) {
    if (StringUtils.isEmpty(mobile)) {
      throw new CustomerException(messageSource.getMessage("mobile.is.not.null"));
    }
    Customer customer = findByMobile(mobile);
    if (null == customer) {
      throw new CustomerException(messageSource.getMessage("customer.not.fund"));
    }
    try {
      mobileValidateService.checkMobileCode(mobile, code, ValidateType.RESET_LOGIN_PASSWORD);
    } catch (MobileVlidateException e) {
      throw new CustomerException(e.getMessage());
    }
    customer.setPassword(MD5Util.digest(password));
    return customerDao.updateCustomer(customer);
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.customer.CustomerService#identityVerification(int, java.lang.String, java.lang.String)
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public Customer identityVerification(int customerId, String idCardNumber,
                                       String realName,boolean isAuth) {
    Customer customer = findById(customerId);
    if (null == customer) {
      throw new CustomerException(messageSource.getMessage("customer.not.fund"));
    }

    if (customer.isIdCardAuth()) {
      throw new CustomerException(messageSource.getMessage("id.card.number.is.auth"));
    }


//    if (!StringUtils.isEmpty(customer.getIdCard()) && !customer.isIdCardAuth()) {
//      throw new CustomerException(messageSource.getMessage("id.card.number.is.auth.once"));
//    }

    try {
      Customer customer2 = findByIdCard(idCardNumber);
      if (null != customer2 && customer2.isIdCardAuth()) {
        throw new CustomerException("该身份证已经被绑定");
      }
    } catch (Exception e) {
      throw new CustomerException("该身份证已经被绑定");
    }

    //TODO 验证身份证是否已经绑定过了

    IdCardInfo idCardInfo = IdCardUtil.checkIdCard(idCardNumber, realName);
    if (!isAuth) {
      customer.setIdCard(idCardNumber);
      customer.setIdCard(realName);
      customer.setIdCardAuth(false); //TODO 如果有身份证号码，但是是否验证未false，则表示已经验证过了
    } else {
      customer.setIdCard(idCardNumber);
      customer.setIdCardAuth(true);
      customer.setRealName(realName);
      CustomerInfo customerInfo = customerInfoService.findByUserId(customer.getId());
      customerInfo.setBirthDay(idCardInfo.getDate());
      customerInfo.setGender(EnumUtil.getEnumByName(idCardInfo.getGener(), Gender.class));
      customerInfo.setRealName(realName);
      customerInfo.setVartar(null);
      customerInfo.setProvince(idCardInfo.getProvince());
      customerInfo.setCity(idCardInfo.getCity());
      customerInfo.setIdCardValidateTime(new Date());
      customerInfoService.update(customerInfo);
    }
        
        /*if(null != customer.getChannelId()){
          Channel channel = channelService.findById(customer.getChannelId());
        	if(null != channel){
        		channel.setCardCount(channel.getCardCount()+1);
        		channelService.update(channel);
        	}
        }
        */
    return customerDao.updateCustomer(customer);
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.customer.CustomerService#register(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public Customer register(String mobile, String code, String password, String passwordAgain,
                           String recommendCode, Integer channelId, AppOSType registerOsType
      , String idfa, String mac, String recommend) {
    // TODO 添加 customer 、customerInfo  、 customerScore 、添加推荐

    //TODO 进行数据校验，暂时不进行校验，有controller进行数据校验
    try {
      mobileValidateService.checkMobileCode(mobile, code, ValidateType.REGISTER);
    } catch (MobileVlidateException e) {
      throw new CustomerException(e.getMessage());
    }

    Customer customerTmp = findByMobile(mobile);
    if (null != customerTmp) {
      throw new CustomerException("该手机号已经注册");
    }

    Customer customer = new Customer();

    customer.setEnable(true);
    customer.setCustomerSource(CustomerSource.MOBILE_REGISTER); // 用户是手机注册用户
//		customer.setCustomerInfo(customerInfo);
    customer.setHasPayPassword(false);
    customer.setIdCardAuth(false);
    customer.setMobile(mobile);
    customer.setMobileAuth(true);
    customer.setPassword(MD5Util.digest(password));
    customer.setRegisterOSType(registerOsType);// 待确认
    customer.setRegTime(new Date());
    customer.setRecommender(recommend);

    //TODO ios和Android 要做不同的处理，如果是ios的需要去查询广告商推送过来的信息
    customer.setChannelId(channelId);
      /*  if(null != channelId){
          Channel channel = channelService.findById(channelId);
        	if(null != channel){
        		channel.setRegisterCount(channel.getRegisterCount()+1);
        		channelService.update(channel);
        	}
        }*/

    customerDao.save(customer);

    //用户基本信息
    CustomerInfo customerInfo = new CustomerInfo();
    customerInfo.setCustomerId(customer.getId());

    customerInfoService.add(customerInfo);
    //积分账户
    customerScoreService.addCustomerScore(customer.getId());
    // 送积分
    try {
      scoreRecordService.addScoreRecord(customer.getId(), ScoreOperType.REG_SEND.score(), ScoreOperType.REG_SEND, Conf.SCORE_OPERATOR_SYSTEM);
    } catch (Exception e1) {
      e1.printStackTrace();
      log.error("注册赠送积分失败，id=" + customer.getId());
    }

    // 添加推荐
    if (!StringUtils.isEmpty(recommendCode)) {
      try {
        String recommendIdStr = AESUtil.decrypt(recommendCode);
        Integer recommendCustomerId = Integer.parseInt(recommendIdStr);
        customerRecommendService.add(recommendCustomerId, customer.getId());
        //TODO 推荐注册送积分，还未处理
      } catch (AESFailedException e) {
        e.printStackTrace();
        log.error("推荐人id解析失败，code=" + recommendCode + ";id=" + customer.getId());
      }
    }
    
    // 默认添加钱妈妈渠道账户
    ProductChannel productChannel = productChannelService.findByType(ProductChannelType.hwsoft);
    
    customerSubAccountService.addCustomerSubAccount(customer.getId(), ProductChannelType.hwsoft.name()+""+customer.getId(), productChannel.getId());

    return customer;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public Customer updateUserChannelInfo(String mobile, Integer channelId) {
    Customer customer = customerDao.findByMobile(mobile);
    if (null != customer) {
      customer.setChannelId(channelId);
      customerDao.updateCustomer(customer);
    } else {
      log.error("手机号为：" + mobile + "在更新苹果注册渠道的时候，没有找到注册用户");
      return null;
    }
    return null;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public Customer backgroundAddCustomer(String mobileNumber,
                                        String password,
                                        String rePassword,
                                        boolean enable,
                                        String osTypeString) {
    ParamValidate.checkMobile(mobileNumber);
    checkCustomerParameter(password, "密码不能为空");
    checkCustomerParameter(rePassword, "确认密码不能为空");
    if (!password.equals(rePassword)) {
      throw new CustomerException("两次密码不一致");
    }
    checkCustomerParameter(osTypeString, "系统类型字符串不能为空");
    AppOSType appOSType = null;
    try {
      appOSType = AppOSType.valueOf(osTypeString);
    } catch (Exception e) {
      e.printStackTrace();
      log.error("系统类型枚举转换异常", e);
      throw new CustomerException("系统类型枚举转换异常");
    }
    Customer customer = new Customer();
    customer.setMobile(mobileNumber);
    customer.setCustomerSource(CustomerSource.BACKGROUND_ADD);
    customer.setEnable(enable);
    customer.setMobileAuth(true);
    customer.setIdCardAuth(false);
    customer.setPassword(MD5Util.digest(password));
    customer.setRegisterOSType(appOSType);
    customer.setRegTime(new Date());
    customer = customerDao.save(customer);

    // 
    ProductChannel productChannel = productChannelService.findByType(ProductChannelType.hwsoft);
    customerSubAccountService.addCustomerSubAccount(customer.getId(), customer.getId() + "",productChannel.getId());
    // TODO 系统添加用户的渠道号
    return customer;
  }

  private void checkCustomerParameter(String parameter, String errorMsg) {
    if (StringUtils.isEmpty(parameter)) {
      throw new CustomerException(errorMsg);
    }
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.customer.CustomerService#findByIdCard(java.lang.String)
   */
  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public Customer findByIdCard(String idCard) {
    return customerDao.findByIdCard(idCard);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public Customer identityVerificationBack(int customerId,
                                           String idCardNumber, String realName) {
    Customer customer = findById(customerId);
    if (null == customer) {
      throw new CustomerException(messageSource.getMessage("customer.not.fund"));
    }

    /*
    if (customer.isIdCardAuth()) {
      throw new CustomerException(messageSource.getMessage("id.card.number.is.auth"));
    }

    if (!StringUtils.isEmpty(customer.getIdCard()) && customer.isIdCardAuth()) {
      throw new CustomerException(messageSource.getMessage("id.card.number.is.auth.once"));
    }
*/
    try {
      Customer customer2 = findByIdCard(idCardNumber);
      if (null != customer2 && customer2.isIdCardAuth()) {
        throw new CustomerException("该身份证已经被绑定");
      }
    } catch (Exception e) {
      throw new CustomerException("该身份证已经被绑定");
    }

    //TODO 验证身份证是否已经绑定过了
    IdCardInfo idCardInfo = IdCardUtil.checkIdCard(idCardNumber, realName);
    if (null == idCardInfo) {
      throw new CustomerException("实名认证失败");
    } else {
      customer.setIdCard(idCardNumber);
      customer.setIdCardAuth(true);
      customer.setRealName(realName);
      CustomerInfo customerInfo = customerInfoService.findByUserId(customer.getId());
      customerInfo.setBirthDay(idCardInfo.getDate());
      customerInfo.setGender(EnumUtil.getEnumByName(idCardInfo.getGener(), Gender.class));
      customerInfo.setRealName(realName);
      customerInfo.setVartar(idCardInfo.getAvatar());
      customerInfo.setProvince(idCardInfo.getProvince());
      customerInfo.setCity(idCardInfo.getCity());
      customerInfo.setIdCardValidateTime(new Date());
      customerInfoService.update(customerInfo);
    }
    return customerDao.updateCustomer(customer);
  }
  
  @Autowired
  private CustomerStatisticsService customerStatisticsService;

  	@Override
  	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
  	public List<SystemCustomerVo> listAllSystemCustomer() {
  		
  		List<Customer> customers = customerDao.findByCustomerSource(CustomerSource.SYSTEM_CUSTOMER);
  		if(null == customers || customers.size() == 0 ){
  			return null;
  		}
  		
  		List<SystemCustomerVo> systemCustomerVos = new ArrayList<SystemCustomerVo>();
  		
  		for(Customer customer : customers){
  			SystemCustomerVo systemCustomerVo = new SystemCustomerVo();
  			systemCustomerVo.setCustomerSource(customer.getCustomerSource().name());
  			systemCustomerVo.setCustomerSourceValue(customer.getCustomerSource().toString());
  			systemCustomerVo.setRealName(customer.getRealName());
  			systemCustomerVo.setId(customer.getId());
  			systemCustomerVo.setTotalAvailable(customerStatisticsService.totalAvailable(customer.getId(), null));
  			systemCustomerVo.setTotalFreezen(customerStatisticsService.totalFreeze(customer.getId()));
  			systemCustomerVos.add(systemCustomerVo);
  		}
  		
  		
		return systemCustomerVos;
	}
}
