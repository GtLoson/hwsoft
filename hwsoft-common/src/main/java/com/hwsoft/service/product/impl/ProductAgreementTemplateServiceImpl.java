/**
 *
 */
package com.hwsoft.service.product.impl;

import com.google.common.base.Strings;
import com.hwsoft.common.agreement.AgreementType;
import com.hwsoft.common.conf.BusinessConf;
import com.hwsoft.common.order.OrderType;
import com.hwsoft.common.product.ProductChannelType;
import com.hwsoft.dao.product.ProductAgreementTemplateDao;
import com.hwsoft.model.agreement.AgreementTemplate;
import com.hwsoft.model.customer.Customer;
import com.hwsoft.model.customer.CustomerSubAccount;
import com.hwsoft.model.product.*;
import com.hwsoft.service.agreement.AgreementTemplateService;
import com.hwsoft.service.customer.CustomerService;
import com.hwsoft.service.customer.CustomerSubAccountService;
import com.hwsoft.service.product.*;
import com.hwsoft.util.date.DateTools;
import com.hwsoft.util.format.DoubleFormat;
import com.hwsoft.util.math.CalculateUtil;
import com.hwsoft.util.order.OrderUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author tzh
 */
@Service("productAgreementTemplateService")
public class ProductAgreementTemplateServiceImpl implements
		ProductAgreementTemplateService {

	private final Log logger = LogFactory
			.getLog(ProductAgreementTemplateServiceImpl.class);

	@Autowired
	private ProductAgreementTemplateDao productAgreementTemplateDao;

	@Autowired
	private AgreementTemplateService agreementTemplateService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductSubAccountService productSubAccountService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ProductChannelService productChannelService;

	@Autowired
	private CustomerSubAccountService customerSubAccountService;

	@Autowired
	private ProductRepayRecordService productRepayRecordService;

	@Autowired
	private ProductRiskControlService productRiskControlService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hwsoft.service.product.ProductAgreementTemplateService#add(int,
	 * int)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public ProductAgreementTemplate add(int productId, int agereementTemplateId) {
		ProductAgreementTemplate productAgreementTemplate = findByProductId(productId);
		AgreementTemplate agreementTemplate = agreementTemplateService
				.findById(agereementTemplateId);
		if (null != productAgreementTemplate) {
			productAgreementTemplate.setAgreementContent(agreementTemplate
					.getAgreementContent());
			productAgreementTemplate.setAgreemenId(agereementTemplateId);
			return productAgreementTemplateDao.update(productAgreementTemplate);
		} else {
			productAgreementTemplate = new ProductAgreementTemplate();
			productAgreementTemplate.setProductId(productId);
			productAgreementTemplate.setAgreementContent(agreementTemplate
					.getAgreementContent());
			productAgreementTemplate.setAgreemenId(agereementTemplateId);
			return productAgreementTemplateDao.save(productAgreementTemplate);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hwsoft.service.product.ProductAgreementTemplateService#findByProductId
	 * (int)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ProductAgreementTemplate findByProductId(int productId) {
		return productAgreementTemplateDao.findByProductId(productId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hwsoft.service.product.ProductAgreementTemplateService#
	 * findByProductIdForTemplate(int)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ProductAgreementTemplate findByProductIdForTemplate(int productId) {
		ProductAgreementTemplate template = findByProductId(productId);

		Product product = productService.findById(productId);
		// 进行转义处理
		AgreementTemplate agreementTemplate = agreementTemplateService
				.findById(template.getAgreemenId());
		if (agreementTemplate.getAgreementType().equals(
				AgreementType.REPAY_AMOUNT_AND_TRANSFER)) { // 债权回购协议
			return findByProductIdForTemplateForRepayAmountAndTransfer(product,
					template);
		} else {
			String content = template.getAgreementContent();
			String interest = "";
			if (product.getMaxInterest() == product.getMaxInterest()) {
				interest = DoubleFormat.format(
						DoubleFormat.DEFAULE_DECIMAL_FORMAT_PATTERN,
						product.getMaxInterest())
						+ "%";
			} else {
				interest = DoubleFormat.format(
						DoubleFormat.DEFAULE_DECIMAL_FORMAT_PATTERN,
						product.getMinInterest())
						+ "%"
						+ "~"
						+ DoubleFormat.format(
								DoubleFormat.DEFAULE_DECIMAL_FORMAT_PATTERN,
								product.getMaxInterest()) + "%";
			}

			String agreementFormId = OrderUtil.getOrderFormId(
					OrderType.BUY_PLAN.ordinal(), productId,
					BusinessConf.GONGMING_ORDER_SERIALNUMBER_LENGTH);

			Date changeDate = product.getCreateTime();

			try {
				changeDate = DateTools.stringToDateDay("2015-03-25");
			} catch (Exception e) {
				logger.error("居间人变更时间日期格式化错误:", e);
			}

			String userName = "王**";
			String identityNumber = "430************013";

			Date saleStartDate = product.getCreateTime();

			if (null != saleStartDate && null != changeDate
					&& saleStartDate.getTime() > changeDate.getTime()) {

				userName = "文**";
				identityNumber = "430************012";
			}

			ProductChannel productChannel = productChannelService
					.findById(product.getProductChannelId());

		/*	if (ProductChannelType.hwsoft.equals(productChannel
					.getProductChannelType())) {

				CustomerSubAccount customerSubAccount = customerSubAccountService
						.findById(product.getCustomerSubAccountId());
				Customer customer = customerService.findById(customerSubAccount
						.getCustomerId());

				userName = customer.getRealName().substring(0, 1) + "**";
				identityNumber = customer.getIdCard().substring(0, 3)
						+ "************" + customer.getIdCard().substring(15);
			}*/

			String startDate = ""; 
			String endDate = "";

			if(ProductChannelType.hwsoft.equals(productChannel.getProductChannelType())){
				startDate = "募集成功次日计息";
				endDate="借款还款完成日";
				if (null != product.getPassTime()) {
					startDate = DateTools.dateToString(product.getPassTime(),
							DateTools.DATE_PATTERN_DAY_CHINNESS_DEFAULT);
					ProductRepayRecord productRepayRecord = productRepayRecordService
							.findByLastestProductRepayRecord(product.getId());

					if (null != productRepayRecord) {
						endDate = DateTools.dateToString(
								productRepayRecord.getRepayDate(),
								DateTools.DATE_PATTERN_DAY_CHINNESS_DEFAULT);
					}
				}
			}else if(ProductChannelType.GONGMING.equals(productChannel.getProductChannelType())){
				startDate = DateTools.dateToString(
						product.getStartInterestBearingDate(),
						DateTools.DATE_PATTERN_DAY_CHINNESS_DEFAULT);

				endDate = DateTools.dateToString(
						product.getEndInterestBearingDate(),
						DateTools.DATE_PATTERN_DAY_CHINNESS_DEFAULT);
			}

			content = String.format(
					content,
					userName,
					identityNumber,
					"",
					"",
					agreementFormId,// 协议编号，这个地方需要处理一下,这个地方先使用这个
					product.getProductName(),
					DoubleFormat.format(
							DoubleFormat.DEFAULE_DECIMAL_FORMAT_PATTERN,
							product.getBaseAmount())
							+ "元",
					interest,
					product.getProductIncomeType().toString(),
					endDate,// 债权到期时间
					DoubleFormat.format(
							DoubleFormat.DEFAULE_DECIMAL_FORMAT_PATTERN,
							product.getTotalAmount())
							+ "元", startDate);
			template.setAgreementContent(content);
			return template;
		}

	}

	/*
	 * (non-Javadoc)sss
	 * 
	 * @see
	 * com.hwsoft.service.product.ProductAgreementTemplateService#findForAgreement
	 * (int, int)
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ProductAgreementTemplate findForAgreement(int productSubAccountId,
			int customerId) {

		ProductSubAccount productSubAccount = productSubAccountService
				.findById(productSubAccountId);

		ProductAgreementTemplate template = findByProductId(productSubAccount
				.getProductId());

		Product product = productService.findById(productSubAccount
				.getProductId());
		// 进行转义处理
		Customer customer = customerService.findById(productSubAccount
				.getCustomerId());

		AgreementTemplate agreementTemplate = agreementTemplateService
				.findById(template.getAgreemenId());
		if (agreementTemplate.getAgreementType().equals(
				AgreementType.REPAY_AMOUNT_AND_TRANSFER)) { // 债权回购协议
			return findForAgreementForRepayAmountAndTransfer(productSubAccount,
					template, product, customer);
		} else {
			String content = template.getAgreementContent();
			String interest = "";
			if (product.getMaxInterest() == product.getMaxInterest()) {
				interest = DoubleFormat.format(
						DoubleFormat.DEFAULE_DECIMAL_FORMAT_PATTERN,
						product.getMaxInterest())
						+ "%";
			} else {
				interest = DoubleFormat.format(
						DoubleFormat.DEFAULE_DECIMAL_FORMAT_PATTERN,
						product.getMinInterest())
						+ "%"
						+ "~"
						+ DoubleFormat.format(
								DoubleFormat.DEFAULE_DECIMAL_FORMAT_PATTERN,
								product.getMaxInterest()) + "%";
			}

			Date changeDate = product.getCreateTime();

			try {
				changeDate = DateTools.stringToDateDay("2015-03-25");
			} catch (Exception e) {
				logger.error("居间人变更时间日期格式化错误:", e);
			}

			String userName = "王**";
			String identityNumber = "430************013";

			Date saleStartDate = product.getCreateTime();

			if (null != saleStartDate && null != changeDate
					&& saleStartDate.getTime() > changeDate.getTime()) {
				userName = "文**";
				identityNumber = "430************012";
			}

			ProductChannel productChannel = productChannelService
					.findById(product.getProductChannelId());

			// if(ProductChannelType.hwsoft.equals(productChannel.getProductChannelType())){
			//
			// CustomerSubAccount customerSubAccount =
			// customerSubAccountService.findById(product.getCustomerSubAccountId());
			// Customer customer1 =
			// customerService.findById(customerSubAccount.getCustomerId());
			//
			// userName = customer1.getRealName().substring(0, 1)+"**";
			// identityNumber = customer1.getIdCard().substring(0,
			// 3)+"************"+customer1.getIdCard().substring(15);
			// }

			String startDate = "";
			String endDate = "";

			if(ProductChannelType.hwsoft.equals(productChannel.getProductChannelType())){
				startDate = "募集成功次日计息";
				endDate="借款还款完成日";
				if (null != product.getPassTime()) {
					startDate = DateTools.dateToString(product.getPassTime(),
							DateTools.DATE_PATTERN_DAY_CHINNESS_DEFAULT);
					ProductRepayRecord productRepayRecord = productRepayRecordService
							.findByLastestProductRepayRecord(product.getId());

					if (null != productRepayRecord) {
						endDate = DateTools.dateToString(
								productRepayRecord.getRepayDate(),
								DateTools.DATE_PATTERN_DAY_CHINNESS_DEFAULT);
					}
				}
			}else if(ProductChannelType.GONGMING.equals(productChannel.getProductChannelType())){
				startDate = DateTools.dateToString(
						product.getStartInterestBearingDate(),
						DateTools.DATE_PATTERN_DAY_CHINNESS_DEFAULT);

				endDate = DateTools.dateToString(
						product.getEndInterestBearingDate(),
						DateTools.DATE_PATTERN_DAY_CHINNESS_DEFAULT);
			}

			content = String.format(
					content,
					userName,
					identityNumber,
					customer.getRealName(),
					customer.getIdCard(),
					productSubAccount.getOrderFormId(),// 这个地方需要进行一下处理
					product.getProductName(),
					DoubleFormat.format(
							DoubleFormat.DEFAULE_DECIMAL_FORMAT_PATTERN,
							productSubAccount.getSuccessAmount())
							+ "元",
					interest,
					product.getProductIncomeType().toString(),
					endDate,// 债权到期时间
					DoubleFormat.format(
							DoubleFormat.DEFAULE_DECIMAL_FORMAT_PATTERN,
							product.getTotalAmount())
							+ "元", startDate);
			template.setAgreementContent(content);
			return template;
		}

	}

	/**
	 * 债权回购协议模板
	 * 
	 * @param product
	 * @param template
	 * @return
	 */
	private ProductAgreementTemplate findByProductIdForTemplateForRepayAmountAndTransfer(
			Product product, ProductAgreementTemplate template) {
		String content = template.getAgreementContent();
		String agreementFormId = OrderUtil.getOrderFormId(
				OrderType.BUY_PLAN.ordinal(), product.getId(),
				BusinessConf.GONGMING_ORDER_SERIALNUMBER_LENGTH);
		String interest = "";
		if (product.getMaxInterest() == product.getMaxInterest()) {
			interest = DoubleFormat.format(
					DoubleFormat.DEFAULE_DECIMAL_FORMAT_PATTERN,
					product.getMaxInterest())
					+ "%";
		} else {
			interest = DoubleFormat.format(
					DoubleFormat.DEFAULE_DECIMAL_FORMAT_PATTERN,
					product.getMinInterest())
					+ "%"
					+ "~"
					+ DoubleFormat.format(
							DoubleFormat.DEFAULE_DECIMAL_FORMAT_PATTERN,
							product.getMaxInterest()) + "%";
		}

		String lenderName = "";
		String lenderIdcard = "";
		int days = 5;// 提前还款利息补偿天数
		String borrowName = "抵押权利人";// 抵押权利人
		String danbaoName = "担保";// 担保方
		ProductRiskControl productRiskControl = productRiskControlService
				.findByProductId(product.getId());
		if (null != productRiskControl) {
			if (!Strings.isNullOrEmpty(productRiskControl.getMortgager())) {
				borrowName = productRiskControl.getMortgager();
			}

			if (!Strings
					.isNullOrEmpty(productRiskControl.getGuaranteeCompany())) {
				danbaoName = productRiskControl.getGuaranteeCompany();
			}
		}

		String startInterestDateString = "";
		if (null != product.getEndInterestBearingDate()) {
			startInterestDateString = DateTools.dateToString(
					product.getEndInterestBearingDate(),
					DateTools.DATE_PATTERN_DAY);
		}
		String endInterestDateString = "";
		if (null != product.getStartInterestBearingDate()) {
			endInterestDateString = DateTools.dateToString(
					product.getStartInterestBearingDate(),
					DateTools.DATE_PATTERN_DAY);
		}

		content = String.format(content, lenderName, lenderIdcard,
				agreementFormId, product.getProductName(), interest, product
						.getProductIncomeType().toString(), DoubleFormat
						.format(DoubleFormat.DEFAULE_DECIMAL_FORMAT_PATTERN,
								CalculateUtil.doubleMultiply(
										product.getTotalAmount(),
										product.getBaseAmount(), 4)),
				DoubleFormat.format(
						DoubleFormat.DEFAULE_DECIMAL_FORMAT_PATTERN,
						product.getBaseAmount()), startInterestDateString,
				endInterestDateString, days, borrowName, danbaoName);
		template.setAgreementContent(content);
		return template;
	}

	/**
	 * 债权回购协议
	 * 
	 * @param productSubAccount
	 * @param template
	 * @param product
	 * @param customer
	 * @return
	 */
	private ProductAgreementTemplate findForAgreementForRepayAmountAndTransfer(
			ProductSubAccount productSubAccount,
			ProductAgreementTemplate template, Product product,
			Customer customer) {
		String content = template.getAgreementContent();
		String agreementFormId = OrderUtil.getOrderFormId(
				OrderType.BUY_PLAN.ordinal(), product.getId(),
				BusinessConf.GONGMING_ORDER_SERIALNUMBER_LENGTH);
		String interest = "";
		if (product.getMaxInterest() == product.getMaxInterest()) {
			interest = DoubleFormat.format(
					DoubleFormat.DEFAULE_DECIMAL_FORMAT_PATTERN,
					product.getMaxInterest())
					+ "%";
		} else {
			interest = DoubleFormat.format(
					DoubleFormat.DEFAULE_DECIMAL_FORMAT_PATTERN,
					product.getMinInterest())
					+ "%"
					+ "~"
					+ DoubleFormat.format(
							DoubleFormat.DEFAULE_DECIMAL_FORMAT_PATTERN,
							product.getMaxInterest()) + "%";
		}
		String lenderName = customer.getRealName();
		String lenderIdcard = customer.getIdCard();
		int days = 5;// 提前还款利息补偿天数
		String borrowName = "抵押权利人";// 抵押权利人
		String danbaoName = "担保";// 担保方
		ProductRiskControl productRiskControl = productRiskControlService
				.findByProductId(product.getId());
		if (null != productRiskControl) {
			if (!Strings.isNullOrEmpty(productRiskControl.getMortgager())) {
				borrowName = productRiskControl.getMortgager();
			}

			if (!Strings
					.isNullOrEmpty(productRiskControl.getGuaranteeCompany())) {
				danbaoName = productRiskControl.getGuaranteeCompany();
			}
		}

		String startInterestDateString = "";
		if (null != product.getEndInterestBearingDate()) {
			startInterestDateString = DateTools.dateToString(
					product.getEndInterestBearingDate(),
					DateTools.DATE_PATTERN_DAY);
		}
		String endInterestDateString = "";
		if (null != product.getStartInterestBearingDate()) {
			endInterestDateString = DateTools.dateToString(
					product.getStartInterestBearingDate(),
					DateTools.DATE_PATTERN_DAY);
		}

		content = String.format(content, lenderName, lenderIdcard,
				agreementFormId, product.getProductName(), interest, product
						.getProductIncomeType().toString(), DoubleFormat
						.format(DoubleFormat.DEFAULE_DECIMAL_FORMAT_PATTERN,
								CalculateUtil.doubleMultiply(
										product.getTotalAmount(),
										product.getBaseAmount(), 4)),
				DoubleFormat.format(
						DoubleFormat.DEFAULE_DECIMAL_FORMAT_PATTERN,
						productSubAccount.getAmount()),
				startInterestDateString, endInterestDateString, days,
				borrowName, danbaoName);
		template.setAgreementContent(content);
		return template;
	}

}
