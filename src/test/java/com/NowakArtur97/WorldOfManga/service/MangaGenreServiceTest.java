package com.NowakArtur97.WorldOfManga.service;

import com.NowakArtur97.WorldOfManga.exception.MangaGenreNotFoundException;
import com.NowakArtur97.WorldOfManga.model.MangaGenre;
import com.NowakArtur97.WorldOfManga.repository.MangaGenreRepository;
import com.NowakArtur97.WorldOfManga.testUtil.generator.NameWithSpacesGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("MangaGenreService_Tests")
public class MangaGenreServiceTest {

    private MangaGenreService mangaGenreService;

    @Mock
    private MangaGenreRepository mangaGenreRepository;

    @BeforeEach
    void setUp() {

        mangaGenreService = new MangaGenreService(mangaGenreRepository);
    }

    @Test
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
    public void when_find_existing_by_genre_should_return_genre() throws MangaGenreNotFoundException {

        String genreEn = "genre en";
        String genrePl = "genre pl";

        MangaGenre mangaGenreExpected = new MangaGenre(genreEn, genrePl);

        when(mangaGenreRepository.findByEnglishTranslation(genreEn)).thenReturn(Optional.of(mangaGenreExpected));

        MangaGenre mangaGenreActual = mangaGenreService.findByEnglishTranslation(genreEn);

        assertAll(
                () -> assertEquals(mangaGenreExpected.getEnglishTranslation(), mangaGenreActual.getEnglishTranslation(),
                        "should return magna genre with english translation: "
                                + mangaGenreExpected.getEnglishTranslation() + ", but was: "
                                + mangaGenreActual.getEnglishTranslation()),
                () -> assertEquals(mangaGenreExpected.getPolishTranslation(), mangaGenreActual.getPolishTranslation(),
                        "should return magna genre with polish translation: "
                                + mangaGenreExpected.getPolishTranslation() + ", but was: "
                                + mangaGenreActual.getPolishTranslation()),
                () -> verify(mangaGenreRepository, times(1)).findByEnglishTranslation(genreEn));
    }

    @Test
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
