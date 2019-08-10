package com.yiwei.dao;

import com.github.pagehelper.Page;
import com.yiwei.pojo.CheckItem;
import com.yiwei.pojo.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.HashMap;

public interface UserDao {

    //根据username查询user
    @Select("SELECT * FROM t_user WHERE username = #{username}")
    User findUserByUsername(String username);

    //分页查询用户
    Page<User> selectByCondition(String queryString);

    //新增用户并且返回主键
    void addUser(User user);

    //往关联表插记录
    void setUserIdAndRoleId(HashMap<String, Integer> map);

    //根据Id查询user
    @Select("select * from t_user where id = #{userId}")
    User findById(Integer userId);

    //修改用户信息
    void edit(User user);

    //删除旧的role关联表
    @Delete("DELETE FROM t_user_role WHERE user_id = #{userId}")
    void deleteRoleIdsByUserId(Integer userId);

    //根据id删除用户
    @Delete("delete from t_user where id = #{userId}")
    void deleteById(Integer userId);
}
