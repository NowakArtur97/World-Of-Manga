package com.NowakArtur97.WorldOfManga.validation.user;

import org.springframework.stereotype.Component;

import com.NowakArtur97.WorldOfManga.dto.UserDTO;
import com.NowakArtur97.WorldOfManga.exception.EmailAlreadyInUseException;
import com.NowakArtur97.WorldOfManga.exception.UsernameAlreadyInUseException;
import com.NowakArtur97.WorldOfManga.service.api.UserService;

@Component
public class UserRegistrationValidatorImpl implements UserRegistrationValidator {

	private final UserService userService;

	public UserRegistrationValidatorImpl(UserService userService) {
		this.userService = userService;
	}

	@Override
	public boolean valdiateUser(UserDTO user) throws UsernameAlreadyInUseException, EmailAlreadyInUseException {

		boolean isUsernameInUse = userService.isUsernameAlreadyInUse(user.getUsername());
		
		boolean isEmailInUse = userService.isEmailAlreadyInUse(user.getEmail());

		
		if (!isUsernameInUse) {
			
			throw new UsernameAlreadyInUseException("Username " + user.getUsername() + " already in use");
		}
		
		if (!isEmailInUse) {
			
			throw new EmailAlreadyInUseException("Email " + user.getEmail() + " already in use");
		}

		
		return true;
	}

}
