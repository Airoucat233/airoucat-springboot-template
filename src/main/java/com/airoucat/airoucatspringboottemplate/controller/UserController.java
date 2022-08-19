package com.airoucat.airoucatspringboottemplate.controller;


import com.airoucat.airoucatspringboottemplate.aop.annotation.RequestLog;
import com.airoucat.airoucatspringboottemplate.domain.SysUser;
import com.airoucat.airoucatspringboottemplate.enums.StatusCodeEnum;
import com.airoucat.airoucatspringboottemplate.global.CurrentUserContextHolder;
import com.airoucat.airoucatspringboottemplate.global.GlobalParams;
import com.airoucat.airoucatspringboottemplate.pojo.vo.UserVo;
import com.airoucat.airoucatspringboottemplate.service.SysUserService;
import com.airoucat.airoucatspringboottemplate.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private SysUserService userService;
    @Autowired
    private RedisUtil redisUtil;

    @GetMapping(value = "getUserInfo")
    @RequestLog
    public HttpResult<Map<String, Object>> getUserInfo(){
        SysUser user = CurrentUserContextHolder.getCurrentUser();
        HttpResult<Map<String, Object>> result = HttpResult.success(new HashMap<>());
        UserVo userVo = SplitUtils.splitPart(user, UserVo.class);
        result.putToMap("userInfo",userVo);
        return result;
    }

    @PostMapping(value = "update")
    @RequestLog
    public HttpResult<Map<String, Object>> update(@RequestBody SysUser updateUser){
        SysUser user = CurrentUserContextHolder.getCurrentUser();
        updateUser.setUserId(user.getUserId());
        if (!StringUtils.isEmpty(updateUser.getPassword())){
            updateUser.setPassword(EncryptionUtil.BCencrypt(updateUser.getPassword()));
        }
        userService.update(updateUser);
        return getUserInfo();
    }

    @GetMapping(value = "validMailCode")
    @RequestLog
    public HttpResult<Map<String, Object>> validMailCode(String loginId,String code){
        Object cCode = redisUtil.get(GlobalParams.RESET_CODE_PREFIX + loginId);
        if (cCode != null){
            try {
                code = EncryptionUtil.AESdecrypt(code,GlobalParams.MAIL_CODE_SECRET_KEY);
                if (cCode.equals(code)){
                    return HttpResult.success();
                }
            } catch (Exception e) {
                return HttpResult.error(StatusCodeEnum.APPERROR);
            }
        }
        return HttpResult.error(StatusCodeEnum.VALIEDCODEERROR);
    }

    @PostMapping(value = "resetPassword")
    @RequestLog
    public HttpResult<?> resetPassword(String loginId,String newPassword){
        Object cCode = redisUtil.get(GlobalParams.RESET_CODE_PREFIX + loginId);
        if (cCode == null){
            return HttpResult.error(StatusCodeEnum.VALIEDCODEEXPIRED);
        }
        SysUser user = userService.queryByloginId(loginId);
        user.setPassword(EncryptionUtil.BCencrypt(newPassword));
        userService.update(user);
        return HttpResult.success();
    }
}
