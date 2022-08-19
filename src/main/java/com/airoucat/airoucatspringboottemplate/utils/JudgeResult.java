package com.airoucat.airoucatspringboottemplate.utils;

import lombok.Data;

/**
 * 判断结果封装类
 */
@Data
public class JudgeResult<T> {
    private int code;
    private String msg;
    private T result;

    //期待的结果
    public static <E> JudgeResult<E> expect(E result){
        JudgeResult<E> jresult = new JudgeResult<>();
        jresult.setCode(0);
        jresult.setResult(result);
        return jresult;
    }
    //错误的结果
    @SuppressWarnings(value = {"rawtypes","unchecked"})
    public static <E> JudgeResult<E> error(){
        JudgeResult jresult = new JudgeResult();
        jresult.setCode(-1);
        return jresult;
    }

    //意外的结果
    @SuppressWarnings(value = {"rawtypes","unchecked"})
    public static <E> JudgeResult<E> except(int code,String msg){
        JudgeResult jresult = new JudgeResult<>();
        jresult.setCode(code);
        jresult.setMsg(msg);
        return jresult;
    }

}
