/**
 * 
 */
package com.hwsoft.service.point.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.common.customer.CustomerSource;
import com.hwsoft.common.point.CustomerSubAccountPointType;
import com.hwsoft.common.product.ProductChannelType;
import com.hwsoft.dao.point.CustomerSubAccountPointDao;
import com.hwsoft.exception.user.CustomerException;
import com.hwsoft.model.customer.Customer;
import com.hwsoft.model.customer.CustomerSubAccount;
import com.hwsoft.model.point.CustomerSubAccountPoint;
import com.hwsoft.model.product.ProductChannel;
import com.hwsoft.service.customer.CustomerService;
import com.hwsoft.service.customer.CustomerSubAccountService;
import com.hwsoft.service.log.customer.CustomerSubAccountPointLogService;
import com.hwsoft.service.point.CustomerSubAccountPointService;
import com.hwsoft.service.product.ProductChannelService;
import com.hwsoft.util.math.CalculateUtil;
import com.hwsoft.util.string.StringUtils;

/**
 * @author tzh
 *
 */
@Service("customerSubAccountPointService")
public class CustomerSubAccountPointServiceImpl implements
		CustomerSubAccountPointService {
	
	@Autowired
	private CustomerSubAccountPointDao customerSubAccountPointDao;
	
	@Autowired
	private CustomerSubAccountService customerSubAccountService;
	
	@Autowired
	private ProductChannelService productChannelService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CustomerSubAccountPointLogService customerSubAccountPointLogService;

	/* (non-Javadoc)
	 * @see com.hwsoft.service.point.CustomerSubAccountPointService#addCustomerSubAccountPoint(com.hwsoft.model.point.CustomerSubAccountPoint)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public CustomerSubAccountPoint addCustomerSubAccountPoint(
			CustomerSubAccountPoint customerSubAccountPoint) {
		
		Date now = new Date();
		customerSubAccountPoint.setCreateTime(now);
		customerSubAccountPoint.setUpdateTime(now);
		return customerSubAccountPointDao.save(customerSubAccountPoint);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public CustomerSubAccountPoint findCustomerSubAccountPointByCustomerSubAccountId(
			int customerSubAccountId) {
		return customerSubAccountPointDao.findCustomerSubAccountPointByCustomerSubAccountId(customerSubAccountId);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public CustomerSubAccountPoint update(
			CustomerSubAccountPoint customerSubAccountPoint) {
		customerSubAccountPoint.setUpdateTime(new Date());
		return customerSubAccountPointDao.update(customerSubAccountPoint);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public CustomerSubAccountPoint systemTransfer(int fromCustomerId,
			int toCustomerId, ProductChannelType productChannelType,
			double amount,String notes,int staffId,String staffName) {
		
		
		ProductChannel productChannel = productChannelService.findByType(productChannelType);
		if(null == productChannel){
			throw new CustomerException("渠道未找到");
		}
		
		Customer fromCustomer = customerService.findById(fromCustomerId);
		if(null == fromCustomer){
			throw new CustomerException("转出账户未找到");
		}
		CustomerSubAccount fromCustomerSubAccount = customerSubAccountService.findByCustomerSubAccountByUserIdAndProductChannelId(fromCustomerId, productChannel.getId());
		if(null == fromCustomerSubAccount){
			throw new CustomerException("转出渠道账户未找到");
		}
		if(!CustomerSource.SYSTMER_CUSTOMER.equals(fromCustomer.getCustomerSource())){
			throw new CustomerException("该账户不能进行此转出操作");
		}
		CustomerSubAccountPoint fromCustomerSubAccountPoint = findCustomerSubAccountPointByCustomerSubAccountId(fromCustomerSubAccount.getId());
//		if(fromCustomerSubAccountPoint.getAvailablePoints() < amount){
//			throw new CustomerException("转出渠道账户可用余额不足");
//		}
		
		
		Customer toCustomer = customerService.findById(toCustomerId);
		if(null == toCustomer){
			throw new CustomerException("转入账户未找到");
		}
		CustomerSubAccount toCustomerSubAccount = customerSubAccountService.findByCustomerSubAccountByUserIdAndProductChannelId(toCustomerId, productChannel.getId());
		if(null == toCustomerSubAccount){
			throw new CustomerException("转入渠道账户未找到");
		}
		if(!CustomerSource.hwsoft_SYSTEM_CUSTOMER_BALANCE.equals(toCustomer.getCustomerSource())
				&& !CustomerSource.hwsoft_SYSTEM_CUSTOMER_RISK.equals(toCustomer.getCustomerSource())){
			throw new CustomerException("该账户不能进行此转入操作");
		}
		CustomerSubAccountPoint toCustomerSubAccountPoint = findCustomerSubAccountPointByCustomerSubAccountId(toCustomerSubAccount.getId());
		
		// 进行转账
		fromCustomerSubAccountPoint.setAvailablePoints(CalculateUtil.doubleSubtract(fromCustomerSubAccountPoint.getAvailablePoints(), amount, 4));
		update(fromCustomerSubAccountPoint);
		String remark = "系统子账户【"+fromCustomerSubAccountPoint.getCustomerSubAccountId()+"】转账【"+
				amount+"】元给系统子账户【"+toCustomerSubAccountPoint.getCustomerSubAccountId()+"】，备注【"+notes+"】";
		customerSubAccountPointLogService.add(amount, fromCustomerSubAccountPoint.getAvailablePoints(), 
				fromCustomerSubAccountPoint.getFrozenPoints(), CustomerSubAccountPointType.SYSTEM_TRANSFER, 
				remark, null, fromCustomerSubAccount.getId(), fromCustomerSubAccount.getId(), toCustomerSubAccount.getId(), 
				fromCustomerSubAccount.getId(), false, fromCustomer.getId(), null,staffId,staffName);
		
		
		
		toCustomerSubAccountPoint.setAvailablePoints(CalculateUtil.doubleAdd(toCustomerSubAccountPoint.getAvailablePoints(), amount, 4));
		String remark1 = "系统子账户收到【"+toCustomerSubAccountPoint.getCustomerSubAccountId()+"】转账【"+
				amount+"】元，来自系统子账户【"+fromCustomerSubAccountPoint.getCustomerSubAccountId()+"】，备注【"+notes+"】";
		customerSubAccountPointLogService.add(amount, toCustomerSubAccountPoint.getAvailablePoints(), 
				toCustomerSubAccountPoint.getFrozenPoints(), CustomerSubAccountPointType.SYSTEM_TRANSFER, 
				remark1, null, toCustomerSubAccount.getId(), fromCustomerSubAccount.getId(), toCustomerSubAccount.getId(), 
				fromCustomerSubAccount.getId(), false, toCustomer.getId(), null,staffId,staffName);
		
		return null;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public CustomerSubAccountPoint systemReceive(int fromCustomerId,
			int toCustomerId, ProductChannelType productChannelType,
			double amount, String notes, int staffId, String staffName) {
		ProductChannel productChannel = productChannelService.findByType(productChannelType);
		if(null == productChannel){
			throw new CustomerException("渠道未找到");
		}
		
		Customer fromCustomer = customerService.findById(fromCustomerId);
		if(null == fromCustomer){
			throw new CustomerException("转出账户未找到");
		}
		CustomerSubAccount fromCustomerSubAccount = customerSubAccountService.findByCustomerSubAccountByUserIdAndProductChannelId(fromCustomerId, productChannel.getId());
		if(null == fromCustomerSubAccount){
			throw new CustomerException("转出渠道账户未找到");
		}
		if(!CustomerSource.hwsoft_MEDIATOR.equals(fromCustomer.getCustomerSource())){
			throw new CustomerException("该账户不能进行此转出操作");
		}
		CustomerSubAccountPoint fromCustomerSubAccountPoint = findCustomerSubAccountPointByCustomerSubAccountId(fromCustomerSubAccount.getId());
//		if(fromCustomerSubAccountPoint.getAvailablePoints() < amount){
//			throw new CustomerException("转出渠道账户可用余额不足");
//		}
		
		
		Customer toCustomer = customerService.findById(toCustomerId);
		if(null == toCustomer){
			throw new CustomerException("转入账户未找到");
		}
		CustomerSubAccount toCustomerSubAccount = customerSubAccountService.findByCustomerSubAccountByUserIdAndProductChannelId(toCustomerId, productChannel.getId());
		if(null == toCustomerSubAccount){
			throw new CustomerException("转入渠道账户未找到");
		}
		if(!CustomerSource.SYSTMER_CUSTOMER.equals(toCustomer.getCustomerSource())){
			throw new CustomerException("该账户不能进行此转入操作");
		}
		CustomerSubAccountPoint toCustomerSubAccountPoint = findCustomerSubAccountPointByCustomerSubAccountId(toCustomerSubAccount.getId());
		
		// 进行转账
		fromCustomerSubAccountPoint.setAvailablePoints(CalculateUtil.doubleSubtract(fromCustomerSubAccountPoint.getAvailablePoints(), amount, 4));
		update(fromCustomerSubAccountPoint);
		String remark = "居间人子账户【"+fromCustomerSubAccountPoint.getCustomerSubAccountId()+"】转账【"+
				amount+"】元给系统子账户【"+toCustomerSubAccountPoint.getCustomerSubAccountId()+"】，备注【"+notes+"】";
		customerSubAccountPointLogService.add(amount, fromCustomerSubAccountPoint.getAvailablePoints(), 
				fromCustomerSubAccountPoint.getFrozenPoints(), CustomerSubAccountPointType.SYSTEM_RECEIVE, 
				remark, null, fromCustomerSubAccount.getId(), fromCustomerSubAccount.getId(), toCustomerSubAccount.getId(), 
				fromCustomerSubAccount.getId(), false, fromCustomer.getId(), null,staffId,staffName);
		
		
		
		toCustomerSubAccountPoint.setAvailablePoints(CalculateUtil.doubleAdd(toCustomerSubAccountPoint.getAvailablePoints(), amount, 4));
		String remark1 = " 子账户收到【"+toCustomerSubAccountPoint.getCustomerSubAccountId()+"】转账【"+
				amount+"】元，来自居间人子账户【"+fromCustomerSubAccountPoint.getCustomerSubAccountId()+"】，备注【"+notes+"】";
		customerSubAccountPointLogService.add(amount, toCustomerSubAccountPoint.getAvailablePoints(), 
				toCustomerSubAccountPoint.getFrozenPoints(), CustomerSubAccountPointType.SYSTEM_RECEIVE, 
				remark1, null, toCustomerSubAccount.getId(), fromCustomerSubAccount.getId(), toCustomerSubAccount.getId(), 
				fromCustomerSubAccount.getId(), false, toCustomer.getId(), null,staffId,staffName);
		
		return null;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public CustomerSubAccountPoint systemRewardBatch(int fromCustomerId,
			String mobile, ProductChannelType productChannelType,
			double amount, String notes, int staffId, String staffName) {
	
		
		
		
		if(StringUtils.isEmpty(mobile)){
			throw new CustomerException("转入手机号码不能为空");
		}
		
		List<String> mobileList = StringUtils.split(mobile, StringUtils.SPLIT_COMMA);
		
		if(null != mobileList && mobileList.size() != 0){
			
			for(String m : mobileList){
				systemReward(fromCustomerId, m, productChannelType, amount, notes, staffId, staffName);
			}
			
		}
		
		return null;
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public CustomerSubAccountPoint systemReward(int fromCustomerId,
			String mobile, ProductChannelType productChannelType,
			double amount, String notes, int staffId, String staffName){
		ProductChannel productChannel = productChannelService.findByType(productChannelType);
		if(null == productChannel){
			throw new CustomerException("渠道未找到");
		}
		
		Customer fromCustomer = customerService.findById(fromCustomerId);
		if(null == fromCustomer){
			throw new CustomerException("转出账户未找到");
		}
		CustomerSubAccount fromCustomerSubAccount = customerSubAccountService.findByCustomerSubAccountByUserIdAndProductChannelId(fromCustomerId, productChannel.getId());
		if(null == fromCustomerSubAccount){
			throw new CustomerException("转出渠道账户未找到");
		}
		if(!CustomerSource.SYSTMER_CUSTOMER.equals(fromCustomer.getCustomerSource())){
			throw new CustomerException("该账户不能进行此转出操作");
		}
		CustomerSubAccountPoint fromCustomerSubAccountPoint = findCustomerSubAccountPointByCustomerSubAccountId(fromCustomerSubAccount.getId());
//		if(fromCustomerSubAccountPoint.getAvailablePoints() < amount){
//			throw new CustomerException("转出渠道账户可用余额不足");
//		}
		
		
		Customer toCustomer = customerService.findByMobile(mobile);
		if(null == toCustomer){
			throw new CustomerException("转入账户未找到");
		}
		CustomerSubAccount toCustomerSubAccount = customerSubAccountService.findByCustomerSubAccountByUserIdAndProductChannelId(toCustomer.getId(), productChannel.getId());
		if(null == toCustomerSubAccount){
			throw new CustomerException("转入渠道账户未找到");
		}
		CustomerSubAccountPoint toCustomerSubAccountPoint = findCustomerSubAccountPointByCustomerSubAccountId(toCustomerSubAccount.getId());
		
		// 进行转账
		fromCustomerSubAccountPoint.setAvailablePoints(CalculateUtil.doubleSubtract(fromCustomerSubAccountPoint.getAvailablePoints(), amount, 4));
		update(fromCustomerSubAccountPoint);
		String remark = "系统子账户【"+fromCustomerSubAccountPoint.getCustomerSubAccountId()+"】转账【"+
				amount+"】元给用户子账户【"+toCustomerSubAccountPoint.getCustomerSubAccountId()+"】，备注【"+notes+"】";
		customerSubAccountPointLogService.add(amount, fromCustomerSubAccountPoint.getAvailablePoints(), 
				fromCustomerSubAccountPoint.getFrozenPoints(), CustomerSubAccountPointType.CASH_REWARD, 
				remark, null, fromCustomerSubAccount.getId(), fromCustomerSubAccount.getId(), toCustomerSubAccount.getId(), 
				fromCustomerSubAccount.getId(), false, fromCustomer.getId(), null,staffId,staffName);
		
		
		
		toCustomerSubAccountPoint.setAvailablePoints(CalculateUtil.doubleAdd(toCustomerSubAccountPoint.getAvailablePoints(), amount, 4));
		String remark1 = "用户子账户收到【"+toCustomerSubAccountPoint.getCustomerSubAccountId()+"】转账【"+
				amount+"】元，来自系统子账户【"+fromCustomerSubAccountPoint.getCustomerSubAccountId()+"】，备注【"+notes+"】";
		customerSubAccountPointLogService.add(amount, toCustomerSubAccountPoint.getAvailablePoints(), 
				toCustomerSubAccountPoint.getFrozenPoints(), CustomerSubAccountPointType.CASH_REWARD, 
				remark1, null, toCustomerSubAccount.getId(), fromCustomerSubAccount.getId(), toCustomerSubAccount.getId(), 
				fromCustomerSubAccount.getId(), false, toCustomer.getId(), null,staffId,staffName);
		
		return null;
	}
	
}
