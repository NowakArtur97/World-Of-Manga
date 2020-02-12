package com.NowakArtur97.WorldOfManga.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

import com.NowakArtur97.WorldOfManga.repository.MangaTranslationRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Manga Translation Service Impl Tests")
@Tag("MangaTranslationServiceImpl_Tests")
public class MangaTranslationServiceImplTest {

	@InjectMocks
	private MangaTranslationServiceImpl mangaTranslationService;

	@Mock
	private MangaTranslationRepository mangaTranslationRepository;

	@Test
	@DisplayName("when title is already in use")
	public void when_title_is_already_in_use_should_return_true() {

		boolean isTitleInUse = true;

		String title = "title";

		when(mangaTranslationRepository.existsMangaTranslationByTitle(title)).thenReturn(isTitleInUse);

		boolean isTitleInUseActual = mangaTranslationService.isTitleAlreadyInUse(title);

		assertAll(() -> assertTrue(isTitleInUseActual, () -> "should return true, but was: " + isTitleInUseActual),
				() -> verify(mangaTranslationRepository, times(1)).existsMangaTranslationByTitle(title));
	}

	@Test
	@DisplayName("when title isn`t in use")
	public void when_title_is_not_in_use_should_return_false() {

		boolean isTitleInUse = false;

		String title = "title";

		when(mangaTranslationRepository.existsMangaTranslationByTitle(title)).thenReturn(isTitleInUse);

		boolean isTitleInUseActual = mangaTranslationService.isTitleAlreadyInUse(title);

		assertAll(() -> assertFalse(isTitleInUseActual, () -> "should return false, but was: " + isTitleInUseActual),
				() -> verify(mangaTranslationRepository, times(1)).existsMangaTranslationByTitle(title));
	}
}
