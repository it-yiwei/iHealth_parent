package com.yiwei.dao;

import com.github.pagehelper.Page;
import com.yiwei.pojo.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RoleDao {

    //根据userId查询role
    @Select("select r.* from t_role r,t_user_role ur where r.id = ur.role_id and ur.user_id = #{userId}")
    Set<Role> findRoleByUserId(Integer userId);

    //查询所有角色
    @Select("select * from t_role")
    List<Role> findAll();

    @Select("select role_id from t_user_role where user_id = #{userId}")
    List<Integer> findRoleIdsByUserId(Integer userId);

    //角色分页查询
    Page<Role> selectByCondition(String queryString);
    //添加角色信息
    void addRole(Role role);
    //添加角色和权限的中间表
    void roleAndPermission(Map map);
    //删除角色和权限中间表约束
    void deleterolePermissionByRoleId(Integer roleId);

    void deleteById(Integer roleId);
    //回显角色信息
    Role findById(Integer roleId);
    //查询角色关联权限
    List<Integer> findPermissionIdsByRoleId(Integer roleId);
    //更新角色信息
    void edit(Role role);
}
