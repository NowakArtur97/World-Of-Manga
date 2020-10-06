package com.NowakArtur97.WorldOfManga.controller.unloggedUser;

import com.NowakArtur97.WorldOfManga.dto.UserDTO;
import com.NowakArtur97.WorldOfManga.exception.RoleNotFoundException;
import com.NowakArtur97.WorldOfManga.service.UserRegistrationService;
import com.NowakArtur97.WorldOfManga.validation.user.UserRegistrationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserRegistrationService userRegistrationService;

    private final UserRegistrationValidator userRegistrationValidator;

    @GetMapping(path = "/register")
    public String showRegistrationPage(Model theModel) {

        theModel.addAttribute("userDTO", new UserDTO());

        return "views/user-registration";
    }

    @PostMapping(path = "/register")
    public String processUserRegistration(Model theModel, @ModelAttribute("userDTO") @Valid UserDTO userDTO,
                                          BindingResult result) throws RoleNotFoundException {

        userRegistrationValidator.validate(userDTO, result);

        if (result.hasErrors()) {

            theModel.addAttribute("userDTO", userDTO);

            return "views/user-registration";
        }

        userRegistrationService.registerUser(userDTO);

        theModel.addAttribute("afterRegistration", "Success");

        return "views/user-login";
    }
}
