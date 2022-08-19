package com.airoucat.airoucatspringboottemplate.utils;


import com.airoucat.airoucatspringboottemplate.enums.StatusCodeEnum;
import lombok.Data;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.Serializable;
import java.util.Map;

/**
 * 统一请求返回格式 {"code":0,"message":"success","data":{}}
 */
@Data
public class HttpResult<T> implements Serializable{
    private int code;
    private String message;
    private T data;

    private HttpResult() {
    }

    @SuppressWarnings("unchecked")
    public void putToMap(String key, Object value){
        if (this.data instanceof Map){
            ((Map<String, Object>) this.data).put(key,value);
            return;
        }
        throw new ClassCastException(data.toString()+"不是Map类型!");
    }
    public static <T> HttpResult<T> success(T data) {
        HttpResult<T> resultBean = new HttpResult<>();
        resultBean.setCode(0);
        resultBean.setMessage("success");
        resultBean.setData(data);
        return resultBean;
    }

    public static <T> HttpResult<T> success() {
        HttpResult<T> resultBean = new HttpResult<>();
        resultBean.setCode(0);
        resultBean.setMessage("success!");
        return resultBean;
    }

    public static <T> HttpResult<T> success(String message) {
        HttpResult<T> resultBean = new HttpResult<>();
        resultBean.setCode(0);
        resultBean.setMessage(message);
        resultBean.setData(null);
        return resultBean;
    }

    public static <T> HttpResult<T> success(String message,T data) {
        HttpResult<T> resultBean = new HttpResult<>();
        resultBean.setCode(0);
        resultBean.setMessage(message);
        resultBean.setData(data);
        return resultBean;
    }

    public static <T> HttpResult<T> error(String message) {
        HttpResult<T> resultBean = new HttpResult<>();
        resultBean.setCode(-1);
        resultBean.setMessage(message);
        return resultBean;
    }

    public static <T> HttpResult<T> error(int code, String message) {
        HttpResult<T> resultBean = new HttpResult<>();
        resultBean.setCode(code);
        resultBean.setMessage(message);
        return resultBean;
    }
    public static <T> HttpResult<T> error(StatusCodeEnum sc) {
        HttpResult<T> resultBean = new HttpResult<>();
        resultBean.setCode(sc.getCode());
        resultBean.setMessage(sc.getMsg());
        return resultBean;
    }

    public static <T> HttpResult<T> error(StatusCodeEnum sc,String exMessage) {
        HttpResult<T> resultBean = new HttpResult<>();
        resultBean.setCode(sc.getCode());
        resultBean.setMessage(sc.getMsg()+" : "+exMessage);
        return resultBean;
    }

    public static <T> HttpResult<T> warning(String message,T data) {
        HttpResult<T> resultBean = new HttpResult<>();
        resultBean.setCode(1);
        resultBean.setMessage(message);
        resultBean.setData(data);
        return resultBean;
    }

    public static <T> HttpResult<T> warning(String message) {
        HttpResult<T> resultBean = new HttpResult<>();
        resultBean.setCode(1);
        resultBean.setMessage(message);
        return resultBean;
    }

    @Override
    public String toString() {
        return "{" +
                "\"code\":" + code +
                ", \"message\":\"" + message + '\"' +
                ", \"data\":" + data +
                "}";
    }
}
