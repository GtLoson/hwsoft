package com.hwsoft.dao.statistcs;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.hwsoft.common.statistices.StatisticesDateType;
import com.hwsoft.dao.BaseDao;
import com.hwsoft.util.date.DateTools;
import com.hwsoft.util.string.StringUtils;
import com.hwsoft.vo.statistices.RegisterStaistices;

@Repository("platStatisticsDao")
public class PlatStatisticsDao extends BaseDao {

	@Override
	protected Class<?> entityClass() {
		return null;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<RegisterStaistices> findRegisterStatistices(Date startDate,
			Date endDate, Integer channelId,
			StatisticesDateType statisticesDateType, boolean channelEd) {

		StringBuffer sql = new StringBuffer("select IF(c.countNumber IS NULL , 0 , c.countNumber ) count,");
		
//		sql.append("if(is null , ");
		if (!StringUtils.isEmpty(statisticesDateType
				.groupSqlForMysql("c.reg_time"))) {
			sql.append( statisticesDateType.groupSqlForMysql("c.reg_time")+ " statisticesDate,");
		} else {
			
			if(null != startDate && null != endDate){
				sql.append(  " '"+DateTools.dateToString(startDate,
						DateTools.DATE_PATTERN_DAY)+"至"+DateTools.dateToString(endDate,
								DateTools.DATE_PATTERN_DAY)+"' statisticesDate,");
			} else if(null != startDate && null == endDate){
				sql.append(  " '"+DateTools.dateToString(startDate,
						DateTools.DATE_PATTERN_DAY)+"至今' statisticesDate,");
			} else if(null == startDate && null != endDate){
				sql.append(  " '至"+DateTools.dateToString(endDate,
						DateTools.DATE_PATTERN_DAY)+"截止' statisticesDate,");
			} else {
				sql.append(  " '至今' statisticesDate,");
			}
		}
		
		if(null == channelId && !channelEd){
			sql.append(" count(ch.id) ,");
		}
		
		if (channelEd || null != channelId) {
			sql.append(" ch.channel_id channelId,ch.name channelName,");
		}
		sql.append(" IF(p.successAmount IS NULL, 0 , p.successAmount) orderAmount," +
					" IF(p.number IS NULL, 0 , p.number) orderNumber ," +
					" TRUNCATE(IF(p.successAmount IS NULL, 0 , p.successAmount/p.number),2) orderPrice");
		sql.append(" from channel ch  ");
		
		sql.append(" left join ( SELECT COUNT(c.id) countNumber,c.channel_id ,reg_time");
		
		sql.append(" FROM customer c where 1=1 ");
		if (null != startDate) {
			sql.append(" and c.reg_time >= '"
					+ DateTools.dateToString(startDate,
							DateTools.DATE_PATTERN_DAY) + " 00:00:00'");
		}
		
		if (null != endDate) {
			sql.append(" and c.reg_time <= '"
					+ DateTools.dateToString(endDate,
							DateTools.DATE_PATTERN_DAY) + " 23:59:59'");
		}
		if (null != channelId) {
			sql.append(" and c.channel_id=" + channelId);
		}
		
		if(channelEd || !StringUtils.isEmpty(statisticesDateType
				.groupSqlForMysql("c.reg_time"))){
			sql.append(" group by ");
		}
		
		if (channelEd) {
			sql.append(" c.channel_id");
		}
		if(channelEd && !StringUtils.isEmpty(statisticesDateType
				.groupSqlForMysql("c.reg_time"))){
			sql.append(", " + statisticesDateType.groupSqlForMysql("c.reg_time"));
			sql.append(" order by " + statisticesDateType.groupSqlForMysql("c.reg_time"));
		}
		if(!channelEd && !StringUtils.isEmpty(statisticesDateType
				.groupSqlForMysql("c.reg_time"))){
			sql.append(" " + statisticesDateType.groupSqlForMysql("c.reg_time"));
			sql.append(" order by " + statisticesDateType.groupSqlForMysql("c.reg_time"));
		}
		sql.append(" ) c ON ");
		
		if(null == channelId && !channelEd){
			sql.append(" 1=1 ");
		} else {
			sql.append(" c.channel_id = ch.channel_id ");
		}
		sql.append(" LEFT JOIN"
				+ " ( SELECT IF(SUM(psa.success_amount) IS NULL , 0 ,SUM(psa.success_amount)) successAmount,COUNT(psa.id) number, c.channel_id channelId");
		if (!StringUtils.isEmpty(statisticesDateType
				.groupSqlForMysql("c.reg_time"))) {
			sql.append(" , "+statisticesDateType.groupSqlForMysql("psa.buy_time")+"buyTime ");
		}

		sql.append(" FROM product_sub_account psa "
				+ " LEFT JOIN customer c ON psa.customer_id = c.id"
				+ " WHERE ( STATUS = 'HAS_PAIED' OR STATUS='HAS_BID' OR STATUS = 'BIDING' OR STATUS='TRANSFERRING' OR STATUS='REDEEMED' OR STATUS='HAS_WITHDRAWAL' OR STATUS='WAITTING_REFUND' OR STATUS='HAS_REFUND' OR STATUS='REDEMPTION' OR STATUS='BID_SUCCESS' OR STATUS = 'PROFITING')");
		if (null != startDate) {
			sql.append(" and psa.buy_time >= '"
					+ DateTools.dateToString(startDate,
							DateTools.DATE_PATTERN_DAY) + " 00:00:00'");
		}
		if (null != endDate) {
			sql.append(" and psa.buy_time <= '"
					+ DateTools.dateToString(endDate,
							DateTools.DATE_PATTERN_DAY) + " 23:59:59'");
		}
		
		if (null != channelId) {
			sql.append(" and c.channel_id=" + channelId);
		}
		
		if(channelEd || !StringUtils.isEmpty(statisticesDateType
				.groupSqlForMysql("p.buy_time"))){
			sql.append(" group by ");
		}
		
		if (channelEd) {
			sql.append(" c.channel_id");
		}
		if(channelEd && !StringUtils.isEmpty(statisticesDateType
				.groupSqlForMysql("psa.buy_time"))){
			sql.append(", " + statisticesDateType.groupSqlForMysql("psa.buy_time"));
		}
		if(!channelEd && !StringUtils.isEmpty(statisticesDateType
				.groupSqlForMysql("psa.buy_time"))){
			sql.append(" " + statisticesDateType.groupSqlForMysql("psa.buy_time"));
		}

		sql.append(" )  p ON ");
		
		
		if(!StatisticesDateType.ALL.equals(statisticesDateType)){
			sql.append(" p.buyTime= "+statisticesDateType.groupSqlForMysql("c.reg_time"));
		}
		if(channelEd || null != channelId){

			if(!StatisticesDateType.ALL.equals(statisticesDateType)){
				sql.append(" and   p.channelId=c.channel_id");
			} else {
				sql.append(" p.channelId=c.channel_id ");
			}
		} else {
			if(StatisticesDateType.ALL.equals(statisticesDateType)){
				sql.append(" 1=1 ");
			}
		}
		sql.append(" where 1=1 ");
		if (null != channelId) {
			sql.append(" and ch.channel_id=" + channelId);
		}
		
		if(channelEd || !StringUtils.isEmpty(statisticesDateType
				.groupSqlForMysql("c.reg_time"))){
			sql.append(" group by ");
		}
		
		if (channelEd) {
			sql.append(" c.channel_id");
		}
		if(channelEd && !StringUtils.isEmpty(statisticesDateType
				.groupSqlForMysql("c.reg_time"))){
			sql.append(", " + statisticesDateType.groupSqlForMysql("c.reg_time"));
			sql.append(" order by " + statisticesDateType.groupSqlForMysql("c.reg_time"));
		}
		if(!channelEd && !StringUtils.isEmpty(statisticesDateType
				.groupSqlForMysql("c.reg_time"))){
			sql.append(" " + statisticesDateType.groupSqlForMysql("c.reg_time"));
			sql.append(" order by " + statisticesDateType.groupSqlForMysql("c.reg_time"));
		}
		
		System.out.println(sql.toString());

		SQLQuery sqlQuery = super.getSessionFactory().getCurrentSession()
				.createSQLQuery(sql.toString())
				.addScalar("count", Hibernate.INTEGER)
				.addScalar("orderAmount", Hibernate.DOUBLE)
				.addScalar("orderNumber", Hibernate.INTEGER)
				.addScalar("orderPrice", Hibernate.DOUBLE);
		sqlQuery.addScalar("statisticesDate", Hibernate.STRING);
				
		if (channelEd || null != channelId) {
			sqlQuery.addScalar("channelId", Hibernate.INTEGER);
			sqlQuery.addScalar("channelName", Hibernate.STRING);
		}
		sqlQuery.setResultTransformer(Transformers
				.aliasToBean(RegisterStaistices.class));
		return sqlQuery.list();
	}

}
