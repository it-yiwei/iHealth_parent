package com.yiwei.service;

import com.yiwei.pojo.Role;

import java.util.List;

public interface RoleService {

    //查询所有角色
    List<Role> findAll();

    //根据userId查询roleIds
    List<Integer> findRoleIdsByUserId(Integer userId);
}
