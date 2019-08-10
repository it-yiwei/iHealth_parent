package com.yiwei.dao;
import com.yiwei.pojo.Order;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface OrderDao {
    public void add(Order order);
    public List<Order> findByCondition(Order order);
    public Map findById4Detail(Integer id);
    public Integer findOrderCountByDate(String date);
    public Integer findOrderCountAfterDate(String date);
    public Integer findVisitsCountByDate(String date);
    public Integer findVisitsCountAfterDate(String date);
    public List<Map> findHotSetmeal();

    @Select("SELECT COUNT(o.id) value,s.name FROM t_order o, t_setmeal s WHERE o.setmeal_id = s.id GROUP BY s.name")
    public List<Map> findSetmealCount();

}
