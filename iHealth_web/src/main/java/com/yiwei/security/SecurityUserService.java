package com.yiwei.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yiwei.pojo.Permission;
import com.yiwei.pojo.Role;
import com.yiwei.pojo.User;
import com.yiwei.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component("securityUserService")
public class SecurityUserService implements UserDetailsService {
    //注入userService
    @Reference
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据username查询user表
        User user = userService.findUserByUsername(username);

        //若user存在，获取username，password，权限关键字存入user(非自定义的pojo)中
        if (user == null) {
            return null;
        }
        else {
            //获取username，password，权限关键字存入user(非自定义的pojo)中
            String password = user.getPassword();

            //获取权限关键字
            List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
            Set<Role> roles = user.getRoles();
            if (roles!=null && roles.size()>0){
                for (Role role : roles) {
                    //获取role关键字
                    String roleKeyword = role.getKeyword();
                    grantedAuthorityList.add(new SimpleGrantedAuthority(roleKeyword));

                    //获取permission关键字
                    Set<Permission> permissions = role.getPermissions();
                    for (Permission permission : permissions) {
                        String permissionKeyword = permission.getKeyword();
                        grantedAuthorityList.add(new SimpleGrantedAuthority(permissionKeyword));
                    }
                }
            }
            //存入user中
            org.springframework.security.core.userdetails.User userdetails =
                    new org.springframework.security.core.userdetails.User(username, password, grantedAuthorityList);
            //返回user
            return userdetails;
        }

    }
}
