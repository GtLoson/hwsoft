package com.hwsoft.datamove;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

public class DB {

	private static Connection conn = null;	
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static String URL = "jdbc:mysql://localhost:3306/hwsoft";
	private static String USERNAME = "hwsoft";
	private static String PASSWORD = "Qmm123.hwsoft";

	public static Connection getConn(){
//		Connection conn = null;
		try{
			DbUtils.loadDriver(DRIVER);
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			conn.setAutoCommit(false); // 关闭自动提交
			return conn;
		}catch (Exception ex) {
			ex.printStackTrace();
			throw new DbUtilException(ex.getMessage());
		} 
	}
	
	public static Map<String, Object> findObject(String sql){
		conn = getConn();
		try {
			QueryRunner qRunner = new QueryRunner();
			Map<String, Object> map = (Map<String, Object>) qRunner.query(conn,sql,new MapHandler());
			return map;
		}catch (Exception ex) {
			ex.printStackTrace();
			throw new DbUtilException(ex.getMessage());
		} finally {
			DbUtils.closeQuietly(conn);
		}
	}
	
	public static List<Map<String, Object>> findList(String sql){
		conn = getConn();
		try {
			QueryRunner qRunner = new QueryRunner();
			List<Map<String, Object>> list = (List<Map<String, Object>>) qRunner.query(conn,sql, new MapListHandler());
			return list;
		}catch (Exception ex) {
			ex.printStackTrace();
			throw new DbUtilException(ex.getMessage());
		} finally {
			DbUtils.closeQuietly(conn);
		}
		
	}

}
