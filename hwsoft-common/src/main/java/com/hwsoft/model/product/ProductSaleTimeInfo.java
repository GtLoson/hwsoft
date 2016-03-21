/**
 * 
 */
package com.hwsoft.model.product;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 产品销售时间信息
 * @author tzh
 *
 */
@Entity
@Table(name="product_sale_time_info")
public class ProductSaleTimeInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	@Column(name = "product_id", nullable = false ,unique=true)
	private Integer productId;
	
	@Column(name = "start_hour", nullable = false)
	private Integer startHour;
	
	@Column(name = "start_minute", nullable = false)
	private Integer startMinute;
	
	@Column(name = "end_hour", nullable = false)
	private Integer endHour;
	
	@Column(name = "end_minute", nullable = false)
	private Integer endMinute;
	


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getStartHour() {
		return startHour;
	}

	public void setStartHour(Integer startHour) {
		this.startHour = startHour;
	}

	public Integer getStartMinute() {
		return startMinute;
	}

	public void setStartMinute(Integer startMinute) {
		this.startMinute = startMinute;
	}

	public Integer getEndHour() {
		return endHour;
	}

	public void setEndHour(Integer endHour) {
		this.endHour = endHour;
	}

	public Integer getEndMinute() {
		return endMinute;
	}

	public void setEndMinute(Integer endMinute) {
		this.endMinute = endMinute;
	}
	//TODO 以后可以考虑星期问题
}
