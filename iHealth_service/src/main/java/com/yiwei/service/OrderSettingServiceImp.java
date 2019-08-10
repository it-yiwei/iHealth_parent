package com.yiwei.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.yiwei.dao.OrderSettingDao;
import com.yiwei.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static java.lang.Integer.parseInt;

@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImp implements  OrderSettingService{

    //注入dao
    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public void add(ArrayList<OrderSetting> orderSettings) {
        //获取orderSetting
        for (OrderSetting orderSetting : orderSettings) {
            //判断当前日期是否已设置
            int count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
            //若已设置，则重置信息
            if (count>0) {
                orderSettingDao.updateNumberByOrderDate(orderSetting);
            }else {
                //若无则新存入
                orderSettingDao.addOrderSetting(orderSetting);
            }

        }
    }

    @Override
    public List<Map> getOrderSettingByMonth(String currentMon) {
        //查询当前月份内的预约信息
        String startDate = currentMon+"-1";
        String endDate = currentMon+"-31";

        HashMap<String, String> params = new HashMap<>();
        params.put("startDate",startDate);
        params.put("endDate",endDate);

        //查询
        List<OrderSetting> orderSettings = orderSettingDao.getOrderSettingByMonth(params);

        List<Map> data = new ArrayList<>();
        for (OrderSetting orderSetting : orderSettings) {
            Map orderSettingMap = new HashMap();
            orderSettingMap.put("date", orderSetting.getOrderDate().getDate());//获得日期（几号）
            orderSettingMap.put("number", orderSetting.getNumber());//可预约人数
            orderSettingMap.put("reservations", orderSetting.getReservations());//已预约人数
            data.add(orderSettingMap);
        }
        return data;
    }

    //更改预约设置
    @Override
    public void editOrderSetting(String orderDate, Integer number) {
        OrderSetting orderSetting = new OrderSetting();
        orderSetting.setNumber(number);
        String replace = orderDate.replace("-", "/");
        Date date = new Date(replace);
        orderSetting.setOrderDate(date);

        //判断当前日期是否有预约设置
        //若有则更改信息
        //没有则新增一条信息
        //判断当前日期是否已设置
        int count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
        //若已设置，则重置信息
        if (count>0) {
            orderSettingDao.updateNumberByOrderDate(orderSetting);
        }else {
            //若无则新存入
            orderSettingDao.addOrderSetting(orderSetting);
        }
    }
}
