package com.hwsoft.util.excel;

import com.google.common.base.Preconditions;
import com.hwsoft.common.product.ProductDateUnit;
import com.hwsoft.common.product.ProductIncomeType;
import com.hwsoft.common.product.ProductProfitProgressType;
import com.hwsoft.model.product.Product;
import com.hwsoft.util.date.DateTools;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 债权表格工具类
 */
public class ProductExcelHelper {

  /**
   * 产品excel表头信息
   */
  public static String[] productHeaders = {
      // 0
      "产品名称",
      // 1
      "产品描述",
      // 2
      "销售开始时间",
      // 3
      "销售结束时间",
      // 4
      "产品期限数字",
      // 5
      "产品期限单位",
      // 6
      "最低投资份数",
      // 7
      "最高投资份数",
      // 8
      "总计份数",
      // 9
      "总金额",
      // 10
      "产品收益方式",
      // 11
      "收益处理方式",
      // 12
      "收益率",
      // 13
      "平台销售时间",
      // 14
      "是否在手机端显示"

  };
  private static Log logger = LogFactory.getLog(ProductExcelHelper.class);
  private static String BOOLEAN_YES = "是";
  private static String BOOLEAN_NO = "否";

  /**
   * 校验excel表头信息是否合法
   *
   * @param headerRow
   * @throws IllegalStateException
   */
  public static void verifyHeader(Row headerRow) throws IllegalStateException {
    for (int i = 0; i < productHeaders.length; ++i) {
      Cell cell = headerRow.getCell(i);
      Preconditions.checkState(
          cell.getCellType() == Cell.CELL_TYPE_STRING, productHeaders[i] + "的值不合法");
      Preconditions.checkState(productHeaders[i].equals(cell.getStringCellValue()), "HEADER[" + i + "]的值{" + productHeaders[i] +
          "}不等于{" + cell.getStringCellValue() + "}");
    }
  }

  /**
   * 从表格的一行中获取产品信息
   *
   * @param productRow
   * @return
   */
  public static Product obtainProductInfoFromSheetRow(Row productRow) {
    
    Date now = new Date();
    
    Product product = new Product();
    product.setProductName(getStringAtIndex(productRow, 0));
    product.setProductDesc(getStringAtIndex(productRow, 1));
    product.setSaleStartTime(getDateTime(productRow, 2));
    product.setSaleEndTime(getDateTime(productRow, 3));
    product.setMaturityDuration(getIntAtIndex(productRow, 4));
    

    
    
    
    
    
    product.setMinAmount(getBigDecimalAtIndex(productRow, 6).doubleValue());
    product.setMaxAmount(getBigDecimalAtIndex(productRow, 7).doubleValue());
    product.setCreateTime(now);
    int copies = getIntAtIndex(productRow, 8);

    double totalAmount = getBigDecimalAtIndex(productRow, 9).doubleValue();

    Double baseAmount = totalAmount / copies;

    // TODO 这里要校验baseAmount 为整数
    
    product.setRemainingAmount(Double.valueOf(copies + ""));
    product.setTotalCopies(copies);
    product.setTotalAmount(totalAmount);

    product.setBaseAmount(baseAmount);

    product.setMaturityUnit(ProductDateUnit.DAY);

    String incomeString = getStringAtIndex(productRow, 10);
    ProductIncomeType productIncomeType = ProductIncomeType.buildFromString(incomeString);

    Preconditions.checkArgument(null != productIncomeType,"收益方式错误："+incomeString);
    product.setProductIncomeType(productIncomeType);

    String unitString = getStringAtIndex(productRow,5);

    ProductDateUnit productDateUnit = ProductDateUnit.fromExcelUnit(unitString);
    
    switch (productIncomeType){
      case AVERAGE_CAPITAL_PLUS_INTEREST:
      case PWRIOD_REPAYS_CAPTITAL:
      case PERIOD_REPAYS_CAPTITAL:
      case DAILY_INTEREST:
      case DAILY_INTEREST_MONTH_REPAY:
      {
        Preconditions.checkArgument(productIncomeType.productDateUnit().equals(productDateUnit), "当前收益方式："+productIncomeType.toString()+" 的产品期限单位必须是： "
            +productIncomeType.productDateUnit().toString()+",当前导入为： "+ productDateUnit);
        break;
      }
    }

    product.setMaturityUnit(productDateUnit);
    String profitProcessType = getStringAtIndex(productRow, 11);
    ProductProfitProgressType productProfitProgressType = ProductProfitProgressType.buildFromString(profitProcessType);
    
    
    product.setProductProfitProgressType(productProfitProgressType);

    Double interest = getBigDecimalAtIndex(productRow, 12).doubleValue();
    product.setInterest(interest);
    product.setMinInterest(interest);
    product.setMaxInterest(interest);


    product.setPlatSaleTime(getDateTime(productRow, 13));
    product.setShow(getBooleanAtIndex(productRow, 14));

    return product;
  }

  public static String getStringAtIndex(Row row, int index) {
    Cell cell = row.getCell(index);
    Preconditions.checkState(cell != null, productHeaders[index] + "不能为空");
    int cellType = cell.getCellType();
    return
        cellType == Cell.CELL_TYPE_STRING ? cell.getStringCellValue() :
            cellType == Cell.CELL_TYPE_NUMERIC ? String.format("%.0f", cell.getNumericCellValue()) :
                "";
  }

  public static BigDecimal getBigDecimalAtIndex(Row row, int index) {
    Cell cell = row.getCell(index);
    Preconditions.checkState(cell != null, productHeaders[index] + "不能为空");
    int cellType = cell.getCellType();
    Double value =
        cellType == Cell.CELL_TYPE_STRING ? Double.parseDouble(cell.getStringCellValue()) :
            cellType == Cell.CELL_TYPE_NUMERIC ? cell.getNumericCellValue() :
                0.0;

    return new BigDecimal(value);
  }

  private static int getIntAtIndex(Row row, int index) {
    Cell cell = row.getCell(index);
    Preconditions.checkState(cell != null, productHeaders[index] + "不能为空");
    int cellType = cell.getCellType();
    return
        cellType == Cell.CELL_TYPE_STRING ? Integer.parseInt(cell.getStringCellValue()) :
            cellType == Cell.CELL_TYPE_NUMERIC ? (int) cell.getNumericCellValue() :
                0;
  }

  public static Long getLongAtIndex(Row row, int index) {
    Cell cell = row.getCell(index);
    Preconditions.checkState(cell != null, productHeaders[index] + "不能为空");
    int cellType = cell.getCellType();
    return
        cellType == Cell.CELL_TYPE_STRING ? Long.parseLong(cell.getStringCellValue()) :
            cellType == Cell.CELL_TYPE_NUMERIC ? (int) cell.getNumericCellValue() :
                0;
  }

  private static boolean getBooleanAtIndex(Row row, int index) {
    Cell cell = row.getCell(index);
    Preconditions.checkState(cell != null, productHeaders[index] + "不能为空");
    int cellType = cell.getCellType();
    return
        cellType == Cell.CELL_TYPE_STRING ? BOOLEAN_YES.equals(cell.getStringCellValue()) :
            cellType == Cell.CELL_TYPE_BOOLEAN ? cell.getBooleanCellValue() :
                false;
  }

  private static Date getDateTime(Row row, int index) {
    String dateTimeString = getStringAtIndex(row, index);
    logger.info("参数：" + dateTimeString);
    Date dateTime = null;
    try {
      dateTime = DateTools.stringToDateTime(dateTimeString);
    } catch (Exception e) {
      logger.error("格式化日期出错：" + dateTimeString, e);
    }
    Preconditions.checkState(dateTime != null, productHeaders[index] + "数据日期格式不正确");
    return dateTime;
  }
}