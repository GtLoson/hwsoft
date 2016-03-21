package com.hwsoft.service.customer.impl;

import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.common.conf.Conf;
import com.hwsoft.common.customer.RechargeRecordStatus;
import com.hwsoft.common.point.CustomerSubAccountPointType;
import com.hwsoft.dao.customer.RechargeRecordDao;
import com.hwsoft.exception.user.RechargeRecordException;
import com.hwsoft.model.customer.Customer;
import com.hwsoft.model.customer.CustomerSubAccount;
import com.hwsoft.model.customer.RechargeRecord;
import com.hwsoft.model.point.CustomerSubAccountPoint;
import com.hwsoft.service.bank.UserBankCardService;
import com.hwsoft.service.customer.CustomerService;
import com.hwsoft.service.customer.CustomerSubAccountService;
import com.hwsoft.service.customer.RechargeRecordService;
import com.hwsoft.service.log.customer.CustomerSubAccountPointLogService;
import com.hwsoft.service.order.hwsoft.RechargeOrderService;
import com.hwsoft.service.point.CustomerSubAccountPointService;
import com.hwsoft.util.math.CalculateUtil;
import com.hwsoft.vo.customer.RechargeRecordVo;

/**
 * 
 * @author tzh
 *
 */
@Service("rechargeRecordService")
public class RechargeRecordServiceImpl implements RechargeRecordService {
	
	@Autowired
	private RechargeRecordDao rechargeRecordDao;
	
	@Autowired
	private CustomerSubAccountPointService customerSubAccountPointService;
	
	@Autowired
	private CustomerSubAccountPointLogService customerSubAccountPointLogService;
	
	@Autowired
	private RechargeOrderService rechargeOrderService;

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CustomerSubAccountService customerSubAccountService;
	
	@Autowired
	private UserBankCardService userBankCardService;
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public RechargeRecord add( int productChannelId,
			int userBankCardId, String bankCardNumber, String bankCode,
			double amount, int customerId,int customerSubAccountId) {
		
		RechargeRecord rechargeRecord = new RechargeRecord();
		rechargeRecord.setAmount(amount);
		rechargeRecord.setBankCardNumber(bankCardNumber);
		rechargeRecord.setBankCode(bankCode);
		rechargeRecord.setCreateTime(new Date());
		
//		double fee = 0D;
//		
//		
//		rechargeRecord.setFee(fee);
		rechargeRecord.setProductChannelId(productChannelId);
		rechargeRecord.setStatus(RechargeRecordStatus.WAITING_PAY);
		rechargeRecord.setSuccessAmount(0D);
		rechargeRecord.setUserBankCardId(userBankCardId);
		rechargeRecord.setCustomerId(customerId);
		rechargeRecord.setCustomerSubAccountId(customerSubAccountId);
		rechargeRecord = rechargeRecordDao.save(rechargeRecord);
		
		// 添加充值订单
		rechargeOrderService.add(rechargeRecord.getId());
		return rechargeRecord;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public RechargeRecord updateSuccess(int id,double successAmount,double fee,String orderFormId, String payOrderFormId, String code,
			String msg, String response,String bankCardAgreementNumber) {
		
		RechargeRecord rechargeRecord = findById(id);
		if(null == rechargeRecord){
			throw new RechargeRecordException("没有找到相关充值记录");
		}
		
		
		userBankCardService.bindBankCardSuccess(rechargeRecord.getUserBankCardId(), bankCardAgreementNumber);
		
		if(rechargeRecord.getStatus().equals(RechargeRecordStatus.RECHARGE_SUCCESS)){
			return rechargeRecord;
		}
		
		
		rechargeRecord.setArriveTime(new Date());
		rechargeRecord.setFee(fee);
		rechargeRecord.setStatus(RechargeRecordStatus.RECHARGE_SUCCESS);
		rechargeRecord.setSuccessAmount(successAmount);
		// 处理资金
		
		CustomerSubAccountPoint customerSubAccountPoint = customerSubAccountPointService
				.findCustomerSubAccountPointByCustomerSubAccountId(rechargeRecord.getCustomerSubAccountId());
		
		
		
		customerSubAccountPoint.setAvailablePoints(CalculateUtil.doubleAdd(customerSubAccountPoint.getAvailablePoints(), successAmount, 4));
		
		customerSubAccountPointService.update(customerSubAccountPoint);
		
		//资金记录
		String notes = "充值【"+successAmount+"】元";
		customerSubAccountPointLogService.add(successAmount, customerSubAccountPoint.getAvailablePoints(), 
				customerSubAccountPoint.getFrozenPoints(), CustomerSubAccountPointType.RECHARGE, notes, 
				null, rechargeRecord.getCustomerSubAccountId(), rechargeRecord.getCustomerSubAccountId(), 
				rechargeRecord.getCustomerSubAccountId(),rechargeRecord.getCustomerSubAccountId(), true, 
				rechargeRecord.getCustomerId(), null,null,null);
		
		// 手续费处理
		Customer systemCustomer = customerService.findByUsername(Conf.SYSTEM_CUSTOMER);
		
		
		CustomerSubAccount systemCustomerSubAccount = customerSubAccountService
				.findByCustomerSubAccountByUserIdAndProductChannelId(systemCustomer.getId(), rechargeRecord.getProductChannelId());
		rechargeRecord.setRealFeeCostAccount(systemCustomerSubAccount.getId());
		
		CustomerSubAccountPoint systemCustomerSubAccountPoint = customerSubAccountPointService
				.findCustomerSubAccountPointByCustomerSubAccountId(systemCustomerSubAccount.getId());
		
		systemCustomerSubAccountPoint.setAvailablePoints(
				CalculateUtil.doubleSubtract(systemCustomerSubAccountPoint.getAvailablePoints(), fee, 4));
//		systemCustomerSubAccountPoint.setFrozenPoints(
//				CalculateUtil.doubleAdd(systemCustomerSubAccountPoint.getFrozenPoints(), fee, 4));
		customerSubAccountPointService.update(systemCustomerSubAccountPoint);
		
		// 处理资金记录
		String remark = "用户子账户【"+rechargeRecord.getCustomerSubAccountId()+"】充值【"+rechargeRecord.getSuccessAmount()+"】元，系统账户补贴手续费【"+fee+"】元";
		customerSubAccountPointLogService.add(fee, systemCustomerSubAccountPoint.getAvailablePoints(), 
				systemCustomerSubAccountPoint.getFrozenPoints(), CustomerSubAccountPointType.RECHARGE, 
				remark, null, systemCustomerSubAccount.getId(), systemCustomerSubAccount.getId(), systemCustomerSubAccount.getId(), 
				systemCustomerSubAccount.getId(), false, systemCustomer.getId(), null,null,null);
		
		
		//更新订单信息
		rechargeOrderService.updateSuccess(orderFormId, payOrderFormId, code, msg, response);
		
		
		return rechargeRecordDao.update(rechargeRecord);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public RechargeRecord updateFailed(int id, String reason,String orderFormId, String payOrderFormId, String code,
			String msg, String response) {
		RechargeRecord rechargeRecord = findById(id);
		if(null == rechargeRecord){
			throw new RechargeRecordException("没有找到相关充值记录");
		}
		
		if(rechargeRecord.getStatus().equals(RechargeRecordStatus.RECHARGE_SUCCESS)){
			return rechargeRecord;
		}
		
		rechargeRecord.setReason(reason);
		rechargeRecord.setStatus(RechargeRecordStatus.RECHARGE_FAILED);
		rechargeRecord.setSuccessAmount(0D);
		rechargeRecord.setFee(0D);
		rechargeOrderService.updateFailed(orderFormId, payOrderFormId, code, msg, response);
		return rechargeRecordDao.update(rechargeRecord);
	}


	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public RechargeRecord findById(int id) {
		return rechargeRecordDao.findById(id);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public RechargeRecord updateProgress(int id,String orderFormId, String payOrderFormId, String code,
			String msg, String response) {
		RechargeRecord rechargeRecord = findById(id);
		if(null == rechargeRecord){
			throw new RechargeRecordException("没有找到相关充值记录");
		}
		
		rechargeRecord.setStatus(RechargeRecordStatus.RECHARGEING);
		rechargeOrderService.updateSuccess(orderFormId, payOrderFormId, code, msg, response);
		
		return rechargeRecordDao.update(rechargeRecord);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public RechargeRecord updateWaitting(int id, String orderFormId,
			String payOrderFormId, String code, String msg, String response) {
		RechargeRecord rechargeRecord = findById(id);
		if(null == rechargeRecord){
			throw new RechargeRecordException("没有找到相关充值记录");
		}
		
		rechargeRecord.setStatus(RechargeRecordStatus.WAITING_PAY);
		rechargeOrderService.updateSuccess(orderFormId, payOrderFormId, code, msg, response);
		
		return rechargeRecordDao.update(rechargeRecord);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public RechargeRecord updateRefund(int id, String orderFormId,
			String payOrderFormId, String code, String msg, String response) {
		
		RechargeRecord rechargeRecord = findById(id);
		if(null == rechargeRecord){
			throw new RechargeRecordException("没有找到相关充值记录");
		}

		// TODO 这个地方需要和支付确认推广相关流程和手续费收取
		rechargeRecord.setStatus(RechargeRecordStatus.RECHARGE_REFUND);
		
		rechargeOrderService.updateSuccess(orderFormId, payOrderFormId, code, msg, response);
		
		return rechargeRecordDao.update(rechargeRecord);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<Integer> findAllProgress() {
		
		return rechargeRecordDao.findAllProgress();
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<RechargeRecordVo> list(int customerId,
			Integer productChannelId, int from, int pageSize) {
		Integer customerSubAccountId = null;
		
		CustomerSubAccount customerSubAccount = customerSubAccountService.findByCustomerSubAccountByUserIdAndProductChannelId(customerId, productChannelId);
		if(null != customerSubAccount){
			customerSubAccountId = customerSubAccount.getId();
		}
		return rechargeRecordDao.list(customerId, customerSubAccountId, from, pageSize);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public long listTotalCount(int customerId, Integer productChannelId) {
		Integer customerSubAccountId = null;
		
		CustomerSubAccount customerSubAccount = customerSubAccountService.findByCustomerSubAccountByUserIdAndProductChannelId(customerId, productChannelId);
		if(null != customerSubAccount){
			customerSubAccountId = customerSubAccount.getId();
		}
		return rechargeRecordDao.listTotalCount(customerId, customerSubAccountId);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<RechargeRecordVo> listAll(RechargeRecordStatus status,
			Date startTime, Date endTime, String mobile, int from, int pageSize) {
		return rechargeRecordDao.listAll(status, startTime, endTime, mobile, from, pageSize);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public long listAllTotalCount(RechargeRecordStatus status, Date startTime,
			Date endTime, String mobile) {
		return rechargeRecordDao.listAllTotalCount(status, startTime, endTime, mobile);
	}


}
