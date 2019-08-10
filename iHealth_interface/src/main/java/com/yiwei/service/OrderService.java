package com.yiwei.service;

import com.yiwei.pojo.Result;

import java.util.List;
import java.util.Map;

public interface OrderService {

    //保存预约信息
    Result submitOrder(Map<String, String> map) throws Exception;

    //根据id查询预约信息
    Map findById(Integer id);

    //获取热门套餐的预约人数
    List<Map> findHostSetmealCount();
}
