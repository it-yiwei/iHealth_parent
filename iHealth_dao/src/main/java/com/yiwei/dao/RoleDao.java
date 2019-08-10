package com.yiwei.dao;

import com.yiwei.pojo.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;
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
}
