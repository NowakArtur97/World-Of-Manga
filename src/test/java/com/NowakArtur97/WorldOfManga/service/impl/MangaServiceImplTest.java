package com.NowakArtur97.WorldOfManga.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import com.NowakArtur97.WorldOfManga.dto.MangaDTO;
import com.NowakArtur97.WorldOfManga.mapper.manga.MangaMapper;
import com.NowakArtur97.WorldOfManga.model.Author;
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

	@Mock
	private MangaMapper mangaMapper;

	@Test
	@DisplayName("when add manga should save manga")
	public void when_add_manga_should_save_manga() throws IOException {

		MangaDTO mangaDTO = new MangaDTO();

		MangaTranslation mangaTranslationEnExpected = MangaTranslation.builder().title("English title")
				.description("English description").build();
		MangaTranslation mangaTranslationPlExpected = MangaTranslation.builder().title("Polish title")
				.description("Polish description").build();

		Set<MangaTranslation> mangaTranslationsExpected = new HashSet<>();
		mangaTranslationsExpected.add(mangaTranslationEnExpected);
		mangaTranslationsExpected.add(mangaTranslationPlExpected);

		Set<Author> authorsExpected = new HashSet<>();
		Author authorExpected = new Author("FirsName LastName");
		authorsExpected.add(authorExpected);

		mangaDTO.setAuthors(authorsExpected);

		MockMultipartFile mockMultipartFile = new MockMultipartFile("manga.jpg", "file bytes".getBytes());
		mangaDTO.setImage(mockMultipartFile);

		Manga mangaExpected = new Manga();
		mangaExpected.addAuthor(authorExpected);
		mangaExpected.addTranslation(mangaTranslationEnExpected);
		mangaExpected.addTranslation(mangaTranslationPlExpected);
		mangaExpected.setImage(mockMultipartFile.getBytes());

		when(mangaMapper.mapMangaDTOToManga(mangaDTO, mangaTranslationsExpected)).thenReturn(mangaExpected);

		Manga mangaActual = mangaService.addOrUpdate(mangaDTO, mangaTranslationsExpected);

		assertAll(
				() -> assertEquals(authorsExpected.size(), mangaActual.getAuthors().size(),
						() -> "should contain one author: " + authorExpected + " but wasn`t"),
				() -> assertTrue(mangaActual.getAuthors().contains(authorExpected),
						() -> "should contain author: " + authorExpected + " but wasn`t"),
				() -> assertEquals(mangaTranslationsExpected.size(), mangaActual.getTranslations().size(),
						() -> "should contain both translations: " + mangaTranslationsExpected + " but wasn`t"),
				() -> assertTrue(mangaActual.getTranslations().contains(mangaTranslationEnExpected),
						() -> "should contain en translation: " + mangaTranslationEnExpected + " but wasn`t"),
				() -> assertTrue(mangaActual.getTranslations().contains(mangaTranslationEnExpected),
						() -> "should contain en translation: " + mangaTranslationEnExpected + " but wasn`t"),
				() -> assertNotNull(mangaActual.getImage(),
						() -> "should save manga with image, but was: " + mangaActual.getImage()),
				() -> verify(mangaMapper, times(1)).mapMangaDTOToManga(mangaDTO, mangaTranslationsExpected),
				() -> verify(mangaRepository, times(1)).save(mangaActual));
	}
}
