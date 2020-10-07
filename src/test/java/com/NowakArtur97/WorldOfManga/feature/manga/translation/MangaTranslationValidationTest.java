package com.NowakArtur97.WorldOfManga.feature.manga.translation;

import com.NowakArtur97.WorldOfManga.testUtil.generator.NameWithSpacesGenerator;
import org.junit.jupiter.api.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("MangaTranslationValidation_Tests")
class MangaTranslationValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void when_manga_translation_is_correct_should_not_have_violations() {

        MangaTranslationDTO mangaTranslationDTO = MangaTranslationDTO.builder().title("Title")
                .description("Description").build();

        Set<ConstraintViolation<MangaTranslationDTO>> violations = validator.validate(mangaTranslationDTO);

        assertAll(() -> assertTrue(violations.isEmpty(),
                () -> "shouldn`t have violations, but have: " + violations.size()));
    }

    @Test
    void when_manga_translation_is_incorrect_should_have_violations() {

        MangaTranslationDTO mangaTranslationDTO = MangaTranslationDTO.builder().title(null).description(null).build();

        Set<ConstraintViolation<MangaTranslationDTO>> violations = validator.validate(mangaTranslationDTO);

        assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violation, but: "), () -> assertEquals(2,
                violations.size(), () -> "should have two violation, but have: " + violations.size()));
    }

    @Nested
    class TitleValidationTest {

        @Test
        void when_title_is_null_should_have_violations() {

            String title = null;

            MangaTranslationDTO mangaTranslationDTO = MangaTranslationDTO.builder().title(title)
                    .description("Description").build();

            Set<ConstraintViolation<MangaTranslationDTO>> violations = validator.validate(mangaTranslationDTO);

            assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violation, but: "),
                    () -> assertEquals(1, violations.size(),
                            () -> "should have one violation, but have: " + violations.size()));
        }

        @Test
        void when_title_is_empty_should_have_violations() {

            String title = "";

            MangaTranslationDTO mangaTranslationDTO = MangaTranslationDTO.builder().title(title)
                    .description("Description").build();

            Set<ConstraintViolation<MangaTranslationDTO>> violations = validator.validate(mangaTranslationDTO);

            assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violation, but: "),
                    () -> assertEquals(1, violations.size(),
                            () -> "should have one violation, but have: " + violations.size()));
        }

        @Test
        void when_title_is_blank_should_have_violations() {

            String title = "     ";

            MangaTranslationDTO mangaTranslationDTO = MangaTranslationDTO.builder().title(title)
                    .description("Description").build();

            Set<ConstraintViolation<MangaTranslationDTO>> violations = validator.validate(mangaTranslationDTO);

            assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violation, but: "),
                    () -> assertEquals(1, violations.size(),
                            () -> "should have one violation, but have: " + violations.size()));
        }

        @Test
        void when_title_is_too_long_should_have_violations() {

            String title = "asdfghjklpasdfghjklpasdfghjklpasdfghjklpasdfghjklp!@#$%";

            MangaTranslationDTO mangaTranslationDTO = MangaTranslationDTO.builder().title(title)
                    .description("Description").build();

            Set<ConstraintViolation<MangaTranslationDTO>> violations = validator.validate(mangaTranslationDTO);

            assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violation, but: "),
                    () -> assertEquals(1, violations.size(),
                            () -> "should have one violation, but have: " + violations.size()));
        }
    }

    @Nested
    class DescriptionValidationTest {

        @Test
        void when_description_is_null_should_have_violations() {

            String description = null;

            MangaTranslationDTO mangaTranslationDTO = MangaTranslationDTO.builder().title("Title")
                    .description(description).build();

            Set<ConstraintViolation<MangaTranslationDTO>> violations = validator.validate(mangaTranslationDTO);

            assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violation, but: "),
                    () -> assertEquals(1, violations.size(),
                            () -> "should have one violation, but have: " + violations.size()));
        }

        @Test
        void when_description_is_empty_should_have_violations() {

            String description = "";

            MangaTranslationDTO mangaTranslationDTO = MangaTranslationDTO.builder().title("Title")
                    .description(description).build();

            Set<ConstraintViolation<MangaTranslationDTO>> violations = validator.validate(mangaTranslationDTO);

            assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violation, but: "),
                    () -> assertEquals(1, violations.size(),
                            () -> "should have one violation, but have: " + violations.size()));
        }

        @Test
        void when_description_is_blank_should_have_violations() {

            String description = "     ";

            MangaTranslationDTO mangaTranslationDTO = MangaTranslationDTO.builder().title("Title")
                    .description(description).build();

            Set<ConstraintViolation<MangaTranslationDTO>> violations = validator.validate(mangaTranslationDTO);

            assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violation, but: "),
                    () -> assertEquals(1, violations.size(),
                            () -> "should have one violation, but have: " + violations.size()));
        }

        @Test
        void when_description_is_too_long_should_have_violations() {

            String description = "asdfghjklpasdfghjklpasdfghjklpasdfghjklpasdfghjklp!@#$%".repeat(30);

            MangaTranslationDTO mangaTranslationDTO = MangaTranslationDTO.builder().title("Title")
                    .description(description).build();

            Set<ConstraintViolation<MangaTranslationDTO>> violations = validator.validate(mangaTranslationDTO);

            assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violation, but: "),
                    () -> assertEquals(1, violations.size(),
                            () -> "should have one violation, but have: " + violations.size()));
        }
    }
}
