package com.yiwei.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.sun.org.apache.regexp.internal.RE;
import com.yiwei.constant.MessageConstant;
import com.yiwei.constant.RedisMessageConstant;
import com.yiwei.pojo.Order;
import com.yiwei.pojo.Result;
import com.yiwei.service.OrderService;
import com.yiwei.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    //注入jedis
    @Autowired
    private JedisPool jedisPool;

    //注入service
    @Reference
    private OrderService orderService;

    @RequestMapping("/submit")
    public Result submit(@RequestBody Map<String,String> map){
        //验证短信验证码是否正确
        String validateCode = map.get("validateCode");
        String code = jedisPool.getResource().get(map.get("telephone") + "_" +RedisMessageConstant.SENDTYPE_ORDER);

        if (code != null && validateCode.equals(code)) {
            //验证成功
            map.put("orderType",Order.ORDERTYPE_WEIXIN);
            //业务处理，保存预约信息
            Result result = new Result();
            try {
                result = orderService.submitOrder(map);

            } catch (Exception e) {
                //预约失败，服务器原因导致失败
                e.printStackTrace();
                return new Result(false,MessageConstant.ORDER_FALL);
            }

            //预约成功，发送预约成功短信
            /*if (result.isFlag()) {
                String orderDate = map.get("orderDate");
                try {
                    SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE,map.get("telephone"),orderDate);
                } catch (ClientException e) {
                    e.printStackTrace();
                }
            }*/
            //返回预约成功信息
            return result;
        }
        //验证失败
        return new Result(false, MessageConstant.VALIDATECODE_ERROR);

    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            Map orderInfo = orderService.findById(id);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,orderInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }
}
