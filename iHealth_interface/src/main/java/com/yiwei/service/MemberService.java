package com.yiwei.service;

public interface MemberService {

    //根据telephone查询会员
    void checkMember(String telephone);

    //根据日期统计会员数量
    Integer countMember(String date);
}
