/**
 * 
 */
package com.hwsoft.service.product.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.dao.product.ProductBankDao;
import com.hwsoft.exception.product.ProductException;
import com.hwsoft.model.bank.Bank;
import com.hwsoft.model.product.ProductBank;
import com.hwsoft.service.bank.BankService;
import com.hwsoft.service.product.ProductBankService;

/**
 * @author tzh
 *
 */
@Service("productBankService")
public class ProductBankServiceImpl implements ProductBankService {
	
	@Autowired
	private ProductBankDao productBankDao;
	
	@Autowired
	private BankService bankService;

	/* (non-Javadoc)
	 * @see com.hwsoft.service.product.ProductBankService#addBatch(java.util.List, int)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public void addBatch(List<Integer> bankIdList, int productId) {
		if(null == bankIdList || bankIdList.size() == 0){
			return;
		}
		//先将原来的数据删除掉
		productBankDao.del(productId);
		
		for(Integer bankId : bankIdList){
			Bank bank = bankService.getById(bankId);
			if(null == bank){
				throw new ProductException("银行卡不存在");
			}
			ProductBank productBank = new ProductBank();
			productBank.setBankId(bankId);
			productBank.setProductI(productId);
			productBank.setEnable(true);//这个根据
			productBank.setBankEnable(bank.getEnable());//银行卡是否可用
			productBankDao.save(productBank);
		}
	}


}
