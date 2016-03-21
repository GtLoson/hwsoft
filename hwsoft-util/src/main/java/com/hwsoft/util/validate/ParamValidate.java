/**
 *
 */
package com.hwsoft.util.validate;

import com.hwsoft.util.exception.ParamException;
import com.hwsoft.util.string.StringUtils;

/**
 * @author tzh
 */
public class ParamValidate {

  public static final String LOGIN_PASSWORD_PATTERN = "^[a-zA-Z0-9]{6,16}$";

  public static final String PAY_PASSWORD_PATTERN = "^[0-9]{6}$";


  /**
   * 密码规则校验
   *
   * @param password
   */
  public static void checkLoginPassword(String password) {
    if (StringUtils.isEmpty(password)) {
      throw new ParamException("登陆密码不能为空");
    }

    if (!password.matches(LOGIN_PASSWORD_PATTERN)) {
      throw new ParamException("登陆密码必须由6至16位数字或字母组成");
    }
  }

  /**
   * 支付密码校验
   *
   * @param payPassword
   */
  public static void checkPayPassword(String payPassword) {
    if (StringUtils.isEmpty(payPassword)) {
      throw new ParamException("支付密码不能为空");
    }

    if (!payPassword.matches(PAY_PASSWORD_PATTERN)) {
      throw new ParamException("支付密码必须由6位数字组成");
    }
  }


  /**
   * 手机规则校验
   *
   * @param password
   */
  public static void checkMobile(String mobile) {
    if (StringUtils.isEmpty(mobile)) {
      throw new ParamException("手机号码不能为空");
    }

    if (!ValidateUtil.isMobile(mobile)) {
      throw new ParamException("手机号码不正确");
    }
  }

  /**
   * 身份证规则校验
   *
   * @param password
   */
  public static void checkIdCard(String idCardNumber) {
    if (StringUtils.isEmpty(idCardNumber)) {
      throw new ParamException("身份证号码不能为空");
    }

    if (!IdentityValidate.isIdentityNumberValid(idCardNumber)) {
      throw new ParamException("身份证号码不正确");
    }
  }

  /**
   * 验证码证规则校验
   *
   * @param password
   */
  public static void checkCode(String mobileCode, Integer length) {
    if (StringUtils.isEmpty(mobileCode)) {
      throw new ParamException("验证码不能为空");
    }
    if (!ValidateUtil.isNum(mobileCode)) {
      throw new ParamException("验证码不正确");
    }

    if (null != length && mobileCode.length() != length) {
      throw new ParamException("验证码不正确");
    }

  }

  /**
   * 真实姓名规则校验
   *
   * @param password
   */
  public static void checkRealName(String realName) {
    if (StringUtils.isEmpty(realName)) {
      throw new ParamException("真实姓名不能为空");
    }
    
    if(!ValidateUtil.checkChinese(realName)){
    	throw new ParamException("真实姓名只能是中文");
    }
  }

  /**
   * id证规则校验
   *
   * @param password
   */
  public static void checkId(Integer id) {
    if (null == id) {
      throw new ParamException("参数不正确");
    }

    if (id <= 0) {
      throw new ParamException("参数不正确");
    }
  }

  /**
   * 银行卡号码规则校验
   *
   * @param password
   */
  public static void checkBankCardNumber(String bankCardNumber) {
    if (StringUtils.isEmpty(bankCardNumber)) {
      throw new ParamException("银行卡号码不能为空");
    }
    if (!ValidateUtil.checkBankCard(bankCardNumber)) {
      throw new ParamException("银行卡号码不正确");
    }
  }


  public static void main(String[] args) {
    checkRealName("唐智");
  }
}
