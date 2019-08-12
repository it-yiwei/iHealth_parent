package com.yiwei.service;

import com.yiwei.pojo.PageResult;
import com.yiwei.pojo.Permission;
import com.yiwei.pojo.QueryPageBean;

import java.util.List;

/**
 * @author dengwangpeng
 * @dete 2019/8/11 - 11:15
 */
public interface PermissionService {


    PageResult findPage(QueryPageBean queryPageBean);

    void add(Permission permission);

    void deleteById(Integer permissionId);

    Permission findById(Integer permissionId);

    void edit(Permission permission);

    List<Permission> findAll();
}
