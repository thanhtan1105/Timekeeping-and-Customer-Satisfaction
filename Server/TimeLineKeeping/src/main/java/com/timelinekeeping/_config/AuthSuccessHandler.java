package com.timelinekeeping._config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lethanhtan on 9/18/16.
 */

@Configuration
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString();

        String targetUrl = "";
        if (role.contains("MANAGER")) {
            targetUrl = "/client/index";
        } else if (role.contains("EMPLOYEE")) {
            targetUrl = "/agency/index";
        } else if (role.contains("ADMIN")) {
            targetUrl = "";
        }
        return targetUrl;
    }
}
