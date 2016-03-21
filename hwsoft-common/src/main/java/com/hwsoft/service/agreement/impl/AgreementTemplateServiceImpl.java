/**
 *
 */
package com.hwsoft.service.agreement.impl;

import com.google.common.collect.Lists;
import com.hwsoft.common.agreement.AgreementType;
import com.hwsoft.dao.agreement.AgreementTemplateDao;
import com.hwsoft.exception.agreementtemplate.AgreementTemplateException;
import com.hwsoft.model.agreement.AgreementTemplate;
import com.hwsoft.service.agreement.AgreementTemplateService;
import com.hwsoft.util.string.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author tzh
 */
@Service("agreementTemplateService")
public class AgreementTemplateServiceImpl implements AgreementTemplateService {

  private static Log logger = LogFactory.getLog(AgreementTemplateServiceImpl.class);

  @Autowired
  private AgreementTemplateDao agreementTemplateDao;

  /* (non-Javadoc)
   * @see com.hwsoft.service.agreement.AgreementTemplateService#findByType(com.hwsoft.common.agreement.AgreementType)
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public List<AgreementTemplate> findByType(AgreementType agreementType) {
    return agreementTemplateDao.findByType(agreementType);
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.agreement.AgreementTemplateService#findById(int)
   */
  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public AgreementTemplate findById(int id) {
    return agreementTemplateDao.getById(id);
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public long getTotalCount() {
    return agreementTemplateDao.getTotalCount();
  }


  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public List<AgreementTemplate> listAll(int from, int pageSize) {
    return agreementTemplateDao.listAll(from, pageSize);
  }

  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public AgreementTemplate update(AgreementTemplate agreementTemplate) {
	  
	  AgreementTemplate agreementTemplate2 = findById(agreementTemplate.getId());
	  if(null == agreementTemplate2){
		  throw new AgreementTemplateException("合同模板未找到");
	  }
	  agreementTemplate2.setAgreementContent(agreementTemplate.getAgreementContent());
//	  agreementTemplate2.setEnable(true);
    return agreementTemplateDao.update(agreementTemplate2);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public AgreementTemplate addAgreementTemplate(String name, String agreementType, String agreementContents) {
   
	  
	checkAgreementTemplateParameter(name, "合同模板名称不能为空");
    checkAgreementTemplateParameter(agreementType, "合同模板类型不能为空");
    checkAgreementTemplateParameter(agreementContents, "合同内容不能为空");

    try {
      AgreementType.valueOf(agreementType);
    } catch (Exception e) {
      logger.error("合同类型枚举转换异常", e);
      throw new AgreementTemplateException("合同类型转换枚举异常");
    }
    agreementContents = StringUtils.replaceBlank(agreementContents);
    AgreementTemplate agreementTemplate = new AgreementTemplate();
    agreementTemplate.setName(name);
    agreementTemplate.setEnable(true);
    Date now = new Date();
    agreementTemplate.setCreateTime(now);
    agreementTemplate.setUpdateTime(now);
    agreementTemplate.setAgreementContent(agreementContents);
    agreementTemplate.setAgreementType(AgreementType.valueOf(agreementType));
    return agreementTemplateDao.save(agreementTemplate);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public AgreementTemplate enable(Integer agreementTemplateId) {
    AgreementTemplate agreementTemplate = findById(agreementTemplateId);
    if (null == agreementTemplate) {
      throw new AgreementTemplateException("Id对应的合同模板为空：Id=" + agreementTemplateId);
    }
    agreementTemplate.setEnable(true);
    agreementTemplate.setUpdateTime(new Date());

    return agreementTemplateDao.update(agreementTemplate);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public AgreementTemplate disable(Integer agreementTemplateId) {
    AgreementTemplate agreementTemplate = findById(agreementTemplateId);
    if (null == agreementTemplate) {
      throw new AgreementTemplateException("Id对应的合同模板为空：Id=" + agreementTemplateId);
    }
    agreementTemplate.setEnable(false);
    agreementTemplate.setUpdateTime(new Date());

    return agreementTemplateDao.update(agreementTemplate);
  }

  private void checkAgreementTemplateParameter(String parameter, String msg) {
    if (StringUtils.isEmpty(parameter)) {
      throw new AgreementTemplateException(msg);
    }
  }

	/* (non-Javadoc)
	 * @see com.hwsoft.service.agreement.AgreementTemplateService#findAgreementTemplateByType(com.hwsoft.common.agreement.AgreementType)
	 */
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public AgreementTemplate findAgreementTemplateByType(AgreementType agreementType) {
		return agreementTemplateDao.findAgreementTemplateByType(agreementType);
	}
  
  @Override
  @Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
  public List<AgreementTemplate> findByTypes(AgreementType... types){
    List<AgreementTemplate> agreementTemplateList = Lists.newArrayList();
    for(AgreementType type :types){

      AgreementTemplate agreementTemplate = agreementTemplateDao.findAgreementTemplateByType(type);
      agreementTemplateList.add(agreementTemplate);
    }
    return agreementTemplateList;
  }
}
