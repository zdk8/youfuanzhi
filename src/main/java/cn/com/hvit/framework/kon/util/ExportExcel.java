package cn.com.hvit.framework.kon.util;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by ling on 2016/7/28.
 */
public class ExportExcel {



    /**
     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
     *
     * @param title
     *            表格标题名
     * @param headers
     *            表格属性列名数组

     * @param out
     *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern
     *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
     */
    @SuppressWarnings("unchecked")
    public void exportExcel(String title, String[] headers,
                            List list, OutputStream out, String pattern) {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth( 15);


        //遍历集合数据，产生数据行
        Iterator it = list.iterator();
        int index = 0;
        while (it.hasNext()) {

            HSSFRow row = sheet.createRow(index);
            Map myMap = (Map) it.next();
            Iterator myIterator = myMap.keySet().iterator();
            int columnCount=0;
            while (myIterator.hasNext()) {
                String keyName = (String) myIterator.next();
                HSSFCell cell=row.createCell(columnCount++);
                Object obj = myMap.get(keyName);
                if (obj!=null) {
                    cell.setCellValue(obj+"");
                }
            }
            index++;
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}

