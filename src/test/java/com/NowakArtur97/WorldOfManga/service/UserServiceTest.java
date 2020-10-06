package com.NowakArtur97.WorldOfManga.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.NowakArtur97.WorldOfManga.model.User;
import com.NowakArtur97.WorldOfManga.repository.UserRepository;
import com.NowakArtur97.WorldOfManga.testUtil.generator.NameWithSpacesGenerator;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("UserServiceImpl_Tests")
public class UserServiceTest {

	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private Authentication authentication;

	@Mock
	private SecurityContext securityContext;

	@Mock
	private Principal principal;

	@BeforeEach
	void setUp() {

		userService = new UserService(userRepository);
	}

	@Nested
	@Tag("UserServiceIntegration_Tests")
	class UserServiceIntegrationTest {

		@Test
		public void when_find_by_username_should_return_user_by_username() {

			String username = "user";

			User userExpected = User.builder().username(username).firstName("first name").lastName("last name")
					.password("password1").email("user@email.com").isEnabled(true).build();

			when(userRepository.findByUsername(username)).thenReturn(Optional.of(userExpected));

			User userActual = userService.findByUsername(username).get();

			assertAll(
					() -> assertEquals(userExpected.getUsername(), userActual.getUsername(),
							() -> "should return user with username: " + userExpected.getUsername() + ", but was: "
									+ userActual.getUsername()),
					() -> assertEquals(userExpected.getFirstName(), userActual.getFirstName(),
							() -> "should return user with first name: " + userExpected.getFirstName() + ", but was: "
									+ userActual.getFirstName()),
					() -> assertEquals(userExpected.getLastName(), userActual.getLastName(),
							() -> "should return user with last name: " + userExpected.getLastName() + ", but was: "
									+ userActual.getLastName()),
					() -> assertEquals(userExpected.getPassword(), userActual.getPassword(),
							() -> "should return user with password: " + userExpected.getPassword() + ", but was: "
									+ userActual.getPassword()),
					() -> assertEquals(userExpected.getEmail(), userActual.getEmail(),
							() -> "should return user with email: " + userExpected.getEmail() + ", but was: "
									+ userActual.getEmail()),
					() -> assertEquals(userExpected.isEnabled(), userActual.isEnabled(),
							() -> "should return user which is enabled: " + userExpected.isEnabled() + ", but was: "
									+ userActual.isEnabled()),
					() -> verify(userRepository, times(1)).findByUsername(username));
		}

		@Test
		public void when_find_not_existing_user_by_username_should_return_empty_optional() {

			String username = "user";

			when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

			Optional<User> userActual = userService.findByUsername(username);

			assertAll(
					() -> assertTrue(userActual.isEmpty(),
							() -> "should return empty Optional, but was: " + userActual),
					() -> verify(userRepository, times(1)).findByUsername(username));
		}

		@Test
		public void when_username_is_already_in_use_should_return_true() {

			boolean isUsernameInUse = true;

			String username = "username";

			when(userRepository.existsUserByUsername(username)).thenReturn(isUsernameInUse);

			boolean isUsernameInUseActual = userService.isUsernameAlreadyInUse(username);

			assertAll(
					() -> assertTrue(isUsernameInUseActual,
							() -> "should return true, but was: " + isUsernameInUseActual),
					() -> verify(userRepository, times(1)).existsUserByUsername(username));
		}

		@Test
		public void when_username_is_not_in_use_should_return_false() {

			boolean isUsernameInUse = false;

			String username = "username";

			when(userRepository.existsUserByUsername(username)).thenReturn(isUsernameInUse);

			boolean isUsernameInUseActual = userService.isUsernameAlreadyInUse(username);

			assertAll(
					() -> assertFalse(isUsernameInUseActual,
							() -> "should return false, but was: " + isUsernameInUseActual),
					() -> verify(userRepository, times(1)).existsUserByUsername(username));
		}

		@Test
		public void when_email_is_already_in_use_should_return_true() {

			boolean isEmailInUse = true;

			String email = "username@email.com";

			when(userRepository.existsUserByEmail(email)).thenReturn(isEmailInUse);

			boolean isEmailInUseActual = userService.isEmailAlreadyInUse(email);

			assertAll(() -> assertTrue(isEmailInUseActual, () -> "should return true, but was: " + isEmailInUseActual),
					() -> verify(userRepository, times(1)).existsUserByEmail(email));
		}

		@Test
		public void when_email_is_not_in_use_should_return_false() {

			boolean isEmailInUse = false;

			String email = "username@email.com";

			when(userRepository.existsUserByEmail(email)).thenReturn(isEmailInUse);

			boolean isEmailInUseActual = userService.isEmailAlreadyInUse(email);

			assertAll(
					() -> assertFalse(isEmailInUseActual, () -> "should return false, but was: " + isEmailInUseActual),
					() -> verify(userRepository, times(1)).existsUserByEmail(email));
		}

		@Test
		public void when_save_user_should_save_and_return_user() {

			User userExpected = User.builder().username("username").firstName("first name").lastName("last name")
					.password("password1").email("user@email.com").isEnabled(true).build();

			when(userRepository.save(userExpected)).thenReturn(userExpected);

			User userActual = userService.save(userExpected);

			assertAll(
					() -> assertEquals(userExpected.getUsername(), userActual.getUsername(),
							() -> "should return user with username: " + userExpected.getUsername() + ", but was: "
									+ userActual.getUsername()),
					() -> assertEquals(userExpected.getFirstName(), userActual.getFirstName(),
							() -> "should return user with first name: " + userExpected.getFirstName() + ", but was: "
									+ userActual.getFirstName()),
					() -> assertEquals(userExpected.getLastName(), userActual.getLastName(),
							() -> "should return user with last name: " + userExpected.getLastName() + ", but was: "
									+ userActual.getLastName()),
					() -> assertEquals(userExpected.getPassword(), userActual.getPassword(),
							() -> "should return user with password: " + userExpected.getPassword() + ", but was: "
									+ userActual.getPassword()),
					() -> assertEquals(userExpected.getEmail(), userActual.getEmail(),
							() -> "should return user with email: " + userExpected.getEmail() + ", but was: "
									+ userActual.getEmail()),
					() -> assertEquals(userExpected.isEnabled(), userActual.isEnabled(),
							() -> "should return user which is enabled: " + userExpected.isEnabled() + ", but was: "
									+ userActual.isEnabled()),
					() -> verify(userRepository, times(1)).save(userExpected));
		}

		@Test
		public void when_user_is_logged_id_should_return_true() {

			when(securityContext.getAuthentication()).thenReturn(authentication);

			SecurityContextHolder.setContext(securityContext);

			boolean isUserLoggedInActual = userService.isUserLoggedIn();

			assertAll(() -> assertTrue(isUserLoggedInActual, () -> "should user be logged in but wasn`t"),
					() -> verify(securityContext, times(1)).getAuthentication(),
					() -> verify(authentication, times(1)).getName());
		}

		@Test
		public void when_user_is_not_logged_in_should_return_false() {

			when(securityContext.getAuthentication()).thenReturn(authentication);
			when(authentication.getName()).thenReturn("anonymousUser");

			SecurityContextHolder.setContext(securityContext);

			boolean isUserLoggedInActual = userService.isUserLoggedIn();

			assertAll(() -> assertFalse(isUserLoggedInActual, () -> "should user be logged in but wasn`t"),
					() -> verify(securityContext, times(1)).getAuthentication(),
					() -> verify(authentication, times(1)).getName());
		}

		@Test
		public void when_user_is_not_logged_in_and_authentication_is_null_should_return_false() {

			when(securityContext.getAuthentication()).thenReturn(null);

			SecurityContextHolder.setContext(securityContext);

			boolean isUserLoggedInActual = userService.isUserLoggedIn();

			assertAll(() -> assertFalse(isUserLoggedInActual, () -> "should user be logged in but wasn`t"),
					() -> verify(securityContext, times(1)).getAuthentication(),
					() -> verify(authentication, never()).getName());
		}
	}

	@Nested
	@Tag("UserDetailsIntegration_Tests")
	class UserDetailsIntegrationTest {

		@Test
		public void when_load_user_details_by_username_should_return_user_details() {

			String username = "user";

			User userExpected = User.builder().username(username).firstName("first name").lastName("last name")
					.password("password1").email("user@email.com").isEnabled(true).build();

			boolean accountNonExpired = true;
			boolean credentialsNonExpired = true;
			boolean accountNonLocked = true;

			UserDetails userDetailsExpected = new org.springframework.security.core.userdetails.User(
					userExpected.getUsername(), userExpected.getPassword(), userExpected.isEnabled(), accountNonExpired,
					credentialsNonExpired, accountNonLocked, new ArrayList<SimpleGrantedAuthority>());

			when(userRepository.findByUsername(username)).thenReturn(Optional.of(userExpected));

			UserDetails userDetailsActual = userService.loadUserByUsername(username);

			assertAll(
					() -> assertEquals(userDetailsExpected.getUsername(), userDetailsActual.getUsername(),
							() -> "should return user details with username: " + userDetailsExpected.getUsername()
									+ ", but was: " + userDetailsActual.getUsername()),
					() -> assertEquals(userDetailsExpected.getPassword(), userDetailsActual.getPassword(),
							() -> "should return user details with password: " + userDetailsExpected.getPassword()
									+ ", but was: " + userDetailsActual.getPassword()),
					() -> assertEquals(userDetailsExpected.getAuthorities(), userDetailsActual.getAuthorities(),
							() -> "should return user details with authorities: " + userDetailsExpected.getAuthorities()
									+ ", but was: " + userDetailsActual.getAuthorities()),
					() -> assertEquals(userDetailsExpected.isEnabled(), userDetailsActual.isEnabled(),
							() -> "should return user details which is enabled: " + userDetailsExpected.isEnabled()
									+ ", but was: " + userDetailsActual.isEnabled()),
					() -> verify(userRepository, times(1)).findByUsername(username));
		}

		@Test
		public void when_load_logged_in_user_should_return_user() {

			String username = "principal";

			User userExpected = User.builder().username(username).firstName("first name").lastName("last name")
					.password("password1").email("user@email.com").isEnabled(true).build();

			when(securityContext.getAuthentication()).thenReturn(authentication);
			when(authentication.getPrincipal()).thenReturn(principal);
			when(userRepository.findByUsername(username)).thenReturn(Optional.of(userExpected));

			SecurityContextHolder.setContext(securityContext);

			User userActual = userService.loadLoggedInUsername();

			assertAll(
					() -> assertEquals(userExpected.getUsername(), userActual.getUsername(),
							() -> "should return user with username: " + userExpected.getUsername() + ", but was: "
									+ userActual.getUsername()),
					() -> assertEquals(userExpected.getFirstName(), userActual.getFirstName(),
							() -> "should return user with first name: " + userExpected.getFirstName() + ", but was: "
									+ userActual.getFirstName()),
					() -> assertEquals(userExpected.getLastName(), userActual.getLastName(),
							() -> "should return user with last name: " + userExpected.getLastName() + ", but was: "
									+ userActual.getLastName()),
					() -> assertEquals(userExpected.getPassword(), userActual.getPassword(),
							() -> "should return user with password: " + userExpected.getPassword() + ", but was: "
									+ userActual.getPassword()),
					() -> assertEquals(userExpected.getEmail(), userActual.getEmail(),
							() -> "should return user with email: " + userExpected.getEmail() + ", but was: "
									+ userActual.getEmail()),
					() -> assertEquals(userExpected.isEnabled(), userActual.isEnabled(),
							() -> "should return user which is enabled: " + userExpected.isEnabled() + ", but was: "
									+ userActual.isEnabled()),
					() -> verify(securityContext, times(1)).getAuthentication(),
					() -> verify(authentication, times(1)).getPrincipal(),
					() -> verify(userRepository, times(1)).findByUsername(username));
		}

		@Test
		public void when_load_logged_in_user_when_isn_not_logged_should_return_user() {

			String username = "user not exists";

			Class<UsernameNotFoundException> expectedException = UsernameNotFoundException.class;

			when(userRepository.findByUsername(username)).thenThrow(expectedException);

			assertAll(
					() -> assertThrows(expectedException, () -> userService.loadUserByUsername(username),
							() -> "should throw UsernameNotFoundException, but nothing was thrown"),
					() -> verify(userRepository, times(1)).findByUsername(username));
		}

		@Test
		public void when_load_user_details_by_username_does_not_find_user_should_throw_UsernameNotFoundException() {

			String username = "user";

			Class<UsernameNotFoundException> expectedException = UsernameNotFoundException.class;

			when(userRepository.findByUsername(username)).thenThrow(expectedException);

			assertAll(
					() -> assertThrows(expectedException, () -> userService.loadUserByUsername(username),
							() -> "should throw UsernameNotFoundException, but nothing was thrown"),
					() -> verify(userRepository, times(1)).findByUsername(username));
		}
	}
}
