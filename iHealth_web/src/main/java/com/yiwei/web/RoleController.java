package com.yiwei.web;


import com.alibaba.dubbo.config.annotation.Reference;
import com.yiwei.constant.MessageConstant;
import com.yiwei.pojo.*;
import com.yiwei.service.RoleService;
import com.yiwei.service.UserService;
import org.springframework.web.bind.annotation.*;

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

    //弹出编辑框回显角色信息
    @RequestMapping("/findById")
    public Result findById(Integer roleId){
        try {
            Role role = roleService.findById(roleId);
            return new Result(true,"回显角色信息成功",role);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"回显角色信息失败");
        }
    }

    //查询角色关联的权限
    @RequestMapping("/findPermissionIdsByRoleId")
    public Result findPermissionIdsByRoleId(Integer roleId){
        try {
            List<Integer> permissionIds = roleService.findPermissionIdsByRoleId(roleId);
            return new Result(true,"查询角色关联权限成功",permissionIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"查询角色关联权限失败");
        }
    }

    //编辑角色提交
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    public Result edit(@RequestBody Role role,@RequestParam List<Integer> permissionIds){
        try {
            roleService.edit(role,permissionIds);
            return new Result(true,MessageConstant.EDIT_ROLE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_ROLE_FALL);
        }
    }




}
