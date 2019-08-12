package com.yiwei.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.yiwei.dao.MenuDao;
import com.yiwei.dao.RoleDao;
import com.yiwei.dao.UserDao;
import com.yiwei.pojo.Menu;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service(interfaceClass = MenuService.class)
public class MenuServiceImp implements MenuService {
    //注入Dao
    @Autowired
    private MenuDao menuDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    //根据userId获取菜单
    @Override
    public List<Menu> findMenuByUserId(Integer userId) {
        //获取关联roleId
        List<Integer> roleIds = userDao.findRoleIdByUserId(userId);
        //根据roleIds获取menu信息
        List<Menu> menus = new ArrayList<>();
        for (Integer roleId : roleIds) {
            //根据roleId获取MenuId
            List<Integer> menuIds = roleDao.findMenuIdsByRoleId(roleId);
            for (Integer menuId : menuIds) {
                //先查询父级menu
                Menu parentMenu =menuDao.findParentByMenuId(menuId);
                //若是父级menu，则再查询关联的子menu
                if (parentMenu !=null ){
                    List<Menu> childMenus = menuDao.findChildByMenuId(menuId);
                    parentMenu.setChildren(childMenus);
                    menus.add(parentMenu);
                }
            }
        }
        return menus;
    }
}
