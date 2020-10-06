package com.NowakArtur97.WorldOfManga.feature.user;

import com.NowakArtur97.WorldOfManga.exception.RoleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
class UserController {

    private final UserRegistrationService userRegistrationService;

    private final UserRegistrationValidator userRegistrationValidator;

    @GetMapping(path = "/user/login")
    String showLoginPage() {

        return "views/user-login";
    }

    @GetMapping(path = "/user/register")
    String showRegistrationPage(Model theModel) {

        theModel.addAttribute("userDTO", new UserDTO());

        return "views/user-registration";
    }

    @PostMapping(path = "/user/register")
    String processUserRegistration(Model theModel, @ModelAttribute("userDTO") @Valid UserDTO userDTO,
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

    @GetMapping(path = "/auth/logout")
    String logoutUser(HttpServletRequest request) {

        SecurityContextHolder.clearContext();

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        for (Cookie cookie : request.getCookies()) {
            cookie.setMaxAge(0);
        }

        return "redirect:/";
    }
}
