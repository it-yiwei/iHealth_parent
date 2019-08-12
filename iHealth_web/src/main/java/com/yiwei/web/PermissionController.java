package com.yiwei.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yiwei.constant.MessageConstant;
import com.yiwei.pojo.PageResult;
import com.yiwei.pojo.Permission;
import com.yiwei.pojo.QueryPageBean;
import com.yiwei.pojo.Result;
import com.yiwei.service.PermissionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.SplittableRandom;

/**
 * @author dengwangpeng
 * @dete 2019/8/11 - 11:08
 * 权限控制
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Reference
    private PermissionService permissionService;

    //分页查询
    @RequestMapping(value = "/findPage", method = RequestMethod.POST)
    public Result permission(@RequestBody QueryPageBean queryPageBean) {
        //业务处理，返回查询结果
        try {
            PageResult pageResult = permissionService.findPage(queryPageBean);

            //查询成功
            return new Result(true, "获取权限列表成功", pageResult);

        } catch (Exception e) {
            e.printStackTrace();
            //查询失败
            return new Result(false, "获取权限列表失败");
        }
    }

    //添加权限
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(@RequestBody Permission permission) {
        try {
            permissionService.add(permission);
            return new Result(true, MessageConstant.ADD_PERMISSION_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_PERMISSION_FALL);
        }
    }

    //根据id删除权限
    @RequestMapping("/deleteById")
    public Result deleteById(Integer permissionId){
        try {
            permissionService.deleteById(permissionId);
            return new Result(true,MessageConstant.DELETE_PERMISSION_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_PERMISSION_FALL);
        }
    }

    //弹出编辑回显权限信息
    @RequestMapping("/findById")
    public Result findById(Integer permissionId){
        try {
            Permission permission = permissionService.findById(permissionId);
            return new Result(true,"回显权限信息成功",permission);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"回显权限信息成功");
        }
    }

    //编辑权限
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    public Result edit(@RequestBody Permission permission){
        try {
            permissionService.edit(permission);
            return new Result(true,"编辑权限成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"编辑权限失败");
        }
    }

    //查询全部权限
    @RequestMapping("/findAll")
    public Result findAll(){
        try {
            List<Permission> lists = permissionService.findAll();
            return new Result(true,"查询全部权限成功",lists);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"查询全部权限失败");
        }
    }






}
