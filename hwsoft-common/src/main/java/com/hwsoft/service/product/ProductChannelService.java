/**
 * 
 */
package com.hwsoft.service.product;

import java.util.List;

import com.hwsoft.common.product.ProductChannelType;
import com.hwsoft.model.product.ProductChannel;

/**
 * @author tzh
 *
 */
public interface ProductChannelService {

	public ProductChannel findByType(ProductChannelType productChannelType);
	
	public ProductChannel findById(int productChannelId);
	
	public List<ProductChannel> listAll(int from, int pageSize);

	public List<ProductChannel> listAll();

	public Long getTotalCount();
	
	public ProductChannel add(ProductChannelType productChannelType,String name,String desc);
	
	public ProductChannel update(Integer productChannelId,String desc);
}
