package com.y.uc.util;


import com.y.uc.model.dto.Principal;

/**
 * Created by zhangyong on 2017/7/21.
 */
public class PrincipalUtil {

    private PrincipalUtil() {

    }

    private static final ThreadLocal<Principal> PRINCIPAL = new ThreadLocal<>();

    public static Principal getPrincipal() {
        return PRINCIPAL.get();
    }

    public static void setPrincipal(Principal principal) {
        PRINCIPAL.set(principal);
    }

}
