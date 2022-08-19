package com.airoucat.airoucatspringboottemplate.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.qjyy.swjzgxweb.enums.StatusCodeEnum;
import com.qjyy.swjzgxweb.global.GlobalParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: Jwt工具类，生成JWT和认证
 */
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    /**
     * 密钥
     */
    private static final String SECRET = EncryptionUtil.getMD5(GlobalParams.ORIGINAL_SECRET,"233");

    /**
     * 过期时间
     **/
    private static final long EXPIRATION = 172800L;//单位为秒

    /**
     * 生成用户token,设置token超时时间
     */
    public static String createToken(Map<String,String> payload,Long timeSeconds) {
        Date expireDate = new Date(System.currentTimeMillis() + timeSeconds * 1000);
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        JWTCreator.Builder builder = JWT.create().withHeader(header);
        for(String key : payload.keySet()){
            builder = builder.withClaim(key,payload.get(key));
        }
        return builder.withIssuer("system")
                .withExpiresAt(expireDate) //超时设置,设置过期的日期
                .withIssuedAt(new Date()) //签发时间
                .sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * 校验token并解析token
     */
    public static JudgeResult<Map<String,Claim>> verifyToken(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
        } catch (TokenExpiredException e) {
            //logger.error("token已失效: " + e.getMessage());
            return JudgeResult.except(StatusCodeEnum.TOKENEXPIRED.getCode(),StatusCodeEnum.TOKENEXPIRED.getMsg());
        }
        catch (Exception e) {
            logger.error("token解码异常: " + e.getMessage());
//            解码异常则抛出异常
            return JudgeResult.error();
        }
        return JudgeResult.expect(jwt.getClaims());
    }

}
