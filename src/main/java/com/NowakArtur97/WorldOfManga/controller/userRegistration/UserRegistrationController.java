package com.NowakArtur97.WorldOfManga.controller.userRegistration;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.NowakArtur97.WorldOfManga.dto.UserDTO;

@Controller
@RequestMapping(path = "/user")
public class UserRegistrationController {

	@GetMapping(path = "/register")
	public String showRegistrationPage(Model theModel) {

		theModel.addAttribute("userDTO", new UserDTO());

		return "views/user-registration";
	}

	@PostMapping(path = "/processRegister")
	public String processUserRegistration(Model theModel, @ModelAttribute("userDTO") @Valid UserDTO userDTO,
			BindingResult result) {

		if (result.hasErrors()) {

			theModel.addAttribute("userDTO", userDTO);

			return "views/user-registration";
		}

		return "views/user-login";
	}

}
