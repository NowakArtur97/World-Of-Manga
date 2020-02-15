package com.NowakArtur97.WorldOfManga.validation.author;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.NowakArtur97.WorldOfManga.dto.AuthorDTO;
import com.NowakArtur97.WorldOfManga.service.api.AuthorService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Author Validator Tests")
@Tag("AuthorValidator_Tests")
public class AuthorValidatorTest {

	@InjectMocks
	private AuthorValidator authorValidator;

	@Mock
	private AuthorService authorService;

	@Test
	@DisplayName("when validate correct author dto")
	public void when_validate_correct_author_dto_should_not_have_errors() {

		String fullName = "Firstname LastName";

		AuthorDTO authorDTO = new AuthorDTO(fullName);

		Errors errors = new BeanPropertyBindingResult(authorDTO, "authorDTO");

		when(authorService.isAuthorAlreadyInDatabase(fullName)).thenReturn(false);

		authorValidator.validate(authorDTO, errors);

		assertAll(() -> assertFalse(errors.hasErrors(), () -> "shouldn`t have errors: " + errors.hasErrors()),
				() -> assertNull(errors.getFieldError("fullName"),
						() -> "shouldn`t full name be in use, but was: " + errors.getFieldError("fullName")),
				() -> verify(authorService, times(1)).isAuthorAlreadyInDatabase(fullName));
	}

	@Test
	@DisplayName("when validate correct author dto but author is alreadysaved")
	public void when_validate_correct_author_dto_but_author_is_already_saved_should_have_errors() {

		String fullName = "Firstname LastName";

		AuthorDTO authorDTO = new AuthorDTO(fullName);

		Errors errors = new BeanPropertyBindingResult(authorDTO, "authorDTO");

		when(authorService.isAuthorAlreadyInDatabase(fullName)).thenReturn(true);

		authorValidator.validate(authorDTO, errors);

		assertAll(() -> assertTrue(errors.hasErrors(), () -> "should have errors: " + errors.hasErrors()),
				() -> assertNotNull(errors.getFieldError("fullName"),
						() -> "should author be saved in database, but was: " + errors.getFieldError("fullName")),
				() -> verify(authorService, times(1)).isAuthorAlreadyInDatabase(fullName));
	}
}
