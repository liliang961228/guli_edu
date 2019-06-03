package com.liang.guli.educenter.controller;


import com.liang.guli.educenter.entity.UcenterMember;
import com.liang.guli.educenter.service.UcenterMemberService;
import com.liang.guli.educenter.utils.JwtUtils;
import com.liang.guli.educommon.R;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import springfox.documentation.annotations.ApiIgnore;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author liliang
 * @since 2019-05-21
 */
@Api(tags = "会员信息测试")
@RestController
@RequestMapping("/educenter/center-member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    /**
     * 通过传入得时间统计今日注册数
     * @param day
     * @return
     */
    @ApiOperation(value = "通过传入得时间统计今日注册数")
    @GetMapping("registerCount/{day}")
    public R registerCount(@PathVariable String day){

        Integer i = ucenterMemberService.registerCount(day);

        return R.success().data("number",i);
    }


    //根据jwt的token值获取扫描人的信息
    @GetMapping("userInfo/{token}")
    public R getUserInfoToken(@PathVariable String token) {
        Claims claims = JwtUtils.checkJWT(token);
        String id = (String)claims.get("id");
        String nickname = (String)claims.get("nickname");
        String avatar = (String)claims.get("avatar");

        UcenterMember member = new UcenterMember();
        member.setId(id);
        member.setNickname(nickname);
        member.setAvatar(avatar);

        return R.success().data("member",member);
    }

}

