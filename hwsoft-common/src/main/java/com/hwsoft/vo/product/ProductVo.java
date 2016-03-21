/**
 * 
 */
package com.hwsoft.vo.product;

import java.util.List;

import com.hwsoft.model.bank.Bank;
import com.hwsoft.model.product.Product;
import com.hwsoft.model.product.ProductAgreementTemplate;
import com.hwsoft.model.product.ProductBank;
import com.hwsoft.model.product.ProductChannel;
import com.hwsoft.model.product.ProductSaleTimeInfo;

/**
 * @author tzh
 *
 */
public class ProductVo {

	private Product product;
	
	private ProductChannel productChannel;
	
	private ProductSaleTimeInfo productSaleTimeInfo;
	
	private ProductAgreementTemplate productAgreementTemplate;
	
	private ProductBank productBank;
	
	private List<Bank> bankList;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public ProductChannel getProductChannel() {
		return productChannel;
	}

	public void setProductChannel(ProductChannel productChannel) {
		this.productChannel = productChannel;
	}

	public ProductSaleTimeInfo getProductSaleTimeInfo() {
		return productSaleTimeInfo;
	}

	public void setProductSaleTimeInfo(ProductSaleTimeInfo productSaleTimeInfo) {
		this.productSaleTimeInfo = productSaleTimeInfo;
	}

	public ProductAgreementTemplate getProductAgreementTemplate() {
		return productAgreementTemplate;
	}

	public void setProductAgreementTemplate(
			ProductAgreementTemplate productAgreementTemplate) {
		this.productAgreementTemplate = productAgreementTemplate;
	}

	public ProductBank getProductBank() {
		return productBank;
	}

	public void setProductBank(ProductBank productBank) {
		this.productBank = productBank;
	}

	public List<Bank> getBankList() {
		return bankList;
	}

	public void setBankList(List<Bank> bankList) {
		this.bankList = bankList;
	}
	
}
