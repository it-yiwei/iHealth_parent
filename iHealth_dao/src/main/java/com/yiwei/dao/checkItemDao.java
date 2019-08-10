package com.yiwei.dao;

import com.github.pagehelper.Page;
import com.yiwei.pojo.CheckItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface checkItemDao {

    //添加检查项目
    @Insert("insert into t_checkitem(code,name,sex,age,price,type,remark,attention) values (#{code},#{name},#{sex},#{age},#{price},#{type},#{remark},#{attention})")
    void addCheckItem(CheckItem checkItem);

    //分页查询
    Page<CheckItem> selectByCondition(String queryString);

    //查询此项目被项目组依赖总次数
    @Select("SELECT COUNT(*) FROM t_checkgroup_checkitem where checkitem_id=#{id}")
    int countCheckItemById(Integer id);

    //根据id删除检查项
    @Delete("DELETE FROM t_checkitem WHERE id=#{id}")
    void deleteById(Integer id);

    //根据id查询检查项
    @Select("select * from t_checkitem where id=#{id}")
    CheckItem findById(int id);

    //根据id修改检查项
    void update(CheckItem checkItem);

    //查询所有的检查项
    @Select("select * from t_checkitem ")
    List<CheckItem> findAll();

    //根据检查组查询检查项
    List<CheckItem> findCheckItemById(Integer id);
}
