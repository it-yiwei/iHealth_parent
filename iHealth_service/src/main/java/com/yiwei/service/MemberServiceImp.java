package com.yiwei.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.yiwei.dao.MemberDao;
import com.yiwei.pojo.Member;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

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
}
