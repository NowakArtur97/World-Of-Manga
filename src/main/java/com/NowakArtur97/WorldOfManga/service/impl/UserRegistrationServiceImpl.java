package com.NowakArtur97.WorldOfManga.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.NowakArtur97.WorldOfManga.dto.UserDTO;
import com.NowakArtur97.WorldOfManga.exception.RoleNotFoundException;
import com.NowakArtur97.WorldOfManga.mapper.user.UserMapper;
import com.NowakArtur97.WorldOfManga.model.User;
import com.NowakArtur97.WorldOfManga.repository.neo4j.UserNeo4jRepository;
import com.NowakArtur97.WorldOfManga.service.api.RoleService;
import com.NowakArtur97.WorldOfManga.service.api.UserRegistrationService;
import com.NowakArtur97.WorldOfManga.service.api.UserService;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

	private final RoleService roleService;

	private final UserMapper userMapper;

	private final UserService userService;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	private final UserNeo4jRepository userNeo4jRepository;

	@Autowired
	public UserRegistrationServiceImpl(RoleService roleService, UserMapper userMapper, UserService userService,
			BCryptPasswordEncoder bCryptPasswordEncoder, UserNeo4jRepository userNeo4jRepository) {
		this.roleService = roleService;
		this.userMapper = userMapper;
		this.userService = userService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userNeo4jRepository = userNeo4jRepository;
	}

	@Override
	public User registerUser(UserDTO userDTO) throws RoleNotFoundException {

		User userToRegister = userMapper.mapUserDTOToUser(userDTO);

		userToRegister.addRole(roleService.findByName("ROLE_USER"));
		userToRegister.setEnabled(true);

		String hashedPassword = bCryptPasswordEncoder.encode(userToRegister.getPassword());
		userToRegister.setPassword(hashedPassword);

		userService.save(userToRegister);

		com.NowakArtur97.WorldOfManga.model.neo4j.User userNeo4j = new com.NowakArtur97.WorldOfManga.model.neo4j.User(
				userToRegister.getUsername());
		userNeo4jRepository.save(userNeo4j);

		return userToRegister;
	}
}
