package com.yiwei;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class POITest {

   // @Test
    public void read() throws IOException {
        //获取表格
        XSSFWorkbook workbook = new XSSFWorkbook("C:\\Users\\Yi Wei\\Desktop\\考试成绩.xlsx");

        //获取sheet
        int sheets = workbook.getNumberOfSheets();
        for (int i = 0; i < sheets; i++) {
            XSSFSheet sheet = workbook.getSheetAt(i);

            int firstRowNum = sheet.getFirstRowNum();
            int lastRowNum = sheet.getLastRowNum();

            for (int j=firstRowNum;j <=lastRowNum;j++){
                //获取行
                XSSFRow row = sheet.getRow(j);

                //获取列
                for (Cell cell : row) {
                    System.out.println(cell);
                }
            }
        }workbook.close();

    }

   // @Test
    public void writ() throws IOException {

        //1.创建工作簿对象
        XSSFWorkbook workbook = new XSSFWorkbook();

        //2.创建工作表对象
        XSSFSheet sheet = workbook.createSheet("info");

        //3.创建行对象
        XSSFRow row1 = sheet.createRow(1);
        XSSFRow row2 = sheet.createRow(2);


        //4.创建列(格子)对象, 设置内容
        XSSFCell cell1 = row1.createCell(1);
        cell1.setCellValue("学号");
        XSSFCell cell2 = row1.createCell(2);
        cell2.setCellValue("姓名");
        XSSFCell cell3 = row1.createCell(3);
        cell3.setCellValue("成绩");

        XSSFCell cell21 = row2.createCell(1);
        cell21.setCellValue("01");
        XSSFCell cell22 = row2.createCell(2);
        cell22.setCellValue("许多多");
        XSSFCell cell23 = row2.createCell(3);
        cell23.setCellValue("99");


        //5.通过流写入到磁盘
        FileOutputStream outputStream = new FileOutputStream("F:\\student.xlsx");
        workbook.write(outputStream);

        outputStream.flush();
        outputStream.close();
        workbook.close();

    }
}
