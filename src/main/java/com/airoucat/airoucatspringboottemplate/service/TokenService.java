package com.airoucat.airoucatspringboottemplate.service;

import com.airoucat.airoucatspringboottemplate.domain.SysUser;
import com.airoucat.airoucatspringboottemplate.global.GlobalParams;
import com.airoucat.airoucatspringboottemplate.utils.EncryptionUtil;
import com.airoucat.airoucatspringboottemplate.utils.JwtUtils;
import com.airoucat.airoucatspringboottemplate.utils.RedisUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class TokenService {
    @Autowired
    private RedisUtil redisUtil;

    public JSONObject generateToken(SysUser user) throws Exception {
        Map<String,String> payload = new HashMap<>();
        String tokenId = UUID.randomUUID().toString();
        String tokenKey = GlobalParams.LOGIN_TOKEN_PREFIX + user.getLoginId() + ":" + tokenId;
        payload.put("tokenKey", EncryptionUtil.AESencrypt(tokenKey,GlobalParams.TOKEN_SECRET_KEY));
        payload.put("isRefresh","false");
        String accessToken = JwtUtils.createToken(payload, GlobalParams.ACCESS_TOKEN_EXPIRE_TIME);

        payload.put("isRefresh","true");
        String refreshToken = JwtUtils.createToken(payload,GlobalParams.REFRESH_TOKEN_EXPIRE_TIME);
        JSONObject tokens = new JSONObject();
        tokens.put("tokenKey",tokenKey);
        tokens.put("accessToken",accessToken);
        tokens.put("refreshToken",refreshToken);
        return tokens;
    }

    public String refreshToken(String tokenKey) throws Exception {
        Map<String,String> payload = new HashMap<>();
        payload.put("tokenKey",EncryptionUtil.AESencrypt(tokenKey,GlobalParams.TOKEN_SECRET_KEY));
        payload.put("isRefresh","false");
        String accessToken = JwtUtils.createToken(payload, GlobalParams.ACCESS_TOKEN_EXPIRE_TIME);
        redisUtil.hset(tokenKey,"accessToken",accessToken);
        return accessToken;
    }

    public boolean verifyCacheToken(String tokenKey,String tokenType,String token){
        if (tokenKey!=null && redisUtil.hasKey(tokenKey)){
            if (redisUtil.hget(tokenKey, tokenType).equals(token)){
                return true;
            }
            else {
                redisUtil.del(tokenKey);
                return false;
            }
        }
        return false;
    }
}
