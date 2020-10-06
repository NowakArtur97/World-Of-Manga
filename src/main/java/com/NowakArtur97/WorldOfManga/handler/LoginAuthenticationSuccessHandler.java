package com.NowakArtur97.WorldOfManga.handler;

import com.NowakArtur97.WorldOfManga.model.User;
import com.NowakArtur97.WorldOfManga.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@Component
public class LoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;

    @Autowired
    public LoginAuthenticationSuccessHandler(UserService userService) {

        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        String username = authentication.getName();

        Optional<User> loggedUserOptional = userService.findByUsername(username);

        if (loggedUserOptional.isPresent()) {

            User loggedUser = loggedUserOptional.get();

            HttpSession session = request.getSession();
            session.setAttribute("user", loggedUser);
        }

        response.sendRedirect(request.getContextPath() + "/");
    }
}
