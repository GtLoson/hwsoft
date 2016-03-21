package com.hwsoft.vo.product;

import java.util.Date;

/**
 * 订单详情
 */
public class ProductSubAccountDetail {

  public String productName;

  public String bankName;

  public String cardNumber;

  public Double amount;

  public Double successAmount;

  public Date buyTime;

  public String realName;

  public String mobile;

  public Integer channelId;

  public String channelName;

  public Double totalInterest;

  public Double dayInterest;

  public Date startDate;

  public Date endDate;

  public String status;

  public String response;

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public String getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public Double getSuccessAmount() {
    return successAmount;
  }

  public void setSuccessAmount(Double successAmount) {
    this.successAmount = successAmount;
  }

  public Date getBuyTime() {
    return buyTime;
  }

  public void setBuyTime(Date buyTime) {
    this.buyTime = buyTime;
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

  public Integer getChannelId() {
    return channelId;
  }

  public void setChannelId(Integer channelId) {
    this.channelId = channelId;
  }

  public String getChannelName() {
    return channelName;
  }

  public void setChannelName(String channelName) {
    this.channelName = channelName;
  }

  public Double getTotalInterest() {
    return totalInterest;
  }

  public void setTotalInterest(Double totalInterest) {
    this.totalInterest = totalInterest;
  }

  public Double getDayInterest() {
    return dayInterest;
  }

  public void setDayInterest(Double dayInterest) {
    this.dayInterest = dayInterest;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getResponse() {
    return response;
  }

  public void setResponse(String response) {
    this.response = response;
  }
}
