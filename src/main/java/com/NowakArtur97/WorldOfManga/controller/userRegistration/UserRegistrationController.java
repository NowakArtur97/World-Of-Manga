package com.NowakArtur97.WorldOfManga.controller.userRegistration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/user")
public class UserRegistrationController {

	@GetMapping(path = "/register")
	public String showRegistrationPage() {

		return "views/user-registration";
	}
}
