package com.yiwei.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yiwei.constant.MessageConstant;
import com.yiwei.pojo.*;
import com.yiwei.service.CheckGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkGroup")
public class checkGroupController {

    //注入service
    @Reference
    private CheckGroupService checkGroupService;

    //增加检查组
    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, @RequestParam List<Integer> checkitemIds){

        try {
            checkGroupService.add(checkGroup,checkitemIds);

            Result result = new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            Result result = new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
            return result;
        }
    }

    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        //业务处理，返回查询结果
        try {
            PageResult pageResult = checkGroupService.findPage(queryPageBean);

            //查询成功
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,pageResult);

        } catch (Exception e) {
            e.printStackTrace();
            //查询失败
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping("/findById")
    public Result findById(Integer checkGroupId){
        //业务处理，返回查询结果
        try {
            CheckGroup checkGroup = checkGroupService.findById(checkGroupId);

            //查询成功
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);

        } catch (Exception e) {
            e.printStackTrace();
            //查询失败
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(Integer checkGroupId){
        //业务处理，返回查询结果
        try {
            List<Integer> CheckItemIds = checkGroupService.findCheckItemIdsByCheckGroupId(checkGroupId);

            //查询成功
            return new Result(true,MessageConstant.QUERY_CHECKITEMIds_SUCCESS,CheckItemIds);

        } catch (Exception e) {
            e.printStackTrace();
            //查询失败
            return new Result(false,MessageConstant.QUERY_CHECKITEMIds_FAIL);
        }
    }

    //增加检查组
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup, @RequestParam List<Integer> checkitemIds){

        try {
            checkGroupService.edit(checkGroup,checkitemIds);

            Result result = new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            Result result = new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
            return result;
        }
    }

    //删除检查组
    @RequestMapping("/deleteById")
    public Result deleteById(Integer checkGroupId){

        try {
            checkGroupService.deleteById(checkGroupId);

            Result result = new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
            return result;

        } catch (RuntimeException e){
            return new Result(false, e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            Result result = new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL);
            return result;
        }
    }

    //查询所有检查组
    @RequestMapping("/findAll")
    public Result findAll(){
        //业务处理，返回查询结果
        try {
            List<CheckGroup> checkGroups = checkGroupService.findAll();

            //查询成功
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroups);

        } catch (Exception e) {
            e.printStackTrace();
            //查询失败
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }
}
