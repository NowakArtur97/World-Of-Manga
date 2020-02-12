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

	@Test
	@DisplayName("when user is correct")
	public void when_user_is_correct_should_not_have_violations() {

		UserDTO userDTO = UserDTO.builder().username("username").firstName("first name").lastName("last name")
				.userPasswordDTO(UserPasswordDTO.builder().password("password1").matchingPassword("password1").build())
				.email("user@email.com").areTermsAccepted(true).build();

		Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);

		assertAll(() -> assertTrue(violations.isEmpty(),
				() -> "shouldn`t have violations, but have: " + violations.size()));
	}

	@Nested
	@DisplayName("Username Validation Tests")
	class UsernameValidationTest {

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
					() -> assertEquals(1, violations.size(),
							() -> "should have one violation, but have: " + violations.size()));
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
					() -> assertEquals(1, violations.size(),
							() -> "should have one violation, but have: " + violations.size()));
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
					() -> assertEquals(1, violations.size(),
							() -> "should have one violation, but have: " + violations.size()));
		}

		@Test
		@DisplayName("when username is too long")
		public void when_username_is_too_long_should_have_violations() {

			String username = "abcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghij";

			UserDTO userDTO = UserDTO.builder().username(username).firstName("first name").lastName("last name")
					.userPasswordDTO(
							UserPasswordDTO.builder().password("password1").matchingPassword("password1").build())
					.email("user@email.com").areTermsAccepted(true).build();

			Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);

			assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violations, but: "),
					() -> assertEquals(1, violations.size(),
							() -> "should have one violation, but have: " + violations.size()));
		}
	}

	@Nested
	@DisplayName("Email Validation Tests")
	class EmailValidationTest {

		@Test
		@DisplayName("when email is null")
		public void when_email_is_null_should_have_violations() {

			String email = null;

			UserDTO userDTO = UserDTO.builder().username("username").firstName("first name").lastName("last name")
					.userPasswordDTO(
							UserPasswordDTO.builder().password("password1").matchingPassword("password1").build())
					.email(email).areTermsAccepted(true).build();

			Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);

			assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violation, but: "),
					() -> assertEquals(1, violations.size(),
							() -> "should have one violation, but have: " + violations.size()));
		}

		@Test
		@DisplayName("when email is empty")
		public void when_email_is_empty_should_have_violations() {

			String email = "";

			UserDTO userDTO = UserDTO.builder().username("username").firstName("first name").lastName("last name")
					.userPasswordDTO(
							UserPasswordDTO.builder().password("password1").matchingPassword("password1").build())
					.email(email).areTermsAccepted(true).build();

			Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);

			assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violation, but: "),
					() -> assertEquals(1, violations.size(),
							() -> "should have one violation, but have: " + violations.size()));
		}

		@Test
		@DisplayName("when email is blank")
		public void when_email_is_blank_should_have_violations() {

			String email = "    ";

			UserDTO userDTO = UserDTO.builder().username("username").firstName("first name").lastName("last name")
					.userPasswordDTO(
							UserPasswordDTO.builder().password("password1").matchingPassword("password1").build())
					.email(email).areTermsAccepted(true).build();

			Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);

			assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violation, but: "),
					() -> assertEquals(2, violations.size(),
							() -> "should have two violations, but have: " + violations.size()));
		}

		@Test
		@DisplayName("when email is too long")
		public void when_email_is_too_long_should_have_violations() {

			String email = "abcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghij@email.com";

			UserDTO userDTO = UserDTO.builder().username("username").firstName("first name").lastName("last name")
					.userPasswordDTO(
							UserPasswordDTO.builder().password("password1").matchingPassword("password1").build())
					.email(email).areTermsAccepted(true).build();

			Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);

			assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violations, but: "),
					() -> assertEquals(2, violations.size(),
							() -> "should have two violations, but have: " + violations.size()));
		}

		@Test
		@DisplayName("when email has incorrect format")
		public void when_email_has_incorrect_format_should_have_violations() {

			String email = "email,";

			UserDTO userDTO = UserDTO.builder().username("username").firstName("first name").lastName("last name")
					.userPasswordDTO(
							UserPasswordDTO.builder().password("password1").matchingPassword("password1").build())
					.email(email).areTermsAccepted(true).build();

			Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);

			assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violation, but: "),
					() -> assertEquals(1, violations.size(),
							() -> "should have one violation, but have: " + violations.size()));
		}
	}

	@Test
	@DisplayName("when firstname is too long")
	public void when_firstname_is_too_long_should_have_violations() {

		String firstname = "abcdefghijabcdefghijabcdefghijabcdefghijabcdefghij";

		UserDTO userDTO = UserDTO.builder().username("username").firstName(firstname).lastName("last name")
				.userPasswordDTO(UserPasswordDTO.builder().password("password1").matchingPassword("password1").build())
				.email("user@email.com").areTermsAccepted(true).build();

		Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);

		assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violations, but: "), () -> assertEquals(1,
				violations.size(), () -> "should have one violation, but have: " + violations.size()));
	}

	@Test
	@DisplayName("when lastname is too long")
	public void when_lastname_is_too_long_should_have_violations() {

		String lastname = "abcdefghijabcdefghijabcdefghijabcdefghijabcdefghij";

		UserDTO userDTO = UserDTO.builder().username("username").firstName("first name").lastName(lastname)
				.userPasswordDTO(UserPasswordDTO.builder().password("password1").matchingPassword("password1").build())
				.email("user@email.com").areTermsAccepted(true).build();

		Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);

		assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violations, but: "), () -> assertEquals(1,
				violations.size(), () -> "should have one violation, but have: " + violations.size()));
	}

	@Test
	@DisplayName("when terms are not accepted")
	public void when_terms_are_not_accepted_should_have_violations() {

		UserDTO userDTO = UserDTO.builder().username("username").firstName("first name").lastName("last name")
				.userPasswordDTO(UserPasswordDTO.builder().password("password1").matchingPassword("password1").build())
				.email("user@email.com").areTermsAccepted(false).build();

		Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);

		assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violations, but: "), () -> assertEquals(1,
				violations.size(), () -> "should have one violation, but have: " + violations.size()));
	}
}
