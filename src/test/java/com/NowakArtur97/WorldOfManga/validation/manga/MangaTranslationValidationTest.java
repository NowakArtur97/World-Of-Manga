package com.NowakArtur97.WorldOfManga.validation.manga;

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

import com.NowakArtur97.WorldOfManga.dto.MangaTranslationDTO;

@DisplayName("Manga Translation Validation Tests")
@Tag("MangaTranslationValidation_Tests")
public class MangaTranslationValidationTest {

	private Validator validator;

	@BeforeEach
	public void setUp() {

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	@DisplayName("when manga translation is correct")
	public void when_manga_translation_is_correct_should_not_have_violations() {

		MangaTranslationDTO mangaTranslationDTO = MangaTranslationDTO.builder().title("Title")
				.description("Description").build();

		Set<ConstraintViolation<MangaTranslationDTO>> violations = validator.validate(mangaTranslationDTO);

		assertAll(() -> assertTrue(violations.isEmpty(),
				() -> "shouldn`t have violations, but have: " + violations.size()));
	}

	@Test
	@DisplayName("when manga translation is incorrect")
	public void when_manga_translation_is_incorrect_should_have_violations() {

		MangaTranslationDTO mangaTranslationDTO = MangaTranslationDTO.builder().title(null).description(null).build();

		Set<ConstraintViolation<MangaTranslationDTO>> violations = validator.validate(mangaTranslationDTO);

		assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violation, but: "), () -> assertEquals(2,
				violations.size(), () -> "should have two violation, but have: " + violations.size()));
	}

	@Nested
	@DisplayName("Title Validation Tests")
	class TitleValidationTest {

		@Test
		@DisplayName("when title is null")
		public void when_title_is_null_should_have_violations() {

			String title = null;

			MangaTranslationDTO mangaTranslationDTO = MangaTranslationDTO.builder().title(title)
					.description("Description").build();

			Set<ConstraintViolation<MangaTranslationDTO>> violations = validator.validate(mangaTranslationDTO);

			assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violation, but: "),
					() -> assertEquals(1, violations.size(),
							() -> "should have one violation, but have: " + violations.size()));
		}

		@Test
		@DisplayName("when title is empty")
		public void when_title_is_empty_should_have_violations() {

			String title = "";

			MangaTranslationDTO mangaTranslationDTO = MangaTranslationDTO.builder().title(title)
					.description("Description").build();

			Set<ConstraintViolation<MangaTranslationDTO>> violations = validator.validate(mangaTranslationDTO);

			assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violation, but: "),
					() -> assertEquals(1, violations.size(),
							() -> "should have one violation, but have: " + violations.size()));
		}

		@Test
		@DisplayName("when title is blank")
		public void when_title_is_blank_should_have_violations() {

			String title = "     ";

			MangaTranslationDTO mangaTranslationDTO = MangaTranslationDTO.builder().title(title)
					.description("Description").build();

			Set<ConstraintViolation<MangaTranslationDTO>> violations = validator.validate(mangaTranslationDTO);

			assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violation, but: "),
					() -> assertEquals(1, violations.size(),
							() -> "should have one violation, but have: " + violations.size()));
		}

		@Test
		@DisplayName("when title is too long")
		public void when_title_is_too_long_should_have_violations() {

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
	@DisplayName("Description Validation Tests")
	class DescriptionValidationTest {

		@Test
		@DisplayName("when description is null")
		public void when_description_is_null_should_have_violations() {

			String description = null;

			MangaTranslationDTO mangaTranslationDTO = MangaTranslationDTO.builder().title("Title")
					.description(description).build();

			Set<ConstraintViolation<MangaTranslationDTO>> violations = validator.validate(mangaTranslationDTO);

			assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violation, but: "),
					() -> assertEquals(1, violations.size(),
							() -> "should have one violation, but have: " + violations.size()));
		}

		@Test
		@DisplayName("when description is empty")
		public void when_description_is_empty_should_have_violations() {

			String description = "";

			MangaTranslationDTO mangaTranslationDTO = MangaTranslationDTO.builder().title("Title")
					.description(description).build();

			Set<ConstraintViolation<MangaTranslationDTO>> violations = validator.validate(mangaTranslationDTO);

			assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violation, but: "),
					() -> assertEquals(1, violations.size(),
							() -> "should have one violation, but have: " + violations.size()));
		}

		@Test
		@DisplayName("when description is blank")
		public void when_description_is_blank_should_have_violations() {

			String description = "     ";

			MangaTranslationDTO mangaTranslationDTO = MangaTranslationDTO.builder().title("Title")
					.description(description).build();

			Set<ConstraintViolation<MangaTranslationDTO>> violations = validator.validate(mangaTranslationDTO);

			assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violation, but: "),
					() -> assertEquals(1, violations.size(),
							() -> "should have one violation, but have: " + violations.size()));
		}

		@Test
		@DisplayName("when description is too long")
		public void when_description_is_too_long_should_have_violations() {

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
