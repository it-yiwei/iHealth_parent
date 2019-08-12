package com.yiwei.jobs;

import com.yiwei.service.OrderService;
import com.yiwei.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ClearOrderSettingJob
{     @Autowired
     private OrderSettingService orderSettingService;
public void clearOrderSetting(){
    Date date = new Date();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String time = simpleDateFormat.format(date);
    orderSettingService.clearOrderSetting(time);
}}

