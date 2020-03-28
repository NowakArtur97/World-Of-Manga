package com.NowakArtur97.WorldOfManga.validation.user;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.NowakArtur97.WorldOfManga.dto.UserDTO;
import com.NowakArtur97.WorldOfManga.dto.UserPasswordDTO;
import com.NowakArtur97.WorldOfManga.service.api.UserService;
import com.NowakArtur97.WorldOfManga.testUtil.generator.ReplaceUnderscoresGenerator;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscoresGenerator.class)
@Tag("UserRegistrationValidator_Tests")
public class UserRegistrationValidatorTest {

	@InjectMocks
	private UserRegistrationValidator userRegistrationValidator;

	@Mock
	private UserService userService;

	@Test
	public void when_validate_correct_user_dto_should_not_have_errors() {

		String username = "username";

		String email = "user@email.com";

		UserDTO userDTO = UserDTO.builder().username(username).firstName("first name").lastName("last name")
				.userPasswordDTO(UserPasswordDTO.builder().password("password1").matchingPassword("password1").build())
				.email(email).areTermsAccepted(true).build();

		Errors errors = new BeanPropertyBindingResult(userDTO, "user");

		when(userService.isUsernameAlreadyInUse(username)).thenReturn(false);
		when(userService.isEmailAlreadyInUse(email)).thenReturn(false);

		userRegistrationValidator.validate(userDTO, errors);

		assertAll(() -> assertFalse(errors.hasErrors(), () -> "shouldn`t have errors: " + errors.hasErrors()),
				() -> assertNull(errors.getFieldError("username"),
						() -> "shouldn`t username be in use, but was: " + errors.getFieldError("username")),
				() -> assertNull(errors.getFieldError("email"),
						() -> "shouldn`t email be in use, but was: " + errors.getFieldError("email")),
				() -> verify(userService, times(1)).isUsernameAlreadyInUse(username),
				() -> verify(userService, times(1)).isEmailAlreadyInUse(email));
	}

	@Test
	public void when_validate_correct_username_but_email_is_already_in_use_should_have_errors() {

		String username = "username";

		String email = "user@email.com";

		UserDTO userDTO = UserDTO.builder().username(username).firstName("first name").lastName("last name")
				.userPasswordDTO(UserPasswordDTO.builder().password("password1").matchingPassword("password1").build())
				.email(email).areTermsAccepted(true).build();

		Errors errors = new BeanPropertyBindingResult(userDTO, "user");

		when(userService.isUsernameAlreadyInUse(username)).thenReturn(false);
		when(userService.isEmailAlreadyInUse(email)).thenReturn(true);

		userRegistrationValidator.validate(userDTO, errors);

		assertAll(() -> assertTrue(errors.hasErrors(), () -> "should have errors: " + errors.hasErrors()),
				() -> assertNull(errors.getFieldError("username"),
						() -> "shouldn`t username be in use, but was: " + errors.getFieldError("username")),
				() -> assertNotNull(errors.getFieldError("email"),
						() -> "should email be in use, but was: " + errors.getFieldError("email")),
				() -> verify(userService, times(1)).isUsernameAlreadyInUse(username),
				() -> verify(userService, times(1)).isEmailAlreadyInUse(email));
	}

	@Test
	public void when_validate_correct_email_but_username_is_already_in_use_should_have_errors() {

		String username = "username";

		String email = "user@email.com";

		UserDTO userDTO = UserDTO.builder().username(username).firstName("first name").lastName("last name")
				.userPasswordDTO(UserPasswordDTO.builder().password("password1").matchingPassword("password1").build())
				.email(email).areTermsAccepted(true).build();

		Errors errors = new BeanPropertyBindingResult(userDTO, "user");

		when(userService.isUsernameAlreadyInUse(username)).thenReturn(true);
		when(userService.isEmailAlreadyInUse(email)).thenReturn(false);

		userRegistrationValidator.validate(userDTO, errors);

		assertAll(() -> assertTrue(errors.hasErrors(), () -> "should have errors: " + errors.hasErrors()),
				() -> assertNotNull(errors.getFieldError("username"),
						() -> "should username be in use, but was: " + errors.getFieldError("username")),
				() -> assertNull(errors.getFieldError("email"),
						() -> "shouldn`t email be in use, but was: " + errors.getFieldError("email")),
				() -> verify(userService, times(1)).isUsernameAlreadyInUse(username),
				() -> verify(userService, times(1)).isEmailAlreadyInUse(email));
	}

	@Test
	public void when_validate_email_and_username_already_in_use_should_have_errors() {

		String username = "username";

		String email = "user@email.com";

		UserDTO userDTO = UserDTO.builder().username(username).firstName("first name").lastName("last name")
				.userPasswordDTO(UserPasswordDTO.builder().password("password1").matchingPassword("password1").build())
				.email(email).areTermsAccepted(true).build();

		Errors errors = new BeanPropertyBindingResult(userDTO, "user");

		when(userService.isUsernameAlreadyInUse(username)).thenReturn(true);
		when(userService.isEmailAlreadyInUse(email)).thenReturn(true);

		userRegistrationValidator.validate(userDTO, errors);

		assertAll(() -> assertTrue(errors.hasErrors(), () -> "should have errors: " + errors.hasErrors()),
				() -> assertNotNull(errors.getFieldError("username"),
						() -> "should username be in use, but was: " + errors.getFieldError("username")),
				() -> assertNotNull(errors.getFieldError("email"),
						() -> "should email be in use, but was: " + errors.getFieldError("email")),
				() -> verify(userService, times(1)).isUsernameAlreadyInUse(username),
				() -> verify(userService, times(1)).isEmailAlreadyInUse(email));
	}
}
