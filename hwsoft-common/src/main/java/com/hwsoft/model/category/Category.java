package com.hwsoft.model.category;

import com.hwsoft.model.BaseModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "category")
public class Category extends BaseModel implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "parent_id", columnDefinition = "int DEFAULT 0")
	private Integer parentId;

	@Column(name = "name", length = 64, nullable = false)
	private String name;

	@Column(name = "url", length = 128, nullable = true)
	private String url;

	@Column(name = "category_desc", length = 256)
	private String desc;

	@OneToMany(targetEntity = Category.class, cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "parentId")
	private List<Category> children;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public List<Category> getChildren() {
		return children;
	}

	public void setChildren(List<Category> children) {
		this.children = children;
	}
}

