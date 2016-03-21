package com.hwsoft.service.customer;

import java.util.Date;
import java.util.List;

import com.hwsoft.common.customer.WithdrawalStatus;
import com.hwsoft.common.customer.WithdrawalType;
import com.hwsoft.model.customer.WithdrawalRecord;
import com.hwsoft.vo.customer.WithdrawalRecordVo;

public interface WithdrawalRecordService {

	public WithdrawalRecord add(double amount,int customerSubAccountId,WithdrawalType type,int userBankCardId);

	public List<WithdrawalRecordVo> list(WithdrawalType type,WithdrawalStatus status, String mobile, String timeCollumn, Date startDate,Date endDate,int from,int pageSize);
	
	public long getTotalCount(WithdrawalType type,WithdrawalStatus status, String mobile, String timeCollumn, Date startDate,Date endDate);
	
	public WithdrawalRecord updateAudit(int id , String notes, boolean flag,int staffId,String staffName);
	
	
	public WithdrawalRecord findById(int id);
	
	public WithdrawalRecord updateWithdrawal(int id , String notes, int staffId,String staffName);
	
	public WithdrawalRecord updateBankDeal(int id  ,String orderFormId,String payOrderFormId);
	
	public WithdrawalRecord updateWithdrawalSuccess(int id ,String orderFormId,String payOrderFormId);
	
	public WithdrawalRecord updateWithdrawalFailed(int id ,String orderFormId,String payOrderFormId);
	
	public WithdrawalRecord updateWithdrawalCancel(int id ,String orderFormId,String payOrderFormId);
	
//	public WithdrawalRecord updateWithdrawalRequestFailed(int id );
	
	public List<Integer> listAllPayProgress();
	
	public WithdrawalRecord addAgain(int withdrawalRecordId, String notes, int staffId,String staffName);
	
	/**
	 * 复核失败
	 * @param id
	 * @param notes
	 * @param flag
	 * @param staffId
	 * @param staffName
	 * @return
	 */
	public WithdrawalRecord updateAuditAgainFailed(int id , String notes,int staffId,String staffName);
	
	/**
	 * 手机端查询提现记录
	 * @param customerSubAccountId
	 * @param from
	 * @param pageSize
	 * @return
	 */
	public List<WithdrawalRecordVo> list(int customerId, Integer productChannelId, int from, int pageSize);
	
	/**
	 * 手机端查询提现记录总数
	 * @param customerSubAccountId
	 * @param from
	 * @param pageSize
	 * @return
	 */
	public long listTotalCount(int customerId, Integer productChannelId);
	
	
}
