package com.NowakArtur97.WorldOfManga.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.NowakArtur97.WorldOfManga.dto.UserDTO;
import com.NowakArtur97.WorldOfManga.mapper.UserMapper;
import com.NowakArtur97.WorldOfManga.model.User;
import com.NowakArtur97.WorldOfManga.service.api.RoleService;
import com.NowakArtur97.WorldOfManga.service.api.UserRegistrationService;
import com.NowakArtur97.WorldOfManga.service.api.UserService;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

	private final RoleService roleService;

	private final UserMapper userMapper;

	private final UserService userService;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public UserRegistrationServiceImpl(RoleService roleService, UserMapper userMapper, UserService userService,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.roleService = roleService;
		this.userMapper = userMapper;
		this.userService = userService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public User registerUser(UserDTO userDTO) {

		User userToRegister = userMapper.mapUserDTOToUser(userDTO);

		userToRegister.addRole(roleService.findByName("ROLE_USER").get());
		userToRegister.setEnabled(true);
		userToRegister.setPassword(bCryptPasswordEncoder.encode(userToRegister.getPassword()));

		userService.save(userToRegister);

		return userToRegister;
	}
}
