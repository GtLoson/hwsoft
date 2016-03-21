/**
 *
 */
package com.hwsoft.dao.staff;

import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.staff.Staff;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tzh
 */
@Repository("staffDao")
public class StaffDao extends BaseDao {

    /* (non-Javadoc)
     * @see com.hwsoft.dao.BaseDao#entityClass()
     */
    @Override
    protected Class<?> entityClass() {
        return Staff.class;
    }

    public Staff save(Staff staff) {
        return super.add(staff);
    }

    public Staff update(Staff staff) {
        return super.update(staff);
    }

    public Staff getById(final int id) {
        return super.get(id);
    }

    /**
     * 根据用户名称获取后台用户
     */
    public Staff getByName(final String name) {
        Session session = getSessionFactory().getCurrentSession();
        String hql = "from Staff where name = :name";
        Object object = session.createQuery(hql).setParameter("name", name).uniqueResult();
        return object == null ? null : (Staff) object;
    }

	
	/**
	 * 分页查询后台用户
	 * @param from
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Staff> list(int from , int pageSize){
		String hql = "from Staff";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql).setFirstResult(from).setMaxResults(from+pageSize);
		List<Staff> staffs = query.list();
		return staffs;
	}
	
	/**
	 * 拿到后台用户总记录数
	 */
	public long totalCount(){
		return super.getTotalCount();
	}
	
	/**
	 * 根据id查询staff对象
	 * @param id
	 * @return
	 */
	public Staff getStaffById(final int id){
		return super.get(id);
	}
	
	public Staff updateStaff(Staff staff ){
		return super.update(staff);
	}
}
