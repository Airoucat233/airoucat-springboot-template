package com.airoucat.airoucatspringboottemplate.enums;


/**
 * 正则表达式枚举
 */
public enum RegexEnum {
    USERNAME("^\\w[\\w_]{7,14}$","用户名8-15位,数字或字母开头,数字字母或下划线组成"),
    EMAIL("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$","邮箱地址"),
    PHONE("^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$","手机号码"),
    USERPASSWORD("^(?=.*[a-zA-Z])(?=.*\\d+)(?=.*[\\.!@#])[a-zA-Z][\\w\\.!@#]{7,14}$","密码8-15位,字母开头,必须包含数字字母和[.!@#]中至少一个");

    private final String regex;
    private final String desc;

    RegexEnum(String regex, String desc) {
        this.regex = regex;
        this.desc = desc;
    }

    public String getRegex() {
        return regex;
    }

    public String getDesc() {
        return desc;
    }

    public static boolean contains(String s){
        for (RegexEnum r : values()){
            if (r.name().equals(s)){
                return true;
            }
        }
        return false;
    }
    public static String parse(String username) {
        for (RegexEnum r : RegexEnum.values()){
            if (username.matches(r.getRegex())){
                return r.name();
            }
        }
        return null;
    }
}
