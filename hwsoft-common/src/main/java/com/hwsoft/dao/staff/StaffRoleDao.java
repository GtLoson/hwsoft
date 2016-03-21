/**
 *
 */
package com.hwsoft.dao.staff;

import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.staff.StaffRole;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import com.hwsoft.common.staff.StaffRoleType;
import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.staff.StaffRole;

/**
 * @author tzh
 */
@Repository("staffRoleDao")
public class StaffRoleDao extends BaseDao {

    
	@Override
	protected Class<?> entityClass() {
		return StaffRole.class;
	}

	/**
	 * 根据名字获取角色
	 * 
	 * @param name
	 * @return
	 */
	public StaffRole getByName(final String name) {
		Session session = getSessionFactory().getCurrentSession();
		String hql = "from StaffRole where name = :name";
		Object object = session.createQuery(hql).setParameter("name", name)
				.uniqueResult();
		return object == null ? null : (StaffRole) object;
	}

	/**
	 * 添加角色
	 */
	public StaffRole addStaffRole(final StaffRole staffRole) {
		return super.add(staffRole);
	}

	/**
	 * 获取所有staffrole（分页）
	 * 
	 * @param from
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<StaffRole> ListAll(int from, int pageSize) {
		String hql = "from StaffRole order by id";
		Query q = getSessionFactory().getCurrentSession().createQuery(hql)
				.setMaxResults(pageSize).setFirstResult(from);
		return q.list();
	}

	/***
	 * 根据角色ID查询角色信息
	 * 
	 * @return
	 */
	public StaffRole getById(int id) {
		String hql = "from StaffRole where id= :id";
		Object object = getSessionFactory().getCurrentSession()
				.createQuery(hql).setParameter("id", id).uniqueResult();
		return object == null ? null : (StaffRole) object;
	}

	/***
	 * 更新角色信息
	 * 
	 * @return
	 */
	public StaffRole updateStaffRole(StaffRole staffRole) {
		return super.update(staffRole);
	}

	/***
	 * 根据Id禁用当前角色
	 * 
	 * @return
	 */
	public StaffRole disableStaffRole(StaffRole staffRole) {
		return super.update(staffRole);

	}

	/***
	 * 根据Id设置启用角色
	 * 
	 * @param id
	 * @return
	 */
	public StaffRole enableStaffRole(StaffRole staffRole) {
		return super.update(staffRole);
	}

	/***
	 * 查询所有角色信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<StaffRole> listAll() {
		String hql = "from StaffRole order by id ";
		Query q = getSessionFactory().getCurrentSession().createQuery(hql);
		return q.list();
	}

	/**
	 * @return
	 */
	public int getCount() {
		return super.getTotalCount().intValue();
	}
	
	/**
	 * 
	 * @param functionId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<StaffRole> findStaffRolesByFunctionId(final int functionId){
		
		String sql = " SELECT a.* FROM staff_role a "+
					 " LEFT JOIN rbac_function_info_staff_role_associate aa ON a.id=aa.staff_role_id " +
					 " WHERE aa.function_info_id=?";
		SQLQuery query = getSessionFactory().getCurrentSession().createSQLQuery(sql);
		query.addEntity(StaffRole.class);
		query.setParameter(0, functionId);
		List<StaffRole> roles = query.list();
		return roles;
	}
	
	/***
	 * 根据角色ID查询角色信息
	 * 
	 * @return
	 */
	public StaffRole getByname(String name) {
		String hql = "from StaffRole where roleName= :name";
		Object object = getSessionFactory().getCurrentSession()
				.createQuery(hql).setParameter("name", name).uniqueResult();
		return object == null ? null : (StaffRole) object;
	}
	
	public StaffRole getByEnName(String enName) {
		String hql = "from StaffRole where roleEnName= :enName";
		Object object = getSessionFactory().getCurrentSession()
				.createQuery(hql).setParameter("enName", enName).uniqueResult();
		return object == null ? null : (StaffRole) object;
	}
	
	/**
	 * 查询所有可用的角色
	 * @return
	 */
	public List<StaffRole> listAllEnable() {
		String hql = "from StaffRole where enable= :enable";
		List<StaffRole> roles = getSessionFactory().getCurrentSession()
				.createQuery(hql).setParameter("enable", true).list();
		return roles;
	}
	
	/***
	 * 根据角色id查询据说是否能警用
	 * 
	 * @return
	 */
	public Integer getRoleIsUsingStaffCount(final int id) {
		String sql = "SELECT COUNT(s.id) FROM staff_role_associate sra "+
					" LEFT JOIN staff s ON s.id = sra.staff_id"+
					" WHERE s.is_enable = TRUE"+
					" AND sra.staff_role_id = "+id;
		Object object = getSessionFactory().getCurrentSession().createSQLQuery(sql).uniqueResult();
				
		return object == null ? null : Integer.parseInt(object.toString());
	}

}
