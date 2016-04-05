package com.hwsoft.model.category;

import com.hwsoft.model.BaseModel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "category")
public class Category extends BaseModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "category_level", nullable = false)
	private CategoryLevel categoryLevel;

	@Column(name = "name", length = 64, nullable = false)
	private String name;

	@Column(name = "desc", length = 256, nullable = false)
	private String desc;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public CategoryLevel getCategoryLevel() {
		return categoryLevel;
	}

	public void setCategoryLevel(CategoryLevel categoryLevel) {
		this.categoryLevel = categoryLevel;
	}
}

