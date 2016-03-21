/**
 * 
 */
package com.hwsoft.util.idcard;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tzh
 *
 */
public class IdCardInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6539774280345276263L;

	private String idCardNumber;

	private String realName;
	
	private String gener;
	
	private Date date;
	
	private String province;
	
	private String city;
	
	private String address;
	
	private Date valiDate;
	
	private String avatar;

	public String getIdCardNumber() {
		return idCardNumber;
	}

	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getGener() {
		return gener;
	}

	public void setGener(String gener) {
		this.gener = gener;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getValiDate() {
		return valiDate;
	}

	public void setValiDate(Date valiDate) {
		this.valiDate = valiDate;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Override
	public String toString() {
		return "IdCardInfo{" +
				"idCardNumber='" + idCardNumber + '\'' +
				", realName='" + realName + '\'' +
				", gener='" + gener + '\'' +
				", date=" + date +
				", province='" + province + '\'' +
				", city='" + city + '\'' +
				", address='" + address + '\'' +
				", valiDate=" + valiDate +
				", avatar='" + avatar + '\'' +
				'}';
	}
}
