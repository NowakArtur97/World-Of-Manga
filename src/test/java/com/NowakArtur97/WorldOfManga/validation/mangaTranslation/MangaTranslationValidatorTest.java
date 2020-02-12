package com.NowakArtur97.WorldOfManga.validation.mangaTranslation;

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

import com.NowakArtur97.WorldOfManga.dto.MangaTranslationDTO;
import com.NowakArtur97.WorldOfManga.service.api.MangaTranslationService;
import com.NowakArtur97.WorldOfManga.validation.manga.MangaTranslationValidator;

@ExtendWith(MockitoExtension.class)
@DisplayName("MangaTranslationValidator Tests")
@Tag("MangaTranslationValidator_Tests")
public class MangaTranslationValidatorTest {

	@InjectMocks
	private MangaTranslationValidator mangaTranslationValidator;

	@Mock
	private MangaTranslationService mangaTranslationService;

	@Test
	@DisplayName("when validate correct manga translation dto")
	public void when_validate_correct_manga_translation_dto_should_not_have_errors() {

		String title = "Correct title";

		String description = "Correct description";

		MangaTranslationDTO mangaTranslationDTO = MangaTranslationDTO.builder().title(title).description(description)
				.build();

		Errors errors = new BeanPropertyBindingResult(mangaTranslationDTO, "mangaTranslationDTO");

		when(mangaTranslationService.isMangaTitleAlreadyInUse(title)).thenReturn(false);

		mangaTranslationValidator.validate(mangaTranslationDTO, errors);

		assertAll(() -> assertFalse(errors.hasErrors(), () -> "shouldn`t have errors: " + errors.hasErrors()),
				() -> assertNull(errors.getFieldError("title"),
						() -> "shouldn`t title be in use, but was: " + errors.getFieldError("title")),
				() -> verify(mangaTranslationService, times(1)).isMangaTitleAlreadyInUse(title));
	}

	@Test
	@DisplayName("when validate correct manga translation dto but title is already in use")
	public void when_validate_correct_manga_translation_but_title_is_already_in_use_should_have_errors() {

		String title = "Title in use";

		String description = "Correct description";

		MangaTranslationDTO mangaTranslationDTO = MangaTranslationDTO.builder().title(title).description(description)
				.build();

		Errors errors = new BeanPropertyBindingResult(mangaTranslationDTO, "mangaTranslationDTO");

		when(mangaTranslationService.isMangaTitleAlreadyInUse(title)).thenReturn(true);

		mangaTranslationValidator.validate(mangaTranslationDTO, errors);

		assertAll(() -> assertTrue(errors.hasErrors(), () -> "should have errors: " + errors.hasErrors()),
				() -> assertNotNull(errors.getFieldError("title"),
						() -> "should title be in use, but was: " + errors.getFieldError("title")),
				() -> verify(mangaTranslationService, times(1)).isMangaTitleAlreadyInUse(title));
	}
}
