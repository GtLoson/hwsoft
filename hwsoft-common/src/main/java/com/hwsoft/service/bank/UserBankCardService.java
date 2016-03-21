/**
 * 
 */
package com.hwsoft.service.bank;

import java.util.List;

import com.hwsoft.model.bank.UserBankCard;
import com.hwsoft.vo.bank.UserBankCardVo;

/**
 * @author tzh
 *
 */
public interface UserBankCardService {

	public UserBankCard findUserBankCardById(int id);
	
	public UserBankCard bindBankCardSuccess(int id,String agreementNumber);
	
	public UserBankCard bindBankCardFailed(int id);
	
	public List<UserBankCardVo> findUserBankCardVosByUserIdAndProductId(int userId,int productId);
	
	
	public UserBankCard addUserBankCard(int customerId,int bankId,String cardNumber,String province,String city,
			String branch,String mobile,int productChannelId);
	
	public List<UserBankCard> findUserBankCardByBankCardNumber(String bankCardNumber);

	/**
	 *  给钱妈妈渠道产品，连连支付，添加银行卡
	 *  *
	 * @param customerId 用户Id
	 * @param bankId 银行Id
	 * @param cardNumber 卡号
	 * @param provinceCode 省份code
	 * @param cityCode 城市code
	 * @param branchName 支行名称
	 * @param prctcd 大额行号
	 * @param productChannelId 渠道Id
	 * @return
	 */
	public UserBankCard addUserBankCardForSpecialChannel(int customerId,
																			int bankId,
																			String cardNumber,
																			String provinceCode,
																			String cityCode,
																			String branchName,
																			String prctcd,
																			Integer productChannelId);
	
	/**
	 * 根据渠道子账户查询可用银行卡列表
	 * @param customerSubAccountId
	 * @return
	 */
	public List<UserBankCard> findAllEnableBySubAccountId(int customerSubAccountId);
	
	/**
	 * 根据渠道子账户查询可用银行卡列表
	 * @param customerSubAccountId
	 * @return
	 */
	public List<UserBankCardVo> findByChannelId(int productChannelId,int customerId);
}
