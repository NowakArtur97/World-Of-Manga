package com.NowakArtur97.WorldOfManga.validation.author;

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

import com.NowakArtur97.WorldOfManga.dto.AuthorDTO;

@DisplayName("Author Validation Tests")
@Tag("AuthorValidation_Tests")
public class AuthorValidationTest {

	private Validator validator;

	@BeforeEach
	public void setUp() {

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	@DisplayName("when author is correct")
	public void when_author_is_correct_should_not_have_violations() {

		String fullName = "Firstname LastName";

		AuthorDTO authorDTO = new AuthorDTO(fullName);

		Set<ConstraintViolation<AuthorDTO>> violations = validator.validate(authorDTO);

		assertAll(() -> assertTrue(violations.isEmpty(),
				() -> "shouldn`t have violations, but have: " + violations.size()));
	}

	@Nested
	@DisplayName("Full Name Validation Tests")
	class FullNameValidationTest {

		@Test
		@DisplayName("when full name is null")
		public void when_full_name_is_null_should_have_violations() {

			String fullName = null;

			AuthorDTO authorDTO = new AuthorDTO(fullName);

			Set<ConstraintViolation<AuthorDTO>> violations = validator.validate(authorDTO);

			assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violation, but: "),
					() -> assertEquals(1, violations.size(),
							() -> "should have one violation, but have: " + violations.size()));
		}
		
		@Test
		@DisplayName("when full name is empty")
		public void when_full_name_is_empty_should_have_violations() {

			String fullName = "";

			AuthorDTO authorDTO = new AuthorDTO(fullName);

			Set<ConstraintViolation<AuthorDTO>> violations = validator.validate(authorDTO);

			assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violation, but: "),
					() -> assertEquals(1, violations.size(),
							() -> "should have one violation, but have: " + violations.size()));
		}
		
		@Test
		@DisplayName("when full name is blank")
		public void when_full_name_is_blank_should_have_violations() {

			String fullName = "			";

			AuthorDTO authorDTO = new AuthorDTO(fullName);

			Set<ConstraintViolation<AuthorDTO>> violations = validator.validate(authorDTO);

			assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violation, but: "),
					() -> assertEquals(1, violations.size(),
							() -> "should have one violation, but have: " + violations.size()));
		}
	}
}
