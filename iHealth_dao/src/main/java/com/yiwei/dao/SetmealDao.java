package com.yiwei.dao;

import com.github.pagehelper.Page;
import com.yiwei.pojo.CheckGroup;
import com.yiwei.pojo.Setmeal;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

public interface SetmealDao {

    //新增套餐
    void addSetmeal(Setmeal setmeal);

    //往中间表插入id
    void setSetmealIdAndCheckgroupId(HashMap<String, Integer> map);

    //分页查询所有套餐
    Page<CheckGroup> selectByCondition(String queryString);

    //删除套餐
    @Delete("DELETE FROM t_setmeal WHERE id = #{setmealId}")
    void deleteById(Integer setmealId);

    //删除中间表
    @Delete("DELETE FROM t_setmeal_checkgroup WHERE setmeal_id = #{setmealId}")
    void deleteSetmealAndCheckGroup(Integer setmealId);

    //获取所有套餐
    @Select("select * from t_setmeal")
    List<Setmeal> getSetmeal();

    //根据id查询套餐
    Setmeal findById(Integer id);
}
