package com.NowakArtur97.WorldOfManga.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.NowakArtur97.WorldOfManga.exception.UserNotFoundException;
import com.NowakArtur97.WorldOfManga.model.User;
import com.NowakArtur97.WorldOfManga.service.api.UserService;

@Component
public class LoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private final UserService userService;

	@Autowired
	public LoginAuthenticationSuccessHandler(UserService userService) {

		this.userService = userService;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		String username = authentication.getName();

		User loggedUser = null;

		try {
			loggedUser = userService.findByUserName(username);
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}

		HttpSession session = request.getSession();
		session.setAttribute("user", loggedUser);

		response.sendRedirect(request.getContextPath() + "/");
	}

}