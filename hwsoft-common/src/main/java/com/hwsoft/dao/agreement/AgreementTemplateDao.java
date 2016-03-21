/**
 *
 */
package com.hwsoft.dao.agreement;

import com.hwsoft.common.agreement.AgreementType;
import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.agreement.AgreementTemplate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tzh
 */
@Repository("agreementTemplateDao")
public class AgreementTemplateDao extends BaseDao {

  /* (non-Javadoc)
   * @see com.hwsoft.dao.BaseDao#entityClass()
   */
  @Override
  protected Class<?> entityClass() {
    return AgreementTemplate.class;
  }

  public List<AgreementTemplate> findByType(AgreementType agreementType) {
    String hql = " from AgreementTemplate where agreementType=:agreementType and enable=:enable";
    return super.getSessionFactory().getCurrentSession().createQuery(hql)
        .setParameter("agreementType", agreementType)
        .setParameter("enable", true).list();
  }
  
  public AgreementTemplate findAgreementTemplateByType(AgreementType agreementType) {
	    String hql = " from AgreementTemplate where agreementType=:agreementType and enable=:enable";
	    return (AgreementTemplate) super.getSessionFactory().getCurrentSession().createQuery(hql)
	        .setParameter("agreementType", agreementType)
	        .setParameter("enable", true).uniqueResult();
  }

  public Long getTotalCount() {
    return super.getTotalCount();
  }

  public List<AgreementTemplate> listAll(int from, int pageSize) {
    String hql = "from AgreementTemplate";
    Session session = getSessionFactory().getCurrentSession();
    Query query = session.createQuery(hql).setFirstResult(from)
        .setMaxResults(pageSize);
    List<AgreementTemplate> agreementTemplates = query.list();
    return agreementTemplates;
  }

  public AgreementTemplate getById(Integer id) {
    return super.get(id);
  }

  public AgreementTemplate save(AgreementTemplate agreementTemplate) {
    return super.add(agreementTemplate);
  }

  public AgreementTemplate update(AgreementTemplate agreementTemplate) {
    return super.update(agreementTemplate);
  }
}
