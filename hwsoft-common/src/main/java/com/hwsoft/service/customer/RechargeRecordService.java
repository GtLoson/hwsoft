package com.hwsoft.service.customer;

import java.util.Date;
import java.util.List;

import com.hwsoft.common.customer.RechargeRecordStatus;
import com.hwsoft.model.customer.RechargeRecord;
import com.hwsoft.vo.customer.RechargeRecordVo;

public interface RechargeRecordService {

	public RechargeRecord add(int productChannelId,int userBankCardId,String bankCardNumber,
			String bankCode,double amount,int customerId,int customerSubAccountId);

	public RechargeRecord updateSuccess(int id,double successAmount,double fee,String orderFormId, String payOrderFormId,String code,
			String msg, String response,String bankCardAgreementNumber);
	
	public RechargeRecord updateFailed(int id,String reason,String orderFormId, String payOrderFormId, String code,
			String msg, String response);
	
	public RechargeRecord updateProgress(int id,String orderFormId, String payOrderFormId, String code,
			String msg, String response);
	
	public RechargeRecord updateWaitting(int id,String orderFormId, String payOrderFormId, String code,
			String msg, String response);
	
	public RechargeRecord updateRefund(int id,String orderFormId, String payOrderFormId, String code,
			String msg, String response);
	
//	public RechargeRecord findByOrderFormId(String orderFormId);
	public RechargeRecord findById(int id);
	
	public List<Integer> findAllProgress();
	
	public List<RechargeRecordVo> list(int customerId,Integer productChannelId,int from,int pageSize);
	
	public long listTotalCount(int customerId,Integer productChannelId);
	
	/**
	 * 
	 * @param status
	 * @param startTime
	 * @param endTime
	 * @param mobile
	 * @param from
	 * @param pageSize
	 * @return
	 */
	public List<RechargeRecordVo> listAll(RechargeRecordStatus status,Date startTime,Date endTime,String mobile,int from,int pageSize);
	
	
	/**
	 * 
	 * @param status
	 * @param startTime
	 * @param endTime
	 * @param mobile
	 * @return
	 */
	public long listAllTotalCount(RechargeRecordStatus status,Date startTime,Date endTime,String mobile);
	
	
	
	
}
