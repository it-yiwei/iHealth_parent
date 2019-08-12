package com.yiwei.dao;

import com.yiwei.pojo.OrderSetting;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface OrderSettingDao {

    //查询日期是否存在
    @Select("select count(*) from t_ordersetting where orderDate=#{orderDate}")
    int findCountByOrderDate(Date orderDate);

    //重置预约信息
    @Update("update t_ordersetting set number = #{number} where orderDate = #{orderDate}")
    void updateNumberByOrderDate(OrderSetting orderSetting);

    //新建预约
    @Insert("insert into t_ordersetting (orderDate,number,reservations) values (#{orderDate},#{number},#{reservations})")
    void addOrderSetting(OrderSetting orderSetting);

    //查询当月预约信息
    @Select("select * from t_ordersetting where orderDate between #{startDate} and #{endDate}")
    List<OrderSetting> getOrderSettingByMonth(HashMap<String, String> params);

    //根据预约日期查询预约设置信息
    OrderSetting findByOrderDate(Date orderDate);

    //更新已预约人数
    @Update("update t_ordersetting set reservations = #{reservations} where orderDate = #{orderDate}")
    void updateReservations(OrderSetting orderSetting);

    //删除当前日期之前的预约数据
    @Delete("DELETE FROM t_ordersetting WHERE orderDate < #{now}")
    void clearOldOrderSetting(String now);

    //更改预约设置
    /*@Update("update t_ordersetting set number = #{number} where orderDate = #{orderDate}")
    void editOrderSetting(@Param("orderDate") String orderDate, @Param("number") Integer number);*/

}
