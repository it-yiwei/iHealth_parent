package com.yiwei.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yiwei.dao.RoleDao;
import com.yiwei.pojo.PageResult;
import com.yiwei.pojo.QueryPageBean;
import com.yiwei.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = RoleService.class)
public class RoleServiceImp implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public List<Integer> findRoleIdsByUserId(Integer userId) {
        return roleDao.findRoleIdsByUserId(userId);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Role> rolePage = roleDao.selectByCondition(queryPageBean.getQueryString());

        PageResult pageResult = new PageResult();
        pageResult.setRows(rolePage.getResult());
        pageResult.setTotal(rolePage.getTotal());
        return pageResult;
    }

    //添加角色
    @Override
    public void add(Role role, List<Integer> permissionIds) {
        //添加角色信息
        roleDao.addRole(role);
        System.out.println(role.getId()+"------------------------------");
        //循环添加角色和权限的中间表
        roleAndPermission(role.getId(),permissionIds);

    }

    //删除角色
    @Override
    public void deleteById(Integer roleId) {
        //先删除中间表
        roleDao.deleterolePermissionByRoleId(roleId);
        //删除角色表
        roleDao.deleteById(roleId);

    }


    //公共方法，循环添加角色和权限的中间表
    public void roleAndPermission(Integer roleId,List<Integer> permissionIds){
        if (permissionIds != null && permissionIds.size() > 0){
            for (Integer permissionId : permissionIds) {
                Map<String,Integer> map = new HashMap<>();
                map.put("roleId",roleId);
                map.put("permissionId",permissionId);
                roleDao.roleAndPermission(map);
            }
        }
    }




}
