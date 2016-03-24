/**
 *
 */
package com.hwsoft.service.staff;

import java.util.List;

import com.hwsoft.common.staff.StaffRoleType;
import com.hwsoft.model.staff.Staff;


/**
 * @author tzh
 */
public interface StaffService {
    /**
     * 获取后台用户
     */
    Staff getByName(String name);

    /**
     * 更新用户
     *
     * @param
     * @return
     */
    Staff updateStaff(int staffId,String realName,boolean enable, String enName);

    /**
     * 启用用户
     *
     * @param id
     * @return
     */
    Staff enableStaff(int id);

    /**
     * 禁用用户
     *
     * @param id
     * @return
     */
    Staff disableStaff(int id);

    /**
     * 根据id获取用户
     *
     * @param id
     * @return
     */
    Staff getStaffById(int id);
	/**
	 * 添加用户
	 * 
	 * @param staff
	 * @return
	 */
	///Staff addStaff(Staff staff);
	public Staff addStaff(String name,String realname,String password,boolean eable, String enName);

    /**
     * 获取所有的用户
     *
     * @param from
     * @param pageSize
     * @return
     */
    List<Staff> listAll(int from, int pageSize);


    /**
     * 查询后台用户总记录数
     *
     * @return
     */
    public long getTotalCount();

    /**
     * 更新用户登陆信息
     */
    Staff updateStaffLoginInfo(int id, String loginIp);
}
