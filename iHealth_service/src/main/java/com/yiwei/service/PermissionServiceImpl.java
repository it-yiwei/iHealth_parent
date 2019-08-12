package com.yiwei.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yiwei.dao.PermissionDao;
import com.yiwei.pojo.PageResult;
import com.yiwei.pojo.Permission;
import com.yiwei.pojo.QueryPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dengwangpeng
 * @dete 2019/8/11 - 11:16
 *
 */
@Transactional
@Service(interfaceClass = PermissionService.class)
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;


    //分页查询
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        //使用分页插件
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Permission> permissionPage = permissionDao.selectByCondition(queryPageBean.getQueryString());

        //返回结果
        PageResult pageResult = new PageResult();
        pageResult.setRows(permissionPage.getResult());
        pageResult.setTotal(permissionPage.getTotal());
        return pageResult;
    }

    //添加权限
    @Override
    public void add(Permission permission) {
        permissionDao.add(permission);
    }

    //根据id删除权限
    @Override
    public void deleteById(Integer permissionId) {
        permissionDao.deleteById(permissionId);
    }

    //弹出编辑款回显权限信息
    @Override
    public Permission findById(Integer permissionId) {
        return permissionDao.findById(permissionId);
    }

    //编辑权限
    @Override
    public void edit(Permission permission) {
        permissionDao.edit(permission);
    }

    //查询全部权限
    @Override
    public List<Permission> findAll() {
        return permissionDao.findAll();
    }
}
