package com.liang.guli.educenter.utils;

import com.liang.guli.educenter.entity.UcenterMember;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import java.util.Date;

public class JwtUtils {

    public static final String SUBJECT = "guli";
    //秘钥
    public static final String APPSECRET = "guli";

    public static final long EXPIRE = 1000 * 60 * 30;  //过期时间，毫秒，30分钟

    /**
     * 生成jwt token
     * 根据对象（数据）生成一系列字符串值
     */
    public static String geneJsonWebToken(UcenterMember member) {

        if (member == null || StringUtils.isEmpty(member.getId())
                || StringUtils.isEmpty(member.getNickname())
                || StringUtils.isEmpty(member.getAvatar())) {
            return null;
        }
        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("id", member.getId())
                .claim("nickname", member.getNickname())
                .claim("avatar", member.getAvatar())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256, APPSECRET).compact();
        return token;
    }

    /**
     * 校验jwt token
     * 根据jwt生成字符串获取jwt里面数据
     */
    public static Claims checkJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(APPSECRET).parseClaimsJws(token).getBody();
        return claims;
    }

    public static void main(String[] args){
        UcenterMember u = new UcenterMember();
        u.setId("11");
        u.setNickname("lucy");
        u.setAvatar("a.jpg");
        String token = geneJsonWebToken(u);
        System.out.println(token);

        Claims claims = checkJWT(token);
        String id = (String)claims.get("id");
        String nickname = (String)claims.get("nickname");
        String avatar = (String)claims.get("avatar");

        System.out.println(id+":"+nickname+":"+avatar);
    }

}

