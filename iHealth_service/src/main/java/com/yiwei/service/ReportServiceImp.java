package com.yiwei.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.yiwei.dao.MemberDao;
import com.yiwei.dao.OrderDao;
import com.yiwei.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = ReportService.class)
public class ReportServiceImp implements ReportService {

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;

    @Override
    public Map getBusinessReportData() throws Exception {
         /*
        * 返回结果数据格式
        * {
              reportDate:null,
              todayNewMember :0,
              totalMember :0,
              thisWeekNewMember :0,
              thisMonthNewMember :0,
              todayOrderNumber :0,
              todayVisitsNumber :0,
              thisWeekOrderNumber :0,
              thisWeekVisitsNumber :0,
              thisMonthOrderNumber :0,
              thisMonthVisitsNumber :0,
              hotSetmeal :[
                  {name:'阳光爸妈升级肿瘤12项筛查（男女单人）体检套餐',setmeal_count:200,proportion:0.222},
                  {name:'阳光爸妈升级肿瘤12项筛查体检套餐',setmeal_count:200,proportion:0.222}
              ]
          }
        */
        HashMap<String, Object> resultMap = new HashMap<>();
        //reportDate
        Date today = DateUtils.getToday();
        String reportDate = new SimpleDateFormat("yyyy-MM-dd").format(today);
        resultMap.put("reportDate",reportDate);

        //todayNewMember
        Integer todayNewMember = memberDao.findMemberCountByDate(reportDate);
        resultMap.put("todayNewMember",todayNewMember);

        //totalMember
        Integer totalMember = memberDao.findMemberCountBeforeDate(reportDate);
        resultMap.put("totalMember",totalMember);

        //thisWeekNewMember
        String ThisWeek = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
        Integer thisWeekNewMember = memberDao.findMemberCountAfterDate(ThisWeek);
        resultMap.put("thisWeekNewMember",thisWeekNewMember);

        //thisMonthNewMember
        String ThisMonth = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
        Integer thisMonthNewMember = memberDao.findMemberCountAfterDate(ThisMonth);
        resultMap.put("thisMonthNewMember",thisMonthNewMember);

        //todayOrderNumber
        Integer todayOrderNumber = orderDao.findOrderCountByDate(reportDate);
        resultMap.put("todayOrderNumber",todayOrderNumber);

        //todayVisitsNumber
        Integer todayVisitsNumber = orderDao.findVisitsCountByDate(reportDate);
        resultMap.put("todayVisitsNumber",todayVisitsNumber);

        //thisWeekVisitsNumber
        Integer thisWeekVisitsNumber = orderDao.findVisitsCountAfterDate(ThisWeek);
        resultMap.put("thisWeekVisitsNumber",thisWeekVisitsNumber);

        //thisMonthVisitsNumber
        Integer thisMonthVisitsNumber = orderDao.findVisitsCountAfterDate(ThisMonth);
        resultMap.put("thisMonthVisitsNumber",thisMonthVisitsNumber);

        //thisWeekOrderNumber
        Integer thisWeekOrderNumber = orderDao.findOrderCountAfterDate(ThisWeek);
        resultMap.put("thisWeekOrderNumber",thisWeekOrderNumber);

        //thisMonthOrderNumber
        Integer thisMonthOrderNumber = orderDao.findOrderCountAfterDate(ThisMonth);
        resultMap.put("thisMonthOrderNumber",thisMonthOrderNumber);

        //hotSetmeal [
        //                  {name:'阳光爸妈升级肿瘤12项筛查（男女单人）体检套餐',setmeal_count:200,proportion:0.222},
        //                  {name:'阳光爸妈升级肿瘤12项筛查体检套餐',setmeal_count:200,proportion:0.222}
        //              ]
        List<Map> hotSetmeal = orderDao.findHotSetmeal();
        resultMap.put("hotSetmeal",hotSetmeal);

        return resultMap;
    }
}
