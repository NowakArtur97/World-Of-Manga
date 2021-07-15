package com.NowakArtur97.WorldOfManga.feature.author;

import com.NowakArtur97.WorldOfManga.testUtil.generator.NameWithSpacesGenerator;
import org.junit.jupiter.api.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("Author_Tests")
@Tag("Unit_Tests")
@Tag("AuthorValidation_Tests")
class AuthorValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void when_author_is_correct_should_not_have_violations() {

        String fullName = "Firstname LastName";

        AuthorDTO authorDTO = new AuthorDTO(fullName);

        Set<ConstraintViolation<AuthorDTO>> violations = validator.validate(authorDTO);

        assertAll(() -> assertTrue(violations.isEmpty(),
                () -> "shouldn't have violations, but have: " + violations.size()));
    }

    @Nested
    class FullNameValidationTest {

        @Test
        void when_full_name_is_null_should_have_violations() {

            String fullName = null;

            AuthorDTO authorDTO = new AuthorDTO(fullName);

            Set<ConstraintViolation<AuthorDTO>> violations = validator.validate(authorDTO);

            assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violation, but: "),
                    () -> assertEquals(1, violations.size(),
                            () -> "should have one violation, but have: " + violations.size()));
        }

        @Test
        void when_full_name_is_empty_should_have_violations() {

            String fullName = "";

            AuthorDTO authorDTO = new AuthorDTO(fullName);

            Set<ConstraintViolation<AuthorDTO>> violations = validator.validate(authorDTO);

            assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violation, but: "),
                    () -> assertEquals(1, violations.size(),
                            () -> "should have one violation, but have: " + violations.size()));
        }

        @Test
        void when_full_name_is_blank_should_have_violations() {

            String fullName = "			";

            AuthorDTO authorDTO = new AuthorDTO(fullName);

            Set<ConstraintViolation<AuthorDTO>> violations = validator.validate(authorDTO);

            assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violation, but: "),
                    () -> assertEquals(1, violations.size(),
                            () -> "should have one violation, but have: " + violations.size()));
        }
    }
}
