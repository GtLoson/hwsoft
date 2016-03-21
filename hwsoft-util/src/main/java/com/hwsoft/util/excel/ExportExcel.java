package com.hwsoft.util.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hwsoft.util.string.StringUtils;

/**
 * 导出excel文件
 * 
 * @author tzh
 */
public abstract class ExportExcel {
	
	/**
	 * 
	 * @param datas
	 * @return
	 */
	public abstract InputStream exportExcel(List<Object> datas);

	/**
	 * 生成excel
	 * @param datas
	 * @return
	 * @throws IOException 
	 */
	protected static InputStream exportExcel(List<List<String>> datas,String sheetName) throws IOException {
		
		if(null == datas || datas.size() == 0){
			return null;
		}
		
		OutputStream output = new ByteArrayOutputStream();
		XSSFWorkbook wb = new XSSFWorkbook();   
		if(StringUtils.isEmpty(sheetName)){
			sheetName = "Sheet1";
		}
		XSSFSheet sheet= wb.createSheet(sheetName);  
		int size = 0;
		for(List<String> data : datas){
			XSSFRow row = sheet.createRow(size);   
			if(null != data && data.size() != 0){
				int i = 0;
				for(String c : data){
					row.createCell(i).setCellValue(c);
					i++;
				}
			} 
			size ++;
		}
		//写文件   
		wb.write(output);
		ByteArrayOutputStream buffer = (ByteArrayOutputStream) output;
		InputStream inputStream = new ByteArrayInputStream(buffer.toByteArray());
		return inputStream;
	} 
	
	
	public static void main(String[] args) {
		try {
			
			List<List<String>> datas = new ArrayList<List<String>>();
			for(int i = 0 ; i < 20000 ; i++){
				List<String> data = new ArrayList<String>();
				for(int j = 0 ; j < 20 ; j++){
					data.add("test"+j);
				}
				datas.add(data);
			}
			
			InputStream ins = exportExcel(datas, null);
			OutputStream os = new FileOutputStream("/Users/shaodongying/Downloads/test.xlsx");
			   int bytesRead = 0;
			   byte[] buffer = new byte[8192];
			   while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
			    os.write(buffer, 0, bytesRead);
			   }
			   os.close();
			   ins.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}