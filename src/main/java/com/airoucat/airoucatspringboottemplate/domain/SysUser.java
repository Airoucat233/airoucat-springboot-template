package com.airoucat.airoucatspringboottemplate.domain;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Set;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * 用户信息表(SysUser)实体类 表名:sys_user
 *
 * @author makejava
 * @since 2022-06-22 09:15:43
 */
@Data
@TableName(value = "sys_user")
public class SysUser implements Serializable {
    private static final long serialVersionUID = 770446634805607784L;
    /**
    * ID
    */
    @TableId(type = IdType.AUTO)
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
    * 密码
    */
    private String password;
    /**
     * OAID
     */
    private Long oaid;
    /**
     * 英克ID
     */
    private Long ykid;
    /**
     * 英克部门ID
     */
    private Long ykdptid;
    /**
     * 英克部门名称
     */
    private String dptname;
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
    /**
    * 创建者
    */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
    /**
    * 创建时间
    */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
    * 更新者
    */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;
    /**
    * 更新时间
    */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    /**
    * 备注
    */
    private String remark;

    /**
     * 角色列表
     * author: huwei
     */
    private Set<String> roles;

    /**
     * tokenKey
     * author: huwei
     */
    private String tokenKey;
}