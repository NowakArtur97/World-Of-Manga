package com.NowakArtur97.WorldOfManga.controller.user;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/auth")
public class LogoutController {

	@GetMapping(path = "/logout")
	public String logoutUser(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		SecurityContextHolder.clearContext();
		
		session = request.getSession(false);
		
		if (session != null) {
			session.setAttribute("user", null);
		}
		
		for(Cookie cookie : request.getCookies()) {
			cookie.setMaxAge(0);
		}
		
		return "redirect:/";
	}
}
