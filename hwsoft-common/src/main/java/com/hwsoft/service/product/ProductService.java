/**
 * 
 */
package com.hwsoft.service.product;

import java.util.Date;
import java.util.List;

import com.hwsoft.common.product.ProductChannelType;
import com.hwsoft.common.product.ProductCornerType;
import com.hwsoft.common.product.ProductStatus;
import com.hwsoft.model.product.Product;
import com.hwsoft.vo.product.BaseProductVo;
import com.hwsoft.vo.product.ProductVo;

/**
 * @author tzh
 *
 */
public interface ProductService {
	
	public void addProductList(List<Product> productList);
    
    public void addImportProductList(List<Product> importList);

    public List<Product> listAll(int from, int pageSize);

    public Long getTotalCount();
    
    public Product findProductByThirdId(int productChannelId,String thirdId);
    
    public Product findById(int productId);

    public Product updateProductWaittingAmount(int id,double waittingAmount,double buyAmount);
    
    public Product updateProductByPaySuccess(int id,int buyerRecordId,double amount);
    
    public Product updateProductCornerType(int productId,ProductCornerType productCornerType);
    
    public Product updateByOrderTerminated(int id,int buyerRecordId);
    
    public Product findByProductName(String name);
    
    public Product addToSale(Integer productId,Integer agreementId,List<Integer> bankIds,Integer startHour,Integer startMinute,
    		Integer endHour,Integer endMinute);
    
    public List<Product> listAll(ProductChannelType productChannelType,String productName,int from, int pageSize,Boolean show, ProductStatus... productStatus);

    public Long getTotalCount(ProductChannelType productChannelType,String productName, Boolean show, ProductStatus... productStatus);
    
    public ProductVo findProductVoByProductId(int productId);
    
    public Product publish(int productId,boolean recommend,boolean enableDummyBoughtAmount,Double dummyBoughtAmount,
    		boolean enableDummyBoughtCount,Integer dummyBoughtCount,Date startInterestBearingTime,
    		Date endInterestBearingTime,Date publishTime,String desc,
			String desciptionTitle, String mortgageDesc, List<String> picPaths,
			String riskDesc,List<String> riskPicPaths,String activityDesc,String mortgager,String guaranteeCompany);
    
    public Product recommend(int productId,boolean recommand);
    
    /**
     * 查询推荐产品，
     * @return
     */
    public BaseProductVo findRecommend(int productChannelId);
    
    /**
     * 产品列表接口使用
     * @param productStatus
     * @param from
     * @param pageSize
     * @return
     */
    public List<BaseProductVo> listAllByStatus(ProductChannelType productChannelType, int from, int pageSize,Boolean show, ProductStatus... productStatus);

    /**
     * 查询产品，
     * @return
     */
    public BaseProductVo findBaseProductVoById(int productId);
    
    
    public Product addOrUpdateProductInfo(int productId,String desc,
			String desciptionTitle, String mortgageDesc, List<String> picPaths,
			String riskDesc,List<String> riskPicPaths,String activityDesc,String mortgager,String guaranteeCompany);
    
    
    public List<Integer> listALlByStatus(ProductStatus productStatus);
    
    
    public Product updateProductStatus(int productId,ProductStatus status);
    
    public Product updateDummy( int productId, boolean enableDummyBoughtAmount, Double dummyBoughtAmount,
            boolean enableDummyBoughtCount, Integer dummyBoughtCount);
    
    public Product showProduct(Integer productId,boolean show);
    
    /**
     * 投标
     * @param id
     * @param shareNum
     * @param productSubAccountId
     * @return
     */
    public Product bid(int id,int shareNum, int productSubAccountId);
    
    /**
     * 流标
     * @param productId
     * @return
     */
    public Product updateFailedProduct(int productId);
    
    /**
     * 放款
     * @param productId
     * @return
     */
    public Product updateLendingProduct(int productId);
    
    /**
     * 还款
     * @param productId
     * @param orderFormId 还款订单
     * @return
     */
    public Product updateRepaymentProduct(int productId,String orderFormId);
    
    /**
     * 查询所有还款中的产品id
     * @return
     */
    public List<Integer> listAllLendingOrOverDue();
    
    
    /**
     * 产品列表接口使用
     * @param productStatus
     * @param from
     * @param pageSize
     * @return
     */
    public long getTotalCountForRepayment(ProductChannelType productChannelType,  ProductStatus productStatus,Date startDate,Date endDate);
    
    
    /**
     * 产品列表接口使用
     * @param productStatus
     * @param from
     * @param pageSize
     * @return
     */
    public List<Product> listForRepayment(ProductChannelType productChannelType, ProductStatus productStatus,Date startDate,Date endDate, int from, int pageSize);

    
}
