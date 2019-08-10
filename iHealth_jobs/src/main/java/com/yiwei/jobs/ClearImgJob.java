package com.yiwei.jobs;

import com.yiwei.constant.RedisConstant;
import com.yiwei.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

@Component("clearImgJob")
public class ClearImgJob {

    @Autowired
    private JedisPool jedisPool;

    public void clearImg(){
        //计算两个redis差值
        Jedis jedis = jedisPool.getResource();
        Set<String> imageUrl = jedis.sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        for (String url : imageUrl) {
            System.out.println("开始清理垃圾");
            //删除七牛云垃圾资源
            QiniuUtils.deleteFileFromQiniu(url);
            //删除redis上垃圾路径
            jedis.srem(RedisConstant.SETMEAL_PIC_RESOURCES,url);
        }
    }
}
