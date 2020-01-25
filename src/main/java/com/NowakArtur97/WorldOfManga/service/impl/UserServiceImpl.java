package com.NowakArtur97.WorldOfManga.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.NowakArtur97.WorldOfManga.model.Role;
import com.NowakArtur97.WorldOfManga.model.User;
import com.NowakArtur97.WorldOfManga.repository.UserRepository;
import com.NowakArtur97.WorldOfManga.service.api.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Optional<User> findByUsername(String username) {

		return userRepository.findByUsername(username);
	}

	@Override
	public boolean isUsernameAlreadyInUse(String username) {

		return userRepository.existsUserByUsername(username);
	}

	@Override
	public boolean isEmailAlreadyInUse(String email) {

		return userRepository.existsUserByEmail(email);
	}

	@Override
	public User saveUser(User user) {

		userRepository.save(user);

		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Invalid username or password"));

		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				user.isEnabled(), accountNonExpired, credentialsNonExpired, accountNonLocked,
				mapRolesToAuthorities(user.getRoles()));

	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> usersRoles) {

		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

		for (Role role : usersRoles) {
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
			authorities.add(authority);
		}

		return authorities;
	}
}