package cn.com.hvit.framework.kon.util;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wp on 16-8-19.
 */
public class ReadExcel {
    /**
 * 读取Excel测试，兼容 Excel 2003/2007/2010
 */
public List readExcel(String fileName)
{
    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    List resultList = new ArrayList();
    try {
        //同时支持Excel 2003、2007
        File excelFile = new File(fileName); //创建文件对象
        FileInputStream is = new FileInputStream(excelFile); //文件流
        Workbook workbook = WorkbookFactory.create(is); //这种方式 Excel 2003/2007/2010 都是可以处理的
        int sheetCount = workbook.getNumberOfSheets();  //Sheet的数量
        sheetCount=1;//只读第一个
        //遍历每个Sheet
        for (int s = 0; s < sheetCount; s++) {
            Sheet sheet = workbook.getSheetAt(s);
            int rowCount = sheet.getPhysicalNumberOfRows(); //获取总行数
            //遍历每一行
            for (int r = 0; r < rowCount; r++) {
                Row row = sheet.getRow(r);
                int cellCount = row.getPhysicalNumberOfCells(); //获取总列数
                //遍历每一列

                System.out.println(r+"获取总列数"+cellCount);
                List myRow=new ArrayList();
                for (int c = 0; c < cellCount; c++) {
                    Cell cell = row.getCell(c);
                    int cellType = cell.getCellType();
                    String cellValue = null;
                    switch(cellType) {
                        case Cell.CELL_TYPE_STRING: //文本
                            cellValue = cell.getStringCellValue();
                            break;
                        case Cell.CELL_TYPE_NUMERIC: //数字、日期
                            if(DateUtil.isCellDateFormatted(cell)) {
                                cellValue = fmt.format(cell.getDateCellValue()); //日期型
                            }
                            else {
                                cellValue = String.valueOf(cell.getNumericCellValue()); //数字
                            }
                            break;
                        case Cell.CELL_TYPE_BOOLEAN: //布尔型
                            cellValue = String.valueOf(cell.getBooleanCellValue());
                            break;
                        case Cell.CELL_TYPE_BLANK: //空白
                            cellValue = cell.getStringCellValue();
                            break;
                        case Cell.CELL_TYPE_ERROR: //错误
                            cellValue = "错误";
                            break;
                        case Cell.CELL_TYPE_FORMULA: //公式
                            cellValue = "错误";
                            break;
                        default:
                            cellValue = "错误";
                    }
                    myRow.add(cellValue);
                    System.out.print(cellValue + "    ");
                }
                resultList.add(myRow);
                //System.out.println();
            }
        }

    }
    catch (Exception e) {
        e.printStackTrace();
    }

    return resultList;
}
}
