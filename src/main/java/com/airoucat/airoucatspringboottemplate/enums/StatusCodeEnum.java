package com.airoucat.airoucatspringboottemplate.enums;


/**
 * 接口回调状态码枚举
 * 100-199: 用户相关
 * 200-299：文件相关
 */
public enum StatusCodeEnum {
    SUCCESS(0,"success!"),
    UNKNOWERROR(-1,"error:未知错误!"),
    APPERROR(-2,"error:内部错误!"),
    PARAMETERERROR(-3,"error:参数错误!"),

    //登录相关
    USERINFOERROR(100,"用户名或密码错误!"),
    PASSWORDERROR(101,"密码错误!"),
    LOGINFAILED(102,"登录期间发生了错误,请重试或联系管理员"),
    ACCOUNTINFOFORMATERROR(103,"账号或密码格式错误!"),
    REGISTERTYPEERROR(104,"注册类型错误,请检查用户名格式(支持用户名、邮箱、手机号)!"),
    ACCOUNTREPEATED(105,"该用户名已存在!"),
    ACCOUNTNOEXIST(106,"该用户不存在!"),
    REGISTERFAILED(107,"注册失败!"),
    RESETFAILED(108,"重置密码失败!"),
    MAILVALIEDFAILED(109,"用户名或邮箱验证失败!"),
    VALIEDCODEERROR(110,"验证码不正确!"),
    VALIEDCODEEXPIRED(110,"验证码已过期,请重新获取!"),

    //文件相关
    FILEUPLOADFAILED(201,"error:文件上传失败!"),
    FILEPATHNOTEXIST(202,"error:文件路径不存在!"),
    FILEDELETEFAILED(203,"error:文件删除失败!"),

    //权限相关
    WITHOUTTOKEN(300,"没有token!"),
    INVALIDTOKEN(301,"token验证失败!"),
    TOKENEXPIRED(302,"token已失效!"),
    REFRESHTOKENEXPIRED(303,"refreshtoken已失效,请重新登录!"),
    TOKENREFRESHFAILD(304,"token刷新失败!"),
    TOKENGENERATEFAILD(305,"token生成失败!"),
    SC_UNAUTHORIZED(401,"SC_UNAUTHORIZED"),

    //用户相关
//    MODIFYFAILED(401,"用户信息修改失败!");
    FIRSTLOGINWARN(1001,"请设置一个新密码和一个邮箱(用于密码找回)!");
    private final int code;
    private final String msg;

    StatusCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
