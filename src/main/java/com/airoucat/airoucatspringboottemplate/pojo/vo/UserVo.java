package com.airoucat.airoucatspringboottemplate.pojo.vo;
import lombok.Data;

import java.util.Set;

@Data
public class UserVo {
    /**
     * ID
     */
    private Long userId;
    /**
     * loginid
     */
    private String loginId;
    /**
     * 用户账号
     */
    private String userName;
    /**
     * 姓名
     */
    private String name;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 手机号码
     */
    private String phonenumber;
    /**
     * 用户性别（0男 1女 2未知）
     */
    private String sex;
    /**
     * 帐号状态（0正常 1停用）
     */
    private String status;

    private Set<String> roles;
}
