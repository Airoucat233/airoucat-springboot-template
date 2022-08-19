package com.airoucat.airoucatspringboottemplate.handler;

import com.airoucat.airoucatspringboottemplate.domain.SysUser;
import com.airoucat.airoucatspringboottemplate.global.CurrentUserContextHolder;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.injector.AbstractSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert field....");
        SysUser currentUser = CurrentUserContextHolder.getCurrentUser();
        String operator = currentUser==null?"system":currentUser.getName();
//        this.strictUpdateFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
//        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.setFieldValByName("createTime", LocalDateTime.now(),metaObject);
        this.setFieldValByName("updateTime", LocalDateTime.now(),metaObject);
        this.setFieldValByName("createBy", operator,metaObject);
        this.setFieldValByName("updateBy", operator,metaObject);
        this.setFieldValByName("status", "0",metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update field....");
        SysUser currentUser = CurrentUserContextHolder.getCurrentUser();
        String operator = currentUser==null?"system":currentUser.getName();
        this.setFieldValByName("updateTime", LocalDateTime.now(),metaObject);
        this.setFieldValByName("updateBy", operator,metaObject);
    }
}