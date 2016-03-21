package com.hwsoft.vo.product;

import java.util.Date;

public class RepaymentProductVo extends BaseProductVo {
	
	/**
	 * 下次还款时间
	 */
	private Date nextRepayDate;

	public Date getNextRepayDate() {
		return nextRepayDate;
	}

	public void setNextRepayDate(Date nextRepayDate) {
		this.nextRepayDate = nextRepayDate;
	}
	

}
