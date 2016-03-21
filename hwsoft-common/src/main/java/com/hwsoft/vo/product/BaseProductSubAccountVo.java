/**
 * 
 */
package com.hwsoft.vo.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


import com.hwsoft.common.product.ProductBuyerRecordStatus;
import com.hwsoft.util.enumtype.EnumUtil;
import com.hwsoft.util.string.StringUtils;

/**
 * @author tzh
 *
 */
public class BaseProductSubAccountVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8654878188262314374L;

	private Integer id;
	/**
	 * 购买金额
	 */
	private BigDecimal amount;
	/**
	 * 购买金额
	 */
	private BigDecimal totalBuyAmount;
	
	/**
	 * 总收益
	 */
	private BigDecimal totalEarnings;
	
	/**
	 * 昨日收益
	 */
	private BigDecimal todayTotalEarnings;
	
	private Integer productId;
	
	private String productName;
	
	/**
	 * 购买银行卡(需要处理)
	 */
	private String bankCardNumer;
	
	/**
	 * 银行ico(小图标)
	 */
	private String bankIco;
	
	private Date buyTime;
	
	
	/**
	 * toString()  
	 */
	private String status;
	/**
	 * name()
	 */
	private String statusValue;
	
	private String errorMsg;
	
	private Date startInterestBearingDate;

	private Date endInterestBearingDate;
	
	private String realName;
	
	private String bankName;
	
	private int shareNum;
	
	private boolean isSuccess;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getTotalBuyAmount() {
		return totalBuyAmount;
	}

	public void setTotalBuyAmount(BigDecimal totalEarnings) {
		this.totalBuyAmount = totalEarnings;
	}

	public BigDecimal getTotalEarnings() {
		return totalEarnings;
	}

	public void setTotalEarnings(BigDecimal totalEarnings) {
		this.totalEarnings = totalEarnings;
	}

	public BigDecimal getTodayTotalEarnings() {
		return todayTotalEarnings;
	}

	public void setTodayTotalEarnings(BigDecimal todayTotalEarnings) {
		this.todayTotalEarnings = todayTotalEarnings;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getBankCardNumer() {
		return bankCardNumer;
	}

	public void setBankCardNumer(String bankCardNumer) {
		this.bankCardNumer = StringUtils.bankCardDeal(bankCardNumer);
	}

	public String getBankIco() {
		return bankIco;
	}

	public void setBankIco(String bankIco) {
		this.bankIco = bankIco;
	}

	public Date getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(Date buyTime) {
		this.buyTime = buyTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
		ProductBuyerRecordStatus productBuyerRecordStatus = EnumUtil.getEnumByName(status, ProductBuyerRecordStatus.class);
		if(null != productBuyerRecordStatus){
			this.statusValue = productBuyerRecordStatus.toString();
		}
	}

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	public String getProductName() {
		return productName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Date getStartInterestBearingDate() {
		return startInterestBearingDate;
	}

	public void setStartInterestBearingDate(Date startInterestBearingDate) {
		this.startInterestBearingDate = startInterestBearingDate;
	}

	public Date getEndInterestBearingDate() {
		return endInterestBearingDate;
	}

	public void setEndInterestBearingDate(Date endInterestBearingDate) {
		this.endInterestBearingDate = endInterestBearingDate;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public int getShareNum() {
		return shareNum;
	}

	public void setShareNum(int shareNum) {
		this.shareNum = shareNum;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	
}
