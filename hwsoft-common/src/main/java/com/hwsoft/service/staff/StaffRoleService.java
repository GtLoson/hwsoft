/**
 *
 */
package com.hwsoft.service.staff;

import java.util.List;

import com.hwsoft.common.staff.StaffRoleType;
import com.hwsoft.model.staff.StaffRole;


/**
 * @author tzh
 */
public interface StaffRoleService {
    /**
     * 获取所有角色
     */
    List<StaffRole> listAll();

    /**
     * 获取所有角色
     *
     * @return
     */
    List<StaffRole> listAll(int from, int pageSize);

    /**
     * 获取角色数量
     *
     * @return
     */
    int getCount();

    /**
     * 添加角色
     *
     * @return
     */
    StaffRole addStaffRole(boolean enable,String name,String enName);

    /**
     * 根据id获取角色
     *
     * @param id
     * @return
     */
    StaffRole getStaffRoleById(int id);

    /**
     * 开启角色
     *
     * @param id
     * @return
     */
    StaffRole enableStaffRole(int id);

	/**
	 * 关闭角色
	 * 
	 * @param id
	 * @return
	 */
	StaffRole disableStaffRole(int id);
	
	/**
	 * 根据功能查询用户角色
	 * @return
	 */
	public List<StaffRole> findStaffRolesByFunctionId(int functionId) ;
	
	public StaffRole getByName(String name);
	
	
	public StaffRole getByEnName(String enName);
	
	public List<StaffRole> listAllEnable();
	
	public StaffRole authorityUpdateStaff(Integer staffRoleId,String ids);
}
