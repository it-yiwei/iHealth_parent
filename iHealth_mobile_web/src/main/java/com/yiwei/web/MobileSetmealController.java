package com.yiwei.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yiwei.constant.MessageConstant;
import com.yiwei.constant.RedisConstant;
import com.yiwei.pojo.PageResult;
import com.yiwei.pojo.Result;
import com.yiwei.pojo.Setmeal;
import com.yiwei.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/mobileSetmeal")
public class MobileSetmealController {

    @Reference
    private SetmealService setmealService;

    @Autowired
    private JedisPool jedisPool;

    //获取所有的套餐信息
    @RequestMapping("/getSetmeal")
    public Result getSetmeal(){

        //业务处理，返回查询结果
        try {
            Jedis jedis = jedisPool.getResource();
            //先判断redis中是否有缓存
            String data = jedis.get(RedisConstant.ALLSETMEALS);
            if (data==null){
                //从数据库查询，再存入redis
                List<Setmeal> setmeals = setmealService.getSetmeal();

                data = JSON.toJSONString(setmeals);
                jedis.set(RedisConstant.ALLSETMEALS,data);
            }
            //json转list
            List<Setmeal> setmeals = JSON.parseArray(data, Setmeal.class);

            //查询成功
            return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS,setmeals);

        } catch (Exception e) {
            e.printStackTrace();
            //查询失败
            return new Result(false,MessageConstant.QUERY_SETMEALLIST_FAIL);
        }
    }


    //获取套餐详细信息
    @RequestMapping("/findById")
    public Result findById(Integer id){

        //业务处理，返回查询结果
        try {
            Jedis jedis = jedisPool.getResource();
            //先判断redis中是否有缓存
            String data = jedis.hget(RedisConstant.SETMEALDETAIL,RedisConstant.SETMEALDETAIL+"_"+id);
            if (data==null) {
                //先去数据库查询，再存入redis
                Setmeal setmealDetail = setmealService.findById(id);

                //存入redia
                data = JSON.toJSONString(setmealDetail);
                jedis.hset(RedisConstant.SETMEALDETAIL,RedisConstant.SETMEALDETAIL+"_"+id,data);
            }

            //json转list
            Setmeal setmeal = JSON.parseObject(data, Setmeal.class);

            //查询成功
            return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS,setmeal);

        } catch (Exception e) {
            e.printStackTrace();
            //查询失败
            return new Result(false,MessageConstant.QUERY_SETMEALLIST_FAIL);
        }
    }

}
