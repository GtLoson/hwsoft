package com.hwsoft.util.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.hwsoft.model.order.common.BuyPlanOrder;

public class BuyPlanOderExportExcel extends ExportExcel {

	@Override
	public InputStream exportExcel(List<Object> datas) {
		
		if(null == datas || datas.size() == 0){
			return null;
		}
		
		List<List<String>> excelDatas = new ArrayList<List<String>>();
		//标题
		List<String> titles = new ArrayList<String>();	
		titles.add("订单编号");
		excelDatas.add(titles);
		for(Object data : datas){
			BuyPlanOrder buyPlanOrder = (BuyPlanOrder) data;
			List<String> excelData = new ArrayList<String>();	
			
			excelData.add(buyPlanOrder.getOrderFormId());
			//TODO 其他相关字段
			excelDatas.add(excelData);
		}
		List<String> tongji1 = new ArrayList<String>();	
		tongji1.add("申购金额");
		tongji1.add("5000");
		excelDatas.add(tongji1);
		List<String> tongji2 = new ArrayList<String>();	
		tongji2.add("成功金额");
		tongji2.add("4000");
		excelDatas.add(tongji2);
		try {
			return exportExcel(excelDatas, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
