package com.airoucat.airoucatspringboottemplate.filter;

import com.airoucat.airoucatspringboottemplate.domain.SysUser;
import com.airoucat.airoucatspringboottemplate.global.CurrentUserContextHolder;
import com.airoucat.airoucatspringboottemplate.global.GlobalParams;
import com.airoucat.airoucatspringboottemplate.service.TokenService;
import com.airoucat.airoucatspringboottemplate.utils.*;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.Claim;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@WebFilter(filterName = "JwtAuthFilter", urlPatterns = "/*", initParams = {
        @WebInitParam(name = "releaseAPIs", value = "/makePlan/getSteps;/login;/register;/sendCodeToMail;/user/resetPassword;/user/validMailCode"),
        @WebInitParam(name = "encoding", value = "utf-8")
})
public class JwtAuthFilter implements Filter {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private TokenService tokenService;

    //private FilterConfig filterConfig;
    private List<String> releaseAPIs;

    @Override
    public void init(FilterConfig filterConfig) {
        //this.filterConfig = filterConfig;
        this.releaseAPIs = Arrays.asList(filterConfig.getInitParameter("releaseAPIs").split(";"));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setCharacterEncoding("UTF-8");
        if (releaseAPIs.contains(request.getRequestURI()) || request.getRequestURI().contains("/wsc")){
            filterChain.doFilter(request, response);
            return;
        }
        //获取 header里的token
        final String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {//没有token
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return ;
        }
        JudgeResult<Map<String, Claim>> result = JwtUtils.verifyToken(token);
        if (result.getCode()==-1) {//token验证不通过
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        else if(result.getCode()==0) {//token正常
            Map<String, Claim> claimMap = result.getResult();
            //token是否在redis中
            String ecyTokenKey = claimMap.get("tokenKey").asString();
            String tokenKey = null;
            try {
                tokenKey = EncryptionUtil.AESdecrypt(ecyTokenKey, GlobalParams.TOKEN_SECRET_KEY);
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,e.getMessage());
            }
            boolean isRefresh = Boolean.parseBoolean(claimMap.get("isRefresh").asString());
            String tokenType = isRefresh? "refreshToken" : "accessToken";
            if (tokenService.verifyCacheToken(tokenKey,tokenType,token)) {
                CurrentUserContextHolder.setCurrentUser(JSONObject.toJavaObject((JSONObject) redisUtil.hget(tokenKey, "user"), SysUser.class));
//                CurrentUserContextHolder.setCurrentLoginInfo(JSONObject.toJavaObject((JSONObject) redisUtil.hget(tokenKey, "loginInfo"), LoginInfo.class));

            }
            else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }
        else if(result.getCode()==302) {
            //token无效或者已过期
//            response.getWriter().write(HttpResult.error(StatusCodeEnum.TOKENEXPIRED).toString());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        else {
            response.getWriter().write(HttpResult.error(result.getCode(),result.getMsg()).toString());
            return;
        }
        try {
            filterChain.doFilter(request, response);
        }
        finally {
            CurrentUserContextHolder.clearCurrentUser();
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
