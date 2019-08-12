package com.yiwei.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.yiwei.dao.MemberDao;
import com.yiwei.pojo.Member;
import com.yiwei.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service(interfaceClass = MemberService.class)
public class MemberServiceImp implements MemberService {
    //注入dao
    @Autowired
    private MemberDao memberDao;


    @Override
    public void checkMember(String telephone) {
        //根据号码查询，若是会员不处理，不是会员添加成为会员
        Member member = memberDao.findByTelephone(telephone);

        if (member == null){
            //新添加为会员
            Member newMember = new Member();
            newMember.setPhoneNumber(telephone);
            newMember.setRegTime(new Date());
            memberDao.add(newMember);
        }
    }

    @Override
    public Integer countMember(String date) {
        Integer members = memberDao.findMemberCountBeforeDate(date);
        return members;
    }

    /*
    * 根据所选的时间段来动态展示此时间段内的每个月会员数量
    * */
    @Override
    public Map showByDate(String beginDate, String endDate) {


        //定义一个map
        Map<String, Object> map = new HashMap<>();

        //定义一个集合，存放会员数据
        List<Integer> memberCount = new ArrayList<>();

        try {

            //使用dateutils,获取二个日期中差值
            List<String> months = DateUtils.getMonthBetween(beginDate, endDate, "yyyy-MM");

            //把时间差值存放到map中
            map.put("months", months);

            //非空判断
            if (months != null && months.size() > 0) {

                //遍历时间集合，作为查询会员数据的条件
                for (String month : months) {

                    //调用dao，获取会员数据
                    Integer memberCountBeforeDate = memberDao.findMemberCountBeforeDate(month);

                    //把会员数据存放到list中
                    memberCount.add(memberCountBeforeDate);
                }
            }

            //把存放会员数据的list,封装到map中返回
            map.put("memberCount", memberCount);


        } catch (Exception e) {

            e.printStackTrace();
        }

        //返回map
        return map;
    }
}
