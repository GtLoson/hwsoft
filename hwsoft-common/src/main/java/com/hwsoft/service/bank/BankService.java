package com.hwsoft.service.bank;

import com.hwsoft.common.product.ProductChannelType;
import com.hwsoft.model.bank.Bank;
import com.hwsoft.model.file.CustomFile;

import java.util.List;

/**
 * 银行服务
 */
public interface BankService {

    public long getTotalCount();

    public List<Bank> listAll(int from, int pageSize);

    public long getTotalCountByProductChannelType(ProductChannelType productChannelType);

    public List<Bank> listByProductChannelType(int from, int pageSize, ProductChannelType productChannelType);

    public void enableBank(Integer bankId);

    public void disableBank(Integer bankId);

    public Bank addBank(
            String bankName,
            String path,
            Double timeLimit,
            Double dayLimit,
            boolean enable,
            String bankCode,
            String productChannelType);

    public List<Bank> getListByChannelId(int channelId);

    public Bank getById(int id);

    public List<Bank> getListByProductId(int productId);

    public Bank updateBank(Integer bankId,
                           String bankName,
                           String path,
                           Double timeLimit,
                           Double dayLimit,
                           boolean enable,
                           String bankCode,
                           String productChannelType);
}
