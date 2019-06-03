package com.liang.guli.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liang.guli.educenter.entity.UcenterMember;
import com.liang.guli.educenter.mapper.UcenterMemberMapper;
import com.liang.guli.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author liliang
 * @since 2019-05-21
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    /**
     * 通过传入得时间统计今日注册数
     * @param day
     * @return
     */
    @Override
    public Integer registerCount(String day) {
        Integer i = baseMapper.registerCount(day);
        return i;
    }

    /**
     * 查询数据库中是否存在该用户
     * @param openid
     * @return
     */
    @Override
    public UcenterMember selectUserInfo(String openid) {

        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);
        return ucenterMember;
    }
}
