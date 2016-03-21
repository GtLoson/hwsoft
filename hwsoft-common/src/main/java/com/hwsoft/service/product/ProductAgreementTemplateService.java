/**
 * 
 */
package com.hwsoft.service.product;

import com.hwsoft.model.product.ProductAgreementTemplate;

/**
 * @author tzh
 *
 */
public interface ProductAgreementTemplateService {

	public ProductAgreementTemplate add(int productId,int agereementTemplateId);
	
	public ProductAgreementTemplate findByProductId(int productId);
	
	public ProductAgreementTemplate findByProductIdForTemplate(int productId);
	
	public ProductAgreementTemplate findForAgreement(int productSubAccountId,int customerId);
	
}
