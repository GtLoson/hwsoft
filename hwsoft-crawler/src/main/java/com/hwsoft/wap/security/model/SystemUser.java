package com.hwsoft.wap.security.model;

import com.hwsoft.model.customer.Customer;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class SystemUser implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6403754169996141584L;

	private Customer customer;
	
	/**
	 * 上一次操作时间
	 */
	private long lastUpdateTime;
	
	public SystemUser(Customer customer) {
		super();
		this.customer = customer;
		this.lastUpdateTime = System.currentTimeMillis();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return customer.getPassword();
	}

	@Override
	public String getUsername() {
		return customer.getMobile();
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
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
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
		if (this.customer.getId().intValue() != other.customer.getId().intValue()) {
			return false;
		}
		
		return true;
	}

	@Override
	public int hashCode() {
		int result = 31;
		if(null == customer){
			return super.hashCode();
		}
		result = 11 * result + (int) customer.getId();
//		result = 11 * result + customer.getUsername().hashCode();
		return result;
	}

}
