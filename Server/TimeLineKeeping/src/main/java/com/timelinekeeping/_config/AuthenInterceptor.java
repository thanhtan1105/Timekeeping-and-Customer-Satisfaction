package com.timelinekeeping._config;

import com.timelinekeeping.constant.I_URI;
import com.timelinekeeping.model.AccountModel;
import com.timelinekeeping.model.RoleAuthen;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

/**
 * Created by HienTQSE60896 on 10/10/2016.
 */
@Component
public class AuthenInterceptor extends HandlerInterceptorAdapter {
    private static final List<String> IGNORED_PATHS = Arrays
            .asList("/",
                    "/login",
                    "/js/**",
                    "/css/**",
                    "/img/**",
                    "/font/**",
                    "/login/**",
                    "/error/**");

    private static final PathMatcher pathMatcher = new AntPathMatcher();

    private boolean isMatched(final String pattern, final String lookupPath) {
        if (pattern.equals(lookupPath)) {
            return true;
        }
        if (this.pathMatcher.match(pattern, lookupPath)) {
            return true;
        }
        if (!pattern.endsWith("/**") && !pattern.endsWith("/")
                && this.pathMatcher.match(pattern + "/", lookupPath)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("AuthenLogin");
        String url = request.getRequestURI();
        for (String parten : this.IGNORED_PATHS) {
            if (isMatched(parten, url)) {
                return true;
            }
        }
        //TODO Authen api
        if (isMatched(I_URI.API +"/**", url) ||isMatched("/api_mcs/**", url)){
            return true;
        }
        HttpSession session = request.getSession();
        AccountModel accountModel = (AccountModel) session.getAttribute(I_URI.SESSION_AUTHEN);
        if (accountModel == null) {
            response.sendRedirect(I_URI.WEB_URI_LOGIN);
            return false;
        } else {
            List<String> allows = ((RoleAuthen) accountModel.getRole()).getAllows();
            for (String allow : allows) {
                if (isMatched(allow, url)) {
                    return true;
                }
            }
            response.sendRedirect(I_URI.WEB_ERROR_PERMISSION);
            return false;
        }
    }
}
