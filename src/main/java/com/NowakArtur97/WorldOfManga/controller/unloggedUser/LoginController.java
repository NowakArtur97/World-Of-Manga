package com.NowakArtur97.WorldOfManga.controller.unloggedUser;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/user")
public class LoginController {

    @GetMapping(path = "/login")
    public String showLoginPage() {

        return "views/user-login";
    }
}
