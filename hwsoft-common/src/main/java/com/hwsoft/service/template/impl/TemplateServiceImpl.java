package com.hwsoft.service.template.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.common.template.TemplateType;
import com.hwsoft.dao.template.TemplateDao;
import com.hwsoft.exception.template.TemplateException;
import com.hwsoft.model.template.Template;
import com.hwsoft.service.template.TemplateService;
import com.hwsoft.util.string.StringUtils;

@Service("templateService")
public class TemplateServiceImpl implements TemplateService {
	
	@Autowired
	private TemplateDao templateDao;

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public Template findByType(TemplateType templateType) {
		return templateDao.findByType(templateType);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public long getTotalCount() {
		return templateDao.getTotalCount();
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<Template> listAll(int from, int pageSize) {
		return templateDao.listAll(from, pageSize);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public Template add(String content, TemplateType templateType) {
		
		if(StringUtils.isEmpty(content)){
			throw new TemplateException("模板内容不能为空");
		}
		if(null == templateType){
			throw new TemplateException("模板类型不能为空");
		}
		Template template = findByType(templateType);
		if(null != template){
			throw new TemplateException(templateType.toString()+"类型的模板已经存在");
		}
		
		template = new Template();
		template.setCreateTime(new Date());
		template.setName(templateType.toString());
		template.setTemplateType(templateType);
		content = StringUtils.replaceBlank(content);
		template.setContent(content);
		return templateDao.save(template);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public Template update(int templateId, String content) {
		Template template = findById(templateId);
		if(null == template){
			throw new TemplateException("没有找到模板");
		}
		content = StringUtils.replaceBlank(content);
		template.setContent(content);
		return templateDao.update(template);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public Template findById(int templateId) {
		return templateDao.findById(templateId);
	}

}
