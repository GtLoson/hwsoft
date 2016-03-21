package com.hwsoft.service.bank.impl;

import com.hwsoft.common.product.ProductChannelType;
import com.hwsoft.dao.bank.BankDao;
import com.hwsoft.exception.bank.BankException;
import com.hwsoft.exception.productchannel.ProductChannelException;
import com.hwsoft.model.bank.Bank;
import com.hwsoft.model.file.CustomFile;
import com.hwsoft.model.product.ProductChannel;
import com.hwsoft.service.bank.BankService;
import com.hwsoft.service.product.ProductChannelService;
import com.hwsoft.spring.MessageSource;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 银行服务实现类
 */
@Service("bankService")
public class BankServiceImpl implements BankService {

    private static Log logger = LogFactory.getLog(BankServiceImpl.class);

    @Autowired
    private BankDao bankDao;

    @Autowired
    private ProductChannelService productChannelService;

    @Autowired
    private MessageSource messageSource;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public long getTotalCount() {
        return bankDao.getTotalCount().longValue();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public long getTotalCountByProductChannelType(ProductChannelType productChannelType) {
        return 0L;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Bank> listByProductChannelType(int from, int pageSize, ProductChannelType productChannelType) {
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Bank> listAll(int from, int pageSize) {
        return bankDao.list(from, pageSize);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public void enableBank(Integer bankId) {
        Bank bank = getById(bankId);
        bank.setEnable(true);
        bankDao.updateBank(bank);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public void disableBank(Integer bankId) {
        Bank bank = getById(bankId);
        bank.setEnable(false);
        bankDao.updateBank(bank);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public Bank addBank(String bankName,
                        String path,
                        Double timeLimit,
                        Double dayLimit,
                        boolean enable,
                        String bankCode,
                        String productChannelTypeString) {

        checkBankParameter(bankName, "bank.name.can.not.be.null");
        checkBankParameter(bankCode, "bank.code.can.not.be.null");
        checkBankParameter(timeLimit, "bank.time.limit.can.not.be.null");
        checkBankParameter(dayLimit, "bank.day.limit.can.not.be.null");
        checkBankParameter(productChannelTypeString, "bank.channel.can.not.be.null");
        Bank bank = new Bank();
        bank.setEnable(enable);
        bank.setBankName(bankName);
        bank.setBankCode(bankCode);
        bank.setBankPicPath(path);
        bank.setBankDayLimit(dayLimit);
        bank.setBankTimeLimit(timeLimit);
        try {
            ProductChannelType type = ProductChannelType.valueOf(productChannelTypeString);
            ProductChannel channel = productChannelService.findByType(type);

            if (null == channel) {
                throw new ProductChannelException("查询渠道为空，channelString:" + type);
            }
            bank.setProductChannelId(channel.getId());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("ProductChannelType 枚举类型转换成渠道枚举或者渠道model:" + e.getLocalizedMessage());
        }
        bank = bankDao.saveBank(bank);
        return bank;
    }

    private void checkBankParameter(String parameter, String key) {
        if (StringUtils.isEmpty(parameter)) {
            logger.error("请求参数不正确：" + messageSource.getMessage(key) + "传入值：【" + parameter + "】");
            throw new BankException(messageSource.getMessage(key));
        }
    }

    private void checkBankParameter(Double parameter, String key) {
        if (null == parameter | parameter.doubleValue() == 0) {
            logger.error("请求参数不正确：" + messageSource.getMessage(key) + "传入值：【" + parameter + "】");
            throw new BankException(messageSource.getMessage(key));
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Bank> getListByChannelId(int channelId) {
        return bankDao.getListByChannelId(channelId);
    }

    /* (non-Javadoc)
     * @see com.hwsoft.service.bank.BankService#getById(int)
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Bank getById(int id) {
        return bankDao.findById(id);
    }

    /* (non-Javadoc)
     * @see com.hwsoft.service.bank.BankService#getListByProductId(int)
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Bank> getListByProductId(int productId) {
        return bankDao.getListByProductId(productId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public Bank updateBank(Integer bankId,
                           String bankName,
                           String path,
                           Double timeLimit,
                           Double dayLimit,
                           boolean enable,
                           String bankCode,
                           String productChannelTypeString) {
        checkBankParameter(bankName, "bank.name.can.not.be.null");
        checkBankParameter(bankCode, "bank.code.can.not.be.null");
        checkBankParameter(timeLimit, "bank.time.limit.can.not.be.null");
        checkBankParameter(dayLimit, "bank.day.limit.can.not.be.null");
        checkBankParameter(productChannelTypeString, "bank.channel.can.not.be.null");
        Bank bank = getById(bankId);
        if (null == bank) {
            throw new BankException(messageSource.getMessage("bank.not.exiting"));
        }
        bank.setBankCode(bankCode);
        bank.setBankDayLimit(dayLimit);
        bank.setBankTimeLimit(timeLimit);
        if(null != path) {
            bank.setBankPicPath(path);
        }
        bank.setBankName(bankName);
        bank.setEnable(enable);
        ProductChannelType productChannelType;
        try {
            productChannelType = ProductChannelType.valueOf(productChannelTypeString);
        } catch (Exception e) {
            throw new BankException("产品渠道枚举转化异常：" + productChannelTypeString);
        }
        ProductChannel productChannel = productChannelService.findByType(productChannelType);
        if (null == productChannel) {
            throw new BankException("获取产品渠道失败：" + productChannelType);
        }
        bank.setProductChannelId(productChannel.getId());
        bank = bankDao.updateBank(bank);
        return bank;
    }
}
