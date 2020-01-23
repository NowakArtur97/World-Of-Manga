package com.NowakArtur97.WorldOfManga.service.api;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.NowakArtur97.WorldOfManga.exception.UserNotFoundException;
import com.NowakArtur97.WorldOfManga.model.User;

public interface UserService extends UserDetailsService {

	User findByUserName(String userName) throws UserNotFoundException;
	
	User saveUser(User user);
}
