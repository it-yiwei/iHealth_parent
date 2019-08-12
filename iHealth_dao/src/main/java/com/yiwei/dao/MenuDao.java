package com.yiwei.dao;

import com.yiwei.pojo.Menu;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MenuDao {
    //只查询父menu
    @Select("select name ,path,icon from t_menu where level=1 and id=#{menuId}")
    Menu findParentByMenuId(Integer menuId);

    //根据父菜单Id查询子菜单
    @Select("select name,path,linkUrl from t_menu where parentMenuId=#{menuId}")
    List<Menu> findChildByMenuId(Integer menuId);
}
