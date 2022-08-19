package com.airoucat.airoucatspringboottemplate.utils;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.util.DigestUtils;

public class EncryptionUtil {
    public static String BCencrypt(String p){
        return BCrypt.hashpw(p, BCrypt.gensalt());
    }
    public static Boolean BCcheck(String p,String hashed){
        return BCrypt.checkpw(p, hashed);
    }

    public static String AESencrypt(String p,String key) throws Exception {
        AESUtils aes = new AESUtils(key);
        return aes.encryptData(p);
    }
    public static String AESdecrypt(String p,String key) throws Exception {
        AESUtils aes = new AESUtils(key);
        return aes.decryptData(p);
    }

    public static String getMD5(String p,String salt) {
        String base = p+salt;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }
}
