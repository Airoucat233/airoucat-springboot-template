package com.airoucat.airoucatspringboottemplate.utils;

import com.alibaba.fastjson.JSONObject;
import com.qjyy.swjzgxweb.domain.SwjzgxSmallTarget;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ObjectUtils {
    /**
     * 获取对象指定属性的值
     * @param o  对象
     * @param fieldName   要获取值的属性
     * 返回值：对象指定属性的值
     */
    public static Object getFieldValueByName(Object o, String fieldName) {
        try {
            Field field = o.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(o);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 给对象指定的属性设置值
     * @param obj  要设置值的对象
     * @param fieldName   要设置值的属性
     * @param value 值
     */
    public static void setFieldValueByName(Object obj, String fieldName, Object value){
        try {
            // 获取obj类的字节文件对象
            Class c = obj.getClass();
            // 获取该类的成员变量
            Field f = c.getDeclaredField(fieldName);
            // 取消语言访问检查
            f.setAccessible(true);
            // 给变量赋值
            f.set(obj, value);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
    /**
     * 将Object对象里面的属性和值转化成Map对象
     *
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<String,Object>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = StringUtils.nvl(field.get(obj),null);
            map.put(fieldName, value);
        }
        return map;
    }

    public static <T> T mapToObject(Map map,Class<T> clazz){
        return JSONObject.parseObject(JSONObject.toJSONString(map), clazz);
    }
}
