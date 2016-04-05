package com.hwsoft.service.order.hwsoft.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.common.conf.BusinessConf;
import com.hwsoft.common.order.OrderStatus;
import com.hwsoft.common.order.OrderType;
import com.hwsoft.common.product.ProductOfThatStatus;
import com.hwsoft.common.product.ProductRepayRecordStatus;
import com.hwsoft.dao.order.hwsoft.RepaymentProductOrderDao;
import com.hwsoft.exception.repayment.RepaymentException;
import com.hwsoft.model.order.hwsoft.RepaymentProductDetail;
import com.hwsoft.model.order.hwsoft.RepaymentProductOrder;
import com.hwsoft.model.point.CustomerSubAccountPoint;
import com.hwsoft.model.product.Product;
import com.hwsoft.model.product.ProductOfthatRegister;
import com.hwsoft.model.product.ProductRepayRecord;
import com.hwsoft.service.log.product.RepaymentProductLogService;
import com.hwsoft.service.order.hwsoft.RepaymentProductOrderService;
import com.hwsoft.service.point.CustomerSubAccountPointService;
import com.hwsoft.service.product.ProductOfThatRegisterService;
import com.hwsoft.service.product.ProductRepayRecordService;
import com.hwsoft.service.product.ProductService;
import com.hwsoft.util.date.DateTools;
import com.hwsoft.util.interest.InterestCalculatorModeUtil;
import com.hwsoft.util.math.CalculateUtil;
import com.hwsoft.util.order.OrderUtil;
import com.hwsoft.vo.order.RepaymentProductDetailVo;
import com.hwsoft.vo.order.RepaymentProductOrderVo;



/**
 * 
 * @author tzh
 *
 */
@Service("repaymentProductOrderService")
public class RepaymentProductOrderServiceImpl implements
		RepaymentProductOrderService {

	
	@Autowired
	private RepaymentProductOrderDao repaymentProductOrderDao;
	
	@Autowired
	private ProductRepayRecordService productRepayRecordService;
	
	@Autowired
	private ProductOfThatRegisterService productOfThatRegisterService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private RepaymentProductLogService repaymentProductLogService;
	
	@Autowired
	private CustomerSubAccountPointService customerSubAccountPointService;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public RepaymentProductOrder add(int productId, OrderType orderType,
			int staffId, String staffName,List<Integer> repaymentIds) {
		
		Product product = productService.findById(productId);
		
		//查询居间人(借款人)资金账户
		
		CustomerSubAccountPoint customerSubAccountPoint = customerSubAccountPointService.findCustomerSubAccountPointByCustomerSubAccountId(product.getCustomerSubAccountId());
		String notes = "";
		int totalCopies = product.getTotalCopies();
		int dumbCopies = 0;
		if(product.isEnableDummyBoughtAmount() && product.getDummyBoughtAmount() > 0){
			dumbCopies = product.getDummyBoughtAmount().intValue();
		}
		int realTotalCopies = totalCopies - dumbCopies;
		//还款资金处理
		if(orderType.equals(OrderType.REPAYMENT_hwsoft_ADVANCE_ALL_HANDLE)
				){// 提前还款
			//利息为当期应付利息+罚息
			
			List<ProductRepayRecord> productRepayRecords = productRepayRecordService.listAllUnpay(productId);
			if(null == productRepayRecords || productRepayRecords.size() == 0){
				throw new RepaymentException("该产品没有未还的记录");
			}
			//TODO 将所有的未还记录取出来，第一条数据需进行金额计算，每一条都应该有订单（这个地方再考虑一下）
			ProductRepayRecord productRepayRecord = productRepayRecords.get(0);
			double repayDefaultInterest = 0D;//罚息需要罚息公式进行计算
			Date startDate = product.getPassTime();
			
			//查询最近一期已收
			ProductRepayRecord productRepayRecord1 = productRepayRecordService.findByLastProductRepayRecord(productId);
			
			if(null != productRepayRecord1){
				startDate = productRepayRecord1.getRepayDate();
			}
			
			Date endDate = productRepayRecord.getRepayDate();
			System.out.println(DateTools.dateTime2String(startDate)+"---"+DateTools.dateTime2String(endDate));
			
			double repayInterest = InterestCalculatorModeUtil.realTimeInterest(productRepayRecord.getRepayInterest(), startDate, endDate);// 需要进行计算， = 当期中利息*当期已使用时间/当期总时间
			double repayPrincipal = productRepayRecord.getInitialPrincipal();//应还本金=期初待还本金
			
			// 进行资金判断
			if(customerSubAccountPoint.getAvailablePoints() < 
					CalculateUtil.doubleAdd(CalculateUtil.doubleAdd(repayInterest, repayPrincipal, 4), repayDefaultInterest, 4)){
				throw new RepaymentException("提前还款资金不足");
			}
			
			RepaymentProductOrder repaymentProductOrder = new RepaymentProductOrder();
			repaymentProductOrder.setCreateTime(new Date());
			repaymentProductOrder.setOrderStatus(OrderStatus.HANDLE_SUCESS);
			repaymentProductOrder.setOrderType(orderType);
			repaymentProductOrder.setProductId(productId);
			repaymentProductOrder.setStaffId(staffId);
			repaymentProductOrder.setStaffName(staffName);
			repaymentProductOrder.setProductRepayRecordId(productRepayRecord.getId());
			
			repaymentProductOrder.setDefaultInterest(repayDefaultInterest);//罚息
			repaymentProductOrder.setInterest(repayInterest);//利息
			repaymentProductOrder.setPrincipal(repayPrincipal);//本金
			
			//处理详情
			List<RepaymentProductDetail> repaymentProductDetails = new ArrayList<RepaymentProductDetail>();
			//首先查询出份额登记表
			List<ProductOfthatRegister> productOfthatRegisters = productOfThatRegisterService.findByProductId(productId);
			
			for(ProductOfthatRegister productOfthatRegister : productOfthatRegisters){
				//总份额
				
				if(productOfthatRegister.getOfThatNumber() <= 0 ){//当前持有份额
					continue;
				}
				if(productOfthatRegister.getStatus().equals(ProductOfThatStatus.CONFIRMING)
						|| productOfthatRegister.getStatus().equals(ProductOfThatStatus.EXITED)
						|| productOfthatRegister.getStatus().equals(ProductOfThatStatus.TRANSFERED)
						|| productOfthatRegister.getStatus().equals(ProductOfThatStatus.FAILED)){
					continue;
				}
				// 计算利息，罚息，本金
				double totalDefaultInterest = repaymentProductOrder.getDefaultInterest();
				double totalInterest = repaymentProductOrder.getInterest();
				double totalPrincipal = repaymentProductOrder.getPrincipal();
				
				double defaultInterest = CalculateUtil.doubleDivide(CalculateUtil.doubleMultiply(totalDefaultInterest, productOfthatRegister.getOfThatNumber()),realTotalCopies,4);
				double interest = CalculateUtil.doubleDivide(CalculateUtil.doubleMultiply(totalInterest, productOfthatRegister.getOfThatNumber()),realTotalCopies,4);
				double principal = CalculateUtil.doubleDivide(CalculateUtil.doubleMultiply(totalPrincipal, productOfthatRegister.getOfThatNumber()),realTotalCopies,4);
				
				RepaymentProductDetail repaymentProductDetail = new RepaymentProductDetail();
				repaymentProductDetail.setDefaultInterest(defaultInterest);
				repaymentProductDetail.setInterest(interest);
				repaymentProductDetail.setProductId(productId);
				repaymentProductDetail.setPrincipal(principal);
				repaymentProductDetail.setProductSubAccountId(productOfthatRegister.getProductSubAccountId());
				repaymentProductDetails.add(repaymentProductDetail);
				
				
			}
			
			
			repaymentProductOrder.setDetails(repaymentProductDetails);
			// 判断借款人用户账户资金是否足够
			notes = "提前全部还款，产品【"+product.getProductName()+"】提前全部还款，本金【"+repaymentProductOrder.getPrincipal()
					+"】,利息【"+repaymentProductOrder.getInterest()
					+"】,罚息【"+repaymentProductOrder.getDefaultInterest()+"】";
			
			repaymentProductOrderDao.save(repaymentProductOrder);
			//订单编号
			String orderFormId = OrderUtil.getOrderFormId(repaymentProductOrder.getOrderType().ordinal(), 
					repaymentProductOrder.getId(), BusinessConf.hwsoft_ORDER_SERIALNUMBER_LENGTH);
			repaymentProductOrder.setOrderFormId(orderFormId);
			// 处理订单日志
			repaymentProductLogService.addRepaymentProductLog(productId, orderFormId, staffId, staffName, notes);
			
			// 处理还款记录
			productService.updateRepaymentProduct(productId, repaymentProductOrder.getOrderFormId());
			//所有的全部更新为已还
			for(int i = 1 ;i < productRepayRecords.size() ;i++){
				ProductRepayRecord productRepayRecord2 = productRepayRecords.get(i);
				productRepayRecord2.setStatus(ProductRepayRecordStatus.REPAID);
				productRepayRecord2.setRepayPrincipalAndInterest(productRepayRecord2.getRepayPrincipal());
				productRepayRecord2.setAlreadyRepaidAmount(productRepayRecord2.getRepayPrincipal());
				productRepayRecord2.setAlreadyRepaidDefaultInterest(0D);
				productRepayRecord2.setAlreadyRepaidInterest(0D);
				productRepayRecord2.setAlreadyRepaidPrincipal(productRepayRecord2.getRepayPrincipal());
				productRepayRecordService.update(productRepayRecord2);
			}
			
			
		} else if (orderType.equals(OrderType.REPAYMENT_hwsoft_NORMAL_AUTO)
				|| orderType.equals(OrderType.REPAYMENT_hwsoft_NORMAL_HANLDLE)) { //正常还款
			
			if(null == repaymentIds || repaymentIds.size() == 0){
				throw new RepaymentException("请指定还款记录");
			}
			if(repaymentIds.size() > 1){
				throw new RepaymentException("正常还款一次只能还一期");
			}
			
			RepaymentProductOrder repaymentProductOrder = new RepaymentProductOrder();
			repaymentProductOrder.setCreateTime(new Date());
			repaymentProductOrder.setOrderStatus(OrderStatus.HANDLE_SUCESS);
			repaymentProductOrder.setOrderType(orderType);
			repaymentProductOrder.setProductId(productId);
			repaymentProductOrder.setStaffId(staffId);
			repaymentProductOrder.setStaffName(staffName);
			repaymentProductOrder.setProductRepayRecordId(repaymentIds.get(0));
			
			ProductRepayRecord productRepayRecord = productRepayRecordService.findById(repaymentIds.get(0));
			
			repaymentProductOrder.setDefaultInterest(0);//罚息
			repaymentProductOrder.setInterest(productRepayRecord.getRepayInterest());//利息
			repaymentProductOrder.setPrincipal(productRepayRecord.getRepayPrincipal());//本金
			
			//处理详情
			List<RepaymentProductDetail> repaymentProductDetails = new ArrayList<RepaymentProductDetail>();
			
			//首先查询出份额登记表
			List<ProductOfthatRegister> productOfthatRegisters = productOfThatRegisterService.findByProductId(productId);
			for(ProductOfthatRegister productOfthatRegister : productOfthatRegisters){
				//总份额
//				int totalCopies = product.getTotalCopies();
				if(productOfthatRegister.getOfThatNumber() <= 0 ){//当前持有份额
					continue;
				}
				if(productOfthatRegister.getStatus().equals(ProductOfThatStatus.CONFIRMING)
						|| productOfthatRegister.getStatus().equals(ProductOfThatStatus.EXITED)
						|| productOfthatRegister.getStatus().equals(ProductOfThatStatus.TRANSFERED)
						|| productOfthatRegister.getStatus().equals(ProductOfThatStatus.FAILED)){
					continue;
				}
				
				// 计算利息，罚息，本金
				double totalDefaultInterest = repaymentProductOrder.getDefaultInterest();
				double totalInterest = repaymentProductOrder.getInterest();
				double totalPrincipal = repaymentProductOrder.getPrincipal();
				
				double defaultInterest = CalculateUtil.doubleDivide(CalculateUtil.doubleMultiply(totalDefaultInterest, productOfthatRegister.getOfThatNumber()),realTotalCopies,4);
				double interest = CalculateUtil.doubleDivide(CalculateUtil.doubleMultiply(totalInterest, productOfthatRegister.getOfThatNumber()),realTotalCopies,4);
				double principal = CalculateUtil.doubleDivide(CalculateUtil.doubleMultiply(totalPrincipal, productOfthatRegister.getOfThatNumber()),realTotalCopies,4);
				
				RepaymentProductDetail repaymentProductDetail = new RepaymentProductDetail();
				repaymentProductDetail.setDefaultInterest(defaultInterest);
				repaymentProductDetail.setInterest(interest);
				repaymentProductDetail.setProductId(productId);
				repaymentProductDetail.setPrincipal(principal);
				repaymentProductDetail.setProductSubAccountId(productOfthatRegister.getProductSubAccountId());
				repaymentProductDetails.add(repaymentProductDetail);
				
				
				
			}
			
			repaymentProductOrder.setDetails(repaymentProductDetails);
			repaymentProductOrderDao.save(repaymentProductOrder);
			//订单编号
			String orderFormId = OrderUtil.getOrderFormId(repaymentProductOrder.getOrderType().ordinal(), 
					repaymentProductOrder.getId(), BusinessConf.hwsoft_ORDER_SERIALNUMBER_LENGTH);
			repaymentProductOrder.setOrderFormId(orderFormId);
			// 判断借款人用户账户资金是否足够
			if(customerSubAccountPoint.getAvailablePoints() >= CalculateUtil.doubleAdd(repaymentProductOrder.getPrincipal(), repaymentProductOrder.getInterest(), 4)){
				notes = "用户正常还款，产品【"+product.getProductName()+"】正常还款，本金【"+repaymentProductOrder.getPrincipal()+"】,利息【"+repaymentProductOrder.getInterest()+"】";
			} else {
				notes = "风险金垫付还款，产品【"+product.getProductName()+"】正常还款，本金【"+repaymentProductOrder.getPrincipal()+"】,利息【"+repaymentProductOrder.getInterest()+"】";
			}
			// 处理订单日志
			repaymentProductLogService.addRepaymentProductLog(productId, orderFormId, staffId, staffName, notes);
			// 处理还款记录
			productService.updateRepaymentProduct(productId, repaymentProductOrder.getOrderFormId());
			
			
			
		} else if(orderType.equals(OrderType.REPAYMENT_hwsoft_OVERDUE_AUTO)
				|| orderType.equals(OrderType.REPAYMENT_hwsoft_OVERDUE_HANDLE)){//逾期还款

			if(null == repaymentIds || repaymentIds.size() == 0){
				throw new RepaymentException("请指定还款记录");
			}
			// 首先是添加订单，让后调用productService.updateRepaymentProduct(productId, orderFormId);
			for(Integer repaymentId : repaymentIds){
				ProductRepayRecord productRepayRecord = productRepayRecordService.findById(repaymentId);
				if(productRepayRecord.getStatus().equals(ProductRepayRecordStatus.OVER_DUE)){// 如果是逾期则进行逾期处理
					// 添加订单
					RepaymentProductOrder repaymentProductOrder = new RepaymentProductOrder();
					repaymentProductOrder.setCreateTime(new Date());
					repaymentProductOrder.setOrderStatus(OrderStatus.HANDLE_SUCESS);
					repaymentProductOrder.setOrderType(orderType);
					repaymentProductOrder.setProductId(productId);
					repaymentProductOrder.setStaffId(staffId);
					repaymentProductOrder.setStaffName(staffName);
					repaymentProductOrder.setProductRepayRecordId(repaymentId);
					double defaultInterest = 0D;
					repaymentProductOrder.setDefaultInterest(defaultInterest);//TODO 暂时罚息为0
					repaymentProductOrder.setInterest(productRepayRecord.getRepayInterest());//利息
					repaymentProductOrder.setPrincipal(productRepayRecord.getRepayPrincipal());//本金
					
					// 判断借款人用户账户资金是否足够
					notes = "逾期还款，产品【"+product.getProductName()+"】逾期还款，本金【"+repaymentProductOrder.getPrincipal()
							+"】,利息【"+repaymentProductOrder.getInterest()
							+"】,罚息【"+defaultInterest+"】";
					
					repaymentProductOrderDao.save(repaymentProductOrder);
					//订单编号
					String orderFormId = OrderUtil.getOrderFormId(repaymentProductOrder.getOrderType().ordinal(), 
							repaymentProductOrder.getId(), BusinessConf.hwsoft_ORDER_SERIALNUMBER_LENGTH);
					repaymentProductOrder.setOrderFormId(orderFormId);
					// 处理订单日志
					repaymentProductLogService.addRepaymentProductLog(productId, orderFormId, staffId, staffName, notes);
					// 处理还款记录
					productService.updateRepaymentProduct(productId, orderFormId);
					
				} else {
					//其他状态均不予处理
				}
			}
		}
		return null;
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public RepaymentProductOrder findByOrderFormI(String orderFormId) {
		return repaymentProductOrderDao.findByOrderFormI(orderFormId);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<RepaymentProductOrderVo> listAll(OrderStatus orderStatus,
			Date startTime, Date endTime, int from, int pageSize) {
		return repaymentProductOrderDao.listAll(orderStatus, startTime, endTime, from, pageSize);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public Long getTotalCount(OrderStatus orderStatus, Date startTime,
			Date endTime) {
		return repaymentProductOrderDao.getTotalCount(orderStatus, startTime, endTime);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public RepaymentProductOrderVo findWholeById(int id) {
		RepaymentProductOrderVo vo = repaymentProductOrderDao.findVoById(id);
		
		if(null != vo){
			List<RepaymentProductDetailVo> detailVos = repaymentProductOrderDao.findDetailVosByOrderId(id);
			vo.setDetailVos(detailVos);
		}
		return vo;
	}

}
