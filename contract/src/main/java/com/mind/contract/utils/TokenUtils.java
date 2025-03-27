package com.mind.contract.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mind.contract.entity.pojo.User;

import java.util.Date;

/**
 * @author ：long
 * @date ：Created in 2024/8/14 0014 17:55
 * @description：token工具类
 */
public class TokenUtils {

    //token到期时间10小时
    private static final long EXPIRE_TIME= 10*60*60*1000;
    //密钥盐
    private static final String TOKEN_SECRET="ljdyaishijin**3nkjnj??";

    /**
     * 生成token
     * @param user
     * @return
     */
    public static String sign(User user){

        String token=null;
        try {
//            Date expireAt=new Date(System.currentTimeMillis()+EXPIRE_TIME);
            token = JWT.create()
                    //发行人
                    .withIssuer("dd")
                    //存放数据
                    .withClaim("username",user.getUsername()+" "+System.currentTimeMillis())
                    .withClaim("userId",user.getId()+" "+System.currentTimeMillis())
                    //过期时间
//                    .withExpiresAt(expireAt)
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (IllegalArgumentException | JWTCreationException je) {

        }
        return token;
    }


    /**
     * token验证
     * @param token
     * @return
     */
    public static Boolean verify(String token){

        try {
            //创建token验证器
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("dd").build();
            jwtVerifier.verify(token);
        } catch (IllegalArgumentException | JWTVerificationException e) {
            //抛出错误即为验证不通过
            return false;
        }
        return true;
    }

    public static String getUsername(String token) {
        return JWT.decode(token).getClaim("username").asString().split(" ")[0];
    }

    public static String getUserId(String token) {
        return JWT.decode(token).getClaim("userId").asString().split(" ")[0];
    }

    public static void main(String[] args) {
        User user = new User();
        user.setUsername("admin");
        String token = TokenUtils.sign(user);
        System.out.println(token);
        System.out.println(TokenUtils.verify(token));
        System.out.println(TokenUtils.getUsername(token));
    }

}
