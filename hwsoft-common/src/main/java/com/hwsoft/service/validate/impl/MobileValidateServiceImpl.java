/**
 *
 */
package com.hwsoft.service.validate.impl;

import com.hwsoft.common.conf.Conf;
import com.hwsoft.common.systemsetting.SystemSettingType;
import com.hwsoft.common.validate.ValidateType;
import com.hwsoft.dao.validate.MobileValidateDao;
import com.hwsoft.exception.validate.MobileVlidateException;
import com.hwsoft.hessian.manager.MessageHessianManager;
import com.hwsoft.model.customer.Customer;
import com.hwsoft.model.systemsetting.SystemSetting;
import com.hwsoft.model.validate.MobileValidate;
import com.hwsoft.service.customer.CustomerService;
import com.hwsoft.service.systemsetting.SystemSettingService;
import com.hwsoft.service.validate.MobileValidateService;
import com.hwsoft.spring.MessageSource;
import com.hwsoft.util.random.GenerateUtil;
import com.hwsoft.util.string.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author tzh
 */
@Service("mobileValidateService")
public class MobileValidateServiceImpl implements MobileValidateService {

  @Autowired
  private MessageSource messageSource;

  @Autowired
  private MobileValidateDao mobileValidateDao;

  @Autowired
  private SystemSettingService systemSettingService;

  @Autowired
  private CustomerService customerService;

  @Autowired
  private MessageHessianManager messageHessianManager;


  /* (non-Javadoc)
   * @see com.hwsoft.service.validate.MobileValidateService#add(java.lang.String, com.hwsoft.common.validate.ValidateType)
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public MobileValidate add(String mobile, ValidateType validateType) {
    if (StringUtils.isEmpty(mobile)) {
      throw new MobileVlidateException(messageSource.getMessage("mobile.is.not.null"));
    }
    if (null == validateType) {
      throw new MobileVlidateException(messageSource.getMessage("mobile.code.type.is.not.null"));
    }

    if (ValidateType.REGISTER.equals(validateType)) {// 如果是注册，则需要判断手机号码是否已经存在
      Customer customer = customerService.findByMobile(mobile);
      if (null != customer) {
        throw new MobileVlidateException(messageSource.getMessage("mobile.has.registered"));
      }
    }

    if (ValidateType.RESET_LOGIN_PASSWORD.equals(validateType)
        || ValidateType.RESET_PAY_PASSWORD.equals(validateType)) {// 如果是找回密码或者找回登录密码，那么需要判断手机是否已经注册
      Customer customer = customerService.findByMobile(mobile);
      if (null == customer) {
        throw new MobileVlidateException(messageSource.getMessage("mobile.has.not.registered"));
      }
    }

    MobileValidate mobileValidate = findMobileValidateByMobile(mobile);
    if (null != mobileValidate) {
      long currentTime = System.currentTimeMillis();
      long sendTime = mobileValidate.getSendTime().getTime();
      SystemSetting setting = systemSettingService.getByType(SystemSettingType.MOBILE_CODE_SEND_INTERVAL);
      if ((currentTime - sendTime) < setting.getIntValue() * 1000) {
        throw new MobileVlidateException(messageSource.getMessage("mobile.code.send.too.fast"));
      }
    }
    Date now = new Date();
    String code = GenerateUtil.generateNumber(Conf.MOBILE_CODE_LENGTH);
    String content = content(code, validateType);
    messageHessianManager.notifyMobileSender(content, mobile, validateType.level(), validateType);

    if (null == mobileValidate) { //执行添加
      mobileValidate = new MobileValidate();
      mobileValidate.setMobile(mobile);
      mobileValidate.setSendTime(now);
      mobileValidate.setValidateType(validateType);
      mobileValidate.setCode(code);
      return mobileValidateDao.save(mobileValidate);
    } else {
      mobileValidate.setSendTime(now);
      mobileValidate.setValidateType(validateType);
      mobileValidate.setCode(code);
      return mobileValidateDao.update(mobileValidate);
    }
  }

  private String content(String code, ValidateType validateType) {

    SystemSetting setting = systemSettingService.getByType(SystemSettingType.MOBILE_CODE_TIME_OUT_SECONED);

    if (validateType.equals(ValidateType.REGISTER)) {//注册
      return "欢迎加入【钱妈妈】，您申请绑定手机的验证码：" + code + "（" + (setting.getIntValue() / 60) + "分钟内有效）。400-021-0951【钱妈妈】";
    } else if (validateType.equals(ValidateType.RESET_LOGIN_PASSWORD)) {
      return "尊敬的会员，您已申请找回密码，验证码：" + code + "（" + (setting.getIntValue() / 60) + "分钟内有效）。如非本人操作，请致电400-021-0951。【钱妈妈】";
    } else if (validateType.equals(ValidateType.RESET_PAY_PASSWORD)) {
      return "尊敬的会员，您已申请重置支付密码，验证码：" + code + "（" + (setting.getIntValue() / 60) + "分钟内有效）。如非本人操作，请致电400-021-0951。【钱妈妈】";
    } else if (validateType.equals(ValidateType.OTHER)) {
      return "尊敬的用户，您的激活码：" + code + "，请填入完成注册。该激活码30分钟内有效。泰多米携手PICC共同保障资金安全。【泰多米】";
    }
    return null;
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.validate.MobileValidateService#findMobileValidateByMobile(java.lang.String)
   */
  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public MobileValidate findMobileValidateByMobile(String mobile) {
    return mobileValidateDao.findByMobile(mobile);
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.validate.MobileValidateService#checkMobileCode(java.lang.String, java.lang.String, com.hwsoft.common.validate.ValidateType)
   */
  @Override
  public boolean checkMobileCode(String mobile, String code,
                                 ValidateType validateType) {

    if (StringUtils.isEmpty(mobile)) {
      throw new MobileVlidateException(messageSource.getMessage("mobile.code.is.error"));
    }
    if (StringUtils.isEmpty(code)) {
      throw new MobileVlidateException(messageSource.getMessage("mobile.code.is.error"));
    }

    MobileValidate mobileValidate = findMobileValidateByMobile(mobile);
    if (null == mobileValidate) {
      throw new MobileVlidateException(messageSource.getMessage("mobile.code.is.error"));
    }

    long currentTime = System.currentTimeMillis();
    long sendTime = mobileValidate.getSendTime().getTime();
    SystemSetting setting = systemSettingService.getByType(SystemSettingType.MOBILE_CODE_TIME_OUT_SECONED);
    if ((currentTime - sendTime) > setting.getIntValue() * 1000) {
      throw new MobileVlidateException(messageSource.getMessage("mobile.code.is.time.out"));
    }

    if (!mobileValidate.getCode().equals(code)) {
      throw new MobileVlidateException(messageSource.getMessage("mobile.code.is.error"));
    }
    if (!mobileValidate.getValidateType().equals(validateType)) {
      throw new MobileVlidateException(messageSource.getMessage("mobile.code.is.error"));
    }

    return true;
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.validate.MobileValidateService#update(com.hwsoft.model.validate.MobileValidate)
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public MobileValidate update(MobileValidate mobileValidate) {
    return mobileValidateDao.update(mobileValidate);
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public long getCount(String mobile, Date start, Date end) {
    return mobileValidateDao.getCount(mobile, start, end);
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public List<MobileValidate> listAll(int from, int pageSize, String mobile, Date start, Date end) {
    return mobileValidateDao.listAllOfMobile(mobile, pageSize, from, start, end);
  }
}
