package com.yiwei.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yiwei.constant.MessageConstant;
import com.yiwei.pojo.*;
import com.yiwei.service.OrderSettingService;
import com.yiwei.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/OrderSetting")
public class OrderSettingController {

    //注入service
    @Reference
    private OrderSettingService orderSettingService;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile")MultipartFile excelFile){
        try{
            //读取Excel文件数据
            List<String[]> list = POIUtils.readExcel(excelFile);
            //判断是否为空
            if (list != null) {
                //获取每一条字符串数组，存入orderSetting，把所有orderSetting存入一个集合
                //新建集合
                ArrayList<OrderSetting> orderSettings = new ArrayList<>();
                for (String[] strings : list) {
                    //新建orderSetting，并且设置数据
                    OrderSetting orderSetting = new OrderSetting();
                    orderSetting.setOrderDate(new Date(strings[0]));
                    orderSetting.setNumber(Integer.parseInt(strings[1]));
                    //存入集合
                    orderSettings.add(orderSetting);
                }
                //调用业务层保存
                orderSettingService.add(orderSettings);
            }
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);

        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("/editOrderSetting")
    public Result editOrderSetting(@RequestBody HashMap<String,String> map){
        //调用业务层，更改预约信息
        try {
            orderSettingService.editOrderSetting(map.get("orderDate"), Integer.valueOf(map.get("number")));
            //返回结果集
            return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String currentMon){
        //调用业务层，获取预约信息集合
        try {
            List<Map> data = orderSettingService.getOrderSettingByMonth(currentMon);
            //返回结果集
            return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS,data);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }


}
