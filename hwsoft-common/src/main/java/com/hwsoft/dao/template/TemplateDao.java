package com.hwsoft.dao.template;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hwsoft.common.template.TemplateType;
import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.template.Template;

@Repository("templateDao")
public class TemplateDao extends BaseDao {

	@Override
	protected Class<?> entityClass() {
		return Template.class;
	}
	
	public Template findByType(TemplateType templateType){
		String hql = " from Template where templateType=:templateType";
		return (Template) super.getSessionFactory().getCurrentSession().createQuery(hql).setParameter("templateType", templateType).uniqueResult();
	}
	
	public Long getTotalCount() {
		return super.getTotalCount();
	}

	public List<Template> listAll(int from, int pageSize) {
		return super.list(from, pageSize);
	}
	
	public Template save(Template template){
		return super.add(template);
	}
	
	public Template update(Template template){
		return super.update(template);
	}
	
	public Template findById(int id){
		return super.get(id);
	}
}
