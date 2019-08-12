package com.yiwei.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yiwei.constant.MessageConstant;
import com.yiwei.pojo.Member;
import com.yiwei.pojo.Result;
import com.yiwei.service.MemberService;
import com.yiwei.service.OrderService;
import com.yiwei.service.ReportService;
import com.yiwei.utils.DateUtils;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private MemberService memberService;

    @Reference
    private OrderService orderService;

    @Reference
    private ReportService reportService;

    @RequestMapping("/getMemberReport")
    public Result getMemberReport(){
        //获取当前月份的前12个月和每月会员数量，存入map
        Calendar calendar = Calendar.getInstance();

        //前12个月的集合
        calendar.add(calendar.MONTH,-12);
        ArrayList<String> months = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            String month = new SimpleDateFormat("yyyy-MM").format(calendar.getTime());
            months.add(month);
            calendar.add(calendar.MONTH,1);
        }

        //前12个月会员数量统计
        ArrayList<Integer> memberCount = new ArrayList<>();
        for (String month : months) {
            String date = month + "-31";
            //查询当月之前会员总数量
            try {
                Integer members = memberService.countMember(date);
                memberCount.add(members);
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
            }
        }

        HashMap<String, List> map = new HashMap<>();
        map.put("months",months);
        map.put("memberCount",memberCount);

        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);

    }

    //获取每个套餐的预约人数
    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport(){

        try {
            //返回结果类型list<map<string 套餐名,int 预约人数>>
            List<Map> resultList = orderService.findHostSetmealCount();
            List<String> setmealNameList = new ArrayList<>();
            //获取套餐名
            for (Map map : resultList) {
                String setmealName = (String) map.get("name");
                setmealNameList.add(setmealName);
            }

            HashMap<String, List> resultMap = new HashMap<>();
            resultMap.put("setmealNames",setmealNameList);
            resultMap.put("setmealCount",resultList);

            return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }

    }

    //获取男女会员比例
    @RequestMapping("/getSexMemberReport")
    public Result getSexMemberReport(){
        try {
            //获取男女会员数量
            List<Map> resultList = memberService.countMemberBySex();

            ArrayList<String> sexList = new ArrayList<>();
            for (Map map : resultList) {
                String sex = (String) map.get("name");
                sexList.add(sex);
            }
            HashMap<String, List> resultMap = new HashMap<>();
            resultMap.put("sexList",sexList);
            resultMap.put("sexMemberCount",resultList);

            return new Result(true,MessageConstant.GET_MEMBER_SEX_LIST_SUCCESS,resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.GET_MEMBER_SEX_LIST_FALL);
        }
    }

    //获取运营数据
    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData(){

        try {
            Map resultMap = reportService.getBusinessReportData();
            return new Result(true,MessageConstant.GET_BUSINESS_REPORT_SUCCESS,resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }


    //导出数据
    /*@RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取导出所需的数据
        Map reportData = reportService.getBusinessReportData();

        //取出返回结果数据，准备将报表数据写入到Excel文件中
        String reportDate = (String) reportData.get("reportDate");
        Integer todayNewMember = (Integer) reportData.get("todayNewMember");
        Integer totalMember = (Integer) reportData.get("totalMember");
        Integer thisWeekNewMember = (Integer) reportData.get("thisWeekNewMember");
        Integer thisMonthNewMember = (Integer) reportData.get("thisMonthNewMember");
        Integer todayOrderNumber = (Integer) reportData.get("todayOrderNumber");
        Integer thisWeekOrderNumber = (Integer) reportData.get("thisWeekOrderNumber");
        Integer thisMonthOrderNumber = (Integer) reportData.get("thisMonthOrderNumber");
        Integer todayVisitsNumber = (Integer) reportData.get("todayVisitsNumber");
        Integer thisWeekVisitsNumber = (Integer) reportData.get("thisWeekVisitsNumber");
        Integer thisMonthVisitsNumber = (Integer) reportData.get("thisMonthVisitsNumber");
        List<Map> hotSetmeal = (List<Map>) reportData.get("hotSetmeal");

        //获取文件对象
        String filePath = request.getSession().getServletContext().getRealPath("template") + File.separator + "report_template.xlsx";
        FileInputStream is = new FileInputStream(new File(filePath));
        XSSFWorkbook workbook = new XSSFWorkbook(is);
        //获取sheet
        XSSFSheet sheet = workbook.getSheetAt(0);

        //往文件里面写数据
        XSSFRow row1 = sheet.getRow(2);
        XSSFCell cell = row1.getCell(5);
        cell.setCellValue(reportDate);

        XSSFRow row2 = sheet.getRow(4);
        row2.getCell(5).setCellValue(todayNewMember);
        row2.getCell(7).setCellValue(totalMember);

        int rowNumber = 12;
        for (Map map : hotSetmeal) {
            //循环写数据
            String name = (String) map.get("name");
            Long setmeal_count = (Long) map.get("setmeal_count");
            BigDecimal proportion = (BigDecimal) map.get("proportion");
            String remark = (String) map.get("remark");

            XSSFRow row = sheet.getRow(rowNumber);
            row.getCell(4).setCellValue(name);
            row.getCell(5).setCellValue(setmeal_count);
            row.getCell(6).setCellValue(proportion.doubleValue());
            row.getCell(7).setCellValue(remark);

            rowNumber++;
        }

        //导出到excel文件
        ServletOutputStream os = response.getOutputStream();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("content-Disposition", "attachment;filename=report.xlsx");
        workbook.write(os);

        return null;
    }*/


    //导出数据
    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取导出所需的数据
        Map reportData = reportService.getBusinessReportData();

        //获取文件对象
        String filePath = request.getSession().getServletContext().getRealPath("template") + File.separator + "report_template.xlsx";
        FileInputStream is = new FileInputStream(new File(filePath));
        XSSFWorkbook workbook = new XSSFWorkbook(is);

        //将excel对象传入 excel模块中需要的输入传入
        XLSTransformer xlsTransformer = new XLSTransformer();
        xlsTransformer.transformWorkbook(workbook,reportData);

        //导出到excel文件
        ServletOutputStream os = response.getOutputStream();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("content-Disposition", "attachment;filename=report.xlsx");
        workbook.write(os);


        return null;
    }

}
