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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Test
	@DisplayName("when register user")
	public void when_register_user_should_reigster_user() {

		String password = "user";
		
		UserDTO userDTOExpected = UserDTO.builder().username("username").firstName("first name").lastName("last name")
				.userPasswordDTO(UserPasswordDTO.builder().password(password).matchingPassword(password).build())
				.email("user@email.com").areTermsAccepted(true).build();

		User userExpected = User.builder().username("username").firstName("first name").lastName("last name")
				.password(password).email("user@email.com").isEnabled(true).build();

		String roleName = "ROLE_USER";

		Role roleExpected = Role.builder().name(roleName).build();

		String hashedPassword = "$2a$10$FnkNozkIGR1ec3tsByXTG.PjPlu6Ntj7cW.lgPLIFkigHe6jreSw2";

		when(userMapper.mapUserDTOToUser(userDTOExpected)).thenReturn(userExpected);
		when(roleService.findByName(roleName)).thenReturn(Optional.of(roleExpected));
		when(bCryptPasswordEncoder.encode(userExpected.getPassword())).thenReturn(hashedPassword);

		User userActual = userRegistrationService.registerUser(userDTOExpected);

		assertAll(
				() -> assertEquals(userExpected.getUsername(), userActual.getUsername(),
						() -> "should return user with username: " + userExpected.getUsername() + " but was: "
								+ userActual.getUsername()),
				() -> assertEquals(userExpected.getFirstName(), userActual.getFirstName(),
						() -> "should return user with first name: " + userExpected.getFirstName() + " but was: "
								+ userActual.getFirstName()),
				() -> assertEquals(userExpected.getLastName(), userActual.getLastName(),
						() -> "should return user with last name: " + userExpected.getLastName() + " but was: "
								+ userActual.getLastName()),
				() -> assertEquals(userExpected.getPassword(), userActual.getPassword(),
						() -> "should return user with password: " + userExpected.getPassword() + " but was: "
								+ userActual.getPassword()),
				() -> assertEquals(userExpected.getEmail(), userActual.getEmail(),
						() -> "should return user with email: " + userExpected.getEmail() + " but was: "
								+ userActual.getEmail()),
				() -> assertEquals(userExpected.isEnabled(), userActual.isEnabled(),
						() -> "should return user which is enabled: " + userExpected.isEnabled() + " but was: "
								+ userActual.isEnabled()),
				() -> verify(userMapper, times(1)).mapUserDTOToUser(userDTOExpected),
				() -> verify(roleService, times(1)).findByName(roleName),
				() -> verify(bCryptPasswordEncoder, times(1)).encode(password),
				() -> verify(userService, times(1)).save(userActual));
	}
}
