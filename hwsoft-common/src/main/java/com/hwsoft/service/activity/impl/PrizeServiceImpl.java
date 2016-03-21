/**
 *
 */
package com.hwsoft.service.activity.impl;

import com.hwsoft.common.acitivity.PrizeStatus;
import com.hwsoft.common.conf.Conf;
import com.hwsoft.common.score.ScoreOperType;
import com.hwsoft.dao.activity.PrizeDao;
import com.hwsoft.exception.activity.ActivityException;
import com.hwsoft.exception.prize.PrizeException;
import com.hwsoft.model.activity.Prize;
import com.hwsoft.service.activity.PrizeService;
import com.hwsoft.service.score.ScoreRecordService;
import com.hwsoft.spring.MessageSource;
import com.hwsoft.util.string.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author tzh
 */
@Service("prizeService")
public class PrizeServiceImpl implements PrizeService {

  @Autowired
  private MessageSource messageSource;

  @Autowired
  private PrizeDao prizeDao;


  @Autowired
  private ScoreRecordService scoreRecordService;


  /* (non-Javadoc)
   * @see com.hwsoft.service.activity.PrizeService#getTotalCountProgreesing()
   */
  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public long getTotalCountProgreesing() {
    return prizeDao.getTotalCountProgreesing();
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public long getTotalCount() {
    return prizeDao.getTotalCount();
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.activity.PrizeService#listAllProgreesing(int, int)
   */
  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public List<Prize> listAllProgreesing(int from, int pageSize) {
    return prizeDao.listAllProgreesing(from, pageSize);
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public List<Prize> listAll(int from, int pageSize) {
    return prizeDao.listAll(from, pageSize);
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.activity.PrizeService#findById(int)
   */
  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public Prize findById(int prizeId) {
    return prizeDao.findById(prizeId);
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.activity.PrizeService#exchange(int, int)
   */
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public Prize exchange(int customerId, int prizeId) {

    Prize prize = findById(prizeId);
    if (null == prize) {
      throw new ActivityException(messageSource.getMessage("prize.is.not.fund"));
    }
    if (!prize.getStatus().equals(PrizeStatus.PROGREESING)) {
      throw new ActivityException(messageSource.getMessage("prize.is.not.exchange"));
    }

    if (prize.isFixedNumber()) {//是否是固定数量
      if (prize.getLeaveNum() <= 0) {//奖品已被兑换完
        throw new ActivityException(messageSource.getMessage("prize.leave.num.is.zero"));
      }
      prize.setLeaveNum(prize.getLeaveNum() - 1);

    }
    prize.setTotalWinnerNum(prize.getTotalWinnerNum() + 1);

    if (prize.isExchange()) {
      scoreRecordService.addScoreRecord(customerId, prize.getNeedScore(), ScoreOperType.EXCHANGE, Conf.SCORE_OPERATOR_SYSTEM);
    }
    return prizeDao.update(prize);
  }

  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public Prize addPrize(String name, String desc, Integer needScore, String status) {
    checkPrizeParameter(name);
    checkPrizeParameter(desc);
    PrizeStatus prizeStatus = PrizeStatus.from(status);

    Prize prize = new Prize();
    prize.setActivityId(1);
    prize.setExchange(true);
    prize.setFixedNumber(false);
    prize.setPrizeName(name);
    prize.setLevel(1);
    prize.setNeedScore(needScore);
    prize.setPrizeDesc(desc);
    prize.setTotal(0);
    prize.setTotalWinnerNum(0);
    prize.setStatus(prizeStatus);

    return prizeDao.save(prize);
  }


  private void checkPrizeParameter(String parameter) {
    if (StringUtils.isEmpty(parameter)) {
      throw new PrizeException("物品参数不能为空");
    }
  }
}
