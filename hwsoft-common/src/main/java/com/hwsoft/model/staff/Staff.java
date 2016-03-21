/**
 * 
 */
package com.hwsoft.model.staff;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author tzh
 *
 */
@Entity
@Table(name="staff")
public class Staff implements Serializable {
	
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
	 * 名称/邮箱/登陆名称
	 */
	@Column(name = "name", nullable = false, length = 32)
	private String name;

	/**
	 * 密码 MD5
	 */
	@Column(name = "password", nullable = false, length = 64)
	private String password;

	/**
	 * 是否启用
	 */
	@Column(name = "is_enable", nullable = false)
	private Boolean enable = true;

	/**
	 * 真实姓名
	 */
	@Column(name = "real_name", nullable = false, length = 16)
	private String realname;

	/**
	 * 角色列表集合
	 */
	@ManyToMany(targetEntity = StaffRole.class, cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinTable(name = "staff_role_associate", joinColumns = @JoinColumn(name = "staff_id"), inverseJoinColumns = @JoinColumn(name = "staff_role_id"))
	private Set<StaffRole> roles;

	/**
	 * 登录时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "login_time")
	private Date loginTime;
	
	/**
	 * login ip
	 */
	@Column(name = "login_ip", length = 15)
	private String loginIp;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public Set<StaffRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<StaffRole> roles) {
		this.roles = roles;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
