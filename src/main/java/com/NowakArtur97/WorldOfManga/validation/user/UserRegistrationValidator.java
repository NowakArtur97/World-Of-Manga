package com.NowakArtur97.WorldOfManga.validation.user;

import com.NowakArtur97.WorldOfManga.dto.UserDTO;
import com.NowakArtur97.WorldOfManga.exception.UsernameAlreadyInUseException;

public interface UserRegistrationValidator {

	boolean valdiateUser(UserDTO user) throws UsernameAlreadyInUseException;
}
