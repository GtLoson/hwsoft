/**
 * 
 */
package com.hwsoft.vo.customer;

import java.io.Serializable;
import java.math.BigDecimal;

import com.hwsoft.util.string.StringUtils;

/**
 * @author tzh
 *
 */
public class CustomerAccountVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5104467940120177876L;
	//今日收益、总资产、累计收益、产品子账户、真实姓名、手机号码、身份证号（身份证号码处理）、我的钱豆。
	
	/**
	 * 今日总收益
	 */
	private BigDecimal todayTotalEarnings;
	
	/**
	 * 总资产
	 */
	private BigDecimal totalAssets;

	/**
	 * 累计总收益
	 */
	private BigDecimal totalEarnings;
	
	/**
	 * 可用余额
	 */
	private double totalAvailable;
	
	/**
	 * 总冻结金额
	 */
	private double totalFreezen;
	
	/**
	 * 真实姓名
	 */
	private String realName;
	
	/**
	 * 手机号码
	 */
	private String mobile;
	
	/**
	 * 身份证号码（需要处理）
	 */
	private String idCardNumber;
	
	/**
	 * 可用积分
	 */
	private Integer availableScore;

	/**
	 * 是否进行了实名认证
	 */
	private boolean isRealNameAuthed;

	/**
	 * 是否有支付密码
	 */
	private boolean hasPayPwd;

	public BigDecimal getTodayTotalEarnings() {
		return todayTotalEarnings;
	}

	public void setTodayTotalEarnings(double todayTotalEarnings) {
		this.todayTotalEarnings = new BigDecimal(todayTotalEarnings);
	}

	public BigDecimal getTotalAssets() {
		return totalAssets;
	}

	public void setTotalAssets(double totalAssets) {
		this.totalAssets = new BigDecimal(totalAssets);
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdCardNumber() {
		return idCardNumber;
	}

	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = StringUtils.idCardDeal(idCardNumber);
	}

	public Integer getAvailableScore() {
		return availableScore;
	}

	public void setAvailableScore(Integer availableScore) {
		this.availableScore = availableScore;
	}

	public boolean isRealNameAuthed() {
		return isRealNameAuthed;
	}

	public void setRealNameAuthed(boolean isRealNameAuthed) {
		this.isRealNameAuthed = isRealNameAuthed;
	}

	public boolean isHasPayPwd() {
		return hasPayPwd;
	}

	public void setHasPayPwd(boolean hasPayPwd) {
		this.hasPayPwd = hasPayPwd;
	}

	public BigDecimal getTotalEarnings() {
		return totalEarnings;
	}

	public void setTotalEarnings(double totalEarnings) {
		this.totalEarnings = new BigDecimal(totalEarnings);
	}

	public double getTotalAvailable() {
		return totalAvailable;
	}

	public void setTotalAvailable(double totalAvailable) {
		this.totalAvailable = totalAvailable;
	}

	public double getTotalFreezen() {
		return totalFreezen;
	}

	public void setTotalFreezen(double totalFreezen) {
		this.totalFreezen = totalFreezen;
	}
	
}
