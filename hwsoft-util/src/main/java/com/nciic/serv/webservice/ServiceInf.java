package com.nciic.serv.webservice;

public interface ServiceInf {
	
	// 核查方法
	public String nciicCheck(String inLicense, String inConditions);
	// 取得条件文件模板
	public String nciicGetCondition(String inLicense) throws Exception;
	
}
