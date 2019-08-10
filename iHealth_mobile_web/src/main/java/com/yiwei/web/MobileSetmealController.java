package com.yiwei.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yiwei.constant.MessageConstant;
import com.yiwei.pojo.PageResult;
import com.yiwei.pojo.Result;
import com.yiwei.pojo.Setmeal;
import com.yiwei.service.SetmealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mobileSetmeal")
public class MobileSetmealController {

    @Reference
    private SetmealService setmealService;

    //获取所有的套餐信息
    @RequestMapping("/getSetmeal")
    public Result getSetmeal(){

        //业务处理，返回查询结果
        try {
             List<Setmeal> setmeals = setmealService.getSetmeal();

            //查询成功
            return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS,setmeals);

        } catch (Exception e) {
            e.printStackTrace();
            //查询失败
            return new Result(false,MessageConstant.QUERY_SETMEALLIST_FAIL);
        }
    }


    //获取所有的套餐信息
    @RequestMapping("/findById")
    public Result findById(Integer id){

        //业务处理，返回查询结果
        try {
            Setmeal setmeal = setmealService.findById(id);

            //查询成功
            return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS,setmeal);

        } catch (Exception e) {
            e.printStackTrace();
            //查询失败
            return new Result(false,MessageConstant.QUERY_SETMEALLIST_FAIL);
        }
    }

}
