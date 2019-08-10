package com.yiwei.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yiwei.constant.MessageConstant;
import com.yiwei.constant.RedisConstant;
import com.yiwei.pojo.*;
import com.yiwei.service.SetmealService;
import com.yiwei.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    //注入service
    @Reference
    private SetmealService setmealService;

    //注入jedisPool
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile")MultipartFile imgFile){
        try{
            //获取原始文件名
            String originalFilename = imgFile.getOriginalFilename();
            int lastIndexOf = originalFilename.lastIndexOf(".");
            //获取文件后缀
            String suffix = originalFilename.substring(lastIndexOf - 1);
            //使用UUID随机产生文件名称，防止同名文件覆盖
            String fileName = UUID.randomUUID().toString() + suffix;
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            //图片上传成功,文件名存入redis中
            Jedis jedis = jedisPool.getResource();
            jedis.sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);

            Result result = new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS);
            result.setData(fileName);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            //图片上传失败
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    //新增检查套餐
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal,@RequestParam List<Integer> checkgroupIds){
        //业务处理，返回查询结果
        try {
            setmealService.add(setmeal,checkgroupIds);

            //新增成功后把图片id存入redis
            Jedis jedis = jedisPool.getResource();
            jedis.sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());

            //新增成功
            return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
            //查询失败
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    //查询所有检查套餐
    @RequestMapping("/findPage")
    public Result add(@RequestBody QueryPageBean queryPageBean){
        //业务处理，返回查询结果
        try {
            PageResult pageResult = setmealService.findPage(queryPageBean);

            //查询成功
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,pageResult);

        } catch (Exception e) {
            e.printStackTrace();
            //查询失败
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

    //删除检查组
    @RequestMapping("/deleteById")
    public Result deleteById(Integer setmealId){

        try {
            setmealService.deleteById(setmealId);

            Result result = new Result(true, MessageConstant.DELETE_SETMEAL_SUCCESS);
            return result;

        } catch (RuntimeException e){
            return new Result(false, e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            Result result = new Result(false, MessageConstant.DELETE_SETMEAL_FAIL);
            return result;
        }
    }

}
