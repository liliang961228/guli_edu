package com.liang.guli.educenter.mapper;

import com.liang.guli.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author liliang
 * @since 2019-05-21
 */

public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer registerCount(String day);
}
