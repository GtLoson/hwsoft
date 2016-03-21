/**
 *
 */
package com.hwsoft.wap.controller.login;

import com.hwsoft.common.conf.Conf;
import com.hwsoft.common.validate.ValidateType;
import com.hwsoft.common.version.AppOSType;
import com.hwsoft.exception.user.CustomerException;
import com.hwsoft.exception.validate.MobileVlidateException;
import com.hwsoft.service.customer.CustomerService;
import com.hwsoft.service.validate.MobileValidateService;
import com.hwsoft.spring.MessageSource;
import com.hwsoft.util.exception.ParamException;
import com.hwsoft.util.validate.ParamValidate;
import com.hwsoft.wap.common.response.Response;
import com.hwsoft.wap.common.response.ResponseCode;
import com.hwsoft.wap.controller.BaseController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 注册
 */
@Controller
@Scope(BaseController.REQUEST_SCOPE)
public class RegisterController {
  private final Log logger = LogFactory.getLog(RegisterController.class);
  @Autowired
  private CustomerService customerService;

  @Autowired
  private MessageSource messageSource;

  @Autowired
  private MobileValidateService mobileValidateService;

  @RequestMapping(value = "/test")
  @ResponseBody
  public String test() {
    return "test";
  }

  /**
   * 发送注册验证码
   *
   * @param mobile
   * @return
   */
  @RequestMapping(value = "registerCode")
  @ResponseBody
  public Response registerCode(String mobile) {
    try {
      checkRegisterCode(mobile);
      mobileValidateService.add(mobile, ValidateType.REGISTER);
      return Response.addSuccessResponse(messageSource.getMessage("sms.send.success"), null);
    } catch (CustomerException e) {
      logger.error(e.getMessage());
      return Response.addFailedResponse(ResponseCode.SEND_SMS_CODE_FAILED, e.getMessage());
    } catch (MobileVlidateException e) {
      logger.error(e.getMessage());
      return Response.addFailedResponse(ResponseCode.SEND_SMS_CODE_FAILED, e.getMessage());
    } catch (RuntimeException e) {
      if (null == e.getMessage()) {
        e.printStackTrace();
      } else {
        logger.error(e.getMessage());
      }
      return Response.addFailedResponse(ResponseCode.SEND_SMS_CODE_FAILED);
    } catch (Exception e) {
      logger.error(e.getMessage());
      return Response.addFailedResponse(ResponseCode.SEND_SMS_CODE_FAILED);
    }
  }

  private void checkRegisterCode(String mobile) {
    try {
      ParamValidate.checkMobile(mobile);
    } catch (ParamException e) {
      logger.error(e.getMessage());
      throw new CustomerException(e.getMessage());
    } catch (RuntimeException e) {
      if (null == e.getMessage()) {
        e.printStackTrace();
      } else {
        logger.error(e.getMessage());
      }
      throw new CustomerException(messageSource.getMessage("mobile.is.error"));
    } catch (Exception e) {
      logger.error(e.getMessage());
      throw new CustomerException(messageSource.getMessage("mobile.is.error"));
    }
  }

//	@RequestParam String phoneNumber,
//	@RequestParam String password, @RequestParam String mcode,
//	@RequestParam String channel,@RequestParam String mac,
//	@RequestParam String idfa,@RequestParam String openudid,
//	@RequestParam String idfv,@RequestParam String type

  /**
   * 用户注册（wap版）
   *
   * @param mobile
   * @param code
   * @param password
   * @param confirmPassword
   * @return
   */
  @RequestMapping(value = "registerForWap", method = RequestMethod.POST)
  @ResponseBody
  public Response registerForAndroid(String mobile, String code, String password, String confirmPassword,
                                     Integer channelId, String recommend) {
    mobile = mobileFormate(mobile);
    try {
      // 来源渠道、推荐人、注册系统（ios/android）
      checkRegister(mobile, code, password, confirmPassword);
      customerService.register(mobile, code, password, confirmPassword, "", channelId, AppOSType.OS_WAP, null, null,recommend);
      return Response.addSuccessResponse(messageSource.getMessage("register.success"), null);
    } catch (CustomerException e) {
      logger.error(e.getMessage());
      return Response.addFailedResponse(ResponseCode.REGISTER_FAILED, e.getMessage());
    } catch (RuntimeException e) {
      if (null == e.getMessage()) {
        e.printStackTrace();
      } else {
        logger.error(e.getMessage());
      }
      return Response.addFailedResponse(ResponseCode.REGISTER_FAILED);
    } catch (Exception e) {
      logger.error(e.getMessage());
      return Response.addFailedResponse(ResponseCode.REGISTER_FAILED);
    }
  }

  private String mobileFormate(String origin) {
    return origin.replace(",", "");
  }

  private void checkRegister(String mobile, String code, String password, String passwordAgain) {

    try {
      ParamValidate.checkMobile(mobile);
    } catch (ParamException e) {
      logger.error(e.getMessage());
      throw new CustomerException(e.getMessage());
    } catch (RuntimeException e) {
      if (null == e.getMessage()) {
        e.printStackTrace();
      } else {
        logger.error(e.getMessage());
      }
      throw new CustomerException(messageSource.getMessage("mobile.is.error"));
    } catch (Exception e) {
      logger.error(e.getMessage());
      throw new CustomerException(messageSource.getMessage("mobile.is.error"));
    }

    try {
      ParamValidate.checkCode(code, Conf.MOBILE_CODE_LENGTH);
    } catch (ParamException e) {
      logger.error(e.getMessage());
      throw new CustomerException(e.getMessage());
    } catch (RuntimeException e) {
      if (null == e.getMessage()) {
        e.printStackTrace();
      } else {
        logger.error(e.getMessage());
      }
      throw new CustomerException(messageSource.getMessage("code.is.error"));
    } catch (Exception e) {
      logger.error(e.getMessage());
      throw new CustomerException(messageSource.getMessage("code.is.error"));
    }

    try {
      ParamValidate.checkLoginPassword(password);
      ParamValidate.checkLoginPassword(passwordAgain);
    } catch (ParamException e) {
      logger.error(e.getMessage());
      throw new CustomerException(e.getMessage());
    } catch (RuntimeException e) {
      if (null == e.getMessage()) {
        e.printStackTrace();
      } else {
        logger.error(e.getMessage());
      }
      throw new CustomerException(messageSource.getMessage("password.is.error"));
    } catch (Exception e) {
      logger.error(e.getMessage());
      throw new CustomerException(messageSource.getMessage("password.is.error"));
    }

    if (!password.equals(password)) {
      throw new CustomerException(messageSource.getMessage("password.again.not.equal"));
    }
  }

  /**
   * 显示一个渠道的注册页面
   *
   * @param channelId 渠道
   * @return the view
   */
  @RequestMapping(value = "/register", method = RequestMethod.GET)
  public ModelAndView renderRegisterView(String channelId) {
    ModelAndView modelAndView = new ModelAndView("register");
    modelAndView.addObject("channelId", channelId);
    return modelAndView;
  }
}
