package com.airoucat.airoucatspringboottemplate.controller;

import com.airoucat.airoucatspringboottemplate.aop.annotation.RequestLog;
import com.airoucat.airoucatspringboottemplate.domain.SysLogininfor;
import com.airoucat.airoucatspringboottemplate.domain.SysUser;
import com.airoucat.airoucatspringboottemplate.enums.StatusCodeEnum;
import com.airoucat.airoucatspringboottemplate.global.CurrentUserContextHolder;
import com.airoucat.airoucatspringboottemplate.global.GlobalParams;
import com.airoucat.airoucatspringboottemplate.service.LoginService;
import com.airoucat.airoucatspringboottemplate.service.SysUserService;
import com.airoucat.airoucatspringboottemplate.service.TokenService;
import com.airoucat.airoucatspringboottemplate.utils.HttpResult;
import com.airoucat.airoucatspringboottemplate.utils.MailUtils;
import com.airoucat.airoucatspringboottemplate.utils.RedisUtil;
import com.airoucat.airoucatspringboottemplate.utils.ServletUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@Slf4j
public class LoginController {
    @Autowired
    private SysUserService userService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private MailUtils mailUtils;

    /**
     * 登录
     * @param
     * @return
     */
    @PostMapping(value="/login")
    @RequestLog
    public HttpResult<JSONObject> login(@RequestBody JSONObject loginInfo) {
        return loginService.login(loginInfo.getString("loginId"), loginInfo.getString("password"));
    }

    /**
     * 注销
     * @return
     */
    @GetMapping("/logout")
    @RequestLog
    public HttpResult<?> logout(){
        HttpServletRequest request = ServletUtils.getRequest();
        SysUser user = CurrentUserContextHolder.getCurrentUser();
        SysLogininfor sysLogininfor = new SysLogininfor(request,user.getLoginId());
        String tokenKey = user.getTokenKey();
        sysLogininfor.setTokenKey(tokenKey);
        if(tokenKey!=null && redisUtil.hasKey(tokenKey)){
            redisUtil.del(tokenKey);
            sysLogininfor.setStatusAndMsg("0","退出成功");
            return HttpResult.success();
        }
        sysLogininfor.setStatusAndMsg("1","登出失败");
        return HttpResult.error("登出失败!");
    }

    /**
     * 刷新token
     * author: huwei
     */
    @GetMapping(value = "/refreshToken")
    @RequestLog
    public HttpResult<String> refreshToken(){
        String tokenKey = CurrentUserContextHolder.getCurrentUser().getTokenKey();
        String accessToken = null;
        try {
            accessToken = tokenService.refreshToken(tokenKey);
        } catch (Exception e) {
            return HttpResult.error(StatusCodeEnum.TOKENREFRESHFAILD,e.getMessage());
        }
        return HttpResult.success("success!",accessToken);
    }

    @GetMapping(value = "/sendCodeToMail")
    @RequestLog
    public HttpResult<?> sendCodeToMail(String loginId,String mail){
        if (userService.validateMail(loginId,mail)){
            try{
                String code = String.valueOf((int)((Math.random()*9+1)*100000));
                mailUtils.sendCodeWithHTML(mail,code);
                String codeKey = GlobalParams.RESET_CODE_PREFIX + loginId;
                redisUtil.set(codeKey,code,GlobalParams.MAIL_CODE_EXPIRE_TIME);
                return HttpResult.success("已向"+mail+"发送验证码");
            }
            catch (Exception e){
                return HttpResult.error(StatusCodeEnum.RESETFAILED,e.getMessage());
            }
        }
        return HttpResult.error(StatusCodeEnum.MAILVALIEDFAILED);
    }

    @RequestMapping(value="/testToken",method = RequestMethod.POST)
    @RequestLog
    public HttpResult<?> test(@RequestBody Map<String,Object> req) {
//        System.out.println(req.getHeader("Authorization"));
//        ApplicationContext applicationContext = SpringBeanUtil.getApplicationContext();
//        Arrays.stream(applicationContext.getBeanDefinitionNames()).forEach(c-> System.out.println(c));
        return HttpResult.success();
    }


}
