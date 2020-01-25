package com.NowakArtur97.WorldOfManga.service.impl;

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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.NowakArtur97.WorldOfManga.model.User;
import com.NowakArtur97.WorldOfManga.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("User Service Impl Tests")
@Tag("UserServiceImpl_Tests")
public class UserServiceImplTest {

	@InjectMocks
	private UserServiceImpl userService;

	@Mock
	private UserRepository userRepository;

	@Test
	@DisplayName("when find by username")
	public void when_find_by_username_should_return_user_by_username() {

		String username = "user";

		User userExpected = User.builder().username(username).firstName("first nmae").lastName("user lastname")
				.password("password1").email("user@email.com").isEnabled(true).build();

		when(userRepository.findByUsername(username)).thenReturn(Optional.of(userExpected));

		User userActual = userService.findByUsername(username).get();

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
				() -> verify(userRepository, times(1)).findByUsername(username));
	}
}
