package com.airoucat.airoucatspringboottemplate.aop.annotation;

import java.lang.annotation.*;

/**
 * @Desc:统一生成方法访问日志
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RequestLog {

}
