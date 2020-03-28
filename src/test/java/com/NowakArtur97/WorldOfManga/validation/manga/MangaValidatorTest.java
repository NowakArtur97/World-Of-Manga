package com.NowakArtur97.WorldOfManga.validation.manga;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.NowakArtur97.WorldOfManga.dto.MangaDTO;
import com.NowakArtur97.WorldOfManga.dto.MangaTranslationDTO;
import com.NowakArtur97.WorldOfManga.service.api.MangaTranslationService;
import com.NowakArtur97.WorldOfManga.testUtil.generator.ReplaceUnderscoresGenerator;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscoresGenerator.class)
@Tag("MangaValidator_Tests")
public class MangaValidatorTest {

	@InjectMocks
	private MangaValidator mangaValidator;

	@Mock
	private MangaTranslationService mangaTranslationService;

	@Test
	public void when_validate_correct_manga_translation_dto_should_not_have_errors() {

		String title = "Correct title";

		String description = "Correct description";

		MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());

		MangaTranslationDTO mangaTranslationEnDTO = MangaTranslationDTO.builder().title(title).description(description)
				.build();

		MangaTranslationDTO mangaTranslationPlDTO = MangaTranslationDTO.builder().title(title).description(description)
				.build();

		MangaDTO mangaDTO = MangaDTO.builder().enTranslation(mangaTranslationEnDTO).plTranslation(mangaTranslationPlDTO)
				.image(image).build();

		Errors errors = new BeanPropertyBindingResult(mangaDTO, "mangaDTO");

		when(mangaTranslationService.isTitleAlreadyInUse(title)).thenReturn(false);

		mangaValidator.validate(mangaDTO, errors);

		assertAll(() -> assertFalse(errors.hasErrors(), () -> "shouldn`t have errors: " + errors.hasErrors()),
				() -> assertNull(errors.getFieldError("title"),
						() -> "shouldn`t title be in use, but was: " + errors.getFieldError("title")),
				() -> verify(mangaTranslationService, times(2)).isTitleAlreadyInUse(title));
	}

	@Test
	public void when_validate_correct_manga_translation_but_title_in_both_languages_is_already_in_use_should_have_errors() {

		String title = "Title in use";

		String description = "Correct description";

		MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());

		MangaTranslationDTO mangaTranslationEnDTO = MangaTranslationDTO.builder().title(title).description(description)
				.build();

		MangaTranslationDTO mangaTranslationPlDTO = MangaTranslationDTO.builder().title(title).description(description)
				.build();

		MangaDTO mangaDTO = MangaDTO.builder().enTranslation(mangaTranslationEnDTO).plTranslation(mangaTranslationPlDTO)
				.image(image).build();

		Errors errors = new BeanPropertyBindingResult(mangaDTO, "mangaDTO");

		when(mangaTranslationService.isTitleAlreadyInUse(title)).thenReturn(true);

		mangaValidator.validate(mangaDTO, errors);

		assertAll(() -> assertTrue(errors.hasErrors(), () -> "should have errors: " + errors.hasErrors()),
				() -> assertNotNull(errors.getFieldError("enTranslation.title"),
						() -> "should english title be in use, but was: "
								+ errors.getFieldError("enTranslation.title")),
				() -> assertNotNull(errors.getFieldError("plTranslation.title"),
						() -> "should polish title be in use, but was: " + errors.getFieldError("plTranslation.title")),
				() -> verify(mangaTranslationService, times(2)).isTitleAlreadyInUse(title));
	}

	@Test
	public void when_validate_correct_manga_translation_but_title_in_english_is_already_in_use_should_have_errors() {

		String title = "Title in use";

		String description = "Correct description";

		MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());

		MangaTranslationDTO mangaTranslationEnDTO = MangaTranslationDTO.builder().title(title).description(description)
				.build();

		MangaTranslationDTO mangaTranslationPlDTO = MangaTranslationDTO.builder().title(title).description(description)
				.build();

		MangaDTO mangaDTO = MangaDTO.builder().enTranslation(mangaTranslationEnDTO).plTranslation(mangaTranslationPlDTO)
				.image(image).build();

		Errors errors = new BeanPropertyBindingResult(mangaDTO, "mangaDTO");

		when(mangaTranslationService.isTitleAlreadyInUse(title)).thenReturn(true).thenReturn(false);

		mangaValidator.validate(mangaDTO, errors);

		assertAll(() -> assertTrue(errors.hasErrors(), () -> "should have errors: " + errors.hasErrors()),
				() -> assertNotNull(errors.getFieldError("enTranslation.title"),
						() -> "should english title be in use, but was: "
								+ errors.getFieldError("enTranslation.title")),
				() -> assertNull(errors.getFieldError("plTranslation.title"),
						() -> "shouldn`t polish title be in use, but was: "
								+ errors.getFieldError("plTranslation.title")),
				() -> verify(mangaTranslationService, times(2)).isTitleAlreadyInUse(title));
	}

	@Test
	public void when_validate_correct_manga_translation_but_title_in_polish_is_already_in_use_should_have_errors() {

		String title = "Title in use";

		String description = "Correct description";

		MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());

		MangaTranslationDTO mangaTranslationEnDTO = MangaTranslationDTO.builder().title(title).description(description)
				.build();

		MangaTranslationDTO mangaTranslationPlDTO = MangaTranslationDTO.builder().title(title).description(description)
				.build();

		MangaDTO mangaDTO = MangaDTO.builder().enTranslation(mangaTranslationEnDTO).plTranslation(mangaTranslationPlDTO)
				.image(image).build();

		Errors errors = new BeanPropertyBindingResult(mangaDTO, "mangaDTO");

		when(mangaTranslationService.isTitleAlreadyInUse(title)).thenReturn(false).thenReturn(true);

		mangaValidator.validate(mangaDTO, errors);

		assertAll(() -> assertTrue(errors.hasErrors(), () -> "should have errors: " + errors.hasErrors()),
				() -> assertNull(errors.getFieldError("enTranslation.title"),
						() -> "shouldn`t english title be in use, but was: "
								+ errors.getFieldError("enTranslation.title")),
				() -> assertNotNull(errors.getFieldError("plTranslation.title"),
						() -> "should polish title be in use, but was: " + errors.getFieldError("plTranslation.title")),
				() -> verify(mangaTranslationService, times(2)).isTitleAlreadyInUse(title));
	}

	@Test
	public void when_validate_empty_image_in_manga_dto_should_have_errors() {

		String title = "Correct title";

		String description = "Correct description";

		MockMultipartFile image = null;

		MangaTranslationDTO mangaTranslationEnDTO = MangaTranslationDTO.builder().title(title).description(description)
				.build();

		MangaTranslationDTO mangaTranslationPlDTO = MangaTranslationDTO.builder().title(title).description(description)
				.build();

		MangaDTO mangaDTO = MangaDTO.builder().enTranslation(mangaTranslationEnDTO).plTranslation(mangaTranslationPlDTO)
				.image(image).build();

		Errors errors = new BeanPropertyBindingResult(mangaDTO, "mangaDTO");

		when(mangaTranslationService.isTitleAlreadyInUse(title)).thenReturn(false).thenReturn(false);

		mangaValidator.validate(mangaDTO, errors);

		assertAll(() -> assertTrue(errors.hasErrors(), () -> "should have errors: " + errors.hasErrors()),
				() -> assertNotNull(errors.getFieldError("image"),
						() -> "should image be empty, but was: " + errors.getFieldError("image")),
				() -> verify(mangaTranslationService, times(2)).isTitleAlreadyInUse(title));
	}
}
