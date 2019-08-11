package com.yiwei.dao;

import com.github.pagehelper.Page;
import com.yiwei.pojo.Permission;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

public interface PermissionDao {

    //根据roleId查询permission
    @Select("select p.* from t_permission p,t_role_permission rp where p.id = rp.permission_id and rp.role_id = #{roleId}")
    Set<Permission> findPermissionByRoleId(Integer roleId);


    Page<Permission> selectByCondition(String queryString);

    void add(Permission permission);

    void deleteById(Integer permissionId);

    Permission findById(Integer permissionId);

    void edit(Permission permission);

    List<Permission> findAll();
}
