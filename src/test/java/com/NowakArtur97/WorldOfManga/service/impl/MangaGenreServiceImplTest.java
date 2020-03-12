package com.NowakArtur97.WorldOfManga.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.NowakArtur97.WorldOfManga.exception.MangaGenreNotFoundException;
import com.NowakArtur97.WorldOfManga.model.MangaGenre;
import com.NowakArtur97.WorldOfManga.repository.MangaGenreRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Manga Genre Service Impl Test Tests")
@Tag("MangaGenreServiceImpl_Tests")
public class MangaGenreServiceImplTest {

	private MangaGenreServiceImpl mangaGenreService;

	@Mock
	private MangaGenreRepository mangaGenreRepository;

	@BeforeEach
	void setUp() {

		mangaGenreService = new MangaGenreServiceImpl(mangaGenreRepository);
	}

	@Test
	@DisplayName("when find all")
	public void when_find_all_should_return_list_of_mangas() {

		MangaGenre mangaGenreExpected1 = new MangaGenre("genre  en", "genre pl");
		MangaGenre mangaGenreExpected2 = new MangaGenre("genre  en 2", "genre pl 2");

		List<MangaGenre> mangaGenresExpected = new ArrayList<>();
		mangaGenresExpected.add(mangaGenreExpected1);
		mangaGenresExpected.add(mangaGenreExpected2);

		when(mangaGenreRepository.findAll()).thenReturn(mangaGenresExpected);

		List<MangaGenre> mangaGenresActual = mangaGenreService.findAll();

		assertAll(
				() -> assertEquals(mangaGenresExpected.size(), mangaGenresActual.size(),
						"should return list with all authors, but was: " + mangaGenresActual),
				() -> assertTrue(mangaGenresActual.contains(mangaGenreExpected1),
						() -> "should contain author, but was: " + mangaGenresActual),
				() -> assertTrue(mangaGenresActual.contains(mangaGenreExpected2),
						() -> "should contain author, but was: " + mangaGenresActual),
				() -> verify(mangaGenreRepository, times(1)).findAll());
	}

	@Test
	@DisplayName("when find existing by genre")
	public void when_find_existing_by_genre_should_return_genre() throws MangaGenreNotFoundException {

		String genreEn = "genre en";
		String genrePl = "genre pl";

		MangaGenre mangaGenreExpected = new MangaGenre(genreEn, genrePl);

		when(mangaGenreRepository.findByEnglishTranslation(genreEn)).thenReturn(Optional.of(mangaGenreExpected));

		MangaGenre mangaGenreActual = mangaGenreService.findByEnglishTranslation(genreEn);

		assertAll(
				() -> assertEquals(mangaGenreExpected.getEnglishTranslation(), mangaGenreActual.getEnglishTranslation(),
						"should return magna genre with english translation: " + mangaGenreExpected.getEnglishTranslation() + ", but was: "
								+ mangaGenreActual.getEnglishTranslation()),
				() -> assertEquals(mangaGenreExpected.getPolishTranslation(), mangaGenreActual.getPolishTranslation(),
						"should return magna genre with polish translation: " + mangaGenreExpected.getPolishTranslation() + ", but was: "
								+ mangaGenreActual.getPolishTranslation()),
				() -> verify(mangaGenreRepository, times(1)).findByEnglishTranslation(genreEn));
	}

	@Test
	@DisplayName("when find not existing by genre")
	public void when_find_not_existing_by_genre_should_return_genre() throws MangaGenreNotFoundException {

		String genre = "not existing genre";

		Class<MangaGenreNotFoundException> expectedException = MangaGenreNotFoundException.class;

		when(mangaGenreRepository.findByEnglishTranslation(genre)).thenReturn(Optional.empty());

		assertAll(
				() -> assertThrows(expectedException, () -> mangaGenreService.findByEnglishTranslation(genre),
						() -> "should throw MangaGenreNotFoundException, but nothing was thrown"),
				() -> verify(mangaGenreRepository, times(1)).findByEnglishTranslation(genre));
	}
}
