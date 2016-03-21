/**
 *
 */
package com.hwsoft.service.agreement;

import com.hwsoft.common.agreement.AgreementType;
import com.hwsoft.model.agreement.AgreementTemplate;

import java.util.List;

/**
 * @author tzh
 */
public interface AgreementTemplateService {

  public List<AgreementTemplate> findByType(AgreementType agreementType);
  
  public AgreementTemplate findAgreementTemplateByType(AgreementType agreementType);
  
  public List<AgreementTemplate> findByTypes(AgreementType... types);

  public AgreementTemplate findById(int id);

  public long getTotalCount();

  public List<AgreementTemplate> listAll(int from, int pageSize);

  public AgreementTemplate addAgreementTemplate(String name,
                                                String agreementType,
                                                String agreementContents);

  public AgreementTemplate update(AgreementTemplate agreementTemplate);

  public AgreementTemplate enable(Integer agreementTemplateId);

  public AgreementTemplate disable(Integer agreementTemplateId);
}
