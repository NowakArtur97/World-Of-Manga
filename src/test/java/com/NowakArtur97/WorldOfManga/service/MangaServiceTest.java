package com.NowakArtur97.WorldOfManga.service;

import com.NowakArtur97.WorldOfManga.dto.MangaDTO;
import com.NowakArtur97.WorldOfManga.enums.MangaInUserListStatus;
import com.NowakArtur97.WorldOfManga.exception.MangaNotFoundException;
import com.NowakArtur97.WorldOfManga.feature.author.Author;
import com.NowakArtur97.WorldOfManga.mapper.manga.MangaMapper;
import com.NowakArtur97.WorldOfManga.model.*;
import com.NowakArtur97.WorldOfManga.repository.MangaRepository;
import com.NowakArtur97.WorldOfManga.testUtil.generator.NameWithSpacesGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("MangaService_Tests")
public class MangaServiceTest {

    private MangaService mangaService;

    @Mock
    private MangaRepository mangaRepository;

    @Mock
    private MangaMapper mangaMapper;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {

        mangaService = new MangaService(mangaRepository, mangaMapper, userService);
    }

    @Test
    public void when_add_manga_should_save_manga() throws IOException, MangaNotFoundException {

        MangaDTO mangaDTO = new MangaDTO();

        MangaTranslation mangaTranslationEnExpected = MangaTranslation.builder().title("English title")
                .description("English description").build();
        MangaTranslation mangaTranslationPlExpected = MangaTranslation.builder().title("Polish title")
                .description("Polish description").build();

        Set<Author> authorsExpected = new HashSet<>();
        Author authorExpected = new Author("FirsName LastName");
        authorsExpected.add(authorExpected);

        Set<MangaGenre> genresExpected = new HashSet<>();
        MangaGenre genreExpected = new MangaGenre("genre en", "genre pl");
        genresExpected.add(genreExpected);

        mangaDTO.setAuthors(authorsExpected);
        mangaDTO.setGenres(genresExpected);

        MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());
        mangaDTO.setImage(image);

        Manga mangaExpected = new Manga();
        mangaExpected.addAuthor(authorExpected);
        mangaExpected.addGenre(genreExpected);
        mangaExpected.addTranslation(mangaTranslationEnExpected);
        mangaExpected.addTranslation(mangaTranslationPlExpected);
        mangaExpected.setImage(image.getBytes());

        when(mangaMapper.mapMangaDTOToManga(mangaExpected, mangaDTO)).thenReturn(mangaExpected);

        Manga mangaActual = mangaService.addOrUpdate(mangaDTO, mangaExpected);

        assertAll(
                () -> assertEquals(authorsExpected.size(), mangaActual.getAuthors().size(),
                        () -> "should contain one author: " + authorExpected + " but was: " + mangaActual.getAuthors()),
                () -> assertEquals(genresExpected.size(), mangaActual.getGenres().size(),
                        () -> "should contain one genre: " + genreExpected + " but was: " + mangaActual.getGenres()),
                () -> assertTrue(mangaActual.getAuthors().contains(authorExpected),
                        () -> "should contain author: " + authorExpected + " but was: " + mangaActual.getAuthors()),
                () -> assertEquals(2, mangaActual.getTranslations().size(),
                        () -> "should contain both translations, but was: " + mangaActual.getTranslations()),
                () -> assertTrue(mangaActual.getTranslations().contains(mangaTranslationEnExpected),
                        () -> "should contain en translation: " + mangaTranslationEnExpected + " but was: "
                                + mangaActual.getTranslations()),
                () -> assertTrue(mangaActual.getTranslations().contains(mangaTranslationEnExpected),
                        () -> "should contain en translation: " + mangaTranslationEnExpected + " but was: "
                                + mangaActual.getTranslations()),
                () -> assertNotNull(mangaActual.getImage(),
                        () -> "should save manga with image, but was: " + mangaActual.getImage()),
                () -> verify(mangaMapper, times(1)).mapMangaDTOToManga(mangaExpected, mangaDTO),
                () -> verify(mangaRepository, times(1)).save(mangaActual));
    }

    @Test
    public void when_edit_manga_should_update_manga() throws IOException, MangaNotFoundException {

        Long mangaId = 1L;

        MangaTranslation mangaTranslationEnExpected = MangaTranslation.builder().title("English title")
                .description("English description").build();
        MangaTranslation mangaTranslationPlExpected = MangaTranslation.builder().title("Polish title")
                .description("Polish description").build();

        MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());

        Set<Author> authorsExpected = new HashSet<>();
        Author authorExpected = new Author("FirsName LastName");
        authorsExpected.add(authorExpected);

        Set<MangaGenre> genresExpected = new HashSet<>();
        MangaGenre genreExpected = new MangaGenre("genre en", "genre pl");
        genresExpected.add(genreExpected);

        MangaDTO mangaDTO = new MangaDTO();

        mangaDTO.setId(mangaId);
        mangaDTO.setImage(image);
        mangaDTO.setAuthors(authorsExpected);
        mangaDTO.setGenres(genresExpected);

        Manga mangaExpected = new Manga();
        mangaExpected.setId(mangaId);
        mangaExpected.addAuthor(authorExpected);
        mangaExpected.addGenre(genreExpected);
        mangaExpected.addTranslation(mangaTranslationEnExpected);
        mangaExpected.addTranslation(mangaTranslationPlExpected);
        mangaExpected.setImage(image.getBytes());

        Manga mangaAfterMapperExpected = new Manga();
        mangaAfterMapperExpected.setId(mangaId);
        mangaAfterMapperExpected.addAuthor(authorExpected);
        mangaAfterMapperExpected.addTranslation(mangaTranslationEnExpected);
        mangaAfterMapperExpected.addTranslation(mangaTranslationPlExpected);
        mangaAfterMapperExpected.setImage(image.getBytes());
        mangaAfterMapperExpected.addGenre(genreExpected);

        when(mangaMapper.mapMangaDTOToManga(mangaExpected, mangaDTO)).thenReturn(mangaAfterMapperExpected);

        Manga mangaActual = mangaService.addOrUpdate(mangaDTO, mangaExpected);

        assertAll(
                () -> assertEquals(1, mangaActual.getAuthors().size(),
                        () -> "should contain one author: " + authorExpected + " but was: " + mangaActual.getAuthors()),
                () -> assertTrue(mangaActual.getAuthors().contains(authorExpected),
                        () -> "should contain author: " + authorExpected + " but was: " + mangaActual.getAuthors()),
                () -> assertEquals(1, mangaActual.getGenres().size(),
                        () -> "should contain one genre: " + genreExpected + " but was: " + mangaActual.getGenres()),
                () -> assertTrue(mangaActual.getGenres().contains(genreExpected),
                        () -> "should contain genre: " + genreExpected + " but was: " + mangaActual.getGenres()),
                () -> assertEquals(2, mangaActual.getTranslations().size(),
                        () -> "should contain both translations, but was: " + mangaActual.getTranslations()),
                () -> assertTrue(mangaActual.getTranslations().contains(mangaTranslationEnExpected),
                        () -> "should contain en translation: " + mangaTranslationEnExpected + " but was: "
                                + mangaActual.getTranslations()),
                () -> assertTrue(mangaActual.getTranslations().contains(mangaTranslationEnExpected),
                        () -> "should contain en translation: " + mangaTranslationEnExpected + " but was: "
                                + mangaActual.getTranslations()),
                () -> assertNotNull(mangaActual.getImage(),
                        () -> "should save manga with image, but was: " + mangaActual.getImage()),
                () -> verify(mangaMapper, times(1)).mapMangaDTOToManga(mangaExpected, mangaDTO),
                () -> verify(mangaRepository, times(1)).save(mangaActual));
    }

    @Test
    public void when_delete_manga_should_remove_manga() throws IOException, MangaNotFoundException {

        MangaTranslation mangaTranslationEnExpected = MangaTranslation.builder().title("English title")
                .description("English description").build();
        MangaTranslation mangaTranslationPlExpected = MangaTranslation.builder().title("Polish title")
                .description("Polish description").build();

        Set<Author> authorsExpected = new HashSet<>();
        Author authorExpected = new Author("FirsName LastName");
        authorsExpected.add(authorExpected);

        Set<MangaGenre> genresExpected = new HashSet<>();
        MangaGenre genreExpected = new MangaGenre("genre en", "genre pl");
        genresExpected.add(genreExpected);

        MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());

        Manga mangaExpected = new Manga();
        mangaExpected.addAuthor(authorExpected);
        mangaExpected.addGenre(genreExpected);
        mangaExpected.addTranslation(mangaTranslationEnExpected);
        mangaExpected.addTranslation(mangaTranslationPlExpected);
        mangaExpected.setImage(image.getBytes());

        User userExpected = User.builder().username("username").firstName("first name").lastName("last name")
                .password("user").email("user@email.com").isEnabled(true).build();

        int ratingExpected = 5;

        MangaRating mangaRatingExpected = MangaRating.builder().manga(mangaExpected).user(userExpected)
                .rating(ratingExpected).build();
        mangaExpected.getMangasRatings().add(mangaRatingExpected);

        userExpected.addMangaToFavourites(mangaExpected);

        MangaInUserList mangaInListExpected = MangaInUserList.builder().manga(mangaExpected).user(userExpected)
                .status(MangaInUserListStatus.COMPLETED).build();
        mangaExpected.getUsersWithMangaInList().add(mangaInListExpected);

        Long mangaId = 1L;

        when(mangaRepository.findById(mangaId)).thenReturn(Optional.of(mangaExpected));

        Manga mangaActual = mangaService.deleteManga(mangaId);

        assertAll(
                () -> assertTrue(mangaActual.getAuthors().isEmpty(),
                        () -> "shouldn`t manga have authors, but was: " + mangaActual.getAuthors()),
                () -> assertFalse(mangaActual.getAuthors().contains(authorExpected),
                        () -> "shouldn`t contain author, but was: " + mangaActual.getAuthors()),
                () -> assertTrue(mangaActual.getMangasRatings().isEmpty(),
                        () -> "shouldn`t manga have ratings, but was: " + mangaActual.getMangasRatings()),
                () -> assertFalse(mangaActual.getMangasRatings().contains(mangaRatingExpected),
                        () -> "shouldn`t contain rating, but was: " + mangaActual.getMangasRatings()),
                () -> assertTrue(mangaActual.getGenres().isEmpty(),
                        () -> "shouldn`t manga have genres, but was: " + mangaActual.getGenres()),
                () -> assertFalse(mangaActual.getGenres().contains(genreExpected),
                        () -> "shouldn`t contain genre, but was: " + mangaActual.getGenres()),
                () -> assertTrue(mangaActual.getUsersWithMangaInList().isEmpty(),
                        () -> "shouldn`t manga have users with manga in their lists, but was: "
                                + mangaActual.getUsersWithMangaInList()),
                () -> assertFalse(mangaActual.getUsersWithMangaInList().contains(mangaInListExpected),
                        () -> "shouldn`t any user have manga in list, but was: "
                                + mangaActual.getUsersWithMangaInList()),
                () -> assertTrue(mangaActual.getUserWithMangaInFavourites().isEmpty(),
                        () -> "shouldn`t manga have users with manga in their favourites, but was: "
                                + mangaActual.getUserWithMangaInFavourites()),
                () -> assertFalse(mangaActual.getUserWithMangaInFavourites().contains(userExpected),
                        () -> "shouldn`t manga have users with manga in their favourites, but was: "
                                + mangaActual.getUserWithMangaInFavourites()),
                () -> verify(mangaRepository, times(1)).findById(mangaId));
    }

    @Test
    public void when_add_manga_to_favourites_for_first_time_should_add_manga_to_favourites()
            throws MangaNotFoundException, IOException {

        Long mangaId = 1L;

        MangaTranslation mangaTranslationEnExpected = MangaTranslation.builder().title("English title")
                .description("English description").build();
        MangaTranslation mangaTranslationPlExpected = MangaTranslation.builder().title("Polish title")
                .description("Polish description").build();

        MangaGenre genreExpected = new MangaGenre("genre en", "genre pl");

        Author authorExpected = new Author("FirsName LastName");

        MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());

        Manga mangaExpected = new Manga();
        mangaExpected.addAuthor(authorExpected);
        mangaExpected.addTranslation(mangaTranslationEnExpected);
        mangaExpected.addTranslation(mangaTranslationPlExpected);
        mangaExpected.setImage(image.getBytes());
        mangaExpected.addGenre(genreExpected);

        User userExpected = User.builder().username("user").firstName("first name").lastName("last name")
                .password("user").email("user@email.com").isEnabled(true).build();

        when(mangaRepository.findById(mangaId)).thenReturn(Optional.of(mangaExpected));
        when(userService.loadLoggedInUsername()).thenReturn(userExpected);

        Manga mangaActual = mangaService.addOrRemoveFromFavourites(mangaId);

        assertAll(
                () -> assertEquals(mangaExpected, mangaActual,
                        () -> "should return added to list manga: " + mangaExpected + ", but was: " + mangaActual),
                () -> assertTrue(userExpected.getFavouriteMangas().contains(mangaActual),
                        () -> "should manga be in users favourites but wasn`t: " + userExpected.getFavouriteMangas()),
                () -> assertTrue(mangaActual.getUserWithMangaInFavourites().contains(userExpected),
                        () -> "should user be one of the people with the manga in favorites but wasn`t: "
                                + userExpected.getFavouriteMangas()),
                () -> verify(mangaRepository, times(1)).findById(mangaId),
                () -> verify(userService, times(1)).loadLoggedInUsername());
    }

    @Test
    public void when_remove_manga_from_favourites_should_remove_manga_from_favourites()
            throws MangaNotFoundException, IOException {

        Long mangaId = 1L;

        MangaTranslation mangaTranslationEnExpected = MangaTranslation.builder().title("English title")
                .description("English description").build();
        MangaTranslation mangaTranslationPlExpected = MangaTranslation.builder().title("Polish title")
                .description("Polish description").build();

        Author authorExpected = new Author("FirsName LastName");

        MangaGenre genreExpected = new MangaGenre("genre en", "genre pl");

        MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());

        Manga mangaExpected = new Manga();
        mangaExpected.addAuthor(authorExpected);
        mangaExpected.addTranslation(mangaTranslationEnExpected);
        mangaExpected.addTranslation(mangaTranslationPlExpected);
        mangaExpected.setImage(image.getBytes());
        mangaExpected.addGenre(genreExpected);

        User userExpected = User.builder().username("user").firstName("first name").lastName("last name")
                .password("user").email("user@email.com").isEnabled(true).build();

        userExpected.addMangaToFavourites(mangaExpected);

        when(mangaRepository.findById(mangaId)).thenReturn(Optional.of(mangaExpected));
        when(userService.loadLoggedInUsername()).thenReturn(userExpected);

        Manga mangaActual = mangaService.addOrRemoveFromFavourites(mangaId);

        assertAll(
                () -> assertEquals(mangaExpected, mangaActual,
                        () -> "should return deleted manga from list manga: " + mangaExpected + ", but was: "
                                + mangaActual),
                () -> assertFalse(userExpected.getFavouriteMangas().contains(mangaActual),
                        () -> "shouldn`t manga be in users favourites but was: " + userExpected.getFavouriteMangas()),
                () -> assertFalse(mangaActual.getUserWithMangaInFavourites().contains(userExpected),
                        () -> "shouldn`t  user be one of the people with the manga in favorites but was: "
                                + mangaActual.getUserWithMangaInFavourites()),
                () -> verify(mangaRepository, times(1)).findById(mangaId),
                () -> verify(userService, times(1)).loadLoggedInUsername());
    }

    @Test
    public void when_find_all_should_return_list_of_mangas() throws IOException {

        MangaTranslation mangaTranslationEnExpected = MangaTranslation.builder().title("English title")
                .description("English description").build();
        MangaTranslation mangaTranslationPlExpected = MangaTranslation.builder().title("Polish title")
                .description("Polish description").build();

        MangaTranslation mangaTranslationEnExpected2 = MangaTranslation.builder().title("English title 2")
                .description("English description").build();
        MangaTranslation mangaTranslationPlExpected2 = MangaTranslation.builder().title("Polish title 2")
                .description("Polish description").build();

        Author authorExpected = new Author("FirsName LastName");

        MangaGenre genreExpected = new MangaGenre("genre en", "genre pl");
        MangaGenre genreExpected2 = new MangaGenre("genre en 2", "genre pl 2");

        MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());
        MockMultipartFile image2 = new MockMultipartFile("image.jpg", "file bytes".getBytes());

        Manga mangaExpected = new Manga();
        mangaExpected.addAuthor(authorExpected);
        mangaExpected.addGenre(genreExpected);
        mangaExpected.addTranslation(mangaTranslationEnExpected);
        mangaExpected.addTranslation(mangaTranslationPlExpected);
        mangaExpected.setImage(image.getBytes());

        Manga mangaExpected2 = new Manga();
        mangaExpected2.addAuthor(authorExpected);
        mangaExpected2.addGenre(genreExpected2);
        mangaExpected2.addTranslation(mangaTranslationEnExpected2);
        mangaExpected2.addTranslation(mangaTranslationPlExpected2);
        mangaExpected2.setImage(image2.getBytes());

        List<Manga> mangasExpected = new ArrayList<>();
        mangasExpected.add(mangaExpected);
        mangasExpected.add(mangaExpected2);

        when(mangaRepository.findAll()).thenReturn(mangasExpected);

        List<Manga> mangasActual = mangaService.findAll();

        assertAll(
                () -> assertEquals(mangasExpected.size(), mangasActual.size(),
                        "should return list with all authors, but was: " + mangasActual),
                () -> assertTrue(mangasActual.contains(mangaExpected),
                        () -> "should contain author, but was: " + mangasActual),
                () -> assertTrue(mangasActual.contains(mangaExpected2),
                        () -> "should contain author, but was: " + mangasActual),
                () -> verify(mangaRepository, times(1)).findAll());
    }

    @Test
    public void when_find_by_id_found_manga_should_return_manga_with_id() throws IOException, MangaNotFoundException {

        MangaTranslation mangaTranslationEnExpected = MangaTranslation.builder().title("English title")
                .description("English description").build();
        MangaTranslation mangaTranslationPlExpected = MangaTranslation.builder().title("Polish title")
                .description("Polish description").build();

        Author authorExpected = new Author("FirsName LastName");

        MangaGenre genreExpected = new MangaGenre("genre en", "genre pl");

        MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());

        Manga mangaExpected = new Manga();
        mangaExpected.addAuthor(authorExpected);
        mangaExpected.addTranslation(mangaTranslationEnExpected);
        mangaExpected.addTranslation(mangaTranslationPlExpected);
        mangaExpected.setImage(image.getBytes());
        mangaExpected.addGenre(genreExpected);

        Long id = 1L;

        when(mangaRepository.findById(id)).thenReturn(Optional.of(mangaExpected));

        Manga mangaActual = mangaService.findById(id);

        assertAll(
                () -> assertEquals(mangaExpected.getTranslations(), mangaActual.getTranslations(),
                        "should return manga with translations, but was: " + mangaActual.getTranslations()),
                () -> assertEquals(mangaExpected.getGenres(), mangaActual.getGenres(),
                        "should return manga with genres, but was: " + mangaActual.getGenres()),
                () -> assertEquals(mangaExpected.getAuthors(), mangaActual.getAuthors(),
                        "should return manga with authors, but was: " + mangaActual.getAuthors()),
                () -> assertEquals(mangaExpected.getImage(), mangaActual.getImage(),
                        "should return manga with image, but was: " + mangaActual.getImage()),
                () -> verify(mangaRepository, times(1)).findById(id));
    }

    @Test
    public void when_find_by_id_bu_cannot_find_manga_should_return_manga_with_id() throws IOException {

        Long id = 0L;

        Class<MangaNotFoundException> expectedException = MangaNotFoundException.class;

        when(mangaRepository.findById(id)).thenReturn(Optional.empty());

        assertAll(
                () -> assertThrows(expectedException, () -> mangaService.findById(id),
                        () -> "should throw MangaNotFoundException, but nothing was thrown"),
                () -> verify(mangaRepository, times(1)).findById(id));
    }
}
