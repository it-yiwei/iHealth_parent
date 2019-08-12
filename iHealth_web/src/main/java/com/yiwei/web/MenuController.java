package com.yiwei.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yiwei.constant.MessageConstant;
import com.yiwei.pojo.Menu;
import com.yiwei.pojo.Result;
import com.yiwei.service.MenuService;
import com.yiwei.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    //注入service
    @Reference
    private MenuService menuService;

    @Reference
    private UserService userService;


    //根据角色查询menu
    @RequestMapping("/findMenuByUser")
    public Result findMenuByUser(){
        try {
            //获取登陆的用户
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            //获取userId
            Integer userId = userService.findUserIdByUsername(user.getUsername());

            //根据userId获取菜单
            List<Menu> menus = menuService.findMenuByUserId(userId);
            return new Result(true, MessageConstant.GET_MENU_LIST_SUCCESS,menus);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.GET_MENU_LIST_FALL);
        }

    }
}
