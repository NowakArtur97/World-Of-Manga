package com.NowakArtur97.WorldOfManga.validation.manga;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.NowakArtur97.WorldOfManga.dto.MangaDTO;
import com.NowakArtur97.WorldOfManga.dto.MangaTranslationDTO;
import com.NowakArtur97.WorldOfManga.model.Author;
import com.NowakArtur97.WorldOfManga.model.MangaGenre;

@DisplayName("Manga Validation Tests")
@Tag("MangaValidation_Tests")
public class MangaValidationTest {

	private Validator validator;

	@BeforeEach
	public void setUp() {

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	@DisplayName("when manga is correct")
	public void when_manga_is_correct_should_not_have_violations() {

		MangaTranslationDTO mangaTranslationEnDTO = MangaTranslationDTO.builder().title("English title")
				.description("English title description").build();

		MangaTranslationDTO mangaTranslationPlDTO = MangaTranslationDTO.builder().title("Polish title")
				.description("Polish description").build();

		Set<Author> authors = new HashSet<>();
		authors.add(new Author("FirsName LastName"));

		Set<MangaGenre> genres = new HashSet<>();
		genres.add(new MangaGenre("genre"));

		MangaDTO mangaDTO = MangaDTO.builder().enTranslation(mangaTranslationEnDTO).plTranslation(mangaTranslationPlDTO)
				.authors(authors).genres(genres).build();

		Set<ConstraintViolation<MangaDTO>> violations = validator.validate(mangaDTO);

		assertAll(() -> assertTrue(violations.isEmpty(),
				() -> "shouldn`t have violations, but have: " + violations.size()));
	}

	@Test
	@DisplayName("when manga authors are empty")
	public void when_manga_authors_are_empty_should_have_violations() {

		MangaTranslationDTO mangaTranslationEnDTO = MangaTranslationDTO.builder().title("English title")
				.description("English title description").build();

		MangaTranslationDTO mangaTranslationPlDTO = MangaTranslationDTO.builder().title("Polish title")
				.description("Polish description").build();

		Set<Author> authors = new HashSet<>();

		Set<MangaGenre> genres = new HashSet<>();
		genres.add(new MangaGenre("genre"));

		MangaDTO mangaDTO = MangaDTO.builder().enTranslation(mangaTranslationEnDTO).plTranslation(mangaTranslationPlDTO)
				.authors(authors).genres(genres).build();

		Set<ConstraintViolation<MangaDTO>> violations = validator.validate(mangaDTO);

		assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violation, but: "), () -> assertEquals(1,
				violations.size(), () -> "should have one violation, but have: " + violations.size()));
	}

	@Test
	@DisplayName("when manga genres are empty")
	public void when_manga_genres_are_empty_should_have_violations() {

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
