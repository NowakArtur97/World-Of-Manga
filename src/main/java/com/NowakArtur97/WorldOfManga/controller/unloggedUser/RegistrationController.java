package com.NowakArtur97.WorldOfManga.controller.unloggedUser;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.NowakArtur97.WorldOfManga.dto.UserDTO;
import com.NowakArtur97.WorldOfManga.service.api.UserRegistrationService;
import com.NowakArtur97.WorldOfManga.validation.user.UserRegistrationValidator;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(path = "/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RegistrationController {

	private final UserRegistrationService userRegistrationService;

	private final UserRegistrationValidator userRegistrationValidator;

	@GetMapping(path = "/register")
	public String showRegistrationPage(Model theModel) {

		theModel.addAttribute("userDTO", new UserDTO());

		return "views/user-registration";
	}

	@PostMapping(path = "/processRegister")
	public String processUserRegistration(Model theModel, @ModelAttribute("userDTO") @Valid UserDTO userDTO,
			BindingResult result) {

		userRegistrationValidator.validate(userDTO, result);

		if (result.hasErrors()) {

			theModel.addAttribute("userDTO", userDTO);

			return "views/user-registration";
		}

		userRegistrationService.registerUser(userDTO);

		return "views/user-login";
	}

}
