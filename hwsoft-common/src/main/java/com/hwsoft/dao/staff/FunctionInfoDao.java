/**
 *
 */
package com.hwsoft.dao.staff;

import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.staff.FunctionInfo;
import com.hwsoft.vo.staff.AuthorityVo;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author tzh
 */
@Repository("functionInfoDao")
public class FunctionInfoDao extends BaseDao {

	@Override
	protected Class<?> entityClass() {
		return FunctionInfo.class;
	}
	@SuppressWarnings("unchecked")
	public List<FunctionInfo> findFunctionInfosByParenId(Integer parentId) {
		String hql = "from FunctionInfo where parent_id="+parentId;
		List<FunctionInfo> functionInfos = super.getHibernateTemplate().find(hql);
		return functionInfos;
	}
	
	
	
	/**
	 * 根据 parentId查询 总记录数
	 * @param parentId
	 * @return
	 */
	public int getCountByParenId(Integer parentId) {
		String hql = "select count(id) from FunctionInfo where parentId = "+parentId;
		return ((Long) getSessionFactory().getCurrentSession().createQuery(hql).uniqueResult()).intValue();
	}
	
	/**
	 * 根据父亲id分页查询孩子数据
	 * @param parentId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<FunctionInfo> findPageFunctionInfosByParenId(Integer parentId, int from , int pageSize){
		String hql = "from FunctionInfo where parentId="+parentId;
		return super.getSessionFactory().getCurrentSession().createQuery(hql).setFirstResult(from).setMaxResults(pageSize).list();
	}
	
	/***
	 * 根据功能名查询功能
	 * @return
	 */
	public FunctionInfo getByName(final String name){
		 String hql = "from FunctionInfo where name = :name";
	        Object object = getSessionFactory().getCurrentSession().createQuery(hql).setParameter("name", name).uniqueResult();
	        return object == null ? null : (FunctionInfo)object ;
	}
	/***
	 * 添加功能
	 * @return
	 */
	public FunctionInfo addFunctionInfo(final FunctionInfo functionInfo){
		return  super.add(functionInfo);
	}
	/***
	 * 更新功能
	 * @param functionInfo
	 * @return
	 */
	public FunctionInfo updateFunctionInfo(final FunctionInfo functionInfo){
		return super.update(functionInfo);
	}
	/***
	 * 根据ID查询功能
	 * @param functionInfo
	 * @return
	 */
	public FunctionInfo getById(final int id){
		 String hql = "from FunctionInfo where id = :id";
	     Object object = getSessionFactory().getCurrentSession().createQuery(hql).setParameter("id", id).uniqueResult();
	     return object == null ? null : (FunctionInfo)object ;
	}
	/**
	 * @return
	 */
	public int getCount() {
		return super.getTotalCount().intValue();
	}
	
	/**
	 * 查询所有的功能
	 * @return
	 */
	public List<FunctionInfo> listAll(){
		return super.loanAll();
	}
	
	
	
	/**
	 * 查询所有的功能(不查询孩子节点)
	 * 只查询id和url
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> listNoChildrenAll(){
		String sql = " select fi.id id,fi.uri uri from function_info fi";
		SQLQuery sqlQuery = getSessionFactory().getCurrentSession().createSQLQuery(sql);
		return sqlQuery.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP).list();
	}
	
	/***
	 * 根据功能名查询功能
	 * @return
	 */
	public FunctionInfo getByUrl(final String url){
		 String hql = "from FunctionInfo where uri = :uri";
	     Object object = getSessionFactory().getCurrentSession().createQuery(hql).setParameter("uri", url).uniqueResult();
	     return object == null ? null : (FunctionInfo)object ;
	}
	
	
	/**
	 * 加载权限数据
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<AuthorityVo> loadResourceDefine(Integer functionInfoId){
		String sql = "SELECT fi.uri uri,sr.role_en_name roleName FROM function_info fi "+ 
					 "	LEFT JOIN rbac_function_info_staff_role_associate r  ON r.function_info_id = fi.id"+
					 "	LEFT JOIN staff_role sr ON r.staff_role_id = sr.id";
		if(null != functionInfoId){
			sql += " where fi.id="+functionInfoId;
		}
		List<AuthorityVo> vos = getSessionFactory().getCurrentSession().createSQLQuery(sql)
								.addScalar("uri", Hibernate.STRING)
								.addScalar("roleName", Hibernate.STRING)
								.setResultTransformer(Transformers.aliasToBean(AuthorityVo.class)).list();
		return vos;
	}
}
