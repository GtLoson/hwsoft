/**
 *
 */
package com.hwsoft.service.product.impl;

import com.hwsoft.common.conf.Conf;
import com.hwsoft.common.customer.CustomerSource;
import com.hwsoft.common.order.OrderType;
import com.hwsoft.common.point.CustomerSubAccountPointType;
import com.hwsoft.common.product.ProductBidType;
import com.hwsoft.common.product.ProductChannelType;
import com.hwsoft.common.product.ProductRecoveryRecordType;
import com.hwsoft.common.product.ProductRepayRecordStatus;
import com.hwsoft.common.product.ProductCornerType;
import com.hwsoft.common.product.ProductStatus;
import com.hwsoft.dao.product.ProductDao;
import com.hwsoft.exception.BaseException;
import com.hwsoft.exception.bid.BidException;
import com.hwsoft.exception.failed.FailedProductException;
import com.hwsoft.exception.lending.LendingException;
import com.hwsoft.exception.product.ProductException;
import com.hwsoft.exception.repayment.RepaymentException;
import com.hwsoft.model.bank.Bank;
import com.hwsoft.model.customer.Customer;
import com.hwsoft.model.customer.CustomerSubAccount;
import com.hwsoft.model.order.hwsoft.RepaymentProductDetail;
import com.hwsoft.model.order.hwsoft.RepaymentProductOrder;
import com.hwsoft.model.point.CustomerSubAccountPoint;
import com.hwsoft.model.product.*;
import com.hwsoft.service.bank.BankService;
import com.hwsoft.service.customer.CustomerService;
import com.hwsoft.service.customer.CustomerSubAccountService;
import com.hwsoft.service.log.customer.CustomerSubAccountPointLogService;
import com.hwsoft.service.order.hwsoft.RepaymentProductOrderService;
import com.hwsoft.service.order.hwsoft.RiskPointOrderService;
import com.hwsoft.service.point.CustomerSubAccountPointService;
import com.hwsoft.service.product.*;
import com.hwsoft.service.statistics.ProductStatisticsService;
import com.hwsoft.util.date.DateTools;
import com.hwsoft.util.interest.InterestCalculatorModeUtil;
import com.hwsoft.util.math.CalculateUtil;
import com.hwsoft.vo.product.BaseProductVo;
import com.hwsoft.vo.product.ProductVo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author tzh
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {

	private Log logger = LogFactory.getLog(ProductServiceImpl.class);

	@Autowired
	private ProductDao productDao;

	@Autowired
	private ProductSubAccountService productSubAccountService;

	@Autowired
	private ProductBankService productBankService;

	@Autowired
	private ProductSaleTimeInfoService productSaleTimeInfoService;
	@Autowired
	private ProductAgreementTemplateService productAgreementTemplateService;

	@Autowired
	private ProductChannelService productChannelService;

	@Autowired
	private ProductAccountService productAccountService;

	@Autowired
	private BankService bankService;

	@Autowired
	private ProductInfoService productInfoService;

	@Autowired
	private ProductRiskControlService productRiskControlService;

	@Autowired
	private ProductOfThatRegisterService productOfThatRegisterService;

	@Autowired
	private ProductRepayRecordService productRepayRecordService;

	@Autowired
	private RepaymentProductOrderService repaymentProductOrderService;

	@Autowired
	private CustomerSubAccountPointService customerSubAccountPointService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerSubAccountService customerSubAccountService;

	@Autowired
	private CustomerSubAccountPointLogService customerSubAccountPointLogService;

	@Autowired
	private RiskPointOrderService riskPointOrderService;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	private Product addProduct(Product product) {
		return productDao.save(product);
	}

	/**
	 * 更新产品角标
	 * 
	 * @param productId
	 * @param productCornerType
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public Product updateProductCornerType(int productId,
			ProductCornerType productCornerType) {
		Product product = findById(productId);
		product.setCornerType(productCornerType);
		return productDao.updateProduct(product);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Product findByProductName(String name) {
		return productDao.getProductByName(name);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public void addImportProductList(List<Product> importList) {
		
		List<Customer> midCustomerData = customerService.findBuyUserSourceType(CustomerSource.hwsoft_MEDIATOR);
		
		if(null == midCustomerData || midCustomerData.size() == 0){
			throw new RuntimeException("没有找到居间人信息");
		}
		if(midCustomerData.size() > 1){
			throw new RuntimeException("查到多个居间人信息居间人信息");
		}
		
		Customer midCustomer = midCustomerData.get(0);

		if(null == midCustomer ){
			throw new RuntimeException("没有找到居间人信息");
		}
		
		ProductChannel productChannel = productChannelService.findByType(ProductChannelType.hwsoft);
		
		if(null == productChannel){
			throw new RuntimeException("钱妈妈渠道信息没找到");
		}
		
		CustomerSubAccount customerSubAccount = customerSubAccountService.findByCustomerSubAccountByUserIdAndProductChannelId(midCustomer.getId(),productChannel.getId());
		

		if (null != importList && importList.size() != 0) {
			for (Product product : importList) {
				// 先要判断数据是否存在，如果存在则抛异常
				Product product2 = findByProductName(product.getProductName());
				if (null == product2) {
					product.setProductStatus(ProductStatus.THIRD_AUDIT);
					product.setCustomerSubAccountId(customerSubAccount.getId());
					addProduct(product);
				} else {
					throw new BaseException("计划名称已存在："
							+ product.getProductName());
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hwsoft.service.product.ProductService#addProductList(java.util.
	 * List)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public void addProductList(List<Product> productList) {
		if (null != productList && productList.size() != 0) {
			for (Product product : productList) {
				// 先要判断数据是否存在，如果存在则进行更新
				Product product2 = findProductByThirdId(
						product.getProductChannelId(),
						product.getThirdProductId());
				if (null == product2) {

					product.setProductStatus(ProductStatus.THIRD_AUDIT);
					addProduct(product);
				} else {
					// 进行更新操作
					product2.setRemainingAmount(product.getRemainingAmount());
					product2.setPaidAmount(product.getPaidAmount());
					product2.setSaleEndTime(product.getSaleEndTime());
					product2.setTotalAmount(product.getTotalAmount());
					product2.setTotalCopies(product.getTotalCopies());
					if (!product2.getProductStatus().equals(
							ProductStatus.THIRD_AUDIT)
							&& !product2.getProductStatus().equals(
									ProductStatus.AUDIT)) {
						product2.setProductStatus(product.getProductStatus());
					} else {
						if (product.getProductStatus().equals(
								ProductStatus.CANCEL)) {
							product2.setProductStatus(product
									.getProductStatus());
						}

					}
					if (product2.getProductStatus().ordinal() >= ProductStatus.SALES_OVER
							.ordinal()) {
						product2.setCornerType(ProductCornerType.SALE_OVER);
					}
					productDao.updateProduct(product2);
				}
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Product> listAll(int from, int pageSize) {
		return productDao.list(from, pageSize);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Long getTotalCount() {
		return productDao.getTotalCount();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hwsoft.service.product.ProductService#findProductByThirdId(java
	 * .lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Product findProductByThirdId(int productChannelId, String thirdId) {
		return productDao.findProductByThirdId(productChannelId, thirdId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hwsoft.service.product.ProductService#findById(int)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Product findById(int productId) {
		return productDao.findById(productId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hwsoft.service.product.ProductService#updateProductWaittingAmount
	 * (int, double)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public Product updateProductWaittingAmount(int id, double waittingAmount,double amount) {

		Product product = findById(id);
		if (null == product) {
			throw new ProductException("产品不存在");
		}
		product.setWaittingPayAmount(waittingAmount);

		product.setRemainingAmount(CalculateUtil.doubleSubtract(
				product.getRemainingAmount(), amount, 4));

		return productDao.updateProduct(product);
	}
	
	@Autowired
	private ProductStatisticsService productStatisticsService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hwsoft.service.product.ProductService#updateProductByPaySuccess
	 * (int, double)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public Product updateProductByPaySuccess(int id, int buyerRecordId,double amount) {
		Product product = findById(id);
		if (null == product) {
			throw new ProductException("产品不存在");
		}
		ProductSubAccount productBuyerRecord = productSubAccountService
				.findById(buyerRecordId);
		if (null == productBuyerRecord) {
			throw new ProductException("产品订单未找到");
		}
		
		// 计算支付金额
		ProductChannel productChannel = productChannelService.findById(product.getProductChannelId());
		
		if (productChannel.getProductChannelType().equals(ProductChannelType.hwsoft)) { //钱妈妈，
			
			// 查询销售份额
			double shareNum = productStatisticsService.querySalesNum(id);
			System.out.println("销售份额："+shareNum);
			System.out.println("before待支付份额："+product.getWaittingPayAmount());
			//待支付金额
			product.setWaittingPayAmount(CalculateUtil.doubleSubtract(
					product.getWaittingPayAmount(),
					amount, 4));
			System.out.println("after待支付份额："+product.getWaittingPayAmount());
			//放款金额
			product.setOpenAmount(CalculateUtil.doubleMultiply(product.getBaseAmount(), shareNum, 4));
			System.out.println("放款金额："+product.getOpenAmount());
			//已销售金额
			product.setSaleAmount(shareNum);
			product.setPaidAmount(product.getOpenAmount());
			Double dummyBoughtAmount = 0D;
			if(product.isEnableDummyBoughtAmount() && product.getDummyBoughtAmount() >= 0){
				dummyBoughtAmount = product.getDummyBoughtAmount();
			}
			System.out.println(dummyBoughtAmount+"~~~~~~~~~~~~~"+product.getRemainingAmount());
			System.out.println(product.getRemainingAmount().intValue()-dummyBoughtAmount.intValue() <= 0);
			if (product.getRemainingAmount().intValue()-dummyBoughtAmount.intValue() <= 0) {
				product.setProductStatus(ProductStatus.SALES_OVER);
				product.setReadyTime(new Date());
				product.setCornerType(ProductCornerType.SALE_OVER);
			}

		} else { //共鸣
			
			double saleAmount = productStatisticsService.querySalesAmount(id);
			System.out.println("已支付金额："+saleAmount);
			
			product.setOpenAmount(saleAmount);
			product.setSaleAmount(saleAmount);
			product.setPaidAmount(saleAmount);
			// total-pay-remaining
			double tmpAmount = CalculateUtil.doubleSubtract(product.getTotalAmount(),saleAmount,4);
			double waitingAmount = CalculateUtil.doubleSubtract(tmpAmount,product.getRemainingAmount(),4);
			product.setWaittingPayAmount(waitingAmount);
		}


		return productDao.updateProduct(product);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hwsoft.service.product.ProductService#updateByOrderTerminated(int,
	 * int)
	 */
	@Override
	public Product updateByOrderTerminated(int id, int buyerRecordId) {
		Product product = findById(id);
		if (null == product) {
			throw new ProductException("产品不存在");
		}
		ProductSubAccount productBuyerRecord = productSubAccountService
				.findById(buyerRecordId);
		if (null == productBuyerRecord) {
			throw new ProductException("产品订单未找到");
		}
		// product.setOpenAmount(CalculateUtil.doubleAdd(product.getOpenAmount(),
		// productBuyerRecord.getSuccessAmount(), 2));
		// product.setSaleAmount(CalculateUtil.doubleAdd(product.getSaleAmount(),
		// productBuyerRecord.getSuccessAmount(), 2));
		ProductChannel productChannel = productChannelService.findById(product.getProductChannelId());

		if (productChannel.getProductChannelType().equals(ProductChannelType.hwsoft)) { //钱妈妈
			product.setWaittingPayAmount(CalculateUtil.doubleSubtract(
					product.getWaittingPayAmount(),
					productBuyerRecord.getShareNum(), 2));
		}else{
			product.setRemainingAmount(CalculateUtil.doubleAdd(product.getRemainingAmount(),productBuyerRecord.getAmount(),4));
			product.setWaittingPayAmount(CalculateUtil.doubleSubtract(
					product.getWaittingPayAmount(),
					productBuyerRecord.getAmount(), 2));
		}
		
		/*
		if (null != productBuyerRecord.getProductBidType()
				&& !productBuyerRecord.getProductBidType().equals(
						ProductBidType.GONGMING_PRODUCT_BUY)) {
			product.setWaittingPayAmount(CalculateUtil.doubleSubtract(
					product.getWaittingPayAmount(),
					productBuyerRecord.getShareNum(), 2));
		} else {
			product.setRemainingAmount(CalculateUtil.doubleAdd(product.getRemainingAmount(),productBuyerRecord.getAmount(),4));
			product.setWaittingPayAmount(CalculateUtil.doubleSubtract(
					product.getWaittingPayAmount(),
					productBuyerRecord.getAmount(), 2));
		}*/
		return productDao.updateProduct(product);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hwsoft.service.product.ProductService#addToSale(java.lang.Integer,
	 * java.lang.Integer, java.util.List, java.lang.Integer, java.lang.Integer,
	 * java.lang.Integer, java.lang.Integer)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public Product addToSale(Integer productId, Integer agreementId,
			List<Integer> bankIds, Integer startHour, Integer startMinute,
			Integer endHour, Integer endMinute) {
		Product product = findById(productId);
		if (null == product) {
			throw new ProductException("产品未找到");
		}
		if (!product.getProductStatus().equals(ProductStatus.THIRD_AUDIT)) {
			throw new ProductException("该产品不能进行此操作");
		}
		// 处理银行卡问题
		productBankService.addBatch(bankIds, productId);
		// 处理协议问题
		productAgreementTemplateService.add(productId, agreementId);
		// 添加产品可售时间
		productSaleTimeInfoService.addProductSaleTimeInfo(productId, startHour,
				startMinute, endHour, endMinute);
		product.setProductStatus(ProductStatus.AUDIT);// 首先进入审核中，然后再在钱妈妈可售中去处理
		return productDao.updateProduct(product);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hwsoft.service.product.ProductService#getTotalCount(com.hwsoft
	 * .common.product.ProductChannelType,
	 * com.hwsoft.common.product.ProductStatus)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Long getTotalCount(ProductChannelType productChannelType,
			String productName, Boolean show, ProductStatus... productStatus) {
		ProductChannel productChannel = productChannelService
				.findByType(productChannelType);
		Integer productChannelId = null;
		if (null != productChannel) {
			productChannelId = productChannel.getId();
		}
		return productDao.getTotalCount(productChannelId, productName, show,
				productStatus);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hwsoft.service.product.ProductService#listAll(com.hwsoft.common
	 * .product.ProductChannelType, com.hwsoft.common.product.ProductStatus,
	 * int, int)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Product> listAll(ProductChannelType productChannelType,
			String productName, int from, int pageSize, Boolean show,
			ProductStatus... productStatus) {
		ProductChannel productChannel = productChannelService
				.findByType(productChannelType);
		Integer productChannelId = null;
		if (null != productChannel) {
			productChannelId = productChannel.getId();
		}
		return productDao.listAll(productChannelId, productName, from,
				pageSize, show, productStatus);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hwsoft.service.product.ProductService#findProductVoByProductId(int)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ProductVo findProductVoByProductId(int productId) {
		Product product = findById(productId);
		if (null != product) {
			ProductVo productVo = new ProductVo();
			productVo.setProduct(product);
			ProductChannel productChannel = productChannelService
					.findById(product.getProductChannelId());
			ProductSaleTimeInfo productSaleTimeInfo = productSaleTimeInfoService
					.findByProductId(product.getId());
			productVo.setProductChannel(productChannel);
			productVo.setProductSaleTimeInfo(productSaleTimeInfo);

			// 　还有协议和银行卡
			List<Bank> bankList = bankService.getListByProductId(productId);
			productVo.setBankList(bankList);
			return productVo;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hwsoft.service.product.ProductService#publish(int, boolean)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public Product publish(int productId, boolean recommend,
			boolean enableDummyBoughtAmount, Double dummyBoughtAmount,
			boolean enableDummyBoughtCount, Integer dummyBoughtCount,
			Date startInterestBearingTime, Date endInterestBearingTime,
			Date publishTime, String desc, String desciptionTitle,
			String mortgageDesc, List<String> picPaths, String riskDesc,
			List<String> riskPicPaths, String activityDesc,String mortgager,String guaranteeCompany) {
		Product product = findById(productId);
		if (null == product) {
			throw new ProductException("产品未找到");
		}
		if (!product.getProductStatus().equals(ProductStatus.AUDIT)) {
			throw new ProductException("该产品不能进行此操作");
		}
		if (recommend) {// 如果是推荐，那么其他产品全部为非推荐产品
			Product recommendProduct = productDao
					.findProductByRecommend(product.getProductChannelId());
			if (null != recommendProduct) {
				recommendProduct.setRecommend(false);
				productDao.updateProduct(recommendProduct);
			}
		}
		product.setRecommend(recommend);
		product.setShow(true);
		product.setEnableDummyBoughtAmount(enableDummyBoughtAmount);
		product.setCornerType(ProductCornerType.ACTIVITY);
		product.setEnableDummyBoughtCount(enableDummyBoughtCount);
		if (enableDummyBoughtAmount && dummyBoughtAmount == null) {
			throw new ProductException("虚增金额不能为空");
		}
		if (enableDummyBoughtAmount && dummyBoughtAmount < 0) {
			throw new ProductException("虚增金额不能小于0");
		}
		if (enableDummyBoughtCount && dummyBoughtCount == null) {
			throw new ProductException("虚增人数不能为空");
		}
		if (enableDummyBoughtCount && dummyBoughtCount < 0) {
			throw new ProductException("虚增人数不能小于0");
		}
		product.setDummyBoughtAmount(dummyBoughtAmount);
		product.setDummyBoughtCount(dummyBoughtCount);

		
		
		ProductChannel productChannel = productChannelService.findById(product.getProductChannelId());
		
		if(ProductChannelType.GONGMING.equals(productChannel.getProductChannelType())) {
			if (null == startInterestBearingTime) {
				throw new ProductException("起息日不能为空");
			}

			if (null == endInterestBearingTime) {
				throw new ProductException("计息结束日不能为空");
			}

			if (startInterestBearingTime.after(endInterestBearingTime)) {
				throw new ProductException("起息日不能晚于计息结束日");
			}

			product.setStartInterestBearingDate(startInterestBearingTime);

			product.setEndInterestBearingDate(endInterestBearingTime);
			
		}

		if (null == publishTime) {
			product.setProductStatus(ProductStatus.SALES);// 销售中
		} else {
			product.setProductStatus(ProductStatus.PREPARATION);// 筹备中
			product.setSaleStartTime(publishTime);// 定时发布
		}
		// 处理productInfo
		productInfoService.add(productId, desc, desciptionTitle, mortgageDesc,
				picPaths, activityDesc);

		productRiskControlService.add(productId, riskDesc, riskPicPaths,mortgager,guaranteeCompany);

		return productDao.updateProduct(product);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hwsoft.service.product.ProductService#recommend(int, boolean)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public Product recommend(int productId, boolean recommand) {
		Product product = findById(productId);
		if (null == product) {
			throw new ProductException("产品未找到");
		}
		if (!product.getProductStatus().equals(ProductStatus.AUDIT)
				&& !product.getProductStatus()
						.equals(ProductStatus.PREPARATION)
				&& !product.getProductStatus().equals(ProductStatus.SALES)) {
			throw new ProductException("该产品不能进行此操作");
		}
		if (recommand) {// 如果是推荐，那么其他产品全部为非推荐产品

			Product recommendProduct = productDao
					.findProductByRecommend(product.getProductChannelId());
			if (null != recommendProduct) {
				recommendProduct.setRecommend(false);
				productDao.updateProduct(recommendProduct);
			}
		}
		product.setRecommend(recommand);
		return productDao.updateProduct(product);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hwsoft.service.product.ProductService#findRecommend()
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseProductVo findRecommend(int productChannelId) {
		Product product = findProductByRecommend(productChannelId);
		return productToBaseProductVo(product);
	}

	private BaseProductVo productToBaseProductVo(Product product) {

		if (null == product) {
			return null;
		}

		BaseProductVo baseProductVo = new BaseProductVo();
		double InflatedAmount = 0D; // 虚增金额
		if (product.isEnableDummyBoughtAmount()
				&& null != product.getDummyBoughtAmount()) {
			InflatedAmount = product.getDummyBoughtAmount();
		}
		int InflatedNumber = 0;// 虚增人数
		if (product.isEnableDummyBoughtCount()
				&& null != product.getDummyBoughtCount()) {
			InflatedNumber = product.getDummyBoughtCount();
		}
		baseProductVo.setBaseAmount(new BigDecimal(product.getBaseAmount())); // 递进金额倍数
		baseProductVo.setId(product.getId()); // id
		baseProductVo.setInterest(product.getInterest()); // 利率，针对固定收益类产品
		baseProductVo.setMaturityDuration(product.getMaturityDuration()
				+ product.getMaturityUnit().suffix());
		baseProductVo.setMaxAmount(new BigDecimal(product.getMaxAmount())); // 最大可购买金额
		baseProductVo.setMaxInterest(product.getMaxInterest()); // 收益上限
		baseProductVo.setMinAmount(new BigDecimal(product.getMinAmount())); // 起投金额
		baseProductVo.setMinInterest(product.getMinInterest()); // 收益下限
		baseProductVo.setProductChannelId(product.getProductChannelId());// 产品渠道id
		baseProductVo.setProductInterestType(product.getProductInterestType()
				.toString()); // 收益类型
		baseProductVo.setProductName(product.getProductName()); // 产品名称
		baseProductVo.setProductStatus(product.getProductStatus()); // 产品状态
		baseProductVo.setProductStatusValue(product.getProductStatus()
				.toString()); // 产品状态value
		baseProductVo.setRecommend(product.isRecommend()); // 是否推荐
		baseProductVo.setTotalCopies(product.getTotalCopies());// 总份数
		
		baseProductVo.setPassTime(product.getPassTime());
		
		ProductCornerType productCornerType = product.getCornerType();
		if (null != productCornerType) {
			baseProductVo.setCornerType(product.getCornerType().name()); // 产品角标
		} else {
			baseProductVo.setCornerType("");
		}
		ProductChannel productChannel = productChannelService.findById(product.getProductChannelId());
		double finishRatio = 0;
		if(productChannel.getProductChannelType().equals(ProductChannelType.GONGMING)){
			//剩余可销售金额=真实剩余可销售金额-虚增金额
			baseProductVo.setRemainingAmount(new BigDecimal(
					CalculateUtil.doubleSubtract(product.getRemainingAmount(),
							InflatedAmount, 2)));
			// if(product.isEnableDummyBoughtAmount()){
			// 总金额=产品总金额
			baseProductVo.setTotalAmount(new BigDecimal(CalculateUtil.doubleAdd(
					product.getTotalAmount(), 0, 4)));

			// 已经销售金额 = 总金额-剩余可销售金额 (=剩余金额 + 虚增金额)
			baseProductVo.setSaleAmount(new BigDecimal(CalculateUtil
					.doubleSubtract(baseProductVo.getTotalAmount().doubleValue(),
							baseProductVo.getRemainingAmount().doubleValue(), 4)));
			// 融资完成率
			finishRatio = CalculateUtil.doubleDivide(CalculateUtil
					.doubleMultiply(baseProductVo.getSaleAmount().doubleValue(),
							100, 4), baseProductVo.getTotalAmount().doubleValue(),
					4);
		} else {
			//剩余可销售金额=真实剩余可销售金额-虚增金额
			baseProductVo.setRemainingAmount(new BigDecimal(
					CalculateUtil.doubleSubtract(product.getRemainingAmount(),
							InflatedAmount, 2)));
			// if(product.isEnableDummyBoughtAmount()){
			// 总金额=产品总金额
			baseProductVo.setTotalAmount(new BigDecimal(CalculateUtil.doubleAdd(
					product.getTotalAmount(), 0, 4)));

			// 已经销售金额 = 总金额-剩余可销售金额 (=剩余金额 + 虚增金额)
			baseProductVo.setSaleAmount(new BigDecimal(CalculateUtil
					.doubleSubtract(baseProductVo.getTotalCopies(),
							baseProductVo.getRemainingAmount().doubleValue(), 4)));
			// 融资完成率
			finishRatio = CalculateUtil.doubleDivide(CalculateUtil
					.doubleMultiply(baseProductVo.getSaleAmount().intValue(),
							100, 4), baseProductVo.getTotalCopies(),
					4);
		}
		
		
		if (finishRatio < 0.01
				&& baseProductVo.getSaleAmount().doubleValue() > 0) {
			finishRatio = 0.01;
		} 
		if(finishRatio > 100){
			finishRatio = 100;
		}
		baseProductVo.setFinishRatio(finishRatio);
		
		if(finishRatio >= 100 && !ProductCornerType.LENDING.equals(product.getCornerType()) && !ProductCornerType.CLOSED.equals(product.getCornerType())){
			baseProductVo.setCornerType(ProductCornerType.SALE_OVER.name()); // 产品角标
		}
		// } else {

		// }
		int num = (int) productAccountService.totalBuyNum(product.getId());
		// 购买人数，先按人数统计，
		// if(product.isEnableDummyBoughtCount()){
		num = InflatedNumber + num;
		// }
		baseProductVo.setTotalBuyNumber(num);

		ProductSaleTimeInfo productSaleTimeInfo = productSaleTimeInfoService
				.findByProductId(product.getId());
		if (null != productSaleTimeInfo) {
			baseProductVo.setStartBuyTime(productSaleTimeInfo.getStartHour()
					+ "时" + productSaleTimeInfo.getStartMinute() + "分");
			baseProductVo.setEndBuyTime(productSaleTimeInfo.getEndHour() + "时"
					+ productSaleTimeInfo.getEndMinute() + "分");
		} else {
			baseProductVo.setStartBuyTime("0时0分");
			baseProductVo.setEndBuyTime("24时0分");
		}
		if(null != product.getStartInterestBearingDate()){
			baseProductVo.setStartInterestDate(DateTools.dateToString(
					product.getStartInterestBearingDate(),
					DateTools.DATE_PATTERN_DAY));
			
		}
		if(null != product.getEndInterestBearingDate()){
			baseProductVo
					.setEndInterestDate(DateTools.dateToString(
							product.getEndInterestBearingDate(),
							DateTools.DATE_PATTERN_DAY));
		}
		
		baseProductVo.setSaleStartTime(product.getSaleStartTime());
		baseProductVo.setSaleEndTime(product.getSaleEndTime());

		// 详情中添加资产标题和活动描述信息
		ProductInfo productInfo = productInfoService.findByProductId(product
				.getId());

		if (null != productInfo) {
			baseProductVo.setDescriptionTitle(productInfo.getDesciptionTitle());
			baseProductVo.setActivityDescription(productInfo.getActivityDesc());
		}
		return baseProductVo;
	}

	/**
	 * 查询推荐项目
	 * 
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	private Product findProductByRecommend(int productChannelId) {
		return productDao.findProductByRecommend(productChannelId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hwsoft.service.product.ProductService#listAll(com.hwsoft.common
	 * .product.ProductStatus, int, int)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BaseProductVo> listAllByStatus(
			ProductChannelType productChannelType, int from, int pageSize,
			Boolean show, ProductStatus... productStatus) {

		List<Product> products = listAll(productChannelType, null, from,
				pageSize, show, productStatus);
		List<BaseProductVo> productVos = new ArrayList<BaseProductVo>();
		if (null != products && products.size() != 0) {
			for (Product product : products) {
				BaseProductVo baseProductVo = productToBaseProductVo(product);
				productVos.add(baseProductVo);
			}
		}
		return productVos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hwsoft.service.product.ProductService#findBaseProductVoById(int)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseProductVo findBaseProductVoById(int productId) {
		Product product = findById(productId);
		return productToBaseProductVo(product);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public Product addOrUpdateProductInfo(int productId, String desc,
			String desciptionTitle, String mortgageDesc, List<String> picPaths,
			String riskDesc, List<String> riskPicPaths, String activityDesc,String mortgager,String guaranteeCompany) {
		Product product = findById(productId);
		if (null == product) {
			throw new ProductException("产品未找到");
		}
		if (!product.getProductStatus().equals(ProductStatus.AUDIT)) {
			throw new ProductException("该产品不能进行此操作");
		}

		ProductInfo productInfo = productInfoService.findByProductId(productId);
		if (null != productInfo) {
			productInfo.setDesciption(desc);
			productInfo.setDesciptionTitle(desciptionTitle);
			productInfo.setMortgageDesc(mortgageDesc);

			List<String> paths = productInfo.getPicPaths();
			picPaths.addAll(picPaths);
			productInfo.setPicPaths(paths);
			productInfo.setActivityDesc(activityDesc);
			productInfoService.update(productInfo);
		} else {

			// 处理productInfo
			productInfoService.add(productId, desc, desciptionTitle,
					mortgageDesc, picPaths, activityDesc);
		}

		ProductRiskControl productRiskControl = productRiskControlService
				.findByProductId(productId);
		if (null != productRiskControl) {
			// TODO 更新
		} else {
			productRiskControlService.add(productId, riskDesc, riskPicPaths,mortgager,guaranteeCompany);
		}

		return product;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Integer> listALlByStatus(ProductStatus productStatus) {
		return productDao.listALlByStatus(productStatus);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public Product updateProductStatus(int productId, ProductStatus status) {

		Product product = findById(productId);
		if (null == product) {
			throw new ProductException("产品未找到");
		}
		// if(!product.getProductStatus().equals(ProductStatus.AUDIT)){
		// throw new RuntimeException("该产品不能进行此操作");
		// }
		product.setProductStatus(status);
		return productDao.updateProduct(product);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public Product updateDummy(int productId, boolean enableDummyBoughtAmount,
			Double dummyBoughtAmount, boolean enableDummyBoughtCount,
			Integer dummyBoughtCount) {
		Product product = findById(productId);
		if (null == product) {
			throw new ProductException("产品未找到");
		}
		if (!product.getProductStatus().equals(ProductStatus.SALES)
				&& !product.getProductStatus().equals(ProductStatus.SALES_OVER)) {
			throw new RuntimeException("该产品不能进行此操作");
		}
		product.setEnableDummyBoughtAmount(enableDummyBoughtAmount);
		product.setEnableDummyBoughtCount(enableDummyBoughtCount);
		if (enableDummyBoughtAmount && dummyBoughtAmount == null) {
			throw new ProductException("虚增金额不能为空");
		}
		if (enableDummyBoughtAmount && dummyBoughtAmount < 0) {
			throw new ProductException("虚增金额不能小于0");
		}
		if (enableDummyBoughtAmount
				&& dummyBoughtAmount > CalculateUtil.doubleSubtract(
						product.getTotalAmount(), product.getSaleAmount(), 2)) {
			throw new ProductException("虚增金额超限（虚增金额<=产品总金额-已销售金额）");
		}
		if (enableDummyBoughtCount && dummyBoughtCount == null) {
			throw new ProductException("虚增人数不能为空");
		}
		if (enableDummyBoughtCount && dummyBoughtCount < 0) {
			throw new ProductException("虚增人数不能小于0");
		}
		product.setDummyBoughtAmount(dummyBoughtAmount);
		product.setDummyBoughtCount(dummyBoughtCount);
		
		
		
		

		return productDao.updateProduct(product);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public Product showProduct(Integer productId, boolean show) {
		Product product = findById(productId);
		if (null == product) {
			throw new ProductException("产品未找到");
		}
		if (!product.getProductStatus().equals(ProductStatus.SALES)
				&& !product.getProductStatus().equals(ProductStatus.SALES_OVER)) {
			throw new RuntimeException("该产品不能进行此操作");
		}
		product.setShow(show);
		return productDao.updateProduct(product);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public Product bid(int productId, int shareNum, int productSubAccountId) {

		// TODO 更新产品信息，添加产品份额信息
		Product product = findById(productId);
		if (null == product) {
			logger.info("产品未找到");
			throw new ProductException("产品未找到");
		}
		if (!product.getProductStatus().equals(ProductStatus.SALES)) {
			logger.info("该产品不在销售期");
			throw new ProductException("该产品不在销售期");
		}

		ProductSaleTimeInfo productSaleTimeInfo = productSaleTimeInfoService
				.findByProductId(product.getId());
		if (null != productSaleTimeInfo) {
			// TODO 进行时间判断
			int hour = DateTools.getCurrentStateHour();
			int minute = DateTools.getCurrentStateMinute();

			if (hour < productSaleTimeInfo.getStartHour()
					|| hour > productSaleTimeInfo.getEndHour()) {
				logger.info("对不起，第三方正在结算中，不能购买");
				throw new ProductException("对不起，第三方正在结算中，不能购买");
			}
			if (hour == productSaleTimeInfo.getStartHour()
					&& minute < productSaleTimeInfo.getStartMinute()) {

				logger.info("对不起，第三方正在结算中，不能购买");
				throw new ProductException("对不起，第三方正在结算中，不能购买");
			}

			if (hour == productSaleTimeInfo.getEndHour()
					&& minute > productSaleTimeInfo.getEndMinute()) {
				logger.info("对不起，第三方正在结算中，不能购买");
				throw new ProductException("对不起，第三方正在结算中，不能购买");
			}
		}
		ProductChannel productChannel = productChannelService.findById(product
				.getProductChannelId());
		if (null == productChannel) {
			logger.info("没有找到产品渠道：" + product.getProductChannelId());
			throw new ProductException("没有找到产品渠道："
					+ product.getProductChannelId());
		}
		if (product.getProductChannelId() != productChannel.getId()) {
			logger.info("产品渠道不一致");
			throw new ProductException("产品渠道不一致");
		}
		if (shareNum < new Double(product.getMinAmount()).intValue()) {
			logger.info("不能小于最低购买份额");
			throw new BidException("不能小于最低购买份额");
		}
		double amount = CalculateUtil.doubleMultiply(shareNum,
				product.getBaseAmount(), 4);

		if (CalculateUtil.doubleSubtract(product.getRemainingAmount(), amount,
				2) < 0) {
			logger.info("购买份额超过产品可售份额");
			throw new ProductException("购买份额额超过产品可售份额");
		}

		product.setRemainingAmount(new Double(product.getRemainingAmount())
				- shareNum);

		if (product.getRemainingAmount() == 0) {
			product.setProductStatus(ProductStatus.SALES_OVER);
			product.setReadyTime(new Date());
		}

		return productDao.updateProduct(product);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public Product updateFailedProduct(int productId) {
		Product product = findById(productId);
		if (null == product) {
			throw new FailedProductException("产品不存在");
		}

		if (product.getProductStatus().equals(ProductStatus.LOCK_UP)
				|| product.getProductStatus().equals(
						ProductStatus.OPEN_REDEMPTION)
				|| product.getProductStatus().equals(ProductStatus.REDEMPTION)
				|| product.getProductStatus().equals(ProductStatus.ENDED)) {
			throw new FailedProductException("该产品现在不能流标");
		}

		// 流标处理，
		if (product.getProductStatus().equals(ProductStatus.SALES)
				|| product.getProductStatus().equals(ProductStatus.SALES_OVER)) {
			// 已开标，处理份额，处理产品账户，产品子账户
			// 处理份额
			productOfThatRegisterService
					.updateProductOfthatRegisterForFailedProduct(productId);
			// 处理产品账户，处理产品子账户
			productAccountService.updateForFailed(productId);

		}

		product.setProductStatus(ProductStatus.CANCEL);
		product.setFailedTime(new Date());
		productDao.updateProduct(product);
		return product;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public Product updateLendingProduct(int productId) {
		Product product = findById(productId);
		if (null == product) {
			throw new LendingException("产品不存在");
		}

		if (!product.getProductStatus().equals(ProductStatus.SALES_OVER)) {
			throw new LendingException("该产品现在不能放款");
		}
		Date now = new Date();
		// 处理产品状态
		product.setProductStatus(ProductStatus.LENDING);// 进入还款中
		product.setStartInterestBearingDate(now);
		product.setPassTime(now);
		product.setCornerType(ProductCornerType.LENDING);
		// 放款金额
		product.setOpenAmount(CalculateUtil.doubleMultiply(
				product.getSaleAmount(), product.getBaseAmount(), 4));
		// 处理产品份额
		productOfThatRegisterService.updateLeding(productId);

		// 处理产品账户
		productAccountService.updateForLending(productId);

		// 处理手续费，暂时手续费线下自行处理

		// 还款记录
		int borrowCustomerSubAccountId = product.getCustomerSubAccountId();
		List<ProductRepayRecord> productRepayRecords = InterestCalculatorModeUtil
				.calcutorInterest(product, borrowCustomerSubAccountId);
		productRepayRecordService.addBatch(productRepayRecords);

		return productDao.updateProduct(product);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public Product updateRepaymentProduct(int productId, String orderFormId) {

		// 借款人资金处理（转入平衡金账户，平衡金再进行还款），用户资金处理，还款记录处理，收益记录处理，产品账户处理，产品子账户处理
		Product product = findById(productId);

		RepaymentProductOrder repaymentProductOrder = repaymentProductOrderService
				.findByOrderFormI(orderFormId);

		OrderType orderType = repaymentProductOrder.getOrderType();

		// 处理还款记录, 需要处理是垫付的情况（）
		ProductRepayRecord productRepayRecord = productRepayRecordService
				.findById(repaymentProductOrder.getProductRepayRecordId());

		// 借款人资金
		CustomerSubAccountPoint customerSubAccountPoint = customerSubAccountPointService
				.findCustomerSubAccountPointByCustomerSubAccountId(product
						.getCustomerSubAccountId());
		CustomerSubAccount customerSubAccount = customerSubAccountService
				.findById(product.getCustomerSubAccountId());

		// 平衡金
		Customer balanceCustomer = customerService
				.findByUsername(Conf.SYSTEM_CUSTOMER_BALANCE);
		CustomerSubAccount balanceCustomerSubAccount = customerSubAccountService
				.findByCustomerSubAccountByUserIdAndProductChannelId(
						balanceCustomer.getId(), product.getProductChannelId());

		// Customer balanceCustomer = customerService.findById(6);
		// CustomerSubAccount balanceCustomerSubAccount =
		// customerSubAccountService.findByCustomerSubAccountByUserIdAndProductChannelId(6,
		// 2);
		CustomerSubAccountPoint balanceCustomerSubAccountPoint = customerSubAccountPointService
				.findCustomerSubAccountPointByCustomerSubAccountId(balanceCustomerSubAccount
						.getId());

		// 还款记录

		// 资金真实来源
		Integer realFromCustomerSubAccountId = null;

		Date now = new Date();

		ProductRecoveryRecordType type = null;

		// 1、先将借款人的钱转入平衡金，
		if (orderType.equals(OrderType.REPAYMENT_hwsoft_ADVANCE_ALL_HANDLE)) {// TODO
																				// 提前还款
			realFromCustomerSubAccountId = product.getCustomerSubAccountId();// 只能是借款人（居间人）
			// 将资金转入平衡金账户
			customerSubAccountPoint.setAvailablePoints(CalculateUtil
					.doubleSubtract(
							customerSubAccountPoint.getAvailablePoints(),
							repaymentProductOrder.getTotalRepayAmount()));
			customerSubAccountPointService.update(customerSubAccountPoint);
			// 资金记录处理
			String remark = "产品【" + product.getProductName()
					+ "】提前还款资金由借款人账户转入平衡金账户【"
					+ repaymentProductOrder.getTotalRepayAmount() + "】元，其中本金【"
					+ repaymentProductOrder.getPrincipal() + "】元，利息【"
					+ repaymentProductOrder.getInterest() + "】元，罚息【"
					+ repaymentProductOrder.getDefaultInterest() + "】元";
			customerSubAccountPointLogService.add(
					repaymentProductOrder.getTotalRepayAmount(),
					customerSubAccountPoint.getAvailablePoints(),
					customerSubAccountPoint.getFrozenPoints(),
					CustomerSubAccountPointType.REPAYMENT_INPUT_BALANCE,
					remark, orderFormId,
					customerSubAccountPoint.getCustomerSubAccountId(),
					customerSubAccountPoint.getCustomerSubAccountId(),
					balanceCustomerSubAccount.getId(),
					realFromCustomerSubAccountId, false,
					customerSubAccount.getCustomerId(), productId,null,null);

			// 平衡金资金添加
			balanceCustomerSubAccountPoint
					.setAvailablePoints(CalculateUtil.doubleAdd(
							balanceCustomerSubAccountPoint.getAvailablePoints(),
							repaymentProductOrder.getTotalRepayAmount()));
			customerSubAccountPointService
					.update(balanceCustomerSubAccountPoint);
			String remark1 = "产品【" + product.getProductName()
					+ "】提前还款资金由借款人账户转入平衡金账户【"
					+ repaymentProductOrder.getTotalRepayAmount() + "】元，其中本金【"
					+ repaymentProductOrder.getPrincipal() + "】元，利息【"
					+ repaymentProductOrder.getInterest() + "】元，罚息【"
					+ repaymentProductOrder.getDefaultInterest() + "】元";
			customerSubAccountPointLogService.add(
					repaymentProductOrder.getTotalRepayAmount(),
					balanceCustomerSubAccountPoint.getAvailablePoints(),
					balanceCustomerSubAccountPoint.getFrozenPoints(),
					CustomerSubAccountPointType.REPAYMENT_INPUT_BALANCE,
					remark1, orderFormId,
					balanceCustomerSubAccountPoint.getCustomerSubAccountId(),
					customerSubAccountPoint.getCustomerSubAccountId(),
					balanceCustomerSubAccount.getId(),
					realFromCustomerSubAccountId, false,
					balanceCustomer.getId(), productId,null,null);

			productRepayRecord.setAlreadyRepaidAmount(CalculateUtil.doubleAdd(
					CalculateUtil.doubleAdd(
							repaymentProductOrder.getDefaultInterest(),
							repaymentProductOrder.getInterest(), 4),
					productRepayRecord.getRepayPrincipal(), 4));// 已还金额
			productRepayRecord
					.setAlreadyRepaidDefaultInterest(repaymentProductOrder
							.getDefaultInterest());
			productRepayRecord.setAlreadyRepaidInterest(repaymentProductOrder
					.getInterest());
			productRepayRecord.setAlreadyRepaidPrincipal(productRepayRecord
					.getRepayPrincipal());
			productRepayRecord.setRealyRepayDate(now);
			productRepayRecord.setRepaidByGuarantor(false);
			productRepayRecord.setStatus(ProductRepayRecordStatus.REPAID);

			type = ProductRecoveryRecordType.ADVANCE;// 提前还款

			// TODO 处理所有的未还

			// 2、再将平衡金中的资金按比例转入用户账户(还款分账)
			for (RepaymentProductDetail repaymentProductDetail : repaymentProductOrder
					.getDetails()) {
				productSubAccountService.updateForRepayment(
						repaymentProductDetail.getProductSubAccountId(),
						repaymentProductDetail.getInterest(),
						repaymentProductDetail.getPrincipal(),
						repaymentProductDetail.getDefaultInterest(),
						orderFormId, realFromCustomerSubAccountId,
						productRepayRecord.getId(),
						productRepayRecord.getPhaseNumber(), type);
			}

			// 更新还款记录
			productRepayRecordService.update(productRepayRecord);
			List<ProductRepayRecord> productRepayRecords = productRepayRecordService
					.listAllUnpay(productId);
			if (null == productRepayRecords || productRepayRecords.size() == 0) {
				// throw new RepaymentException("该产品没有未还的记录");
			} else {
				for (int i = 1; i < productRepayRecords.size(); i++) {
					ProductRepayRecord productRepayRecord2 = productRepayRecords
							.get(i);
					productRepayRecord2
							.setAlreadyRepaidAmount(productRepayRecord2
									.getRepayPrincipal());// 已还金额
					productRepayRecord2.setAlreadyRepaidDefaultInterest(0D);
					productRepayRecord2.setAlreadyRepaidInterest(0D);
					productRepayRecord2
							.setAlreadyRepaidPrincipal(productRepayRecord2
									.getRepayPrincipal());
					productRepayRecord2.setRealyRepayDate(now);
					productRepayRecord2.setRepaidByGuarantor(false);
					productRepayRecord2
							.setStatus(ProductRepayRecordStatus.REPAID);

					productRepayRecordService.update(productRepayRecord2);
				}
			}

			// 结标
			product.setProductStatus(ProductStatus.ENDED);// 结标
			product.setCornerType(ProductCornerType.CLOSED);
			product.setClosedTime(new Date());
			productDao.updateProduct(product);

			// 更新产品账户和产品子账户
			productSubAccountService.updateOver(product.getId());

		} else if (orderType.equals(OrderType.REPAYMENT_hwsoft_NORMAL_AUTO)
				|| orderType
						.equals(OrderType.REPAYMENT_hwsoft_NORMAL_HANLDLE)) { // 正常还款
			// 判断资金是否足额
			if (customerSubAccountPoint.getAvailablePoints() >= repaymentProductOrder
					.getTotalRepayAmount()) {// 资金足额
				realFromCustomerSubAccountId = product
						.getCustomerSubAccountId();

				// 将资金转入平衡金账户
				customerSubAccountPoint.setAvailablePoints(CalculateUtil
						.doubleSubtract(
								customerSubAccountPoint.getAvailablePoints(),
								repaymentProductOrder.getTotalRepayAmount()));
				customerSubAccountPointService.update(customerSubAccountPoint);
				// 资金记录处理
				String remark = "产品【" + product.getProductName()
						+ "】正常还款资金由借款人账户转入平衡金账户【"
						+ repaymentProductOrder.getTotalRepayAmount()
						+ "】元，其中本金【" + repaymentProductOrder.getPrincipal()
						+ "】元，利息【" + repaymentProductOrder.getInterest() + "】元";
				customerSubAccountPointLogService.add(
						repaymentProductOrder.getTotalRepayAmount(),
						customerSubAccountPoint.getAvailablePoints(),
						customerSubAccountPoint.getFrozenPoints(),
						CustomerSubAccountPointType.REPAYMENT_INPUT_BALANCE,
						remark, orderFormId,
						customerSubAccountPoint.getCustomerSubAccountId(),
						customerSubAccountPoint.getCustomerSubAccountId(),
						balanceCustomerSubAccount.getId(),
						realFromCustomerSubAccountId, false,
						customerSubAccount.getCustomerId(), productId,null,null);

				// 平衡金资金添加
				balanceCustomerSubAccountPoint.setAvailablePoints(CalculateUtil
						.doubleAdd(balanceCustomerSubAccountPoint
								.getAvailablePoints(), repaymentProductOrder
								.getTotalRepayAmount()));
				customerSubAccountPointService
						.update(balanceCustomerSubAccountPoint);
				String remark1 = "产品【" + product.getProductName()
						+ "】正常还款资金由借款人账户转入平衡金账户【"
						+ repaymentProductOrder.getTotalRepayAmount()
						+ "，其中本金【" + repaymentProductOrder.getPrincipal()
						+ "】元，利息【" + repaymentProductOrder.getInterest() + "】元";
				customerSubAccountPointLogService.add(repaymentProductOrder
						.getTotalRepayAmount(), balanceCustomerSubAccountPoint
						.getAvailablePoints(), balanceCustomerSubAccountPoint
						.getFrozenPoints(),
						CustomerSubAccountPointType.REPAYMENT_INPUT_BALANCE,
						remark1, orderFormId, balanceCustomerSubAccountPoint
								.getCustomerSubAccountId(),
						customerSubAccountPoint.getCustomerSubAccountId(),
						balanceCustomerSubAccount.getId(),
						realFromCustomerSubAccountId, false, balanceCustomer
								.getId(), productId,null,null);

				productRepayRecord.setAlreadyRepaidAmount(repaymentProductOrder
						.getTotalRepayAmount());// 已还金额
				productRepayRecord.setRealyRepayDate(now);
				productRepayRecord.setRepaidByGuarantor(false);
				productRepayRecord.setStatus(ProductRepayRecordStatus.REPAID);
				type = ProductRecoveryRecordType.NORMAL;// 正常还款
			} else { // TODO 资金不足走风险金

				// 风险金账户将资金转入平衡金账户
				// 风险金
				Customer riskCustomer = customerService
						.findByUsername(Conf.SYSTEM_CUSTOMER_RISK);
				CustomerSubAccount riskCustomerSubAccount = customerSubAccountService
						.findByCustomerSubAccountByUserIdAndProductChannelId(
								riskCustomer.getId(),
								product.getProductChannelId());
				CustomerSubAccountPoint riskCustomerSubAccountPoint = customerSubAccountPointService
						.findCustomerSubAccountPointByCustomerSubAccountId(riskCustomerSubAccount
								.getId());

				realFromCustomerSubAccountId = riskCustomerSubAccount.getId();// 风险金

				riskCustomerSubAccountPoint.setAvailablePoints(CalculateUtil
						.doubleSubtract(riskCustomerSubAccountPoint
								.getAvailablePoints(), repaymentProductOrder
								.getTotalRepayAmount()));
				customerSubAccountPointService
						.update(riskCustomerSubAccountPoint);
				// 资金记录处理
				String remark = "产品【" + product.getProductName()
						+ "】逾期还款资金由风险金账户转入平衡金账户【"
						+ repaymentProductOrder.getTotalRepayAmount()
						+ "】元，其中本金【" + repaymentProductOrder.getPrincipal()
						+ "】元，利息【" + repaymentProductOrder.getInterest()
						+ "】元，罚息【" + repaymentProductOrder.getDefaultInterest()
						+ "】元";
				customerSubAccountPointLogService.add(
						repaymentProductOrder.getTotalRepayAmount(),
						customerSubAccountPoint.getAvailablePoints(),
						customerSubAccountPoint.getFrozenPoints(),
						CustomerSubAccountPointType.REPAYMENT_INPUT_BALANCE,
						remark, orderFormId,
						customerSubAccountPoint.getCustomerSubAccountId(),
						customerSubAccountPoint.getCustomerSubAccountId(),
						balanceCustomerSubAccount.getId(),
						realFromCustomerSubAccountId, false,
						customerSubAccount.getCustomerId(), productId,null,null);

				// 平衡金资金添加
				balanceCustomerSubAccountPoint.setAvailablePoints(CalculateUtil
						.doubleAdd(balanceCustomerSubAccountPoint
								.getAvailablePoints(), repaymentProductOrder
								.getTotalRepayAmount()));
				customerSubAccountPointService
						.update(balanceCustomerSubAccountPoint);
				String remark1 = "产品【" + product.getProductName()
						+ "】逾期还款资金由风险金账户转入平衡金账户【"
						+ repaymentProductOrder.getTotalRepayAmount()
						+ "】元，其中本金【" + repaymentProductOrder.getPrincipal()
						+ "】元，利息【" + repaymentProductOrder.getInterest()
						+ "】元，罚息【" + repaymentProductOrder.getDefaultInterest()
						+ "】元";
				customerSubAccountPointLogService.add(repaymentProductOrder
						.getTotalRepayAmount(), balanceCustomerSubAccountPoint
						.getAvailablePoints(), balanceCustomerSubAccountPoint
						.getFrozenPoints(),
						CustomerSubAccountPointType.REPAYMENT_INPUT_BALANCE,
						remark1, orderFormId, balanceCustomerSubAccountPoint
								.getCustomerSubAccountId(),
						customerSubAccountPoint.getCustomerSubAccountId(),
						balanceCustomerSubAccount.getId(),
						realFromCustomerSubAccountId, false, balanceCustomer
								.getId(), productId,null,null);

				productRepayRecord.setAlreadyRepaidAmount(0D);// 已还金额，实际
				productRepayRecord.setRepaidByGuarantor(true);
				productRepayRecord.setStatus(ProductRepayRecordStatus.OVER_DUE);

				productRepayRecord
						.setAlreadyGuarantedAmount(repaymentProductOrder
								.getTotalRepayAmount());
				productRepayRecord.setGuarantedDate(now);
				productRepayRecord.setGuarantorId(riskCustomerSubAccount
						.getId());// 风险金子账户id
				type = ProductRecoveryRecordType.GUARANTE;// 垫付还款

				// 添加风险金日志
				String riskOrderNotes = "产品【" + product.getProductName()
						+ "】第【" + productRepayRecord.getPhaseNumber()
						+ "】期，借款人账户资金不足，风险金垫付【"
						+ repaymentProductOrder.getTotalRepayAmount() + "】元";
				riskPointOrderService
						.add(productId, OrderType.RISK_POINT_GUARANTE,
								repaymentProductOrder.getTotalRepayAmount(),
								productRepayRecord.getId(), riskOrderNotes,
								orderFormId);
				product.setProductStatus(ProductStatus.OVER_DUE);// 逾期
				productDao.updateProduct(product);
			}
			// 更新还款记录
			productRepayRecordService.update(productRepayRecord);

			// 2、再将平衡金中的资金按比例转入用户账户(还款分账)
			for (RepaymentProductDetail repaymentProductDetail : repaymentProductOrder
					.getDetails()) {
				
				System.out.println(repaymentProductDetail.getProductSubAccountId()+":"+repaymentProductDetail.getPrincipal()+":"+repaymentProductDetail.getInterest());
				productSubAccountService.updateForRepayment(
						repaymentProductDetail.getProductSubAccountId(),
						repaymentProductDetail.getInterest(),
						repaymentProductDetail.getPrincipal(),
						repaymentProductDetail.getDefaultInterest(),
						orderFormId, realFromCustomerSubAccountId,
						productRepayRecord.getId(),
						productRepayRecord.getPhaseNumber(), type);
			}

			// TODO 判断是否已经结标，通过什么来判断，还款记录是否还完？
			ProductRepayRecord productRepayRecord2 = productRepayRecordService
					.findByNewProductRepayRecord(productId);
			if (null == productRepayRecord2) {
				// 已结清，需要更新处理状态
				if(ProductStatus.OVER_DUE.equals(product.getProductStatus())){
					
				} else {
					product.setProductStatus(ProductStatus.ENDED);// 结标
					product.setClosedTime(new Date());
				}
				product.setCornerType(ProductCornerType.CLOSED);
				
				productSubAccountService.updateOver(product.getId());
				//TODO 需要处理产品份额表
				productOfThatRegisterService.updateClosed(product.getId());
			}
			// 结标
			productDao.updateProduct(product);

		} else if (orderType.equals(OrderType.REPAYMENT_hwsoft_OVERDUE_AUTO)
				|| orderType
						.equals(OrderType.REPAYMENT_hwsoft_OVERDUE_HANDLE)) {// TODO逾期还款，
			realFromCustomerSubAccountId = product.getCustomerSubAccountId();// 只能是借款人（居间人）

			// 判断资金是否足额
			if (customerSubAccountPoint.getAvailablePoints() >= repaymentProductOrder
					.getTotalRepayAmount()) {// 资金足额

				// 添加风险金订单
				String riskOrderNotes = "产品【" + product.getProductName()
						+ "】第【" + productRepayRecord.getPhaseNumber()
						+ "】期，逾期还款，风险金回收垫付资金【"
						+ repaymentProductOrder.getTotalRepayAmount() + "】元";
				riskPointOrderService
						.add(productId, OrderType.RISK_POINT_GUARANTE,
								repaymentProductOrder.getTotalRepayAmount(),
								productRepayRecord.getId(), riskOrderNotes,
								orderFormId);

				// 借款人账户资金
				customerSubAccountPoint.setAvailablePoints(CalculateUtil
						.doubleSubtract(
								customerSubAccountPoint.getAvailablePoints(),
								repaymentProductOrder.getTotalRepayAmount()));
				customerSubAccountPointService.update(customerSubAccountPoint);

				// 按照指定的期数将钱从借款人账户转移到风险金账户，如果资金不足则进行回滚？
				Customer riskCustomer = customerService
						.findByUsername(Conf.SYSTEM_CUSTOMER_RISK);
				CustomerSubAccount riskCustomerSubAccount = customerSubAccountService
						.findByCustomerSubAccountByUserIdAndProductChannelId(
								riskCustomer.getId(),
								product.getProductChannelId());
				CustomerSubAccountPoint riskCustomerSubAccountPoint = customerSubAccountPointService
						.findCustomerSubAccountPointByCustomerSubAccountId(riskCustomerSubAccount
								.getId());

				// 风险金
				riskCustomerSubAccountPoint.setAvailablePoints(CalculateUtil
						.doubleAdd(riskCustomerSubAccountPoint
								.getAvailablePoints(), repaymentProductOrder
								.getTotalRepayAmount()));
				customerSubAccountPointService
						.update(riskCustomerSubAccountPoint);
				// 资金记录处理
				String remark = "产品【" + product.getProductName()
						+ "】逾期还款资金由借款人账户转入风险金账户【"
						+ repaymentProductOrder.getTotalRepayAmount() + "】元";
				customerSubAccountPointLogService.add(
						repaymentProductOrder.getTotalRepayAmount(),
						riskCustomerSubAccountPoint.getAvailablePoints(),
						riskCustomerSubAccountPoint.getFrozenPoints(),
						CustomerSubAccountPointType.REPAYMENT_DUE_APPEND_RISK,
						remark, orderFormId,
						customerSubAccountPoint.getCustomerSubAccountId(),
						riskCustomerSubAccountPoint.getCustomerSubAccountId(),
						riskCustomerSubAccountPoint.getId(),
						customerSubAccount.getId(), true,
						riskCustomerSubAccount.getCustomerId(), productId,null,null);

				// 风险金金资金添加
				String remark1 = "产品【" + product.getProductName()
						+ "】逾期还款资金由借款人账户转入风险金账户【"
						+ repaymentProductOrder.getTotalRepayAmount() + "】元";
				customerSubAccountPointLogService.add(
						repaymentProductOrder.getTotalRepayAmount(),
						customerSubAccountPoint.getAvailablePoints(),
						customerSubAccountPoint.getFrozenPoints(),
						CustomerSubAccountPointType.REPAYMENT_DUE_APPEND_RISK,
						remark1, orderFormId,
						customerSubAccountPoint.getCustomerSubAccountId(),
						riskCustomerSubAccountPoint.getCustomerSubAccountId(),
						customerSubAccountPoint.getId(),
						customerSubAccount.getId(), false,
						customerSubAccount.getCustomerId(), productId,null,null);

				productRepayRecord.setAlreadyRepaidAmount(repaymentProductOrder
						.getTotalRepayAmount());// 已还金额，实际
				productRepayRecord.setRealyRepayDate(now);
				productRepayRecord.setRepaidByGuarantor(true);
				productRepayRecord
						.setStatus(ProductRepayRecordStatus.OVERDUE_CLOSED);

				productRepayRecord.setAlreadyGuarantedAmount(0D);
				productRepayRecord.setGuarantorId(riskCustomerSubAccount
						.getId());// 风险金子账户id

				// 更新还款记录
				productRepayRecordService.update(productRepayRecord);
				
				
				product.setProductStatus(ProductStatus.LENDING);// 进入正常状态
				ProductRepayRecord productRepayRecord2 = productRepayRecordService
						.findByNewProductRepayRecord(productId);
				if (null == productRepayRecord2) {
					// 已结清，需要更新处理状态
					product.setProductStatus(ProductStatus.ENDED);// 结标
					product.setClosedTime(new Date());
//					productSubAccountService.updateOver(product.getId());
				}
				
				productDao.updateProduct(product);

			} else {
				throw new RepaymentException("逾期还款资金不足");
			}
		}

		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Integer> listAllLendingOrOverDue() {
		return productDao.listALlByStatus(ProductStatus.LENDING,
				ProductStatus.OVER_DUE);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long getTotalCountForRepayment(
			ProductChannelType productChannelType, ProductStatus productStatus,
			Date startDate, Date endDate) {
		ProductChannel productChannel = productChannelService
				.findByType(productChannelType);
		Integer productChannelId = null;
		if (null != productChannel) {
			productChannelId = productChannel.getId();
		}
		return productDao.getTotalCountForRepayment(productChannelId,
				productStatus, startDate, endDate);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Product> listForRepayment(
			ProductChannelType productChannelType, ProductStatus productStatus,
			Date startDate, Date endDate, int from, int pageSize) {
		ProductChannel productChannel = productChannelService
				.findByType(productChannelType);
		Integer productChannelId = null;
		if (null != productChannel) {
			productChannelId = productChannel.getId();
		}
		return productDao.listForRepayment(productChannelId, productStatus,
				startDate, endDate, from, pageSize);
	}

}
