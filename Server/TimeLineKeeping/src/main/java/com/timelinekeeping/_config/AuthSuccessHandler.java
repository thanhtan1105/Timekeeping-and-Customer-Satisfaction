package com.timelinekeeping._config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * Created by lethanhtan on 9/18/16.
 */

@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains("admin")) {
            response.sendRedirect("/manager/check_in");
        }
    }
}

//        extends SavedRequestAwareAuthenticationSuccessHandler {
//
//    @Override
//    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String role = auth.getAuthorities().toString();
//
//        String targetUrl = "";
//        if (role.contains("manager")) {
//            targetUrl = "/manager/check_in/";
//        } else if (role.contains("employee")) {
//            targetUrl = "";
//        } else if (role.contains("admin")) {
//            targetUrl = "/admin/departments/";
//        }
//        return targetUrl;
//    }
//}
