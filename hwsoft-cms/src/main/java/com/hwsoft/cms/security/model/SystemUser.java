package com.hwsoft.cms.security.model;


import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.hwsoft.model.staff.Staff;
import com.hwsoft.model.staff.StaffRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class SystemUser implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6403754169996141584L;

	private Staff staff;
	
	public SystemUser(Staff staff) {
		super();
		this.staff = staff;
		
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> list = new LinkedList<GrantedAuthority>();
		Set<StaffRole> roles = staff.getRoles();
		if(null != roles && roles.size() != 0){
			for(StaffRole role : roles){
				list.add(new SimpleGrantedAuthority(role.getRoleEnName()));
			}
		}
		
		return list;
	}

	@Override
	public String getPassword() {
		return staff.getPassword();
	}

	@Override
	public String getUsername() {
		return staff.getName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final SystemUser other = (SystemUser) obj;
		if (this.staff.getId().intValue() != other.staff.getId().intValue()) {
			return false;
		}
		
		return true;
	}

	@Override
	public int hashCode() {
		int result = 31;
		if(null == staff){
			return super.hashCode();
		}
		result = 11 * result + (int) staff.getId();
//		result = 11 * result + staff.getName().hashCode();
		return result;
	}

}
