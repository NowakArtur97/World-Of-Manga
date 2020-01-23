package com.NowakArtur97.WorldOfManga.validation.user;

import com.NowakArtur97.WorldOfManga.exception.UserAlreadyExistsException;

public interface UserRegistrationValidator {

	boolean valdiateUsersUsername(String username) throws UserAlreadyExistsException;
}
