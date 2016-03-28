/**
 * 
 */
package com.hwsoft.model.news;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="news")
public class News implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4219947898205356995L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	/**
	 * 标题
	 */
	@Column(name = "title", nullable = false, length = 255)
	private String title;

	/**
	 * 内容
	 */
	@Column(name = "content", nullable = false, length = 3000)
	private String content;

	/**
	 * 图片
	 */
	@Column(name = "image_url", nullable = false, length = 255)
	private String imageUrl;

	/**
	 * 是否启用
	 */
	@Column(name = "status", nullable = false)
	private Boolean status = true;

	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false)
	private Date createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
