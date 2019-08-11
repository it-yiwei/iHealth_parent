package com.yiwei.service;

import com.yiwei.pojo.PageResult;
import com.yiwei.pojo.QueryPageBean;
import com.yiwei.pojo.Role;

import java.util.List;

public interface RoleService {

    //查询所有角色
    List<Role> findAll();

    //根据userId查询roleIds
    List<Integer> findRoleIdsByUserId(Integer userId);
    //角色分页查询
    PageResult findPage(QueryPageBean queryPageBean);

    void add(Role role, List<Integer> permissionIds);
    //删除角0色
    void deleteById(Integer roleId);
}
