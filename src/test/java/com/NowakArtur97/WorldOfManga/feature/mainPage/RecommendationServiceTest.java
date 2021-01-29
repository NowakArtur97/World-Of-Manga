package com.NowakArtur97.WorldOfManga.feature.mainPage;

import com.NowakArtur97.WorldOfManga.feature.manga.details.Manga;
import com.NowakArtur97.WorldOfManga.feature.manga.details.MangaService;
import com.NowakArtur97.WorldOfManga.feature.manga.genre.MangaGenre;
import com.NowakArtur97.WorldOfManga.feature.manga.inUserList.MangaInUserListStatus;
import com.NowakArtur97.WorldOfManga.feature.manga.translation.MangaTranslation;
import com.NowakArtur97.WorldOfManga.feature.user.User;
import com.NowakArtur97.WorldOfManga.feature.user.UserService;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("RecommendationService_Tests")
class RecommendationServiceTest {

    private RecommendationService recommendationService;

    @Mock
    private MangaService mangaService;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {

        recommendationService = new RecommendationService(mangaService, userService);
    }

    @Test
    void when_recommend_manga_for_not_logged_in_user_should_return_most_popular_manga() throws IOException {

        User userExpected = User.builder().username("user").firstName("first name").lastName("last name")
                .password("user123").email("user@email.com").isEnabled(true).build();

        User userExpected2 = User.builder().username("user2").firstName("first name 2").lastName("last name 2")
                .password("user231").email("user2@email.com").isEnabled(true).build();

        User userExpected3 = User.builder().username("user3").firstName("first name 3").lastName("last name 3")
                .password("user321").email("user3@email.com").isEnabled(true).build();

        MangaTranslation mangaTranslationEnExpected = MangaTranslation.builder().title("English title")
                .description("English description").build();
        MangaTranslation mangaTranslationPlExpected = MangaTranslation.builder().title("Polish title")
                .description("Polish description").build();

        MangaTranslation mangaTranslationEnExpected2 = MangaTranslation.builder().title("English title 2")
                .description("English description 2").build();
        MangaTranslation mangaTranslationPlExpected2 = MangaTranslation.builder().title("Polish title 2")
                .description("Polish description 2").build();

        MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());
        MockMultipartFile image2 = new MockMultipartFile("image2.jpg", "file bytes".getBytes());

        MangaGenre genreExpected = new MangaGenre("genre en", "genre pl");
        MangaGenre genreExpected2 = new MangaGenre("genre en 2", "genre pl 2");

        Manga mangaExpected = new Manga();
        mangaExpected.setImage(image.getBytes());
        mangaExpected.addGenre(genreExpected);
        mangaExpected.addTranslation(mangaTranslationEnExpected);
        mangaExpected.addTranslation(mangaTranslationPlExpected);

        userExpected.addMangaToFavourites(mangaExpected);
        userExpected2.addMangaToFavourites(mangaExpected);

        Manga mangaExpected2 = new Manga();
        mangaExpected.setImage(image2.getBytes());
        mangaExpected2.addGenre(genreExpected2);
        mangaExpected2.addTranslation(mangaTranslationEnExpected2);
        mangaExpected2.addTranslation(mangaTranslationPlExpected2);

        userExpected3.addMangaToFavourites(mangaExpected2);

        List<Manga> allMangas = new ArrayList<>();
        allMangas.add(mangaExpected);
        allMangas.add(mangaExpected2);

        when(mangaService.findAll()).thenReturn(allMangas);
        when(userService.isUserLoggedIn()).thenReturn(false);

        List<Manga> recommendationsActual = recommendationService.recommendManga();

        assertAll(
                () -> assertEquals(allMangas.size(), recommendationsActual.size(),
                        () -> "should return " + allMangas.size() + " recommendations, but was: "
                                + recommendationsActual.size()),
                () -> assertTrue(recommendationsActual.contains(mangaExpected),
                        () -> "should contain manga: " + mangaExpected + ", but was: " + recommendationsActual),
                () -> assertTrue(recommendationsActual.contains(mangaExpected2),
                        () -> "should contain manga: " + mangaExpected2 + ", but was: " + recommendationsActual),
                () -> assertEquals(mangaExpected, recommendationsActual.get(0),
                        () -> "should contain most liked manga: " + mangaExpected + ", but was: "
                                + recommendationsActual),
                () -> assertEquals(mangaExpected2, recommendationsActual.get(1),
                        () -> "should contain second most liked manga: " + mangaExpected2 + ", but was: "
                                + recommendationsActual));
    }

    @Test
    void when_recommend_manga_for_logged_in_user_with_manga_in_favourites_should_return_unknown_manga()
            throws IOException {

        User userExpected = User.builder().username("user").firstName("first name").lastName("last name")
                .password("user123").email("user@email.com").isEnabled(true).build();

        User userExpected2 = User.builder().username("user2").firstName("first name 2").lastName("last name 2")
                .password("user231").email("user2@email.com").isEnabled(true).build();

        User userExpected3 = User.builder().username("user3").firstName("first name 3").lastName("last name 3")
                .password("user321").email("user3@email.com").isEnabled(true).build();

        MangaTranslation mangaTranslationEnExpected = MangaTranslation.builder().title("English title")
                .description("English description").build();
        MangaTranslation mangaTranslationPlExpected = MangaTranslation.builder().title("Polish title")
                .description("Polish description").build();

        MangaTranslation mangaTranslationEnExpected2 = MangaTranslation.builder().title("English title 2")
                .description("English description 2").build();
        MangaTranslation mangaTranslationPlExpected2 = MangaTranslation.builder().title("Polish title 2")
                .description("Polish description 2").build();

        MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());
        MockMultipartFile image2 = new MockMultipartFile("image2.jpg", "file bytes".getBytes());

        MangaGenre genreExpected = new MangaGenre("genre en", "genre pl");
        MangaGenre genreExpected2 = new MangaGenre("genre en 2", "genre pl 2");

        Manga mangaExpected = new Manga();
        mangaExpected.setImage(image.getBytes());
        mangaExpected.addGenre(genreExpected);
        mangaExpected.addTranslation(mangaTranslationEnExpected);
        mangaExpected.addTranslation(mangaTranslationPlExpected);

        userExpected.addMangaToFavourites(mangaExpected);
        userExpected2.addMangaToFavourites(mangaExpected);

        Manga mangaExpected2 = new Manga();
        mangaExpected.setImage(image2.getBytes());
        mangaExpected2.addGenre(genreExpected2);
        mangaExpected2.addTranslation(mangaTranslationEnExpected2);
        mangaExpected2.addTranslation(mangaTranslationPlExpected2);

        userExpected3.addMangaToFavourites(mangaExpected2);

        List<Manga> allMangas = new ArrayList<>();
        allMangas.add(mangaExpected);
        allMangas.add(mangaExpected2);

        when(mangaService.findAll()).thenReturn(allMangas);
        when(userService.isUserLoggedIn()).thenReturn(true);
        when(userService.loadLoggedInUsername()).thenReturn(userExpected3);

        List<Manga> recommendationsActual = recommendationService.recommendManga();

        assertAll(
                () -> assertEquals(1, recommendationsActual.size(),
                        () -> "should return one recommendation, but was: " + recommendationsActual.size()),
                () -> assertTrue(recommendationsActual.contains(mangaExpected),
                        () -> "should contain manga: " + mangaExpected + ", but was: " + recommendationsActual),
                () -> assertFalse(recommendationsActual.contains(mangaExpected2),
                        () -> "should not contain manga: " + mangaExpected2 + ", but was: " + recommendationsActual),
                () -> assertEquals(mangaExpected, recommendationsActual.get(0),
                        () -> "should contain most liked manga: " + mangaExpected + ", but was: "
                                + recommendationsActual));
    }

    @Test
    void when_recommend_manga_for_logged_in_user_with_manga_rated_should_return_unknown_manga()
            throws IOException {

        User userExpected = User.builder().username("user").firstName("first name").lastName("last name")
                .password("user123").email("user@email.com").isEnabled(true).build();

        User userExpected2 = User.builder().username("user2").firstName("first name 2").lastName("last name 2")
                .password("user231").email("user2@email.com").isEnabled(true).build();

        User userExpected3 = User.builder().username("user3").firstName("first name 3").lastName("last name 3")
                .password("user321").email("user3@email.com").isEnabled(true).build();

        MangaTranslation mangaTranslationEnExpected = MangaTranslation.builder().title("English title")
                .description("English description").build();
        MangaTranslation mangaTranslationPlExpected = MangaTranslation.builder().title("Polish title")
                .description("Polish description").build();

        MangaTranslation mangaTranslationEnExpected2 = MangaTranslation.builder().title("English title 2")
                .description("English description 2").build();
        MangaTranslation mangaTranslationPlExpected2 = MangaTranslation.builder().title("Polish title 2")
                .description("Polish description 2").build();

        MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());
        MockMultipartFile image2 = new MockMultipartFile("image2.jpg", "file bytes".getBytes());

        MangaGenre genreExpected = new MangaGenre("genre en", "genre pl");
        MangaGenre genreExpected2 = new MangaGenre("genre en 2", "genre pl 2");

        Manga mangaExpected = new Manga();
        mangaExpected.setImage(image.getBytes());
        mangaExpected.addGenre(genreExpected);
        mangaExpected.addTranslation(mangaTranslationEnExpected);
        mangaExpected.addTranslation(mangaTranslationPlExpected);

        userExpected.addMangaToFavourites(mangaExpected);
        userExpected2.addMangaToFavourites(mangaExpected);

        int mangaRating = 5;

        Manga mangaExpected2 = new Manga();
        mangaExpected.setImage(image2.getBytes());
        mangaExpected2.addGenre(genreExpected2);
        mangaExpected2.addTranslation(mangaTranslationEnExpected2);
        mangaExpected2.addTranslation(mangaTranslationPlExpected2);

        userExpected3.addMangaRating(mangaExpected2, mangaRating);

        List<Manga> allMangas = new ArrayList<>();
        allMangas.add(mangaExpected);
        allMangas.add(mangaExpected2);

        when(mangaService.findAll()).thenReturn(allMangas);
        when(userService.isUserLoggedIn()).thenReturn(true);
        when(userService.loadLoggedInUsername()).thenReturn(userExpected3);

        List<Manga> recommendationsActual = recommendationService.recommendManga();

        assertAll(
                () -> assertEquals(1, recommendationsActual.size(),
                        () -> "should return one recommendation, but was: " + recommendationsActual.size()),
                () -> assertTrue(recommendationsActual.contains(mangaExpected),
                        () -> "should contain manga: " + mangaExpected + ", but was: " + recommendationsActual),
                () -> assertFalse(recommendationsActual.contains(mangaExpected2),
                        () -> "should not contain manga: " + mangaExpected2 + ", but was: " + recommendationsActual),
                () -> assertEquals(mangaExpected, recommendationsActual.get(0),
                        () -> "should contain most liked manga: " + mangaExpected + ", but was: "
                                + recommendationsActual));
    }

    @Test
    void when_recommend_manga_for_logged_in_user_with_manga_in_list_should_return_unknown_manga()
            throws IOException {

        User userExpected = User.builder().username("user").firstName("first name").lastName("last name")
                .password("user123").email("user@email.com").isEnabled(true).build();

        User userExpected2 = User.builder().username("user2").firstName("first name 2").lastName("last name 2")
                .password("user321").email("user2@email.com").isEnabled(true).build();

        User userExpected3 = User.builder().username("user3").firstName("first name 3").lastName("last name 3")
                .password("user213").email("user3@email.com").isEnabled(true).build();

        MangaTranslation mangaTranslationEnExpected = MangaTranslation.builder().title("English title")
                .description("English description").build();
        MangaTranslation mangaTranslationPlExpected = MangaTranslation.builder().title("Polish title")
                .description("Polish description").build();

        MangaTranslation mangaTranslationEnExpected2 = MangaTranslation.builder().title("English title 2")
                .description("English description 2").build();
        MangaTranslation mangaTranslationPlExpected2 = MangaTranslation.builder().title("Polish title 2")
                .description("Polish description 2").build();

        MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());
        MockMultipartFile image2 = new MockMultipartFile("image2.jpg", "file bytes".getBytes());

        MangaGenre genreExpected = new MangaGenre("genre en", "genre pl");
        MangaGenre genreExpected2 = new MangaGenre("genre en 2", "genre pl 2");

        Manga mangaExpected = new Manga();
        mangaExpected.setImage(image.getBytes());
        mangaExpected.addGenre(genreExpected);
        mangaExpected.addTranslation(mangaTranslationEnExpected);
        mangaExpected.addTranslation(mangaTranslationPlExpected);

        userExpected.addMangaToFavourites(mangaExpected);
        userExpected2.addMangaToFavourites(mangaExpected);

        MangaInUserListStatus status = MangaInUserListStatus.CURRENTLY_READING;

        Manga mangaExpected2 = new Manga();
        mangaExpected.setImage(image2.getBytes());
        mangaExpected2.addGenre(genreExpected2);
        mangaExpected2.addTranslation(mangaTranslationEnExpected2);
        mangaExpected2.addTranslation(mangaTranslationPlExpected2);

        userExpected3.addMangaToList(mangaExpected2, status);

        List<Manga> allMangas = new ArrayList<>();
        allMangas.add(mangaExpected);
        allMangas.add(mangaExpected2);

        when(mangaService.findAll()).thenReturn(allMangas);
        when(userService.isUserLoggedIn()).thenReturn(true);
        when(userService.loadLoggedInUsername()).thenReturn(userExpected3);

        List<Manga> recommendationsActual = recommendationService.recommendManga();

        assertAll(
                () -> assertEquals(1, recommendationsActual.size(),
                        () -> "should return one recommendation, but was: " + recommendationsActual.size()),
                () -> assertTrue(recommendationsActual.contains(mangaExpected),
                        () -> "should contain manga: " + mangaExpected + ", but was: " + recommendationsActual),
                () -> assertFalse(recommendationsActual.contains(mangaExpected2),
                        () -> "should not contain manga: " + mangaExpected2 + ", but was: " + recommendationsActual),
                () -> assertEquals(mangaExpected, recommendationsActual.get(0),
                        () -> "should contain most liked manga: " + mangaExpected + ", but was: "
                                + recommendationsActual));
    }

    @Test
    void when_recommend_manga_for_logged_in_user_with_all_the_manga_already_known_should_return_empty_list()
            throws IOException {

        User userExpected = User.builder().username("user").firstName("first name").lastName("last name")
                .password("user123").email("user@email.com").isEnabled(true).build();

        User userExpected2 = User.builder().username("user2").firstName("first name 2").lastName("last name 2")
                .password("user321").email("user2@email.com").isEnabled(true).build();

        User userExpected3 = User.builder().username("user3").firstName("first name 3").lastName("last name 3")
                .password("user213").email("user3@email.com").isEnabled(true).build();

        MangaTranslation mangaTranslationEnExpected = MangaTranslation.builder().title("English title")
                .description("English description").build();
        MangaTranslation mangaTranslationPlExpected = MangaTranslation.builder().title("Polish title")
                .description("Polish description").build();

        MangaTranslation mangaTranslationEnExpected2 = MangaTranslation.builder().title("English title 2")
                .description("English description 2").build();
        MangaTranslation mangaTranslationPlExpected2 = MangaTranslation.builder().title("Polish title 2")
                .description("Polish description 2").build();

        MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());
        MockMultipartFile image2 = new MockMultipartFile("image2.jpg", "file bytes".getBytes());

        MangaGenre genreExpected = new MangaGenre("genre en", "genre pl");
        MangaGenre genreExpected2 = new MangaGenre("genre en 2", "genre pl 2");

        Manga mangaExpected = new Manga();
        mangaExpected.setImage(image.getBytes());
        mangaExpected.addGenre(genreExpected);
        mangaExpected.addTranslation(mangaTranslationEnExpected);
        mangaExpected.addTranslation(mangaTranslationPlExpected);

        userExpected.addMangaToFavourites(mangaExpected);
        userExpected2.addMangaToFavourites(mangaExpected);
        userExpected3.addMangaToFavourites(mangaExpected);

        int mangaRating = 5;

        Manga mangaExpected2 = new Manga();
        mangaExpected.setImage(image2.getBytes());
        mangaExpected2.addGenre(genreExpected2);
        mangaExpected2.addTranslation(mangaTranslationEnExpected2);
        mangaExpected2.addTranslation(mangaTranslationPlExpected2);

        userExpected3.addMangaRating(mangaExpected2, mangaRating);

        List<Manga> allMangas = new ArrayList<>();
        allMangas.add(mangaExpected);
        allMangas.add(mangaExpected2);

        when(mangaService.findAll()).thenReturn(allMangas);
        when(userService.isUserLoggedIn()).thenReturn(true);
        when(userService.loadLoggedInUsername()).thenReturn(userExpected3);

        List<Manga> recommendationsActual = recommendationService.recommendManga();

        assertAll(
                () -> assertTrue(recommendationsActual.isEmpty(),
                        () -> "should return empty list, but was: " + recommendationsActual.size()),
                () -> assertFalse(recommendationsActual.contains(mangaExpected),
                        () -> "should contain manga: " + mangaExpected + ", but was: " + recommendationsActual),
                () -> assertFalse(recommendationsActual.contains(mangaExpected2),
                        () -> "should not contain manga: " + mangaExpected2 + ", but was: " + recommendationsActual));
    }
}
