package com.yiwei.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yiwei.dao.SetmealDao;
import com.yiwei.pojo.CheckGroup;
import com.yiwei.pojo.PageResult;
import com.yiwei.pojo.QueryPageBean;
import com.yiwei.pojo.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImp implements SetmealService {

    //注入dao
    @Autowired
    private SetmealDao setmealDao;

    //新增检查套餐
    @Override
    @Transactional
    public void add(Setmeal setmeal, List<Integer> checkgroupIds) {
        //保存套餐信息，并返回主键
        setmealDao.addSetmeal(setmeal);
        Integer setmealId = setmeal.getId();

        //保存套餐和检查组的中间表信息
        if(checkgroupIds != null && checkgroupIds.size() > 0){
            //绑定套餐和检查组的多对多关系
            setSetmealIdAndCheckgroupId(setmealId,checkgroupIds);
        }

    }

    //分页查询所有套餐
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<CheckGroup> page = setmealDao.selectByCondition(queryPageBean.getQueryString());

        //封装结果
        PageResult pageResult = new PageResult(page.getTotal(),page.getResult());
        return pageResult;
    }

    //删除套餐
    @Override
    public void deleteById(Integer setmealId) {
        //先删除中间表
        setmealDao.deleteSetmealAndCheckGroup(setmealId);

        //再删除套餐表
        setmealDao.deleteById(setmealId);
    }

    @Override
    public List<Setmeal> getSetmeal() {
        return setmealDao.getSetmeal();
    }

    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }

    public void setSetmealIdAndCheckgroupId(Integer setmealId,List<Integer> checkgroupIds){
        //遍历checkItemIds，获取的每一个检查项Id和检查组Id一起存入map，把map当作入参存入中间表
        for (Integer checkgroupId : checkgroupIds) {
            HashMap<String, Integer> map = new HashMap<>();
            map.put("setmealId",setmealId);
            map.put("checkgroupId",checkgroupId);

            setmealDao.setSetmealIdAndCheckgroupId(map);
        }
    }
}
