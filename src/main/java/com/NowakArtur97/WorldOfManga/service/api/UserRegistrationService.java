package com.NowakArtur97.WorldOfManga.service.api;

import com.NowakArtur97.WorldOfManga.dto.UserDTO;
import com.NowakArtur97.WorldOfManga.exception.RoleNotFoundException;
import com.NowakArtur97.WorldOfManga.model.User;

public interface UserRegistrationService {

	User registerUser(UserDTO userDTO) throws RoleNotFoundException;
}
