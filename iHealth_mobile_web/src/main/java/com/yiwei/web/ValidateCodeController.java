package com.yiwei.web;


import com.aliyuncs.exceptions.ClientException;
import com.yiwei.constant.MessageConstant;
import com.yiwei.constant.RedisMessageConstant;
import com.yiwei.pojo.Result;
import com.yiwei.utils.SMSUtils;
import com.yiwei.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    //注入redis连接池
    @Autowired
    private JedisPool jedisPool;

    //发送预约验证码，并且将验证码存入redis
    @RequestMapping("/send4Order")
    public Result send4Order(String telephone){

        //获取随机四位验证码
        Integer validateCode = ValidateCodeUtils.generateValidateCode(4);

        try {
            //发送验证码
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,validateCode.toString());

        } catch (ClientException e) {
            //获取验证码失败
            e.printStackTrace();
            return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
        }

        //把验证码存入redis中
        Jedis jedis = jedisPool.getResource();
        jedis.setex(telephone+ "_"+ RedisMessageConstant.SENDTYPE_ORDER,5*60, String.valueOf(validateCode));

        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

    //发送登陆验证码，并且将验证码存入redis
    @RequestMapping("/send4Login")
    public Result send4Login(String telephone){

        //获取随机四位验证码
        Integer validateCode = ValidateCodeUtils.generateValidateCode(6);

        try {
            //发送验证码
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,validateCode.toString());

        } catch (ClientException e) {
            //获取验证码失败
            e.printStackTrace();
            return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
        }

        //把验证码存入redis中
        Jedis jedis = jedisPool.getResource();
        jedis.setex(telephone+ "_"+ RedisMessageConstant.SENDTYPE_LOGIN,5*60, String.valueOf(validateCode));

        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
