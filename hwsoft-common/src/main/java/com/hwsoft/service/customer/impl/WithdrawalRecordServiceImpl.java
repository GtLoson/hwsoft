package com.hwsoft.service.customer.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.common.conf.Conf;
import com.hwsoft.common.customer.WithdrawalStatus;
import com.hwsoft.common.customer.WithdrawalType;
import com.hwsoft.common.point.CustomerSubAccountPointType;
import com.hwsoft.dao.customer.WithdrawalRecordDao;
import com.hwsoft.exception.user.WithdrawalException;
import com.hwsoft.model.bank.UserBankCard;
import com.hwsoft.model.customer.Customer;
import com.hwsoft.model.customer.CustomerSubAccount;
import com.hwsoft.model.customer.WithdrawalRecord;
import com.hwsoft.model.point.CustomerSubAccountPoint;
import com.hwsoft.service.bank.UserBankCardService;
import com.hwsoft.service.customer.CustomerService;
import com.hwsoft.service.customer.CustomerSubAccountService;
import com.hwsoft.service.customer.WithdrawalRecordService;
import com.hwsoft.service.log.customer.CustomerSubAccountPointLogService;
import com.hwsoft.service.order.hwsoft.WithdrawalOrderService;
import com.hwsoft.service.point.CustomerSubAccountPointService;
import com.hwsoft.util.math.CalculateUtil;
import com.hwsoft.vo.customer.WithdrawalRecordVo;


/**
 * 
 * @author tzh
 *
 */
@Service("withdrawalRecordService")
public class WithdrawalRecordServiceImpl implements WithdrawalRecordService {
	
	@Autowired
	private WithdrawalRecordDao withdrawalRecordDao;
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerSubAccountService customerSubAccountService;
	
	@Autowired
	private CustomerSubAccountPointService customerSubAccountPointService;
	
	@Autowired
	private CustomerSubAccountPointLogService customerSubAccountPointLogService;
	
	@Autowired
	private WithdrawalOrderService withdrawalOrderService;
	
	@Autowired
	private UserBankCardService userBankCardService;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public WithdrawalRecord add(double amount, int customerSubAccountId,
			WithdrawalType type,int userBankCardId) {
		
		CustomerSubAccount customerSubAccount = customerSubAccountService.findById(customerSubAccountId);
		
		if(null == customerSubAccount){
			throw new WithdrawalException("钱妈妈账户未找到");
		}
		
		UserBankCard userBankCard = userBankCardService.findUserBankCardById(userBankCardId);
		if(null == userBankCard){
			throw new WithdrawalException("未找到指定的银行卡");
		}
		if(userBankCard.getCustomerSubAccountId() != customerSubAccountId){
			throw new WithdrawalException("银行卡与账户不匹配");
		}
		
		// 需要添加冻结记录
		WithdrawalRecord withdrawalRecord = new WithdrawalRecord();
		withdrawalRecord.setAmount(amount);
		withdrawalRecord.setApplyTime(new Date());
		withdrawalRecord.setCustomerId(customerSubAccount.getCustomerId());
		withdrawalRecord.setCustomerSubAccountId(customerSubAccountId);
		withdrawalRecord.setBankCardNumber(userBankCard.getBankCardNumber());
		withdrawalRecord.setUserBankCardId(userBankCardId);
		if(type.equals(WithdrawalType.CUSTOMER_APPLY_HANDLE)){
			withdrawalRecord.setStatus(WithdrawalStatus.AUDITING);
		} else {
			withdrawalRecord.setStatus(WithdrawalStatus.AUDITED);
//			withdrawalRecord.setAuditStaffId(auditStaffId)
		}
		
		withdrawalRecord.setType(type);
		
		// 资金记录
		CustomerSubAccountPoint customerSubAccountPoint = customerSubAccountPointService.findCustomerSubAccountPointByCustomerSubAccountId(customerSubAccountId);
		if(customerSubAccountPoint.getAvailablePoints() < amount){
			throw new WithdrawalException("账户可提现金额不足");
		}
		customerSubAccountPoint.setAvailablePoints(CalculateUtil.doubleSubtract(customerSubAccountPoint.getAvailablePoints(), amount, 4));
		customerSubAccountPoint.setFrozenPoints(CalculateUtil.doubleAdd(customerSubAccountPoint.getFrozenPoints(), amount, 4));
		customerSubAccountPointService.update(customerSubAccountPoint);
		
		String notes = "提现冻结【"+amount+"】元";
		
		customerSubAccountPointLogService.add(amount, customerSubAccountPoint.getAvailablePoints(), 
				customerSubAccountPoint.getFrozenPoints(), CustomerSubAccountPointType.APPLY_WITHDRAWAL, notes, null, customerSubAccountId, 
				customerSubAccountId, customerSubAccountId, customerSubAccountId, false, customerSubAccount.getCustomerId(), null,null,null);
		
		return withdrawalRecordDao.save(withdrawalRecord);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<WithdrawalRecordVo> list(WithdrawalType type,
			WithdrawalStatus status, String mobile, String timeCollumn,
			Date startDate, Date endDate, int from, int pageSize) {
		return withdrawalRecordDao.list(type, status, mobile, timeCollumn, startDate, endDate, from, pageSize);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public long getTotalCount(WithdrawalType type, WithdrawalStatus status,
			String mobile, String timeCollumn, Date startDate, Date endDate) {
		return withdrawalRecordDao.getTotalCount(type, status, mobile, timeCollumn, startDate, endDate);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public WithdrawalRecord updateAudit(int id, String notes, boolean flag,int staffId,String staffName) {
		
		WithdrawalRecord withdrawalRecord = findById(id);
		
		if(null == withdrawalRecord){
			throw new WithdrawalException("未找到该申请信息");
		}
		
		withdrawalRecord.setAuditStaffId(staffId);
		withdrawalRecord.setAuditStaffName(staffName);
		withdrawalRecord.setNotes(notes);
		if(flag){
			withdrawalRecord.setStatus(WithdrawalStatus.AUDITED);
			
		} else {
			withdrawalRecord.setStatus(WithdrawalStatus.AUDITED_FAILED);
			
			// 需要处理资金记录
			CustomerSubAccountPoint customerSubAccountPoint = customerSubAccountPointService.findCustomerSubAccountPointByCustomerSubAccountId(withdrawalRecord.getCustomerSubAccountId());
			
			customerSubAccountPoint.setFrozenPoints(CalculateUtil.doubleSubtract(customerSubAccountPoint.getFrozenPoints(), withdrawalRecord.getAmount(), 4));
			customerSubAccountPoint.setAvailablePoints(CalculateUtil.doubleAdd(customerSubAccountPoint.getAvailablePoints(), withdrawalRecord.getAmount(), 4));
			customerSubAccountPointService.update(customerSubAccountPoint);
			
			String remark = "提现审核未通过返回冻结【"+withdrawalRecord.getAmount()+"】元";
			
			customerSubAccountPointLogService.add(withdrawalRecord.getAmount(), customerSubAccountPoint.getAvailablePoints(), 
					customerSubAccountPoint.getFrozenPoints(), CustomerSubAccountPointType.WITHDRAWAL_FAILED, remark, null, 
					withdrawalRecord.getCustomerSubAccountId(), withdrawalRecord.getCustomerSubAccountId(), 
					withdrawalRecord.getCustomerSubAccountId(), withdrawalRecord.getCustomerSubAccountId(), 
					false, withdrawalRecord.getCustomerId(), null,null,null);
			
			
			
		}
		withdrawalRecord.setAuditTime(new Date());
		return withdrawalRecordDao.update(withdrawalRecord);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public WithdrawalRecord findById(int id) {
		return withdrawalRecordDao.findById(id);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public WithdrawalRecord updateWithdrawal(int id, String notes, int staffId, String staffName) {
		
		
		WithdrawalRecord withdrawalRecord = findById(id);
		
		if(null == withdrawalRecord){
			throw new WithdrawalException("未找到该申请信息");
		}
		
		withdrawalRecord.setWithdrawalStaffId(staffId);
		withdrawalRecord.setWithdrawalStaffName(staffName);
		withdrawalRecord.setWithdrawalNotes(notes);
		
		withdrawalRecord.setStatus(WithdrawalStatus.WITHDRAWALING);//提现处理中
		withdrawalRecord.setWithdrawalTime(new Date());
		
		//TODO 处理手续费
		Customer systemCustomer = customerService.findByUsername(Conf.SYSTEM_CUSTOMER);
		
		CustomerSubAccount customerSubAccount = customerSubAccountService.findById(withdrawalRecord.getCustomerSubAccountId());
		
		CustomerSubAccount systemCustomerSubAccount = customerSubAccountService
				.findByCustomerSubAccountByUserIdAndProductChannelId(systemCustomer.getId(), customerSubAccount.getProductChannelId());
		withdrawalRecord.setRealFeeCostAccount(systemCustomerSubAccount.getId());
		double fee = 1;
		withdrawalRecord.setFee(fee);//需要设置到系统参数中去
		
		CustomerSubAccountPoint systemCustomerSubAccountPoint = customerSubAccountPointService
				.findCustomerSubAccountPointByCustomerSubAccountId(systemCustomerSubAccount.getId());
		
		systemCustomerSubAccountPoint.setAvailablePoints(
				CalculateUtil.doubleSubtract(systemCustomerSubAccountPoint.getAvailablePoints(), fee, 4));
		systemCustomerSubAccountPoint.setFrozenPoints(
				CalculateUtil.doubleAdd(systemCustomerSubAccountPoint.getFrozenPoints(), fee, 4));
		customerSubAccountPointService.update(systemCustomerSubAccountPoint);
		
		// 处理资金记录
		String remark = "用户子账户【"+withdrawalRecord.getCustomerSubAccountId()+"】提现【"+withdrawalRecord.getAmount()+"】元，系统账户冻结手续费【"+fee+"】元";
		customerSubAccountPointLogService.add(fee, systemCustomerSubAccountPoint.getAvailablePoints(), 
				systemCustomerSubAccountPoint.getFrozenPoints(), CustomerSubAccountPointType.WITHDRAWAL_FEE_FREEZON, 
				remark, null, systemCustomerSubAccount.getId(), systemCustomerSubAccount.getId(), systemCustomerSubAccount.getId(), 
				systemCustomerSubAccount.getId(), false, systemCustomer.getId(), null,null,null);
		return withdrawalRecordDao.update(withdrawalRecord);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public WithdrawalRecord updateWithdrawalSuccess(int id,String orderFormId,String payOrderFormId) {
		WithdrawalRecord withdrawalRecord = findById(id);
		
		if(null == withdrawalRecord){
			throw new WithdrawalException("未找到该申请信息");
		}
		
		if(withdrawalRecord.getStatus().equals(WithdrawalStatus.WITHDRAWAL_SUCCESS)){
			return withdrawalRecord;
		}
		//处理资金记录
		
		withdrawalRecord.setStatus(WithdrawalStatus.WITHDRAWAL_SUCCESS);
		
		CustomerSubAccountPoint customerSubAccountPoint = customerSubAccountPointService.findCustomerSubAccountPointByCustomerSubAccountId(withdrawalRecord.getCustomerSubAccountId());
		
		customerSubAccountPoint.setFrozenPoints(CalculateUtil.doubleSubtract(customerSubAccountPoint.getFrozenPoints(), withdrawalRecord.getAmount(), 4));
		
		customerSubAccountPointService.update(customerSubAccountPoint);
		
		String notes = "提现成功【"+withdrawalRecord.getAmount()+"】元";
		
		customerSubAccountPointLogService.add(withdrawalRecord.getAmount(), customerSubAccountPoint.getAvailablePoints(), 
				customerSubAccountPoint.getFrozenPoints(), CustomerSubAccountPointType.WITHDRAWAL_SUCCESS, notes, null, 
				withdrawalRecord.getCustomerSubAccountId(), withdrawalRecord.getCustomerSubAccountId(), 
				withdrawalRecord.getCustomerSubAccountId(), withdrawalRecord.getCustomerSubAccountId(), 
				false, withdrawalRecord.getCustomerId(), null,null,null);
		
		// 减去手续费
		if(withdrawalRecord.getFee() > 0){
			CustomerSubAccountPoint feeCustomerSubAccountPoint = customerSubAccountPointService.findCustomerSubAccountPointByCustomerSubAccountId(withdrawalRecord.getRealFeeCostAccount());
			CustomerSubAccount feeCustomerSubAccount = customerSubAccountService.findById(feeCustomerSubAccountPoint.getCustomerSubAccountId());
			feeCustomerSubAccountPoint.setFrozenPoints(CalculateUtil.doubleSubtract(feeCustomerSubAccountPoint.getFrozenPoints(), withdrawalRecord.getFee(), 4));
			String remark = "用户子账户【"+withdrawalRecord.getCustomerSubAccountId()+"】提现【"+withdrawalRecord.getAmount()+"】元，系统账户补贴手续费【"+withdrawalRecord.getFee()+"】元";
			customerSubAccountPointLogService.add(withdrawalRecord.getFee(), feeCustomerSubAccountPoint.getAvailablePoints(), 
					feeCustomerSubAccountPoint.getFrozenPoints(), CustomerSubAccountPointType.WITHDRAWAL_SUCCESS_FEE, 
					remark, null, feeCustomerSubAccountPoint.getCustomerSubAccountId(), feeCustomerSubAccountPoint.getCustomerSubAccountId(), 
					feeCustomerSubAccountPoint.getCustomerSubAccountId(), feeCustomerSubAccountPoint.getCustomerSubAccountId(), false, 
					feeCustomerSubAccount.getCustomerId(), null,null,null);
		}
		
		// 处理连连订单号
		withdrawalOrderService.updateByPayOrderFormId(orderFormId, payOrderFormId);
		
		//TODO 更新银行卡绑定状态
		
		return withdrawalRecordDao.update(withdrawalRecord);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public WithdrawalRecord updateWithdrawalFailed(int id,String orderFormId,String payOrderFormId) {
		WithdrawalRecord withdrawalRecord = findById(id);
		
		if(null == withdrawalRecord){
			throw new WithdrawalException("未找到该申请信息");
		}
		
		if(withdrawalRecord.getStatus().equals(WithdrawalStatus.WITHDRAWAL_SUCCESS) || withdrawalRecord.getStatus().equals(WithdrawalStatus.WITHDRAWAL_FAILED)){
			return withdrawalRecord;
		}
		
		withdrawalRecord.setStatus(WithdrawalStatus.WITHDRAWAL_FAILED);
		
		CustomerSubAccountPoint customerSubAccountPoint = customerSubAccountPointService.findCustomerSubAccountPointByCustomerSubAccountId(withdrawalRecord.getCustomerSubAccountId());
		
		customerSubAccountPoint.setFrozenPoints(CalculateUtil.doubleSubtract(customerSubAccountPoint.getFrozenPoints(), withdrawalRecord.getAmount(), 4));
		customerSubAccountPoint.setAvailablePoints(CalculateUtil.doubleAdd(customerSubAccountPoint.getAvailablePoints(), withdrawalRecord.getAmount(), 4));
		customerSubAccountPointService.update(customerSubAccountPoint);
		
		String notes = "提现失败返回冻结【"+withdrawalRecord.getAmount()+"】元";
		
		customerSubAccountPointLogService.add(withdrawalRecord.getAmount(), customerSubAccountPoint.getAvailablePoints(), 
				customerSubAccountPoint.getFrozenPoints(), CustomerSubAccountPointType.WITHDRAWAL_FAILED, notes, null, 
				withdrawalRecord.getCustomerSubAccountId(), withdrawalRecord.getCustomerSubAccountId(), 
				withdrawalRecord.getCustomerSubAccountId(), withdrawalRecord.getCustomerSubAccountId(), 
				false, withdrawalRecord.getCustomerId(), null,null,null);
		
		// 减去手续费
		if(withdrawalRecord.getFee() > 0){
			CustomerSubAccountPoint feeCustomerSubAccountPoint = customerSubAccountPointService.findCustomerSubAccountPointByCustomerSubAccountId(withdrawalRecord.getRealFeeCostAccount());
			CustomerSubAccount feeCustomerSubAccount = customerSubAccountService.findById(feeCustomerSubAccountPoint.getCustomerSubAccountId());
			feeCustomerSubAccountPoint.setFrozenPoints(CalculateUtil.doubleSubtract(feeCustomerSubAccountPoint.getFrozenPoints(), withdrawalRecord.getFee(), 4));
			feeCustomerSubAccountPoint.setAvailablePoints(CalculateUtil.doubleAdd(feeCustomerSubAccountPoint.getAvailablePoints(), withdrawalRecord.getFee(), 4));
			
			
			String remark = "用户子账户【"+withdrawalRecord.getCustomerSubAccountId()+"】提现失败【"+withdrawalRecord.getAmount()+"】元，系统账户解冻手续费【"+withdrawalRecord.getFee()+"】元";
			customerSubAccountPointLogService.add(withdrawalRecord.getFee(), feeCustomerSubAccountPoint.getAvailablePoints(), 
					feeCustomerSubAccountPoint.getFrozenPoints(), CustomerSubAccountPointType.WITHDRAWAL_FAILE_FEE, 
					remark, null, feeCustomerSubAccountPoint.getCustomerSubAccountId(), feeCustomerSubAccountPoint.getCustomerSubAccountId(), 
					feeCustomerSubAccountPoint.getCustomerSubAccountId(), feeCustomerSubAccountPoint.getCustomerSubAccountId(), false, 
					feeCustomerSubAccount.getCustomerId(), null,null,null);
		}
		withdrawalOrderService.updateByPayOrderFormId(orderFormId, payOrderFormId);
		return withdrawalRecordDao.update(withdrawalRecord);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public WithdrawalRecord updateBankDeal(int id,String orderFormId,String payOrderFormId) {
		WithdrawalRecord withdrawalRecord = findById(id);
		
		if(null == withdrawalRecord){
			throw new WithdrawalException("未找到该申请信息");
		}
		
		if(withdrawalRecord.getStatus().equals(WithdrawalStatus.WITHDRAWAL_SUCCESS) || withdrawalRecord.getStatus().equals(WithdrawalStatus.AUDITED_FAILED)){
			return withdrawalRecord;
		}
		
		withdrawalRecord.setStatus(WithdrawalStatus.BANK_DEALING);
		withdrawalOrderService.updateByPayOrderFormId(orderFormId, payOrderFormId);
		return withdrawalRecordDao.update(withdrawalRecord);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public WithdrawalRecord updateWithdrawalCancel(int id, String orderFormId,
			String payOrderFormId) {
		WithdrawalRecord withdrawalRecord = findById(id);
		
		if(null == withdrawalRecord){
			throw new WithdrawalException("未找到该申请信息");
		}
		
		withdrawalRecord.setStatus(WithdrawalStatus.WITHDRAWAL_FAILED);
		
		CustomerSubAccountPoint customerSubAccountPoint = customerSubAccountPointService.findCustomerSubAccountPointByCustomerSubAccountId(withdrawalRecord.getCustomerSubAccountId());
		
		customerSubAccountPoint.setFrozenPoints(CalculateUtil.doubleSubtract(customerSubAccountPoint.getFrozenPoints(), withdrawalRecord.getAmount(), 4));
		customerSubAccountPoint.setAvailablePoints(CalculateUtil.doubleAdd(customerSubAccountPoint.getFrozenPoints(), withdrawalRecord.getAmount(), 4));
		customerSubAccountPointService.update(customerSubAccountPoint);
		
		String notes = "提现撤销【"+withdrawalRecord.getAmount()+"】元";
		
		customerSubAccountPointLogService.add(withdrawalRecord.getAmount(), customerSubAccountPoint.getAvailablePoints(), 
				customerSubAccountPoint.getFrozenPoints(), CustomerSubAccountPointType.WITHDRAWAL_CANCEL, notes, null, 
				withdrawalRecord.getCustomerSubAccountId(), withdrawalRecord.getCustomerSubAccountId(), 
				withdrawalRecord.getCustomerSubAccountId(), withdrawalRecord.getCustomerSubAccountId(), 
				false, withdrawalRecord.getCustomerId(), null,null,null);
		
		// 减去手续费
		if(withdrawalRecord.getFee() > 0){
			CustomerSubAccountPoint feeCustomerSubAccountPoint = customerSubAccountPointService.findCustomerSubAccountPointByCustomerSubAccountId(withdrawalRecord.getRealFeeCostAccount());
			CustomerSubAccount feeCustomerSubAccount = customerSubAccountService.findById(feeCustomerSubAccountPoint.getCustomerSubAccountId());
			feeCustomerSubAccountPoint.setFrozenPoints(CalculateUtil.doubleSubtract(feeCustomerSubAccountPoint.getFrozenPoints(), withdrawalRecord.getFee(), 4));
			feeCustomerSubAccountPoint.setAvailablePoints(CalculateUtil.doubleAdd(feeCustomerSubAccountPoint.getAvailablePoints(), withdrawalRecord.getFee(), 4));
			
			
			String remark = "用户子账户【"+withdrawalRecord.getCustomerSubAccountId()+"】提现撤销【"+withdrawalRecord.getAmount()+"】元，系统账户解冻手续费【"+withdrawalRecord.getFee()+"】元";
			customerSubAccountPointLogService.add(withdrawalRecord.getFee(), feeCustomerSubAccountPoint.getAvailablePoints(), 
					feeCustomerSubAccountPoint.getFrozenPoints(), CustomerSubAccountPointType.WITHDRAWAL_CANCEL_FEE, 
					remark, null, feeCustomerSubAccountPoint.getCustomerSubAccountId(), feeCustomerSubAccountPoint.getCustomerSubAccountId(), 
					feeCustomerSubAccountPoint.getCustomerSubAccountId(), feeCustomerSubAccountPoint.getCustomerSubAccountId(), false, 
					feeCustomerSubAccount.getCustomerId(), null,null,null);
		}
		withdrawalOrderService.updateByPayOrderFormId(orderFormId, payOrderFormId);
		return withdrawalRecordDao.update(withdrawalRecord);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<Integer> listAllPayProgress() {
		return withdrawalRecordDao.listAllPayProgress()	;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public WithdrawalRecord addAgain(int withdrawalRecordId, String notes, int staffId,String staffName) {
		
		WithdrawalRecord withdrawalRecord = findById(withdrawalRecordId);
		
		if(null == withdrawalRecord){
			throw new WithdrawalException("未找到提现记录");
		}
		
		if(withdrawalRecord.isAgain()){
			throw new WithdrawalException("该提现记录已经进行过再次处理，不能重复处理");
		}
		
		withdrawalRecord.setAgain(true);
		withdrawalRecordDao.update(withdrawalRecord);
		
		WithdrawalRecord withdrawalRecord2 = new WithdrawalRecord();
		withdrawalRecord2.setAmount(withdrawalRecord.getAmount());
		withdrawalRecord2.setApplyTime(withdrawalRecord.getApplyTime());
		withdrawalRecord2.setAuditStaffId(staffId);
		withdrawalRecord2.setAuditStaffName(staffName);
		withdrawalRecord2.setBankCardNumber(withdrawalRecord.getBankCardNumber());
		withdrawalRecord2.setCustomerId(withdrawalRecord.getCustomerId());
		withdrawalRecord2.setCustomerSubAccountId(withdrawalRecord.getCustomerSubAccountId());
		withdrawalRecord2.setFee(withdrawalRecord.getFee());
		withdrawalRecord2.setLastWithdrawalRecordId(withdrawalRecord.getId());
		withdrawalRecord2.setNotes(notes);
		withdrawalRecord2.setRealFeeCostAccount(withdrawalRecord.getRealFeeCostAccount());
		withdrawalRecord2.setStatus(WithdrawalStatus.AUDITED);
		withdrawalRecord2.setType(withdrawalRecord.getType());
		withdrawalRecord2.setUserBankCardId(withdrawalRecord.getUserBankCardId());
		withdrawalRecord2.setWithdrawalNotes(notes);
		withdrawalRecord2.setWithdrawalStaffId(staffId);
		withdrawalRecordDao.save(withdrawalRecord2);

		CustomerSubAccount customerSubAccount = customerSubAccountService.findById(withdrawalRecord.getCustomerSubAccountId());
		
		if(null == customerSubAccount){
			throw new WithdrawalException("钱妈妈账户未找到");
		}
		// 资金记录
		CustomerSubAccountPoint customerSubAccountPoint = customerSubAccountPointService.findCustomerSubAccountPointByCustomerSubAccountId(withdrawalRecord.getCustomerSubAccountId());
		if(customerSubAccountPoint.getAvailablePoints() < withdrawalRecord2.getAmount()){
			throw new WithdrawalException("账户可提现金额不足");
		}
		customerSubAccountPoint.setAvailablePoints(CalculateUtil.doubleSubtract(customerSubAccountPoint.getAvailablePoints(), withdrawalRecord2.getAmount(), 4));
		customerSubAccountPoint.setFrozenPoints(CalculateUtil.doubleAdd(customerSubAccountPoint.getFrozenPoints(), withdrawalRecord2.getAmount(), 4));
		customerSubAccountPointService.update(customerSubAccountPoint);
		
		String remark = "提现冻结【"+withdrawalRecord2.getAmount()+"】元";
		
		customerSubAccountPointLogService.add(withdrawalRecord2.getAmount(), customerSubAccountPoint.getAvailablePoints(), 
				customerSubAccountPoint.getFrozenPoints(), CustomerSubAccountPointType.APPLY_WITHDRAWAL, 
				remark, null, withdrawalRecord.getCustomerSubAccountId(), 
				withdrawalRecord.getCustomerSubAccountId(), withdrawalRecord.getCustomerSubAccountId(),
				withdrawalRecord.getCustomerSubAccountId(), false, customerSubAccount.getCustomerId(), null,null,null);
		
		
		return withdrawalRecord2;
	}

	@Override
	public WithdrawalRecord updateAuditAgainFailed(int id, String notes,
			int staffId, String staffName) {
	WithdrawalRecord withdrawalRecord = findById(id);
		
		if(null == withdrawalRecord){
			throw new WithdrawalException("未找到该申请信息");
		}
		
		withdrawalRecord.setWithdrawalStaffId(staffId);
		withdrawalRecord.setWithdrawalStaffName(staffName);
		withdrawalRecord.setWithdrawalNotes(notes);
		
		withdrawalRecord.setStatus(WithdrawalStatus.AUDITED_AGAIN_FAILED);
	
		withdrawalRecord.setAuditTime(new Date());
		CustomerSubAccount customerSubAccount = customerSubAccountService.findById(withdrawalRecord.getCustomerSubAccountId());
		
		if(null == customerSubAccount){
			throw new WithdrawalException("钱妈妈账户未找到");
		}
		
		// 资金记录
		CustomerSubAccountPoint customerSubAccountPoint = customerSubAccountPointService.findCustomerSubAccountPointByCustomerSubAccountId(customerSubAccount.getId());
		if(customerSubAccountPoint.getAvailablePoints() < withdrawalRecord.getAmount()){
			throw new WithdrawalException("账户可提现金额不足");
		}
		customerSubAccountPoint.setAvailablePoints(CalculateUtil.doubleSubtract(customerSubAccountPoint.getAvailablePoints(),  withdrawalRecord.getAmount(), 4));
		customerSubAccountPoint.setFrozenPoints(CalculateUtil.doubleAdd(customerSubAccountPoint.getFrozenPoints(),  withdrawalRecord.getAmount(), 4));
		customerSubAccountPointService.update(customerSubAccountPoint);
		
		String remark = "提现失败返回冻结【"+ withdrawalRecord.getAmount()+"】元";
		
		customerSubAccountPointLogService.add( withdrawalRecord.getAmount(), customerSubAccountPoint.getAvailablePoints(), 
				customerSubAccountPoint.getFrozenPoints(), CustomerSubAccountPointType.WITHDRAWAL_FAILED, remark, null, customerSubAccount.getId(), 
				customerSubAccount.getId(), customerSubAccount.getId(), customerSubAccount.getId(), false, customerSubAccount.getCustomerId(), null,null,null);
		
		
		return withdrawalRecordDao.update(withdrawalRecord);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<WithdrawalRecordVo> list(int customerId, Integer productChannelId,
			int from, int pageSize) {
		
		Integer customerSubAccountId = null;
		
		CustomerSubAccount customerSubAccount = customerSubAccountService.findByCustomerSubAccountByUserIdAndProductChannelId(customerId, productChannelId);
		if(null != customerSubAccount){
			customerSubAccountId = customerSubAccount.getId();
		}
		
		return withdrawalRecordDao.list(customerId, customerSubAccountId, from, pageSize);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public long listTotalCount(int customerId,
			Integer productChannelId) {

		Integer customerSubAccountId = null;
		
		CustomerSubAccount customerSubAccount = customerSubAccountService.findByCustomerSubAccountByUserIdAndProductChannelId(customerId, productChannelId);
		if(null != customerSubAccount){
			customerSubAccountId = customerSubAccount.getId();
		}
		return withdrawalRecordDao.listTotalCount(customerId, customerSubAccountId);
	}


}
