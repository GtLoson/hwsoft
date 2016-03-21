package com.hwsoft.service.template;

import java.util.List;

import com.hwsoft.common.template.TemplateType;
import com.hwsoft.model.template.Template;

public interface TemplateService {

	
	public Template findByType(TemplateType templateType);

	public long getTotalCount();

	public List<Template> listAll(int from, int pageSize);
	
	public Template add(String content,TemplateType templateType);
	
	public Template update(int templateId,String content);
	
	public Template findById(int templateId);
	
}
