package com.yiwei.service;

import com.yiwei.pojo.PageResult;
import com.yiwei.pojo.QueryPageBean;
import com.yiwei.pojo.Setmeal;

import java.util.List;

public interface SetmealService {

    //新增检查套餐
    void add(Setmeal setmeal, List<Integer> checkgroupIds);

    //分页查询所有套餐
    PageResult findPage(QueryPageBean queryPageBean);

    //删除套餐
    void deleteById(Integer setmealId);

    //查询所有套餐
    List<Setmeal> getSetmeal();

    //根据Id查询套餐
    Setmeal findById(Integer id);
}
