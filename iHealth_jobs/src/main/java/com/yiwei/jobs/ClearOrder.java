package com.yiwei.jobs;
import com.yiwei.dao.OrderDao;
import com.yiwei.dao.OrderSettingDao;
import com.yiwei.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component("clearOrder")
public class ClearOrder{

    @Autowired
    private OrderSettingDao orderSettingDao;

    //清理order表的旧数据
    public void clearOldOrderSetting() throws Exception {
        //获取当前时间
        Date today = DateUtils.getToday();
        String now = DateUtils.parseDate2String(today);
        //清理当前日期之前的预约数据
        orderSettingDao.clearOldOrderSetting(now);
        System.out.println("定时器一");

    }
}
