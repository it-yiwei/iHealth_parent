package com.yiwei.service;

import com.yiwei.pojo.OrderSetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface OrderSettingService {

    //存储设置信息
    void add(ArrayList<OrderSetting> orderSettings);

    //获取预约信息
    List<Map> getOrderSettingByMonth(String currentMon);

    //更改预约设置
    void editOrderSetting(String orderDate, Integer number);
}
