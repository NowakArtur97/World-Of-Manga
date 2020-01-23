package com.NowakArtur97.WorldOfManga.service.impl;

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

	public UserRegistrationServiceImpl(RoleService roleService, UserMapper userMapper, UserService userService) {
		this.roleService = roleService;
		this.userMapper = userMapper;
		this.userService = userService;
	}

	@Override
	public User registerUser(UserDTO userDTO) {

		User userToRegister = userMapper.mapUserDTOToUser(userDTO);

		userToRegister.addRole(roleService.findRoleByName("ROLE_USER").get());
		userToRegister.setEnabled(true);

		userService.saveUser(userToRegister);

		return userToRegister;
	}
}
