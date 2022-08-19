package com.airoucat.airoucatspringboottemplate.aop.annotation;

import java.lang.annotation.*;

/**
 * @Desc:将该注解加在方法上计算方法的执行时间(不可作用于静态方法)
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CountTime {

}
