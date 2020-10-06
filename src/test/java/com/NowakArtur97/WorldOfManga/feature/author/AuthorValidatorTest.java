package com.NowakArtur97.WorldOfManga.feature.author;

import com.NowakArtur97.WorldOfManga.feature.author.AuthorDTO;
import com.NowakArtur97.WorldOfManga.feature.author.AuthorValidator;
import com.NowakArtur97.WorldOfManga.feature.author.AuthorService;
import com.NowakArtur97.WorldOfManga.testUtil.generator.NameWithSpacesGenerator;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("AuthorValidator_Tests")
public class AuthorValidatorTest {

    @InjectMocks
    private AuthorValidator authorValidator;

    @Mock
    private AuthorService authorService;

    @Test
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
