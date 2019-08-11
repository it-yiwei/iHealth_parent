package com.yiwei.web;


import com.alibaba.dubbo.config.annotation.Reference;
import com.yiwei.constant.MessageConstant;
import com.yiwei.pojo.*;
import com.yiwei.service.RoleService;
import com.yiwei.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Reference
    private RoleService roleService;

    @RequestMapping("/findAll")
    public Result findAll(){
        //业务处理，返回查询结果
        try {
            List<Role> roles = roleService.findAll();

            //查询成功
            return new Result(true, MessageConstant.GET_ROLE_LIST_SUCCESS,roles);

        } catch (Exception e) {
            e.printStackTrace();
            //查询失败
            return new Result(false,MessageConstant.GET_ROLE_LIST_FALL);
        }
    }

    @RequestMapping("/findRoleIdsByUserId")
    public Result findRoleIdsByUserId(Integer userId){
        //业务处理，返回查询结果
        try {
            List<Integer> roleIds = roleService.findRoleIdsByUserId(userId);

            //查询成功
            return new Result(true,MessageConstant.QUERY_CHECKITEMIds_SUCCESS,roleIds);

        } catch (Exception e) {
            e.printStackTrace();
            //查询失败
            return new Result(false,MessageConstant.QUERY_CHECKITEMIds_FAIL);
        }
    }

    //角色分页查询
    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        try {
            PageResult pageResult = roleService.findPage(queryPageBean);
            return new Result(true,MessageConstant.GET_ROLE_LIST_SUCCESS,pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ROLE_LIST_FALL);
        }
    }

    //添加角色
    @RequestMapping("/add")
    public Result add(@RequestBody Role role ,@RequestParam List<Integer> permissionIds){
        try {
            roleService.add(role,permissionIds);
            return new Result(true,"新增角色成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"新增角色失败");
        }
    }

    //删除角色
    @RequestMapping("/deleteById")
    public Result deleteById(Integer roleId){
        try {
            roleService.deleteById(roleId);
            return new Result(true,MessageConstant.DELETE_ROLE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_ROLE_FALL);
        }
    }



}
