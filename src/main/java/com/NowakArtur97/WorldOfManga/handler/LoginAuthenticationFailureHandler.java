package com.NowakArtur97.WorldOfManga.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		if (exception instanceof BadCredentialsException) {
			response.sendRedirect(request.getContextPath() + "/user/login?badCredentials=true");
		} else if (exception instanceof DisabledException) {
			response.sendRedirect(request.getContextPath() + "/user/login?accountDisabled=true");
		} else {
			response.sendRedirect(request.getContextPath() + "/user/login");
		}
	}

}
