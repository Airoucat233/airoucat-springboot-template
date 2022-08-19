package com.airoucat.airoucatspringboottemplate.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * 拆分工具类
 * author: huwei
 */
@Slf4j
public class SplitUtils {
    /**
     * 从原型对象中分离出新的对象
     * author: huwei
     */ 
    public static <T> T splitPart(Object of,Class<T> to){
        T target;
        try {
            target = to.newInstance();
        } catch (Exception e) {
            log.error(to.getSimpleName()+"实例化失败:"+e.getMessage());
            return null;
        }
        Field[] fields = to.getDeclaredFields();
        for (Field tField : fields){
            try {
                Field pField = of.getClass().getDeclaredField(tField.getName());
                tField.setAccessible(true);
                pField.setAccessible(true);
                tField.set(target,pField.get(of));
            } catch (IllegalAccessException | NoSuchFieldException e) {
                log.warn("字段 "+tField.getName()+"复制失败:"+e.getMessage());
            }
        }
        return target;
    }
}
