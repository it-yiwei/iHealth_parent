package com.yiwei.service;

import java.util.List;
import java.util.Map;

public interface MemberService {

    //根据telephone查询会员
    void checkMember(String telephone);

    //根据日期统计会员数量
    Integer countMember(String date);

    //根据性别统计会员人数
    List<Map> countMemberBySex();
}
