package com.airoucat.airoucatspringboottemplate.domain;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;

/**
 * 角色信息表(SysRole)实体类 表名:sys_role
 *
 * @author makejava
 * @since 2022-07-18 14:58:38
 */
@Data
public class SysRole implements Serializable {
    private static final long serialVersionUID = -77159441425796106L;
    /**
    * 角色ID
    */
    private Long roleId;
    /**
    * 角色名称
    */
    private String roleName;
    /**
    * 角色权限字符串
    */
    private String roleKey;
    /**
    * 角色状态（0正常 1停用）
    */
    private String status;
    /**
    * 创建者
    */
    private String createBy;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 更新者
    */
    private String updateBy;
    /**
    * 更新时间
    */
    private Date updateTime;
    /**
    * 备注
    */
    private String remark;

}