package com.yiwei.service;

import com.yiwei.pojo.PageResult;
import com.yiwei.pojo.QueryPageBean;
import com.yiwei.pojo.User;

import java.util.List;

public interface UserService {

    //根据username获取user信息
    User findUserByUsername(String username);

    //分页查询用户
    PageResult findPage(QueryPageBean queryPageBean);

    //新增用户
    void add(User user, List<Integer> roles);

    //根据ID查询user
    User findById(Integer userId);

    //修改用户信息
    void edit(User user, List<Integer> roleIds);

    //删除用户信息
    void deleteById(Integer userId);



}
