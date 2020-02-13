package com.NowakArtur97.WorldOfManga.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.NowakArtur97.WorldOfManga.dto.MangaDTO;
import com.NowakArtur97.WorldOfManga.dto.MangaTranslationDTO;
import com.NowakArtur97.WorldOfManga.exception.LanguageNotFoundException;
import com.NowakArtur97.WorldOfManga.mapper.MangaTranslationMapper;
import com.NowakArtur97.WorldOfManga.model.Language;
import com.NowakArtur97.WorldOfManga.model.MangaTranslation;
import com.NowakArtur97.WorldOfManga.repository.MangaTranslationRepository;
import com.NowakArtur97.WorldOfManga.service.api.LanguageService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Manga Translation Service Impl Tests")
@Tag("MangaTranslationServiceImpl_Tests")
public class MangaTranslationServiceImplTest {

	@InjectMocks
	private MangaTranslationServiceImpl mangaTranslationService;

	@Mock
	private MangaTranslationRepository mangaTranslationRepository;

	@Mock
	private MangaTranslationMapper mangaTranslationMapper;

	@Mock
	private LanguageService languageService;

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

	@Test
	@DisplayName("when add manga translations")
	public void when_add_manga_translations() throws LanguageNotFoundException {

		MangaTranslationDTO mangaTranslationEnDTO = MangaTranslationDTO.builder().title("English title")
				.description("English description").build();
		MangaTranslationDTO mangaTranslationPlDTO = MangaTranslationDTO.builder().title("Polish title")
				.description("Polish description").build();

		MangaDTO mangaDTO = MangaDTO.builder().enTranslation(mangaTranslationEnDTO).plTranslation(mangaTranslationPlDTO)
				.build();

		Language en = Language.builder().locale("en").build();
		Language pl = Language.builder().locale("pl").build();

		MangaTranslation mangaTranslationEnExpected = MangaTranslation.builder().title("English title")
				.description("English description").build();
		MangaTranslation mangaTranslationPlExpected = MangaTranslation.builder().title("Polish title")
				.description("Polish description").build();

		Set<MangaTranslation> mangaTranslationsExpected = new HashSet<>();
		mangaTranslationsExpected.add(mangaTranslationEnExpected);
		mangaTranslationsExpected.add(mangaTranslationPlExpected);

		when(languageService.findByLocale("en")).thenReturn(en);
		when(languageService.findByLocale("pl")).thenReturn(pl);
		when(mangaTranslationMapper.mapMangaTranslationDTOToMangaTranslation(mangaDTO.getEnTranslation()))
				.thenReturn(mangaTranslationEnExpected);
		when(mangaTranslationMapper.mapMangaTranslationDTOToMangaTranslation(mangaDTO.getPlTranslation()))
				.thenReturn(mangaTranslationPlExpected);

		Set<MangaTranslation> mangaTranslationsActual = mangaTranslationService.addOrUpdate(mangaDTO);

		assertAll(
				() -> assertEquals(mangaTranslationsActual.size(), 2,
						() -> "should return two translations: " + mangaTranslationsActual.size() + " but was: "
								+ mangaTranslationsActual.size()),
				() -> verify(languageService, times(1)).findByLocale("en"),
				() -> verify(languageService, times(1)).findByLocale("pl"),
				() -> verify(mangaTranslationMapper, times(1))
						.mapMangaTranslationDTOToMangaTranslation(mangaDTO.getEnTranslation()),
				() -> verify(mangaTranslationMapper, times(1))
						.mapMangaTranslationDTOToMangaTranslation(mangaDTO.getPlTranslation()),
				() -> verify(mangaTranslationRepository, times(1)).save(mangaTranslationEnExpected),
				() -> verify(mangaTranslationRepository, times(1)).save(mangaTranslationPlExpected));
	}
}
