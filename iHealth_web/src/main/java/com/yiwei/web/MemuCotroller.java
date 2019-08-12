package com.yiwei.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yiwei.constant.MessageConstant;
import com.yiwei.pojo.Result;
import com.yiwei.pojo.User;
import com.yiwei.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/memu")
@RestController
public class MemuCotroller {

    @Reference
    private UserService userService;



}
