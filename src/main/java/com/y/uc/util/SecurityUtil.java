package com.y.uc.util;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by zhangyong on 2017/7/20.
 */
public class SecurityUtil {

    /**
     * 使用 md5 进行加密
     * @param text 原文
     * @return 密文
     * @throws UnsupportedEncodingException
     */
    public static String encryption(String text) throws UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ignored) {
            return "";
        }
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        return base64en.encode(md5.digest(text.getBytes("utf-8")));
    }

}
