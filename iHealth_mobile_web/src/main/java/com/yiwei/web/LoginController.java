package com.yiwei.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yiwei.constant.MessageConstant;
import com.yiwei.constant.RedisMessageConstant;
import com.yiwei.pojo.Result;
import com.yiwei.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RequestMapping("/login")
@RestController
public class LoginController {

    //注入redis
    @Autowired
    private JedisPool jedisPool;

    //注入service
    @Reference
    private MemberService memberService;

    @RequestMapping("/check")
    public Result check(@RequestBody Map<String,String> map, HttpServletResponse response){
        //获取手机号和验证码
        String telephone = map.get("telephone");
        String validateCode = map.get("validateCode");

        //根据手机获取redis中的验证码，进行对比
        Jedis jedis = jedisPool.getResource();
        String code = jedis.get(telephone + "_" + RedisMessageConstant.SENDTYPE_LOGIN);

        //判断验证码
        if (code != null && code.equals(validateCode)){
            //登陆成功,进行会员业务处理，把会员信息存到cookie
            try {
                memberService.checkMember(telephone);
                //发送cookie
                Cookie cookie = new Cookie("login_member_telephone",telephone);
                cookie.setPath("/");
                cookie.setMaxAge(60*60*24*7);
                response.addCookie(cookie);
                return new Result(true, MessageConstant.LOGIN_SUCCESS);
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(false, MessageConstant.ADD_MEMBER_FAIL);
            }
        }
        //登陆失败
        return new Result(false,MessageConstant.VALIDATECODE_ERROR);
    }

}
