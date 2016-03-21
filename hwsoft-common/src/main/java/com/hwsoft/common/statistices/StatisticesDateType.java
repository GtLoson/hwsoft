package com.hwsoft.common.statistices;

/**
 * 统计日期分组类型
 * @author tzh
 *
 */
public enum StatisticesDateType {
	
	
	DAY(){

		@Override
		public String groupSqlForMysql(String columnName) {
			return "DATE_FORMAT( "+columnName+", \"%Y-%m-%d\" )";
		}

		@Override
		public String toString() {
			return "按天分组";
		}
		
	},
//	WEEK(){
//		@Override
//		public String groupSqlForMysql(String columnName) {
//			return "DATE_FORMAT( "+columnName+", \"%Y-%m-%d\" )";
//		}
//
//		@Override
//		public String toString() {
//			return "按天分组";
//		}
//	},
	MONTH(){
		@Override
		public String groupSqlForMysql(String columnName) {
			return "DATE_FORMAT( "+columnName+", \"%Y-%m\" )";
		}

		@Override
		public String toString() {
			return "按天分组";
		}
	},
	YEAR(){
		@Override
		public String groupSqlForMysql(String columnName) {
			return "DATE_FORMAT( "+columnName+", \"%Y\" )";
		}

		@Override
		public String toString() {
			return "按天分组";
		}
	},
	ALL(){
		@Override
		public String groupSqlForMysql(String columnName) {
			return "";
		}

		@Override
		public String toString() {
			return "全部";
		}
	};
	
	public abstract String groupSqlForMysql(String columnName);

}
