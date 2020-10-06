package com.NowakArtur97.WorldOfManga.validation.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.NowakArtur97.WorldOfManga.dto.UserDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
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
