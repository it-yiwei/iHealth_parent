package com.yiwei.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yiwei.constant.MessageConstant;
import com.yiwei.pojo.CheckGroup;
import com.yiwei.pojo.PageResult;
import com.yiwei.pojo.QueryPageBean;
import com.yiwei.pojo.Result;
import com.yiwei.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService userService;

    @RequestMapping("/findUsername")
    public Result findUsername(){
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new Result(true,MessageConstant.GET_USERNAME_SUCCESS,user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }

    //分页查询用户
    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        //业务处理，返回查询结果
        try {
            PageResult pageResult = userService.findPage(queryPageBean);

            //查询成功
            return new Result(true,MessageConstant.GET_USER_LIST_SUCCESS,pageResult);

        } catch (Exception e) {
            e.printStackTrace();
            //查询失败
            return new Result(false,MessageConstant.GET_USER_LIST_FALL);
        }
    }

    //增加用户
    @RequestMapping("/add")
    public Result add(@RequestBody com.yiwei.pojo.User user, @RequestParam List<Integer> roleIds){

        try {
            userService.add(user,roleIds);

            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
    }

    //查询用户
    @RequestMapping("/findById")
    public Result findById(Integer userId){
        //业务处理，返回查询结果
        try {
            com.yiwei.pojo.User user = userService.findById(userId);

            //查询成功
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,user);

        } catch (Exception e) {
            e.printStackTrace();
            //查询失败
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    //修改用户信息
    @RequestMapping("/edit")
    public Result edit(@RequestBody com.yiwei.pojo.User user, @RequestParam List<Integer> roleIds){

        try {
            userService.edit(user,roleIds);

            Result result = new Result(true, MessageConstant.EIDT_USER_SUCCESS);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            Result result = new Result(false, MessageConstant.EIDT_USER_FALL);
            return result;
        }
    }

    //删除用户
    @RequestMapping("/deleteById")
    public Result deleteById(Integer userId){

        try {
            userService.deleteById(userId);

            Result result = new Result(true, MessageConstant.DELETE_USER_SUCCESS);
            return result;

        } catch (RuntimeException e){
            return new Result(false, e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            Result result = new Result(false, MessageConstant.DELETE_USER_FALL);
            return result;
        }
    }

}
