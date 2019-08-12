package com.yiwei.service;

import com.yiwei.pojo.Menu;

import java.util.List;

public interface MenuService {
    //根据userId获取菜单
    List<Menu> findMenuByUserId(Integer userId);
}
