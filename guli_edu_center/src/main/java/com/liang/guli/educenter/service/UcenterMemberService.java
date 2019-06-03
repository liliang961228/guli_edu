package com.liang.guli.educenter.service;

import com.liang.guli.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author liliang
 * @since 2019-05-21
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    Integer registerCount(String day);

    UcenterMember selectUserInfo(String openid);
}
