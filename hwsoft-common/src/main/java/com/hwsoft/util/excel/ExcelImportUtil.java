package com.hwsoft.util.excel;

import com.google.common.base.Preconditions;
import com.hwsoft.model.product.Product;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * excel import util
 */
public class ExcelImportUtil {


  public static final String EXCEL_SUFFIX = ".xlsx";
  private static Log logger = LogFactory.getLog(ExcelImportUtil.class);

  public static List<Product> importProduct(InputStream inputStream) throws Exception{

    List<Product> importProducts = new ArrayList<Product>();

      Workbook workbook = WorkbookFactory.create(inputStream);

      Sheet sheet = workbook.getSheetAt(0);

      Preconditions.checkState(sheet.getLastRowNum() >= 1, "债权表格的数据不能为空");

      // verify the sheet header
      Row headerRow = sheet.getRow(0);
      ProductExcelHelper.verifyHeader(headerRow);

      for (int i = 1; i <= sheet.getLastRowNum(); ++i) {
        Row row = sheet.getRow(i);
        if (row == null) {
          logger.warn("Excel表格含有空行");
          continue;
        }
        logger.debug("Row=" + (i + 1));
        if (row.getLastCellNum() < ProductExcelHelper.productHeaders.length) {
          logger.warn("列数不对等的Excel行." + "Row=" + (i + 1));
          continue;
        }
        Product product = ProductExcelHelper.obtainProductInfoFromSheetRow(row);
        System.out.println("导入产品：" + product);
        checkProductParameter(product);
        importProducts.add(product);
      }
    return importProducts;
  }
  
  private static void checkProductParameter(Product product){
    Preconditions.checkArgument(product.getMaxAmount() <= product.getTotalCopies(),"最大可购份额必须小于总份额");
    Preconditions.checkArgument(product.getMinAmount() >=1,"最小可够份额必须大于等于1");
  }
}