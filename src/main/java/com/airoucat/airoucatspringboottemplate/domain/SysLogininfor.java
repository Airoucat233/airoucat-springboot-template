package com.airoucat.airoucatspringboottemplate.domain;

import java.util.Date;
import java.io.Serializable;
import java.util.Optional;

import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统访问记录(SysLogininfor)实体类 表名:sys_logininfor
 *
 * @author makejava
 * @since 2022-07-19 17:38:01
 */
@Data
public class SysLogininfor implements Serializable {
    private static final long serialVersionUID = -18505081771036744L;
    /**
    * 访问ID
    */
    private Long infoId;
    /**
    * 用户账号
    */
    private String loginId;
    /**
    * tokenID
    */
    private String tokenKey;
    /**
    * 登录IP地址
    */
    private String ipaddr;
    /**
    * 登录地点
    */
    private String loginLocation;
    /**
    * 浏览器类型
    */
    private String browser;
    /**
    * 操作系统
    */
    private String os;
    /**
    * 登录状态（0成功 1失败）
    */
    private String status;
    /**
    * 提示消息
    */
    private String msg;
    /**
    * 访问时间
    */
    private Date loginTime;

    public SysLogininfor(HttpServletRequest request,String loginId){
        setLoginId(loginId);
        setIpaddr(request.getRemoteAddr());
//        String userAgentStr = request.getHeader("User-Agent") + " ";
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        String browser = userAgent.getBrowser().toString();
        browser += Optional.ofNullable(userAgent.getBrowserVersion()).orElse(new Version("","","")).toString();
        setBrowser(browser);
        setOs(userAgent.getOperatingSystem().toString());
        setLoginTime(new Date());
    }
    public void setStatusAndMsg(String status,String msg){
        setStatus(status);
        setMsg(msg);
    }
}