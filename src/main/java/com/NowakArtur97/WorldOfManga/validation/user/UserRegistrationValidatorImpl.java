package com.NowakArtur97.WorldOfManga.validation.user;

import org.springframework.stereotype.Component;

import com.NowakArtur97.WorldOfManga.exception.UserAlreadyExistsException;
import com.NowakArtur97.WorldOfManga.service.api.UserService;

@Component
public class UserRegistrationValidatorImpl implements UserRegistrationValidator {

	private final UserService userService;

	public UserRegistrationValidatorImpl(UserService userService) {
		this.userService = userService;
	}

	@Override
	public boolean valdiateUsersUsername(String username) throws UserAlreadyExistsException {

		userService.findByUserName(username);

		throw new UserAlreadyExistsException("User " + username + " already exists");
	}

}
