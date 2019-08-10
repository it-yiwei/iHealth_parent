package com.yiwei.dao;

import com.github.pagehelper.Page;
import com.yiwei.pojo.CheckGroup;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.HashMap;
import java.util.List;

public interface CheckGroupDao {

    //增加新检查组
    void addCheckGroup(CheckGroup checkGroup);

    //往中间表添加关联
    void setCheckGroupAndCheckItem(HashMap<String, Integer> map);

    //按条件查询所有检查组
    Page<CheckGroup> selectByCondition(String queryString);

    //根据id查询检查组
    @Select("SELECT * FROM t_checkgroup WHERE id = #{id}")
    CheckGroup findById(Integer checkGroupId);

    //根据检查组Id查询检查项Ids
    @Select("SELECT checkitem_id FROM t_checkgroup_checkitem WHERE checkgroup_id = #{checkGroupId}")
    List<Integer> findCheckItemIdsByCheckGroupId(Integer checkGroupId);

    //修改检查组
    void edit(CheckGroup checkGroup);

    //根据检查组Id删除中间表
    @Delete("DELETE FROM t_checkgroup_checkitem WHERE checkgroup_id = #{checkGroupId}")
    void deleteByCheckGroupId(Integer checkGroupId);

    //根据检查组Id删除检查组
    @Delete("DELETE FROM t_checkgroup WHERE id = #{checkGroupId}")
    void deleteCheckGroupById(Integer checkGroupId);

    //查询参套中引用某检查组的次数
    @Select("SELECT count(*) from t_setmeal_checkgroup WHERE checkgroup_id = #{checkGroupId}")
    Integer countById(Integer checkGroupId);

    @Select("select * from t_checkgroup")
    List<CheckGroup> findAll();

    //根据套餐id查询检查组
    List<CheckGroup> findCheckGroupById(Integer id);

}
