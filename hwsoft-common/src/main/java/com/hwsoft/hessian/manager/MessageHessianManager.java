/**
 * 
 */
package com.hwsoft.hessian.manager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hwsoft.common.validate.ValidateType;
import com.hwsoft.exception.validate.MobileVlidateException;
import com.hwsoft.hessian.MessageHessianService;
import com.hwsoft.util.string.StringUtils;

/**
 * @author tzh
 *
 */
@Service("messageHessianManager")
public class MessageHessianManager {
	private Log logger = LogFactory.getLog(MessageHessianManager.class);
	
	@Autowired
	private MessageHessianService messageHessianService;
	
	public boolean notifyMobileSender(String content, String receiver,
			int level, ValidateType sendType){
		try {
			if(StringUtils.isEmpty(content)){
				logger.info("短信内容为空");
				throw new MobileVlidateException("短信验证码发送失败");
			}
			
			return messageHessianService.notifyMobileSender(content, receiver, level, sendType.name());
		} catch (Exception e) {
			e.printStackTrace();
			throw new MobileVlidateException("短信验证码发送失败");
		}
	}
	

}
