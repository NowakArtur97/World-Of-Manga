package com.NowakArtur97.WorldOfManga.validation.userPassword;

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
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.NowakArtur97.WorldOfManga.dto.UserPasswordDTO;

@DisplayName("User Passwords Match Validator Tests")
@Tag("UserPasswordsMatchValidator_Tests")
public class UserPasswordsMatchValidatorTest {

	private Validator validator;

	@BeforeEach
	public void setUp() {

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	@DisplayName("when validating correct password shouldn`t have violations")
	public void when_validating_correct_password_should_not_have_violations() {

		String password = "password1";

		UserPasswordDTO userPasswordDTO = UserPasswordDTO.builder().password(password).matchingPassword(password)
				.build();

		Set<ConstraintViolation<UserPasswordDTO>> violations = validator.validate(userPasswordDTO);

		assertAll(() -> assertTrue(violations.isEmpty(), () -> "shouldn`t have violations but: "));
	}

	@Test
	@DisplayName("when validating not matching passwords should have violations")
	public void when_validating_not_matching_passwords_should_have_violations() {

		String password1 = "password1";
		String password2 = "1drowssap";

		UserPasswordDTO userPasswordDTO = UserPasswordDTO.builder().password(password1).matchingPassword(password2)
				.build();

		Set<ConstraintViolation<UserPasswordDTO>> violations = validator.validate(userPasswordDTO);

		ConstraintViolation<UserPasswordDTO> violation = violations.iterator().next();

		assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violations, but: "),
				() -> assertEquals("password", violation.getPropertyPath().toString(),
						() -> "should have incorrect password field, but: "),
				() -> assertEquals(userPasswordDTO, violation.getInvalidValue(),
						() -> "should have incorrect value: " + violation.getInvalidValue() + ", but was: "),
				() -> assertEquals("{userPassword.password.matchingFields}", violation.getMessage(),
						() -> "should have message: {userPassword.password.matchingFields}" + ", but was: "
								+ violation.getMessage()));
	}
}
