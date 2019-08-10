package com.yiwei.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yiwei.dao.CheckGroupDao;
import com.yiwei.pojo.CheckGroup;
import com.yiwei.pojo.CheckItem;
import com.yiwei.pojo.PageResult;
import com.yiwei.pojo.QueryPageBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImp implements CheckGroupService {

    //注入CheckGroupDao
    @Autowired
    private CheckGroupDao checkGroupDao;

    @Override
    public void add(CheckGroup checkGroup, List<Integer> checkitemIds) {
        //先在checkGroup表增加检查项信息，并且返回自增主键
        checkGroupDao.addCheckGroup(checkGroup);

        //往中间表添加关联
        setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);

    }

    //分页查询所有检查组
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<CheckGroup> page = checkGroupDao.selectByCondition(queryPageBean.getQueryString());

        //封装结果
        PageResult pageResult = new PageResult(page.getTotal(),page.getResult());
        return pageResult;
    }

    ////根据id查询检查组
    @Override
    public CheckGroup findById(Integer checkGroupId) {

        return checkGroupDao.findById(checkGroupId);
    }

    //根据检查组Id查询检查项Ids
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer checkGroupId) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(checkGroupId);
    }

    @Override
    public void edit(CheckGroup checkGroup, List<Integer> checkitemIds) {
        //先修改检查组信息
        checkGroupDao.edit(checkGroup);

        //再删除中间表的关联信息
        checkGroupDao.deleteByCheckGroupId(checkGroup.getId());

        //新增关联信息
        setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);
    }

    //删除检查组
    @Override
    public void deleteById(Integer checkGroupId) {
        //先判断此检查组是否被套餐引用
        Integer count = checkGroupDao.countById(checkGroupId);
        if (count > 0) {
            //引用则不可以删除
            throw new RuntimeException("检查套餐已包含此检查组，禁止删除！");
        }else {
            //可以删除
            //先删除检查项和检查组的关联表，
            checkGroupDao.deleteByCheckGroupId(checkGroupId);
            //再删除此检查组，先后顺序不能乱，外键引用问题
            checkGroupDao.deleteCheckGroupById(checkGroupId);
        }
    }

    //查询所有检查组
    @Override
    public List<CheckGroup> findAll() {

        return checkGroupDao.findAll();
    }

    public void setCheckGroupAndCheckItem(Integer checkGroupId,List<Integer> checkItemIds){
        //遍历checkItemIds，获取的每一个检查项Id和检查组Id一起存入map，把map当作入参存入中间表
        for (Integer checkItemId : checkItemIds) {
            HashMap<String, Integer> map = new HashMap<>();
            map.put("checkGroupId",checkGroupId);
            map.put("checkItemId",checkItemId);

            checkGroupDao.setCheckGroupAndCheckItem(map);
        }
    }
}
