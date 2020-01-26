package com.NowakArtur97.WorldOfManga.validation.user;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.NowakArtur97.WorldOfManga.dto.UserDTO;
import com.NowakArtur97.WorldOfManga.dto.UserPasswordDTO;

@DisplayName("User Validation Tests")
@Tag("UserPassword_Tests")
public class UserValidationTest {

	private Validator validator;

	@BeforeEach
	public void setUp() {

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Nested
	@DisplayName("Username Validation Tests")
	class UsernameValidationTest {

		@Test
		@DisplayName("when user is correct")
		public void when_user_is_correct_should_not_have_violations() {

			UserDTO userDTO = UserDTO.builder().username("username").firstName("first name").lastName("last name")
					.userPasswordDTO(
							UserPasswordDTO.builder().password("password1").matchingPassword("password1").build())
					.email("user@email.com").areTermsAccepted(true).build();

			Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);

			assertAll(() -> assertTrue(violations.isEmpty(), () -> "shouldn`t have violations, but: "));
		}

		@Test
		@DisplayName("when username is null")
		public void when_username_is_null_should_have_violations() {

			String username = null;

			UserDTO userDTO = UserDTO.builder().username(username).firstName("first name").lastName("last name")
					.userPasswordDTO(
							UserPasswordDTO.builder().password("password1").matchingPassword("password1").build())
					.email("user@email.com").areTermsAccepted(true).build();

			Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);

			assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violation, but: "),
					() -> assertEquals(1, violations.size(), () -> "should have one violation, but: "));
		}

		@Test
		@DisplayName("when username is empty")
		public void when_username_is_empty_should_have_violations() {

			String username = "";

			UserDTO userDTO = UserDTO.builder().username(username).firstName("first name").lastName("last name")
					.userPasswordDTO(
							UserPasswordDTO.builder().password("password1").matchingPassword("password1").build())
					.email("user@email.com").areTermsAccepted(true).build();

			Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);

			assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violation, but: "),
					() -> assertEquals(1, violations.size(), () -> "should have one violation, but: "));
		}

		@Test
		@DisplayName("when username is blank")
		public void when_username_is_blank_should_have_violations() {

			String username = "    ";

			UserDTO userDTO = UserDTO.builder().username(username).firstName("first name").lastName("last name")
					.userPasswordDTO(
							UserPasswordDTO.builder().password("password1").matchingPassword("password1").build())
					.email("user@email.com").areTermsAccepted(true).build();

			Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);

			assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violation, but: "),
					() -> assertEquals(1, violations.size(), () -> "should have one violation, but: "));
		}
	}
}
