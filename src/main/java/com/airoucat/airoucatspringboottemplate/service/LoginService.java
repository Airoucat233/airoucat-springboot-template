package com.airoucat.airoucatspringboottemplate.service;

import com.airoucat.airoucatspringboottemplate.domain.SysLogininfor;
import com.airoucat.airoucatspringboottemplate.domain.SysUser;
import com.airoucat.airoucatspringboottemplate.enums.StatusCodeEnum;
import com.airoucat.airoucatspringboottemplate.global.GlobalParams;
import com.airoucat.airoucatspringboottemplate.mapper.SysLogininforMapper;
import com.airoucat.airoucatspringboottemplate.utils.*;
import com.alibaba.fastjson.JSONObject;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service("LoginService")
public class LoginService {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private SysUserService userService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SysLogininforMapper sysLogininforMapper;

    public HttpResult<JSONObject> login(String loginId, String password){
        HttpServletRequest request = ServletUtils.getRequest();
        SysLogininfor sysLogininfor = new SysLogininfor(request,loginId);
        SysUser user = userService.queryByloginId(loginId);
        if(user!=null){
            if(EncryptionUtil.BCcheck(password,user.getPassword())){
                JSONObject tokens;
                try {
                    tokens = tokenService.generateToken(user);
                } catch (Exception e) {
                    sysLogininfor.setStatusAndMsg("1","token失效");
                    sysLogininforMapper.insert(sysLogininfor);
                    return HttpResult.error(StatusCodeEnum.TOKENGENERATEFAILD,e.getMessage());
                }
                user.setRoles(getRoles(user.getUserId()));
                String tokenKey = tokens.getString("tokenKey");
                user.setTokenKey(tokenKey);
                sysLogininfor.setTokenKey(tokenKey.replace(GlobalParams.LOGIN_TOKEN_PREFIX+loginId+":", ""));
                redisUtil.hset(tokenKey,"accessToken",tokens.getString("accessToken"));
                redisUtil.hset(tokenKey,"refreshToken",tokens.getString("refreshToken"),GlobalParams.REFRESH_TOKEN_EXPIRE_TIME);
                redisUtil.hset(tokenKey,"user",user);
//                redisUtil.hset(tokenKey,"sysLogininfor",sysLogininfor);
                sysLogininfor.setStatusAndMsg("0","登录成功");
                sysLogininforMapper.insert(sysLogininfor);
                tokens.remove("tokenKey");
                if (GlobalParams.INITIAL_PASSWORD.equals(password)){
                    return HttpResult.warning(StatusCodeEnum.FIRSTLOGINWARN.getMsg(),tokens);
                }
                return HttpResult.success(tokens);
            }
            else {
                sysLogininfor.setStatusAndMsg("1","密码错误");
                sysLogininforMapper.insert(sysLogininfor);
                return HttpResult.error(StatusCodeEnum.USERINFOERROR);
            }
        }
        else{
            return HttpResult.error(StatusCodeEnum.USERINFOERROR);
        }
    }



    private Set<String> getRoles(Long userId){
        List<String> queryRoles = userService.queryRoles(userId);
        Set<String> roles = new HashSet<>();
        for (String r : queryRoles)
        {
            if (StringUtils.isNotEmpty(r))
            {
                roles.add(r);
            }
        }
        return roles;
    }

}
