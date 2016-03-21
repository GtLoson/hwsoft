/**
 *
 */
package com.hwsoft.service.customer;

import com.hwsoft.common.customer.CustomerSource;
import com.hwsoft.common.version.AppOSType;
import com.hwsoft.model.customer.Customer;
import com.hwsoft.vo.customer.SystemCustomerVo;

import java.util.List;

/**
 * @author tzh
 */
public interface CustomerService {

	public Customer findById(int customerId);

	public List<Customer> listAll(String mobile,int from, int pageSize);

	public long getTotalCount(String mobile);

	public void enableCustomer(Integer customerId);

	public void disableCustomer(Integer customerId);

	public Customer findByUsername(String username);

	public Customer findByMobile(String mobile);
	
	public List<Customer> findBuyUserSourceType(CustomerSource customerSource);

	/**
	 * 后台添加手机用户
	 * 
	 * @param mobileNumber
	 * @param password
	 * @param rePassword
	 * @param enable
	 * @param osTypeString
	 * @return
	 */
	public Customer backgroundAddCustomer(String mobileNumber, String password,
			String rePassword, boolean enable, String osTypeString);

	/**
	 * 更新登录密码
	 * 
	 * @param customerId
	 * @param oldPassword
	 * @param newPassword
	 * @param newPasswordAgain
	 * @return
	 */
	public Customer updateLoginPassword(int customerId, String oldPassword,
			String newPassword, String newPasswordAgain);

	/**
	 * 设置支付密码
	 * 
	 * @param customerId
	 * @param password
	 * @return
	 */
	public Customer setPayPassword(int customerId, String password,
			String passwordAgain);

	/**
	 * 更新支付密码
	 * 
	 * @param customerId
	 * @param payPassword
	 * @return
	 */
	public Customer updatePayPassword(int customerId, String oldPassword,
			String newPassword, String newPasswordAgain);

	/**
	 * 重置支付密码
	 * 
	 * @param customerId
	 * @param idCardNumber
	 * @param password
	 * @param passwordAgain
	 * @return
	 */
	public Customer resetPayPassword(int customerId, String idCardNumber,
			String code, String password, String passwordAgain);

	/**
	 * 重置登录密码
	 * 
	 * @param mobile
	 * @param code
	 * @param password
	 * @param passwordAgain
	 * @return
	 */
	public Customer resetLoginPassword(String mobile, String code,
			String password, String passwordAgain);

	/**
	 * 实名认证接口
	 * 
	 * @param customerId
	 * @param idCardNumber
	 * @param realName
	 * @return
	 */
	public Customer identityVerification(int customerId, String idCardNumber,
			String realName,boolean isAuth);

	/**
	 * 用户注册
	 * 
	 * @param mobile
	 * @param code
	 * @param password
	 * @param passwordAgain
	 * @param recommendCode
	 *            推荐code
	 * @param channelId
	 *            渠道id
	 * @return
	 */
	public Customer register(String mobile, String code, String password,
			String passwordAgain, String recommendCode, Integer channelId,
			AppOSType registerOsType,String idfa,String mac,String recommend);
	
	public Customer updateUserChannelInfo(String mobile,Integer channelId);
	
	public Customer findByIdCard(String idCard);
	
	
	/**
	 * 后台实名认证接口
	 * 
	 * @param customerId
	 * @param idCardNumber
	 * @param realName
	 * @return
	 */
	public Customer identityVerificationBack(int customerId, String idCardNumber,
			String realName);
	
	
	/**
	 * 查询系统用户
	 * @return
	 */
	public List<SystemCustomerVo> listAllSystemCustomer();
}
