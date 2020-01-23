package com.NowakArtur97.WorldOfManga.service.api;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.NowakArtur97.WorldOfManga.model.User;

public interface UserService extends UserDetailsService {

	Optional<User> findByUserName(String userName);
	
	User saveUser(User user);
}
