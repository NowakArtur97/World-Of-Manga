package com.NowakArtur97.WorldOfManga.validation.user;

import com.NowakArtur97.WorldOfManga.dto.UserDTO;
import com.NowakArtur97.WorldOfManga.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UserRegistrationValidator implements Validator {

    private final UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {

        return UserDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        UserDTO user = (UserDTO) target;

        boolean isUsernameInUse = userService.isUsernameAlreadyInUse(user.getUsername());

        boolean isEmailInUse = userService.isEmailAlreadyInUse(user.getEmail());

        if (isUsernameInUse) {

            errors.rejectValue("username", "user.username.inUse");
        }

        if (isEmailInUse) {

            errors.rejectValue("email", "user.email.inUse");
        }
    }
}
