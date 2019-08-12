package com.yiwei.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yiwei.dao.PermissionDao;
import com.yiwei.dao.RoleDao;
import com.yiwei.dao.UserDao;
import com.yiwei.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Service(interfaceClass = UserService.class)
public class UserServiceImp implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private RoleDao roleDao;

    //根据username查询user
    @Override
    public User findUserByUsername(String username) {
        User user = userDao.findUserByUsername(username);

        //判断是否user存在
        if (user != null) {
            //根据userId查询user所关联的role
            Integer userId = user.getId();
            Set<Role> roles = roleDao.findRoleByUserId(userId);

            //判断user是否有role属性
            if (roles != null && roles.size()>0) {
                for (Role role : roles) {
                    //获取role关联的permission属性
                    Integer roleId = role.getId();
                    Set<Permission> permissions = permissionDao.findPermissionByRoleId(roleId);
                    //把permission存入role
                    role.setPermissions(permissions);
                }
                //把roles存入user
                user.setRoles(roles);
            }
            return user;
        }
        return null;
    }

    //分页查询用户
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {

        //分页插件的使用
        //第二种，Mapper接口方式的调用，推荐这种使用方式。
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //dao查询语句,返回page对象（分页插件对象）
        Page<User> UserPage = userDao.selectByCondition(queryPageBean.getQueryString());

        //封装返回结果
        PageResult pageResult = new PageResult();
        pageResult.setRows(UserPage.getResult());
        pageResult.setTotal(UserPage.getTotal());
        return pageResult;
    }

    @Override
    public void add(User user, List<Integer> roleIds) {
        //先往用户表新增记录，返回主键

        //密码加密
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = encoder.encode(user.getPassword());
        user.setPassword(password);
        userDao.addUser(user);

        //往中间表添加关联
        setUserIdAndRoleId(user.getId(),roleIds);

    }

    //根据Id查询user
    @Override
    public User findById(Integer userId) {
        User user = userDao.findById(userId);
        return user;
    }

    //修改用户信息
    @Override
    public void edit(User user, List<Integer> roleIds) {
        //修改用户信息
        //密码加密
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = encoder.encode(user.getPassword());
        user.setPassword(password);
        userDao.edit(user);

        //删除旧的role关联表
        userDao.deleteRoleIdsByUserId(user.getId());

        //新插入关联表
        setUserIdAndRoleId(user.getId(),roleIds);
    }

    //删除用户
    @Override
    public void deleteById(Integer userId) {
        //先删除role关联表
        userDao.deleteRoleIdsByUserId(userId);

        //删除user
        userDao.deleteById(userId);
    }

    @Override
    public Integer findUserIdByUsername(String username) {
        return userDao.findUserIdByUsername(username);
    }

    //循环插入关联表
    public void setUserIdAndRoleId(Integer userId,List<Integer> roleIds){
        //遍历checkItemIds，获取的每一个检查项Id和检查组Id一起存入map，把map当作入参存入中间表
        for (Integer roleId : roleIds) {
            HashMap<String, Integer> map = new HashMap<>();
            map.put("userId",userId);
            map.put("roleId",roleId);

            userDao.setUserIdAndRoleId(map);
        }
    }
}
