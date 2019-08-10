package com.yiwei.dao;

import com.yiwei.pojo.Permission;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

public interface PermissionDao {

    //根据roleId查询permission
    @Select("select p.* from t_permission p,t_role_permission rp where p.id = rp.permission_id and rp.role_id = #{roleId}")
    Set<Permission> findPermissionByRoleId(Integer roleId);
}
