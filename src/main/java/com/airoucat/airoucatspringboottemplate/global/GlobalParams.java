package com.airoucat.airoucatspringboottemplate.global;


import java.text.SimpleDateFormat;

public class GlobalParams {

	public static final String ORIGINAL_SECRET = "qjyy_xxb";
	public static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final long ACCESS_TOKEN_EXPIRE_TIME = 60*60L;// 1小时
	public static final long REFRESH_TOKEN_EXPIRE_TIME = 60*60*24*30L;//30天
	public static final String LOGIN_TOKEN_PREFIX = "login_tokens:";
	public static final String RESET_CODE_PREFIX = "reset_codes:";
	//邮箱验证码失效时间
	public static final long MAIL_CODE_EXPIRE_TIME = 10*60L;//10分钟
	public static final String MAIL_CODE_SECRET_KEY = "mail_codes111111";//邮箱验证码的AES密钥(16字节)
	public static final String TOKEN_SECRET_KEY = "login_tokens1111";//token的AES密钥(16字节)

	//初始密码
    public static final String INITIAL_PASSWORD = "123456";

    // 防重提交 redis key
	public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";
}
