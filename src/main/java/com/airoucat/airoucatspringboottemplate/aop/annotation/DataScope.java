package com.airoucat.airoucatspringboottemplate.aop.annotation;


import com.airoucat.airoucatspringboottemplate.enums.DataScopeType;

import java.lang.annotation.*;

/**
 *
 * @author huwei
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataScope
{
    /**
     * 数据域类型
     */
    public DataScopeType type() default DataScopeType.DEPARTMENTAL;
    /**
     * 目标表格里标记该数据域的字段名
     */
    public String fieldName();
}
