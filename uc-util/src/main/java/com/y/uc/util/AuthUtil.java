package com.y.uc.util;

/**
 * Created by zhangyong on 2017/7/25.
 */
public class AuthUtil {

    public static boolean isLogin() {
        return PrincipalUtil.getPrincipal() != null;
    }

}
