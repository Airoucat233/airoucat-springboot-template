package com.airoucat.airoucatspringboottemplate.enums;

public enum DataScopeType {
    /**
     * 部门
     */
    DEPARTMENTAL("ykdptid"),

    /**
     * 个人
     */
    PERSONAL("loginid");

    //SysUser对象里该权限域字段名称
    private final String fieldName;

    DataScopeType(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
