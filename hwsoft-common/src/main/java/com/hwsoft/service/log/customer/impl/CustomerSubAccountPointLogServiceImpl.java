package com.hwsoft.service.log.customer.impl;

import com.hwsoft.common.point.CustomerSubAccountPointType;
import com.hwsoft.dao.log.customer.CustomerSubAccountPointLogDao;
import com.hwsoft.model.log.CustomerSubAccountPointLog;
import com.hwsoft.service.log.customer.CustomerSubAccountPointLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service("customerSubAccountPointLogService")
public class CustomerSubAccountPointLogServiceImpl implements
    CustomerSubAccountPointLogService {

  @Autowired
  private CustomerSubAccountPointLogDao customerSubAccountPointLogDao;

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public CustomerSubAccountPointLog add(double amount, double availableAmount, double freezonAmount, CustomerSubAccountPointType type, String remark,
                                        String orderFormId, int customerSubAccountId, int fromCustomerSubAccountId, Integer toCustomerSubAccountId, int realFromCustomerSubAccountId,
                                        boolean plus, int customerId, Integer productId,Integer staffId,String staffName) {

    CustomerSubAccountPointLog customerSubAccountPointLog = new CustomerSubAccountPointLog();
    customerSubAccountPointLog.setAmount(amount);
    customerSubAccountPointLog.setCreateTime(new Date());
    customerSubAccountPointLog.setCustomerSubAccountId(customerSubAccountId);
    customerSubAccountPointLog.setFromCustomerSubAccountId(fromCustomerSubAccountId);
    customerSubAccountPointLog.setOrderFormId(orderFormId);
    customerSubAccountPointLog.setPlus(plus);
    customerSubAccountPointLog.setRemark(remark);
    customerSubAccountPointLog.setToCustomerSubAccountId(toCustomerSubAccountId);
    customerSubAccountPointLog.setType(type);
    customerSubAccountPointLog.setCustomerId(customerId);
    customerSubAccountPointLog.setProductId(productId);
    customerSubAccountPointLog.setAvailableAmount(availableAmount);
    customerSubAccountPointLog.setFreezonAmount(freezonAmount);
    customerSubAccountPointLog.setRealFromCustomerSubAccountId(realFromCustomerSubAccountId);
    customerSubAccountPointLog.setStaffId(staffId);
    customerSubAccountPointLog.setStaffName(staffName);
    return customerSubAccountPointLogDao.save(customerSubAccountPointLog);
  }

}
