package com.liang.guli.educenter.controller;

import com.google.gson.Gson;
import com.liang.guli.educenter.entity.UcenterMember;
import com.liang.guli.educenter.service.UcenterMemberService;
import com.liang.guli.educenter.utils.ConstantPropertiesUtil;
import com.liang.guli.educenter.utils.HttpClientUtils;
import com.liang.guli.educenter.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@Controller
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
@Api(tags = "微信扫描登录")
public class WxApiController {

    @Autowired
    private UcenterMemberService memberService;

    /**
     * 生成二维码
     * 拼接生成的二维码地址，请求这个地址
     * @param session
     * @return
     */
    @ApiOperation(value = "生成二维码")
    @GetMapping("login")
    public String genQrConnect(HttpSession session){

        //1 声明生成二维码的固定 微信提供的地址
        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        //获取配置的回调地址，对回调地址进行URL编码
        String redirctUrl = ConstantPropertiesUtil.WX_OPEN_REDIRCT_URL;

        //进行URL编码
        try {
            redirctUrl = URLEncoder.encode(redirctUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // TODO 3 只是当前测试时候使用的这个步骤
        String state = "bayue";

        //把参数拼接到路径中，重定向拼接直接的路径里面
        String qrcodeUrl = String.format(
                                    baseUrl,
                                    ConstantPropertiesUtil.WX_OPEN_APP_ID,
                                    redirctUrl,
                                    state);
        return "redirect:"+qrcodeUrl;
    }


    /**
     * 扫描之后回调的方法
     * @param code
     * @param state
     * @return
     */
    @GetMapping("callback")
    public String callBack(String code,String state){

        System.out.println("code = " + code);
        System.out.println("state = " + state);

        //1.扫描之后返回临时票据，通过code得到临时票据
        //2.声明请求地址
        //通过获取临时票据，发送请求换取访问凭证access_token
        String baseAccessTokenUrl ="https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

        //3.向请求地址里拼接参数
        String accessTokenUrl = String.format(
                baseAccessTokenUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                code);

        //使用httpclient请求拼接以后的地址
        String resultInfo = null;
        try {
            resultInfo = HttpClientUtils.get(accessTokenUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //5 把根据临时票据获取数据转换map集合
        Gson gson = new Gson();
        HashMap map = gson.fromJson(resultInfo, HashMap.class);

        //根据key获取值
        String access_token = (String) map.get("access_token");
        String openid = (String) map.get("openid");

        //根据获取openid到数据库表进行查询，如果有相同数据，直接进行登录
        UcenterMember ucenterMember = memberService.selectUserInfo(openid);

        //如果没有查询相同数据，获取扫描人信息，添加到数据库里面
        if (ucenterMember == null){
            //6 拿着access_token和openid请求地址，得到微信扫描人的信息
            //访问微信的资源服务器，获取用户信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            //7 向请求地址里面拼接参数
            System.out.println("baseUserInfoUrl = " + baseUserInfoUrl);
            System.out.println("access_token = " + access_token);
            System.out.println("openid = " + openid);
            String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid);

            //8 使用httpclient发送请求，得到微信扫描人信息
            String userInfo = null;
            try{
                userInfo = HttpClientUtils.get(userInfoUrl);
            }catch(Exception e) {
            }

            HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
            String nickname = (String) userInfoMap.get("nickname");
            String headimgurl = (String) userInfoMap.get("headimgurl");

            //把扫描人微信信息存储到数据库表里面
            ucenterMember = new UcenterMember();
            ucenterMember.setOpenid(openid);
            ucenterMember.setNickname(nickname);
            ucenterMember.setAvatar(headimgurl);

            memberService.save(ucenterMember);
        }

        //根据ucenterMember对象生成jwt字符串
        String token = JwtUtils.geneJsonWebToken(ucenterMember);

        return "redirect:http://localhost:3000?token="+token;
    }
}
