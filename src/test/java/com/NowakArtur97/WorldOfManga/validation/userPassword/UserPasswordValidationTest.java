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

@DisplayName("User Password Validation Tests")
@Tag("UserPasswordValidation_Tests")
public class UserPasswordValidationTest {

	private Validator validator;

	@BeforeEach
	public void setUp() {

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	@DisplayName("when user`s password is correct")
	public void when_users_password_is_correct_should_not_have_violations() {

		String password = "password";

		UserPasswordDTO userPasswordDTO = UserPasswordDTO.builder().password(password).matchingPassword(password)
				.build();

		Set<ConstraintViolation<UserPasswordDTO>> violations = validator.validate(userPasswordDTO);

		assertAll(() -> assertTrue(violations.isEmpty(), () -> "shouldn`t have violations, but: "));
	}

	@Test
	@DisplayName("when user`s password is null")
	public void when_users_password_is_null_should_have_violations() {

		String password = null;

		UserPasswordDTO userPasswordDTO = UserPasswordDTO.builder().password(password).matchingPassword(password)
				.build();

		Set<ConstraintViolation<UserPasswordDTO>> violations = validator.validate(userPasswordDTO);

		assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violations, but: "),
				() -> assertEquals(3, violations.size(), () -> "should have three violations, but: "));
	}

	@Test
	@DisplayName("when user`s password is empty")
	public void when_users_password_is_empty_should_have_violations() {

		UserPasswordDTO userPasswordDTO = UserPasswordDTO.builder().password("").matchingPassword("").build();

		Set<ConstraintViolation<UserPasswordDTO>> violations = validator.validate(userPasswordDTO);

		assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violations, but: "),
				() -> assertEquals(2, violations.size(), () -> "should have two violations, but: "));
	}

	@Test
	@DisplayName("when user`s password is blank")
	public void when_users_password_is_blank_should_have_violations() {

		UserPasswordDTO userPasswordDTO = UserPasswordDTO.builder().password("   ").matchingPassword("   ").build();

		Set<ConstraintViolation<UserPasswordDTO>> violations = validator.validate(userPasswordDTO);

		assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violations, but: "),
				() -> assertEquals(2, violations.size(), () -> "should have two violations, but: "));
	}
}
