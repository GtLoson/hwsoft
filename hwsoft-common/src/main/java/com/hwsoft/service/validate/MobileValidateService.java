/**
 * 
 */
package com.hwsoft.service.validate;

import com.hwsoft.common.validate.ValidateType;
import com.hwsoft.model.bank.Bank;
import com.hwsoft.model.validate.MobileValidate;

import java.util.Date;
import java.util.List;

/**
 * @author tzh
 *
 */
public interface MobileValidateService {
	
	public MobileValidate add(String mobile,ValidateType validateType);

	public MobileValidate findMobileValidateByMobile(String mobile);
	
	public boolean checkMobileCode(String mobile,String code,ValidateType validateType);
	
	public MobileValidate update(MobileValidate mobileValidate);

	public long getCount(String mobile,Date start,Date end);

	public List<MobileValidate> listAll(int from, int pageSize,String mobile,Date start,Date end);

}
