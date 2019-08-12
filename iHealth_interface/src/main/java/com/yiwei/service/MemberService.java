package com.yiwei.service;

import java.util.Map;

public interface MemberService {

    //根据telephone查询会员
    void checkMember(String telephone);

    //根据日期统计会员数量
    Integer countMember(String date);

    //根据日期获取会员数据
    Map showByDate(String beginDate, String endDate);

}
