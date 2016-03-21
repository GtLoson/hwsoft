/**
 *
 */
package com.hwsoft.model.staff;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author tzh
 */
@Entity
@Table(name="staff_role")
public class StaffRole implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3272572448964964502L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

//	/**
//	 * 角色类型
//	 */
//	@Enumerated(EnumType.STRING)
//	@Column(name = "staff_role_type", nullable = false, length = 32, unique = true)
//	private StaffRoleType staffRoleType;
	
	/**
	 * 角色名称
	 */
	@Column(name = "role_name", nullable = false, length = 32, unique = true)
	private String roleName;
	
	/**
	 * 角色英文名称
	 */
	@Column(name = "role_en_name", nullable = false, length = 32, unique = true)
	private String roleEnName;

	/**
	 * 是否启用
	 */
	@Column(name = "enable", nullable = false)
	private Boolean enable;

	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false)
	private Date createTime;

	/**
	 * 可访问集合
	 */
	@ManyToMany(targetEntity = FunctionInfo.class, cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinTable(name = "rbac_function_info_staff_role_associate", joinColumns = @JoinColumn(name = "staff_role_id"), inverseJoinColumns = @JoinColumn(name = "function_info_id"))
	private Set<FunctionInfo> functionInfos;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

//	public StaffRoleType getStaffRoleType() {
//		return staffRoleType;
//	}
//
//	public void setStaffRoleType(StaffRoleType staffRoleType) {
//		this.staffRoleType = staffRoleType;
//		this.staffRoleName = this.staffRoleType.toString();
//	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Set<FunctionInfo> getFunctionInfos() {
		return functionInfos;
	}

	public void setFunctionInfos(Set<FunctionInfo> functionInfos) {
		this.functionInfos = functionInfos;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleEnName() {
		return roleEnName;
	}

	public void setRoleEnName(String roleEnName) {
		this.roleEnName = roleEnName;
	}


	
}
