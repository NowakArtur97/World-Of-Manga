package com.NowakArtur97.WorldOfManga.feature.manga.details;

import com.NowakArtur97.WorldOfManga.feature.author.Author;
import com.NowakArtur97.WorldOfManga.feature.manga.genre.MangaGenre;
import com.NowakArtur97.WorldOfManga.feature.manga.translation.MangaTranslationDTO;
import com.NowakArtur97.WorldOfManga.testUtil.generator.NameWithSpacesGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("Manga_Tests")
@Tag("Unit_Tests")
@Tag("MangaValidation_Tests")
class MangaValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void when_manga_is_correct_should_not_have_violations() {

        MangaTranslationDTO mangaTranslationEnDTO = MangaTranslationDTO.builder().title("English title")
                .description("English title description").build();

        MangaTranslationDTO mangaTranslationPlDTO = MangaTranslationDTO.builder().title("Polish title")
                .description("Polish description").build();

        Set<Author> authors = new HashSet<>();
        authors.add(new Author("FirsName LastName"));

        Set<MangaGenre> genres = new HashSet<>();
        genres.add(new MangaGenre("genre en", "genre pl"));

        MangaDTO mangaDTO = MangaDTO.builder().enTranslation(mangaTranslationEnDTO).plTranslation(mangaTranslationPlDTO)
                .authors(authors).genres(genres).build();

        Set<ConstraintViolation<MangaDTO>> violations = validator.validate(mangaDTO);

        assertAll(() -> assertTrue(violations.isEmpty(),
                () -> "shouldn`t have violations, but have: " + violations.size()));
    }

    @Test
    void when_manga_authors_are_empty_should_have_violations() {

        MangaTranslationDTO mangaTranslationEnDTO = MangaTranslationDTO.builder().title("English title")
                .description("English title description").build();

        MangaTranslationDTO mangaTranslationPlDTO = MangaTranslationDTO.builder().title("Polish title")
                .description("Polish description").build();

        Set<Author> authors = new HashSet<>();

        Set<MangaGenre> genres = new HashSet<>();
        genres.add(new MangaGenre("genre en", "genre pl"));

        MangaDTO mangaDTO = MangaDTO.builder().enTranslation(mangaTranslationEnDTO).plTranslation(mangaTranslationPlDTO)
                .authors(authors).genres(genres).build();

        Set<ConstraintViolation<MangaDTO>> violations = validator.validate(mangaDTO);

        assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violation, but: "), () -> assertEquals(1,
                violations.size(), () -> "should have one violation, but have: " + violations.size()));
    }

    @Test
    void when_manga_genres_are_empty_should_have_violations() {

        MangaTranslationDTO mangaTranslationEnDTO = MangaTranslationDTO.builder().title("English title")
                .description("English title description").build();

        MangaTranslationDTO mangaTranslationPlDTO = MangaTranslationDTO.builder().title("Polish title")
                .description("Polish description").build();

        Set<Author> authors = new HashSet<>();
        authors.add(new Author("FirsName LastName"));

        Set<MangaGenre> genres = new HashSet<>();

        MangaDTO mangaDTO = MangaDTO.builder().enTranslation(mangaTranslationEnDTO).plTranslation(mangaTranslationPlDTO)
                .authors(authors).genres(genres).build();

        Set<ConstraintViolation<MangaDTO>> violations = validator.validate(mangaDTO);

        assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violation, but: "), () -> assertEquals(1,
                violations.size(), () -> "should have one violation, but have: " + violations.size()));
    }
}
