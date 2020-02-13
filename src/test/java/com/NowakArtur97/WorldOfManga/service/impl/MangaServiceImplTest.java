package com.NowakArtur97.WorldOfManga.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaTranslation;
import com.NowakArtur97.WorldOfManga.repository.MangaRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Manga Service Impl Test Tests")
@Tag("MangaServiceImpl_Tests")
public class MangaServiceImplTest {

	@InjectMocks
	private MangaServiceImpl mangaService;

	@Mock
	private MangaRepository mangaRepository;

	@Test
	@DisplayName("when add manga should save manga")
	public void when_add_manga_should_save_manga() {

		MangaDTO mangaDTO = new MangaDTO();

		MangaTranslation mangaTranslationEnExpected = MangaTranslation.builder().title("English title")
				.description("English description").build();
		MangaTranslation mangaTranslationPlExpected = MangaTranslation.builder().title("Polish title")
				.description("Polish description").build();

		Set<MangaTranslation> mangaTranslationsExpected = new HashSet<>();
		mangaTranslationsExpected.add(mangaTranslationEnExpected);
		mangaTranslationsExpected.add(mangaTranslationPlExpected);

		Manga mangaActual = mangaService.addOrUpdate(mangaDTO, mangaTranslationsExpected);

		assertAll(
				() -> assertEquals(mangaActual.getTranslations().size(), 2,
						() -> "should contain both translations: " + mangaTranslationEnExpected + " but wasn`t"),
				() -> assertTrue(mangaActual.getTranslations().contains(mangaTranslationEnExpected),
						() -> "should contain en translation: " + mangaTranslationEnExpected + " but wasn`t"),
				() -> assertTrue(mangaActual.getTranslations().contains(mangaTranslationEnExpected),
						() -> "should contain en translation: " + mangaTranslationEnExpected + " but wasn`t"),
				() -> verify(mangaRepository, times(1)).save(mangaActual));
	}
}
