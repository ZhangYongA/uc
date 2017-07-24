package com.y.uc.filter;

import com.y.uc.dto.Principal;
import com.y.uc.dto.SimpleUser;
import com.y.uc.service.UserService;
import com.y.uc.util.SpringUtil;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.y.uc.util.PrincipalUtil.setPrincipal;

/**
 * Created by zhangyong on 2017/7/21.
 */
public class PrincipalFilter implements Filter {

    private final String AUTHORIZATION_HEADER_NAME = "Authorization";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader(AUTHORIZATION_HEADER_NAME);
        SimpleUser su = SpringUtil.getBean(UserService.class).getByToken(token);
        if (su != null) {
            Principal principal = new Principal();
            principal.setId(su.getId());
            setPrincipal(principal);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
