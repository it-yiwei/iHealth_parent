package com.yiwei.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yiwei.dao.checkItemDao;
import com.yiwei.pojo.CheckItem;
import com.yiwei.pojo.PageResult;
import com.yiwei.pojo.QueryPageBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImp implements CheckItemService {

    //注入checkItemDao
    @Autowired
    private checkItemDao checkItemDao;

    @Override
    public void addCheckItem(CheckItem checkItem) {
        checkItemDao.addCheckItem(checkItem);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        //分页插件的使用
        //第二种，Mapper接口方式的调用，推荐这种使用方式。
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //dao查询语句,返回page对象（分页插件对象）
        Page<CheckItem> page = checkItemDao.selectByCondition(queryPageBean.getQueryString());

        //封装返回结果
        PageResult pageResult = new PageResult();
        pageResult.setRows(page.getResult());
        pageResult.setTotal(page.getTotal());
        return pageResult;
    }

    @Override
    public void delete(Integer id) throws Exception {
        //先判断项目组是否依赖此项目
        int count = checkItemDao.countCheckItemById(id);
        if (count>0) {
            //依赖，禁止删除此项目
            throw new RuntimeException("当前检查项被引用，不能删除");

        }else {
            //执行删除操作
            checkItemDao.deleteById(id);
        }
    }

    @Override
    public CheckItem findById(int id) {
        CheckItem checkItem = checkItemDao.findById(id);
        return checkItem;
    }

    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }

    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }
}
