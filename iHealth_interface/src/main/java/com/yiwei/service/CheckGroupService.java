package com.yiwei.service;

import com.yiwei.pojo.CheckGroup;
import com.yiwei.pojo.PageResult;
import com.yiwei.pojo.QueryPageBean;

import java.util.List;

public interface CheckGroupService {

    //增加检查组
    void add(CheckGroup checkGroup, List<Integer> checkitemIds);

    //分页查询检查组
    PageResult findPage(QueryPageBean queryPageBean);

    //根据Id查询检查组
    CheckGroup findById(Integer checkGroupId);

    //根据检查组Id查询检查项Ids
    List<Integer> findCheckItemIdsByCheckGroupId(Integer checkGroupId);

    //修改检查组
    void edit(CheckGroup checkGroup, List<Integer> checkitemIds);

    //删除检查组
    void deleteById(Integer checkGroupId);

    //查询所有检查组
    List<CheckGroup> findAll();
}
