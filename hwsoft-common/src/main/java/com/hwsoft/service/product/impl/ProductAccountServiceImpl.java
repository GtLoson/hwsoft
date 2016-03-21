/**
 * 
 */
package com.hwsoft.service.product.impl;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.common.product.ProductBidType;
import com.hwsoft.common.product.ProductBuyerRecordStatus;
import com.hwsoft.common.product.ProductBuyerStatus;
import com.hwsoft.common.product.ProductChannelType;
import com.hwsoft.common.product.ProductStatus;
import com.hwsoft.dao.product.ProductAccountDao;
import com.hwsoft.exception.product.ProductException;
import com.hwsoft.exception.repayment.RepaymentException;
import com.hwsoft.model.customer.CustomerSubAccount;
import com.hwsoft.model.product.Product;
import com.hwsoft.model.product.ProductAccount;
import com.hwsoft.model.product.ProductSubAccount;
import com.hwsoft.model.product.ProductChannel;
import com.hwsoft.service.customer.CustomerSubAccountService;
import com.hwsoft.service.product.ProductSubAccountService;
import com.hwsoft.service.product.ProductAccountService;
import com.hwsoft.service.product.ProductChannelService;
import com.hwsoft.service.product.ProductService;
import com.hwsoft.service.statistics.CustomerStatisticsService;
import com.hwsoft.util.math.CalculateUtil;

/**
 * @author tzh
 *
 */
@Service("productAccountService")
public class ProductAccountServiceImpl implements ProductAccountService {
	private static Logger logger = Logger.getLogger("productAccountServiceImpl");
	
	@Autowired
	private ProductAccountDao productBuyerDao;
	
	@Autowired
	private ProductService productService;
	
    @Autowired
    private ProductChannelService productChannelService;
    
    @Autowired
    private CustomerSubAccountService customerSubAccountService;
    
    @Autowired
    private ProductSubAccountService productSubAccountService;
    
    @Autowired
    private CustomerStatisticsService customerStatisticsService;
    
	/* (non-Javadoc)
	 * @see com.hwsoft.service.product.ProductBuyerService#add(int, int, double)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public ProductAccount add(int customerId, int productId, double purchaseAmount) {

		Product product = productService.findById(productId);
		if(null == product){
			logger.info("产品未找到");
			throw new ProductException("产品未找到");
		}
		if(!product.getProductStatus().equals(ProductStatus.SALES)){
			logger.info("该产品不在销售期");
			throw new ProductException("该产品不在销售期");
		}

		CustomerSubAccount customerSubAccount = customerSubAccountService.findByCustomerSubAccountByUserIdAndProductChannelId(customerId, product.getProductChannelId());
		if(null == customerSubAccount){
			throw new ProductException("共鸣账户未找到");
		}

		ProductChannel productChannel = productChannelService.findById(product.getProductChannelId());
		if(null == productChannel){
			logger.info("没有找到产品渠道："+ProductChannelType.GONGMING.toString());
			throw new ProductException("没有找到产品渠道："+ProductChannelType.GONGMING.toString());
		}
		if(product.getProductChannelId() != productChannel.getId()){
			logger.info("该产品不是来自共鸣");
			throw new ProductException("该产品不是来自共鸣");
		}
		if(CalculateUtil.doubleSubtract(product.getRemainingAmount(), purchaseAmount, 2) < 0){
			logger.info("申购金额超过产品可售金额");
			throw new ProductException("申购金额超过产品可售金额");
		}
		ProductAccount productBuyer = new ProductAccount();
		productBuyer.setCustomerId(customerId);
		productBuyer.setProductBuyerStatus(ProductBuyerStatus.NORMAL);
		productBuyer.setPurchaseAmount(purchaseAmount);
		productBuyer.setProductId(productId);
		productBuyer.setShareNum(new Double(purchaseAmount).intValue());//共鸣暂时这样处理
		productBuyer.setProductBidType(ProductBidType.GONGMING_PRODUCT_BUY);
		return productBuyerDao.add(productBuyer);
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.product.ProductBuyerService#findByCustomerIdAndProductId(int, int)
	 */
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public ProductAccount findByCustomerIdAndProductId(int customerId,
			int productId) {
		
		return productBuyerDao.findByCustomerIdAndProductId(customerId, productId);
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.product.ProductBuyerService#updateByPlaceOrderSuccess(int, double, double)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public ProductAccount updateByPlaceOrderSuccess(int id, double buyAmount,
			double purchaseAmount) {
		ProductAccount productBuyer = findById(id);
		if(null == productBuyer){
			throw new ProductException("产品子账户未找到");
		}
		productBuyer.setBuyAmount(buyAmount);
		productBuyer.setPurchaseAmount(purchaseAmount);
		
		return productBuyerDao.update(productBuyer);
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.product.ProductBuyerService#findById(int)
	 */
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public ProductAccount findById(int id) {
		return productBuyerDao.findById(id);
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.product.ProductBuyerService#updateByPaySuccess(int, double)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public ProductAccount updateByPaySuccess(int id,int buyerRecordId) {
		ProductAccount productBuyer = findById(id);
		if(null == productBuyer){
			throw new ProductException("产品账户未找到");
		}
		ProductSubAccount productBuyerRecord = productSubAccountService.findById(buyerRecordId);
		if(null == productBuyerRecord){
			throw new ProductException("产品订单未找到");
		}
//		if(productBuyerRecord.getStatus().equals(ProductBuyerRecordStatus.HAS_PAIED)){
//			throw new ProductException("产品订单已经支付");
//		}
		productBuyer.setBuyAmount(CalculateUtil.doubleAdd(productBuyer.getBuyAmount(), productBuyerRecord.getSuccessAmount(), 2));
		productBuyer.setPurchaseAmount(CalculateUtil.doubleSubtract(productBuyer.getPurchaseAmount(), productBuyerRecord.getAmount(), 2));
		
		productService.updateProductByPaySuccess(productBuyer.getProductId(), productBuyerRecord.getId(),productBuyerRecord.getSuccessAmount());
		
		return productBuyerDao.update(productBuyer);
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.product.ProductBuyerService#updateByOrderTerminated(int, int)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public ProductAccount updateByOrderTerminated(int id, int buyerRecordId) {
		ProductAccount productBuyer = findById(id);
		if(null == productBuyer){
			throw new ProductException("产品子账户未找到");
		}
		ProductSubAccount productBuyerRecord = productSubAccountService.findById(buyerRecordId);
		if(null == productBuyerRecord){
			throw new ProductException("产品订单未找到");
		}
		productBuyer.setPurchaseAmount(CalculateUtil.doubleSubtract(productBuyer.getPurchaseAmount(), productBuyerRecord.getAmount(), 2));
		
		productService.updateByOrderTerminated(productBuyer.getProductId(), productBuyerRecord.getId());
		
		return productBuyerDao.update(productBuyer);
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.product.ProductBuyerService#updateByOrderHasWaithdrawal(int, int, double)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public ProductAccount updateByOrderHasWaithdrawal(int id, int buyerRecordId,
			double amount) {
		ProductAccount productBuyer = findById(id);
		if(null == productBuyer){
			throw new ProductException("产品子账户未找到");
		}
		ProductSubAccount productBuyerRecord = productSubAccountService.findById(buyerRecordId);
		if(null == productBuyerRecord){
			throw new ProductException("产品订单未找到");
		}
		productBuyer.setWithdrawalAmount(CalculateUtil.doubleAdd(productBuyer.getWithdrawalAmount(), amount, 2));
		// productService.updateByOrderTerminated(id, productBuyerRecord.getId());
		return productBuyerDao.update(productBuyer);
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.product.ProductAccountService#updateInterest(int, double)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public ProductAccount updateInterest(int id, double amount) {
		ProductAccount productBuyer = findById(id);
		if(null == productBuyer){
			throw new ProductException("产品子账户未找到");
		}
		//TODO 查询
//		double totalTnterest = customerStatisticsService.findProductTotolInterest(id);
		
		List<ProductSubAccount> productSubAccounts = productSubAccountService.findByProductAccountId(id);
		double totalTnterest = 0D;
		if(null != productSubAccounts && productSubAccounts.size() != 0 ){
			
			for(ProductSubAccount productSubAccount : productSubAccounts){
				if(ProductBuyerRecordStatus.isContain(productSubAccount.getStatus(), ProductBuyerRecordStatus.USING_STATUS)){
					totalTnterest = CalculateUtil.doubleAdd(totalTnterest, productSubAccount.getTotalInterest(), 4);
				}
			}
		}
		
		System.out.println("子账户总计利息："+totalTnterest);
		productBuyer.setTotalInterest(totalTnterest);
		return productBuyerDao.update(productBuyer);
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.product.ProductAccountService#totalBuyNum(int)
	 */
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public long totalBuyNum(int productId) {
		return productBuyerDao.totalBuyNum(productId);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public ProductAccount addForBid(int customerId, int productId,
			double purchaseAmount, int shareNum, ProductBidType productBidType,
			Integer bidProductId, Integer bidProductSubAccountId) {
		Product product = productService.findById(productId);
		if(null == product){
			logger.info("产品未找到");
			throw new ProductException("产品未找到");
		}
		if(!product.getProductStatus().equals(ProductStatus.SALES)){
			logger.info("该产品不在销售期");
			throw new ProductException("该产品不在销售期");
		}

		CustomerSubAccount customerSubAccount = customerSubAccountService.findByCustomerSubAccountByUserIdAndProductChannelId(customerId, product.getProductChannelId());
		if(null == customerSubAccount){
			throw new ProductException("共鸣账户未找到");
		}

		ProductChannel productChannel = productChannelService.findById(product.getProductChannelId());
		if(null == productChannel){
			logger.info("没有找到产品渠道：");
			throw new ProductException("没有找到产品渠道：");
		}
		if(product.getProductChannelId() != productChannel.getId()){
			logger.info("产品渠道不一致");
			throw new ProductException("产品渠道不一致");
		}
		if(CalculateUtil.doubleSubtract(product.getRemainingAmount(), shareNum, 2) < 0){
			logger.info("申购金额超过产品可售份额");
			throw new ProductException("申购金额超过产品可售份额");
		}
		ProductAccount productBuyer = findByCustomerIdAndProductId(customerId, productId);
		
		if(null != productBuyer){
			productBuyer.setBuyAmount(CalculateUtil.doubleAdd(purchaseAmount, productBuyer.getBuyAmount(), 4));
			productBuyer.setShareNum(productBuyer.getShareNum()+shareNum);
			productBuyer.setWaittingPrincipal(productBuyer.getBuyAmount());
			
			return productBuyerDao.update(productBuyer);
		} else {
		
			productBuyer = new ProductAccount();
			productBuyer.setCustomerId(customerId);
			productBuyer.setProductBuyerStatus(ProductBuyerStatus.NORMAL);
	//		productBuyer.setPurchaseAmount(purchaseAmount);//没有申购中的金额
			productBuyer.setProductId(productId);
			productBuyer.setBidProductId(bidProductId);
			productBuyer.setBidProductSubAccountId(bidProductSubAccountId);
			productBuyer.setProductBidType(productBidType);
			productBuyer.setShareNum(shareNum);
			productBuyer.setBuyAmount(purchaseAmount);
			productBuyer.setWaittingPrincipal(purchaseAmount);
			return productBuyerDao.add(productBuyer);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public ProductAccount updateForFailed(int productId) {
		
		//查询出所有的产品账户
		List<ProductAccount> productAccountList = findByProductId(productId);
		
		if(null == productAccountList || productAccountList.size() == 0){
			return null;
		}
		
		for(ProductAccount productAccount : productAccountList){
			productAccount.setProductBuyerStatus(ProductBuyerStatus.OVER);
			productBuyerDao.update(productAccount);
		
			// 进行产品子账户流标处理,同时处理用户冻结资金
			productSubAccountService.updateForFailed(productAccount.getId(), productId);
		}
		
		return null;
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<ProductAccount> findByProductId(int productId) {

		return productBuyerDao.findByProductId(productId);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public ProductAccount updateForLending(int productId) {
		//查询出所有的产品账户
		List<ProductAccount> productAccountList = findByProductId(productId);
		
		if(null == productAccountList || productAccountList.size() == 0){
			return null;
		}
		
		for(ProductAccount productAccount : productAccountList){
			
			if(!ProductBuyerStatus.OVER.equals(productAccount.getProductBuyerStatus())){
				productAccount.setProductBuyerStatus(ProductBuyerStatus.NORMAL);

				productAccount.setWaittingPrincipal(productAccount.getBuyAmount());
				productBuyerDao.update(productAccount);
			
				// 进行产品子账户流标处理,同时处理用户冻结资金
				productSubAccountService.updateForLending(productAccount.getId(), productId);
			}
			
			
		}
		
		return null;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public ProductAccount updateForRepayment(int productAccountId,
			double interest, double principal, double defaultInterest) {
		
		ProductAccount productAccount = findById(productAccountId);
		if(null == productAccount){
			throw new RepaymentException("产品账户未找到");
		}
		productAccount.setTotalDefaulAndInterest(CalculateUtil.doubleAdd(productAccount.getTotalDefaulAndInterest(), CalculateUtil.doubleAdd(interest, defaultInterest, 4), 4));
		productAccount.setTotalDefaulInterest(CalculateUtil.doubleAdd(productAccount.getTotalDefaulInterest(), defaultInterest, 4));
		productAccount.setTotalRepaiedInterest(CalculateUtil.doubleAdd(productAccount.getTotalRepaiedInterest(), interest, 4));
		productAccount.setTotalInterest(productAccount.getTotalRepaiedInterest());
		productAccount.setWaittingPrincipal(CalculateUtil.doubleSubtract(productAccount.getWaittingPrincipal(), principal, 4));
		
		return productBuyerDao.update(productAccount);
	}
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<Integer> findAllNormalProductAccountId() {
		return productBuyerDao.findAllNormalProductAccountId();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public ProductAccount updateStatus(int productAccountId) {
		
		ProductAccount productAccount = findById(productAccountId);
		if(null == productAccount){
			throw new RepaymentException("产品账户未找到");
		}
		
		//查询出所有的产品账户
		List<ProductSubAccount> productSubAccounts = productSubAccountService.findByProductAccountId(productAccountId);
		
		if(null == productSubAccounts || productSubAccounts.size() == 0){
			return null;
		}
		
		ProductBuyerStatus status = null;
		
		for(ProductSubAccount productSubAccount : productSubAccounts){
			if(null != status && status.equals(ProductBuyerStatus.NORMAL)){
				break;
			}
			if(ProductBuyerRecordStatus.isContain(productSubAccount.getStatus(), ProductBuyerRecordStatus.USING_STATUS)){
				status = ProductBuyerStatus.NORMAL;
				break;
			} else {
				status = ProductBuyerStatus.OVER;
			}
		}
		productAccount.setProductBuyerStatus(status);
		
		return productBuyerDao.update(productAccount);
	}

}
