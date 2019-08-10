package com.yiwei.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yiwei.constant.MessageConstant;
import com.yiwei.pojo.CheckItem;
import com.yiwei.pojo.PageResult;
import com.yiwei.pojo.QueryPageBean;
import com.yiwei.pojo.Result;
import com.yiwei.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checkItem")
public class checkItemController {

    @Reference
    private CheckItemService checkItemService;

    @RequestMapping("/add")
    public Result addCheckItem(@RequestBody CheckItem checkItem){

        try {
            checkItemService.addCheckItem(checkItem);
            Result result = new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            Result result = new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
            return result;
        }
    }


    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        //业务处理，返回查询结果
        try {
            PageResult pageResult = checkItemService.findPage(queryPageBean);

            //查询成功
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,pageResult);

        } catch (Exception e) {
            e.printStackTrace();
            //查询失败
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }


    @RequestMapping("/delete")
    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")
    public Result delete(Integer id){
        //业务处理，无返回结果
        try {
            checkItemService.delete(id);
            //删除成功
            return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
        }catch (RuntimeException e){
            //不允许删除
            return new Result(false,e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            //删除失败
            return new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL);
        }
    }

    @RequestMapping("/findById")
    public Result findById(int id){
        //业务处理，返回查询结果
        try {
            CheckItem checkItem = checkItemService.findById(id);

            //查询成功
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);

        } catch (Exception e) {
            e.printStackTrace();
            //查询失败
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    @RequestMapping("/update")
    public Result update(@RequestBody CheckItem checkItem){
        //业务处理，返回处理结果
        try {
            checkItemService.update(checkItem);

            //修改成功
            return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
            //修改失败
            return new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);
        }
    }

    @RequestMapping("/findAll")
    public Result findAll(){
        //业务处理，返回查询结果
        try {
            List<CheckItem> checkItems = checkItemService.findAll();

            //查询成功
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItems);

        } catch (Exception e) {
            e.printStackTrace();
            //查询失败
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

}
