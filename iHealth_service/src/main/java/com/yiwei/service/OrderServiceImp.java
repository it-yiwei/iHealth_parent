package com.yiwei.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.yiwei.constant.MessageConstant;
import com.yiwei.dao.MemberDao;
import com.yiwei.dao.OrderDao;
import com.yiwei.dao.OrderSettingDao;
import com.yiwei.pojo.Member;
import com.yiwei.pojo.Order;
import com.yiwei.pojo.OrderSetting;
import com.yiwei.pojo.Result;
import com.yiwei.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
public class OrderServiceImp implements  OrderService{

    //注入OrderSettingDao
    @Autowired
    private OrderSettingDao orderSettingDao;

    //注入OrderDao
    @Autowired
    private OrderDao orderDao;

    //注入memberDao
    @Autowired
    private MemberDao memberDao;



    //预约业务
    @Override
    public Result submitOrder(Map<String, String> map) throws Exception {
        /*
        *    1，判断当天日期是否可以预约
             2，当天预约人数是否已满
             3，查看此号码是否是会员，是会员获取会员号，并且查看此会员是否重复预约（时间，套餐）
             4，不是会员新建会员，返回会员号
             5，把预约信息存入预约表
             6，预约成功，当天已预约人数加一
        */
        //获取参数
        String orderDate = map.get("orderDate");
        String telephone = map.get("telephone");
        String setmealId = map.get("setmealId");
        String idCard = map.get("idCard");
        String name = map.get("name");
        String orderType = map.get("orderType");

        //1，判断当天日期是否可以预约
        Date date = DateUtils.parseString2Date(orderDate);
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(date);

        if (orderSetting == null){
            //所选日期不可预约
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }

        //2，当天预约人数是否已满
        if (orderSetting.getNumber() <= orderSetting.getReservations()){
            //预约已满
            return new Result(false, MessageConstant.ORDER_FULL);
        }

        //3，查看此号码是否是会员，是会员获取会员号，并且查看此会员是否重复预约（时间，套餐）
        Member member = memberDao.findByTelephone(telephone);
        Order order = new Order();
        order.setOrderDate(date);
        order.setSetmealId(Integer.valueOf(setmealId));

        if (member !=null){
            //判断是否重复预约
            order.setMemberId(member.getId());
            List<Order> orders = orderDao.findByCondition(order);
            if (orders != null && orders.size()>0){
                //已预约过，重复预约
                return new Result(false, MessageConstant.HAS_ORDERED);
            }
        }else {
            //4，不是会员新建会员，返回会员号
            //新加会员
            Member newMember = new Member();
            newMember.setPhoneNumber(telephone);
            newMember.setIdCard(idCard);
            newMember.setName(name);
            //新添加会员，返回主键id
            memberDao.add(newMember);
            Integer memberId = newMember.getId();
            order.setMemberId(memberId);
        }

        //5，把预约信息存入预约表
        order.setOrderType(orderType);
        order.setOrderStatus("未到诊");
        orderDao.add(order);

        //6，预约成功，当天已预约人数加一
        orderSetting.setReservations(orderSetting.getReservations()+1);
        orderSettingDao.updateReservations(orderSetting);

        return new Result(true,MessageConstant.ORDER_SUCCESS,order);
    }

    @Override
    public Map findById(Integer id) {
        Map byId4Detail = orderDao.findById4Detail(id);
        return byId4Detail;
    }

    @Override
    public List<Map> findHostSetmealCount() {
        //返回结果：list<map<string 套餐名,int 预约人数>>
        List<Map> resultList = orderDao.findSetmealCount();
        return resultList;
    }
}
