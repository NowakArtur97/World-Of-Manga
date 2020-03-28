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
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.NowakArtur97.WorldOfManga.dto.MangaTranslationDTO;
import com.NowakArtur97.WorldOfManga.testUtil.generator.NameWithSpacesGenerator;

@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("MangaTranslationValidation_Tests")
public class MangaTranslationValidationTest {

	private Validator validator;

	@BeforeEach
	public void setUp() {

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void when_manga_translation_is_correct_should_not_have_violations() {

		MangaTranslationDTO mangaTranslationDTO = MangaTranslationDTO.builder().title("Title")
				.description("Description").build();

		Set<ConstraintViolation<MangaTranslationDTO>> violations = validator.validate(mangaTranslationDTO);

		assertAll(() -> assertTrue(violations.isEmpty(),
				() -> "shouldn`t have violations, but have: " + violations.size()));
	}

	@Test
	public void when_manga_translation_is_incorrect_should_have_violations() {

		MangaTranslationDTO mangaTranslationDTO = MangaTranslationDTO.builder().title(null).description(null).build();

		Set<ConstraintViolation<MangaTranslationDTO>> violations = validator.validate(mangaTranslationDTO);

		assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violation, but: "), () -> assertEquals(2,
				violations.size(), () -> "should have two violation, but have: " + violations.size()));
	}

	@Nested
	class TitleValidationTest {

		@Test
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
	class DescriptionValidationTest {

		@Test
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
