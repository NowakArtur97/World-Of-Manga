package com.NowakArtur97.WorldOfManga.configuration.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
class LoginAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        if (exception instanceof BadCredentialsException) {
            response.sendRedirect(request.getContextPath() + "/user/login?badCredentials=true");
        } else if (exception instanceof DisabledException) {
            response.sendRedirect(request.getContextPath() + "/user/login?accountDisabled=true");
        } else {
            response.sendRedirect(request.getContextPath() + "/user/login");
        }
    }
}
