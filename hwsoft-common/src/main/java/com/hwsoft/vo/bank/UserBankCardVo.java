/**
 * 
 */
package com.hwsoft.vo.bank;

import com.hwsoft.common.bank.UserBankCardBindStatus;
import com.hwsoft.util.enumtype.EnumUtil;
import com.hwsoft.util.string.StringUtils;

/**
 * @author tzh
 *
 */
public class UserBankCardVo {
	
	
	private Integer id;

	private Integer bankId;//银行id 关联银行信息维表

	private String bankCode;// 银行代码 购买的时候传的数据
	
	private String bankName;// 银行名称 购买的时候传的数据
	
	/**
	 * 银行卡号码
	 */
	private String bankCardNumber;// 银行卡号
	
	private String fullBankCardNumber;
	
	
	/**
	 * 开户所在省份
	 */
	private String bankCardProvince;// 银行卡对应的开户行所在省份
	
	/**
	 * 开户所在城市
	 */
	private String bankCardCity;// 银行卡对应的开户行所在城市
	
	/**
	 * 支行名称
	 */
	private String bankBranchName;//  支行名称
	
	/**
	 * 预留手机号码
	 */
	private String reservePhoneNumber;// // 预留手机号

	private boolean enable;// 是否可用

	private Integer customerId;//客户id 关联客户信息表
	
	/**
	 * 渠道资金账户id
	 */
	private Integer customerSubAccountId;
	
	/**
	 * 第三方id
	 */
	private String memberId;
	
	private String createTime;// // 操作时间
	
	private String statusName;
	
	private String statusValue;

	private String bankPicPath;// 银行图片路径
	
	private String bankHDPicPath;// 高清图片

	private Double bankTimeLimit;// 银行单次扣款限额

	private Double bankDayLimit;// 银行单日扣款限额


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCardNumber() {
//		if(null == bankCardNumber) {
//			return null;
//		}else if(bankCardNumber.length() <=4) {
//			return bankCardNumber;
//		}
//		CharSequence lastFourString = bankCardNumber.subSequence(bankCardNumber.length() - 4, bankCardNumber.length());
//		return lastFourString.toString();
		return StringUtils.bankCardDeal(bankCardNumber);
	}

	public void setBankCardNumber(String bankCardNumber)
	{
		this.fullBankCardNumber = bankCardNumber;
		this.bankCardNumber = bankCardNumber;
	}

	public String getBankCardProvince() {
		return bankCardProvince;
	}

	public void setBankCardProvince(String bankCardProvince) {
		this.bankCardProvince = bankCardProvince;
	}

	public String getBankCardCity() {
		return bankCardCity;
	}

	public void setBankCardCity(String bankCardCity) {
		this.bankCardCity = bankCardCity;
	}

	public String getBankBranchName() {
		return bankBranchName;
	}

	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}

	public String getReservePhoneNumber() {
		return reservePhoneNumber;
	}

	public void setReservePhoneNumber(String reservePhoneNumber) {
		this.reservePhoneNumber = reservePhoneNumber;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getCustomerSubAccountId() {
		return customerSubAccountId;
	}

	public void setCustomerSubAccountId(Integer customerSubAccountId) {
		this.customerSubAccountId = customerSubAccountId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
		this.statusValue = EnumUtil.getEnumByName(statusName, UserBankCardBindStatus.class).toString();
	}

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	public String getBankPicPath() {
		return bankPicPath;
	}

	public void setBankPicPath(String bankPicPath) {
		this.bankPicPath = bankPicPath;
	}

	public Double getBankTimeLimit() {
		return bankTimeLimit;
	}

	public void setBankTimeLimit(Double bankTimeLimit) {
		this.bankTimeLimit = bankTimeLimit;
	}

	public Double getBankDayLimit() {
		return bankDayLimit;
	}

	public void setBankDayLimit(Double bankDayLimit) {
		this.bankDayLimit = bankDayLimit;
	}

	public String getBankHDPicPath() {
		return bankHDPicPath;
	}

	public void setBankHDPicPath(String bankHDPicPath) {
		this.bankHDPicPath = bankHDPicPath;
	}

	public String getFullBankCardNumber() {
		return fullBankCardNumber;
	}

	public void setFullBankCardNumber(String fullBankCardNumber) {
		this.fullBankCardNumber = fullBankCardNumber;
	}
}
