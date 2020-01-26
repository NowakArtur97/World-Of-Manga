package com.NowakArtur97.WorldOfManga.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.NowakArtur97.WorldOfManga.dto.UserDTO;
import com.NowakArtur97.WorldOfManga.dto.UserPasswordDTO;
import com.NowakArtur97.WorldOfManga.mapper.UserMapper;
import com.NowakArtur97.WorldOfManga.model.Role;
import com.NowakArtur97.WorldOfManga.model.User;
import com.NowakArtur97.WorldOfManga.service.api.RoleService;
import com.NowakArtur97.WorldOfManga.service.api.UserService;

@ExtendWith(MockitoExtension.class)
@DisplayName("User Registration Service Impl Tests")
@Tag("UserRegistrationServiceImpl_Tests")
public class UserRegistrationServiceImplTest {

	@InjectMocks
	private UserRegistrationServiceImpl userRegistrationService;

	@Mock
	private RoleService roleService;

	@Mock
	private UserMapper userMapper;

	@Mock
	private UserService userService;

	@Test
	@DisplayName("when register user")
	public void when_register_user_should_reigster_user() {

		UserDTO userDTOExpected = UserDTO.builder().username("username").firstName("first name").lastName("last name")
				.userPasswordDTO(UserPasswordDTO.builder().password("password1").matchingPassword("password1").build())
				.email("user@email.com").areTermsAccepted(true).build();

		User userExpected = User.builder().username("username").firstName("first name").lastName("last name")
				.password("password1").email("user@email.com").isEnabled(true).build();

		String roleName = "ROLE_USER";

		Role roleExpected = Role.builder().name(roleName).build();

		when(userMapper.mapUserDTOToUser(userDTOExpected)).thenReturn(userExpected);
		when(roleService.findByName(roleName)).thenReturn(Optional.of(roleExpected));

		User userActual = userRegistrationService.registerUser(userDTOExpected);

		assertAll(
				() -> assertEquals(userExpected.getUsername(), userActual.getUsername(),
						() -> "should return user with username"),
				() -> assertEquals(userExpected.getFirstName(), userActual.getFirstName(),
						() -> "should return user with first name"),
				() -> assertEquals(userExpected.getLastName(), userActual.getLastName(),
						() -> "should return user with last name"),
				() -> assertEquals(userExpected.getPassword(), userActual.getPassword(),
						() -> "should return user with password"),
				() -> assertEquals(userExpected.getEmail(), userActual.getEmail(),
						() -> "should return user with email"),
				() -> assertEquals(userExpected.isEnabled(), userActual.isEnabled(),
						() -> "should return user which is enabled"),
				() -> verify(userMapper, times(1)).mapUserDTOToUser(userDTOExpected),
				() -> verify(roleService, times(1)).findByName(roleName),
				() -> verify(userService, times(1)).save(userActual));
	}
}
