package com.yiwei.service;

import com.yiwei.pojo.CheckItem;
import com.yiwei.pojo.PageResult;
import com.yiwei.pojo.QueryPageBean;

import java.util.List;

public interface CheckItemService {

    void addCheckItem(CheckItem checkItem);

    PageResult findPage(QueryPageBean queryPageBean);

    void delete(Integer id) throws Exception;

    CheckItem findById(int id);

    void update(CheckItem checkItem);

    List<CheckItem> findAll();
}
