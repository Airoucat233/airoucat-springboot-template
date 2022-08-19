package com.airoucat.airoucatspringboottemplate.global;

import com.airoucat.airoucatspringboottemplate.domain.SysUser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CurrentUserContextHolder {
    private static final ThreadLocal<SysUser> USER_CONTEXT_HOLDER = new ThreadLocal<>();
//    private static final ThreadLocal<LoginInfo> LOGIN_INFO = new ThreadLocal<>();
    /**
     * 设置当前操作用户
     */
    public static void setCurrentUser(SysUser user)
    {
        USER_CONTEXT_HOLDER.set(user);
    }
    /**
     * 设置当前登录tokenKey
     */
//    public static void setCurrentLoginInfo(LoginInfo info)
//    {
//        LOGIN_INFO.set(info);
//    }

    /**
     * 获得当前操作用户
     */
    public static SysUser getCurrentUser()
    {
        return USER_CONTEXT_HOLDER.get();
    }
    /**
     * 获取当前登录tokenKey
     */
//    public static LoginInfo getCurrentLoginInfo()
//    {
//        return LOGIN_INFO.get();
//    }

    /**
     * 清空当前操作用户
     */
    public static void clearCurrentUser()
    {
        USER_CONTEXT_HOLDER.remove();
//        LOGIN_INFO.remove();
    }
}
