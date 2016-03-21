/**
 *
 */
package com.hwsoft.service.bank.impl;

import com.hwsoft.common.bank.UserBankCardBindStatus;
import com.hwsoft.dao.bank.UserBankCardDao;
import com.hwsoft.exception.bank.UserBankCardException;
import com.hwsoft.model.bank.Bank;
import com.hwsoft.model.bank.UserBankCard;
import com.hwsoft.model.customer.CustomerSubAccount;
import com.hwsoft.model.product.ProductChannel;
import com.hwsoft.service.bank.BankService;
import com.hwsoft.service.bank.UserBankCardService;
import com.hwsoft.service.customer.CustomerSubAccountService;
import com.hwsoft.service.product.ProductChannelService;
import com.hwsoft.util.string.StringUtils;
import com.hwsoft.vo.bank.UserBankCardVo;
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
@Service("userBankCardService")
public class UserBankCardServiceImpl implements UserBankCardService {

  private final Log logger = LogFactory.getLog(UserBankCardServiceImpl.class);

  @Autowired
  private UserBankCardDao userBankCardDao;

  @Autowired
  private BankService bankService;

  @Autowired
  private ProductChannelService productChannelService;

  @Autowired
  private CustomerSubAccountService customerSubAccountService;

  /* (non-Javadoc)
   * @see com.hwsoft.service.bank.UserBankCardService#findUserBankCardById(int)
   */
  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public UserBankCard findUserBankCardById(int id) {
    return userBankCardDao.findById(id);
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.bank.UserBankCardService#bindBankCard(int)
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public UserBankCard bindBankCardSuccess(int id, String agreementNumber) {
    UserBankCard userBankCard = findUserBankCardById(id);
    if (null == userBankCard) {
      throw new UserBankCardException("银行卡未找到");
    }
    userBankCard.setStatus(UserBankCardBindStatus.BINDED);

    if (!StringUtils.isEmpty(agreementNumber)) {
      userBankCard.setAgreementNumber(agreementNumber);
    }
    System.out.println(agreementNumber + "----------" + userBankCard.getAgreementNumber());
    return userBankCardDao.update(userBankCard);
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.bank.UserBankCardService#bindBankCardFailed(int)
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public UserBankCard bindBankCardFailed(int id) {
    UserBankCard userBankCard = findUserBankCardById(id);
    if (null == userBankCard) {
      throw new UserBankCardException("银行卡未找到");
    }
    userBankCard.setStatus(UserBankCardBindStatus.BINDED_FAILED);
    return userBankCardDao.update(userBankCard);
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.bank.UserBankCardService#findUserBankCardVosByUserIdAndProductId(int, int)
   */
  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public List<UserBankCardVo> findUserBankCardVosByUserIdAndProductId(
      int userId, int productId) {
    return userBankCardDao.findUserBankCardVosByUserIdAndProductId(userId, productId);
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.bank.UserBankCardService#bindBankCard(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public UserBankCard addUserBankCard(int customerId, int bankId, String cardNumber,
                                      String province, String city, String branch, String mobile,
                                      int productChannelId) {
    // 查询银行信息，查询产品渠道信息
    Bank bank = bankService.getById(bankId);
    if (null == bank) {
      throw new UserBankCardException("银行未找到");
    }
    ProductChannel productChannel = productChannelService.findById(productChannelId);
    if (null == productChannel) {
      throw new UserBankCardException("产品渠道未找到");
    }
    CustomerSubAccount customerSubAccount = customerSubAccountService.findByCustomerSubAccountByUserIdAndProductChannelId(customerId, productChannelId);
    if (null == customerSubAccount) {
      throw new UserBankCardException("渠道账户未开通");
    }

    // 进行数据校验
    List<UserBankCard> userBankCardTmps = findUserBankCardByBankCardNumber(cardNumber);

    for (UserBankCard userBankCardTmp : userBankCardTmps) {
      if (null != userBankCardTmp && userBankCardTmp.getStatus().equals(UserBankCardBindStatus.BINDED)) {
        if (userBankCardTmp.getCustomerId() == customerId && userBankCardTmp.getCustomerSubAccountId().intValue() == customerSubAccount.getId().intValue()) {
          logger.error("银行卡已绑定：" + userBankCardTmps);
          throw new UserBankCardException("该银行卡已经绑定");
        }
      }

    }


    // if(null == userBankCardTmp){
    UserBankCard userBankCard = new UserBankCard();
    userBankCard.setBankBranchName(branch);
    userBankCard.setBankCardCity(city);
    userBankCard.setBankCardNumber(cardNumber);
    userBankCard.setBankCardProvince(province);
    userBankCard.setBankCode(bank.getBankCode());
    userBankCard.setBankId(bankId);
    userBankCard.setBankName(bank.getBankName());
    userBankCard.setCreateTime(new Date());
    userBankCard.setEnable(true);
    userBankCard.setReservePhoneNumber(mobile);
    userBankCard.setStatus(UserBankCardBindStatus.NO_BIND);
    userBankCard.setCustomerSubAccountId(customerSubAccount.getId());
    userBankCard.setCustomerId(customerId);
    userBankCard.setMemberId(customerSubAccount.getMemberId());
    return userBankCardDao.save(userBankCard);
//		} else if(!userBankCardTmp.isEnable()){
//			userBankCardTmp.setEnable(true);
//			return userBankCardDao.save(userBankCardTmp);
//		} else {
//			return userBankCardTmp;
//		}
  }

  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public UserBankCard addUserBankCardForSpecialChannel(int customerId,
                                                       int bankId,
                                                       String cardNumber,
                                                       String provinceCode,
                                                       String cityCode,
                                                       String branchName,
                                                       String prctcd,
                                                       Integer productChannelId) {

    // 查询银行信息，查询产品渠道信息
    Bank bank = bankService.getById(bankId);
    if (null == bank) {
      throw new UserBankCardException("银行未找到");
    }
    ProductChannel productChannel = productChannelService.findById(productChannelId);
    if (null == productChannel) {
      throw new UserBankCardException("产品渠道未找到");
    }
    CustomerSubAccount customerSubAccount = customerSubAccountService.findByCustomerSubAccountByUserIdAndProductChannelId(customerId, productChannelId);
    if (null == customerSubAccount) {
      throw new UserBankCardException("渠道账户未开通");
    }

    // 进行数据校验
    List<UserBankCard> userBankCardBindTmps = findUserBankCardByBankCardNumber(cardNumber);

    for (UserBankCard userBankCardTmp : userBankCardBindTmps) {
      if (userBankCardTmp.getCustomerId() == customerId && userBankCardTmp.getCustomerSubAccountId().intValue() == customerSubAccount.getId().intValue()) {

        if (userBankCardTmp.getStatus().equals(UserBankCardBindStatus.BINDED)) {
          logger.error("银行卡已绑定：" + userBankCardTmp);
          throw new UserBankCardException("该银行卡已经绑定");
        } else {
          userBankCardTmp.setBankBranchName(branchName);
          userBankCardTmp.setBankCardCityCode(cityCode);
          userBankCardTmp.setBankCardNumber(cardNumber);
          userBankCardTmp.setBankCardProvinceCode(provinceCode);
          userBankCardTmp.setBankCode(bank.getBankCode());
          userBankCardTmp.setBankId(bankId);
          userBankCardTmp.setBankName(bank.getBankName());
          userBankCardTmp.setEnable(true);
          userBankCardTmp.setStatus(UserBankCardBindStatus.PEDDING);
          userBankCardTmp.setPrcptcd(prctcd);
          userBankCardTmp.setCustomerSubAccountId(customerSubAccount.getId());
          userBankCardTmp.setCustomerId(customerId);
          userBankCardTmp.setMemberId(customerSubAccount.getMemberId());
          return userBankCardDao.update(userBankCardTmp);
        }
      }

    }

    UserBankCard userBankCard = new UserBankCard();
    userBankCard.setBankBranchName(branchName);
    userBankCard.setBankCardCityCode(cityCode);
    userBankCard.setBankCardNumber(cardNumber);
    userBankCard.setBankCardProvinceCode(provinceCode);
    userBankCard.setBankCode(bank.getBankCode());
    userBankCard.setBankId(bankId);
    userBankCard.setBankName(bank.getBankName());
    userBankCard.setEnable(true);
    userBankCard.setStatus(UserBankCardBindStatus.PEDDING);
    userBankCard.setPrcptcd(prctcd);
    userBankCard.setCreateTime(new Date());
    userBankCard.setCustomerSubAccountId(customerSubAccount.getId());
    userBankCard.setCustomerId(customerId);
    userBankCard.setMemberId(customerSubAccount.getMemberId());
    return userBankCardDao.save(userBankCard);

//    if (null != userBankCardBindTmps && userBankCardBindTmps.getStatus().equals(UserBankCardBindStatus.BINDED)) {
//      if (userBankCardBindTmps.getCustomerId() == customerId && userBankCardBindTmps.getCustomerSubAccountId().intValue() == customerSubAccount.getId().intValue()) {
//        logger.error("银行卡已绑定：" + userBankCardBindTmps);
//        throw new UserBankCardException("该银行卡已经绑定");
//      }
//    } else if (null != userBankCardBindTmps && userBankCardBindTmps.getStatus().equals(UserBankCardBindStatus.BINDED)) {
//
//
//    }

  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.bank.UserBankCardService#findUserBankCardByBankCardNumber(java.lang.String)
   */
  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public List<UserBankCard> findUserBankCardByBankCardNumber(String bankCardNumber) {
    return userBankCardDao.findUserBankCardByBankCardNumber(bankCardNumber);
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public List<UserBankCard> findAllEnableBySubAccountId(
      int customerSubAccountId) {

    return userBankCardDao.findAllEnableBySubAccountId(customerSubAccountId);
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public List<UserBankCardVo> findByChannelId(int productChannelId, int customerId) {
    return userBankCardDao.findByChannelId(productChannelId, customerId);
  }


}
