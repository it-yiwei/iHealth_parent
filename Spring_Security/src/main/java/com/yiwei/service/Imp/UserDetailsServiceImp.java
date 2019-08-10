package com.yiwei.service.Imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder encoder;


    @Override
    public UserDetails loadUserByUsername(String username) {
        //通过账号获取用户信息,查询user表
        com.yiwei.pojo.User user = findUserByUsername(username);
        
        //判断用户是否存在，存在再查询权限表
        if (user!=null) {
            //先把角色和权限写死,后面从数据库查询出来
            List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
            list.add(new SimpleGrantedAuthority("add"));
            list.add(new SimpleGrantedAuthority("delete"));
            list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            User userDetails = new User(user.getUsername(), user.getPassword(), list);
            return userDetails;
        }

        return null;
    }

    public com.yiwei.pojo.User findUserByUsername(String username){
        
        //模拟dao层查询数据库
        if ("admin".equals(username)){
            com.yiwei.pojo.User user = new com.yiwei.pojo.User();
            user.setUsername(username);
            user.setPassword(encoder.encode("123"));
            return user;
        }
        return null;
    }
}
