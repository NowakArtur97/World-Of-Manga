package com.NowakArtur97.WorldOfManga.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaGenre;
import com.NowakArtur97.WorldOfManga.model.MangaTranslation;
import com.NowakArtur97.WorldOfManga.model.User;
import com.NowakArtur97.WorldOfManga.service.api.MangaService;
import com.NowakArtur97.WorldOfManga.service.api.UserService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Recommendation Service Impl Tests")
@Tag("RecommendationServiceImpl_Tests")
public class RecommendationServiceImplTest {

	private RecommendationServiceImpl recommendationService;

	@Mock
	private MangaService mangaService;

	@Mock
	private UserService userService;

	@BeforeEach
	void setUp() {

		recommendationService = new RecommendationServiceImpl(mangaService, userService);
	}

	@Test
	@DisplayName("when recommend manga for not logged in user")
	public void when_recommend_manga_for_not_logged_in_user_should_return_most_popular_manga() throws IOException {

		User userExpected = User.builder().username("user").firstName("first name").lastName("last name")
				.password("user").email("user@email.com").isEnabled(true).build();

		User userExpected2 = User.builder().username("user2").firstName("first name").lastName("last name")
				.password("user").email("user@email.com").isEnabled(true).build();

		User userExpected3 = User.builder().username("user3").firstName("first name").lastName("last name")
				.password("user").email("user@email.com").isEnabled(true).build();

		MangaTranslation mangaTranslationEnExpected = MangaTranslation.builder().title("English title")
				.description("English description").build();
		MangaTranslation mangaTranslationPlExpected = MangaTranslation.builder().title("Polish title")
				.description("Polish description").build();

		MangaTranslation mangaTranslationEnExpected2 = MangaTranslation.builder().title("English title 2")
				.description("English description").build();
		MangaTranslation mangaTranslationPlExpected2 = MangaTranslation.builder().title("Polish title 2")
				.description("Polish description").build();

		MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());
		MockMultipartFile image2 = new MockMultipartFile("image2.jpg", "file bytes".getBytes());

		MangaGenre genreExpected = new MangaGenre("genre");
		MangaGenre genreExpected2 = new MangaGenre("genre 2");

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

		List<Manga> recommendationsExpected = new ArrayList<>();
		recommendationsExpected.add(mangaExpected);
		recommendationsExpected.add(mangaExpected2);

		when(mangaService.findAll()).thenReturn(recommendationsExpected);
		when(userService.isUserLoggedIn()).thenReturn(false);

		List<Manga> recommendationsActual = recommendationService.recommendManga();

		assertAll(
				() -> assertEquals(recommendationsExpected.size(), recommendationsActual.size(),
						() -> "should return " + recommendationsExpected.size() + " recommendations, but was: "
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
	@DisplayName("when recommend manga for logged in user - favourite")
	public void when_recommend_manga_for_logged_in_user_with_manga_in_favourites_should_return_unknown_manga()
			throws IOException {

		User userExpected = User.builder().username("user").firstName("first name").lastName("last name")
				.password("user").email("user@email.com").isEnabled(true).build();

		User userExpected2 = User.builder().username("user2").firstName("first name").lastName("last name")
				.password("user").email("user@email.com").isEnabled(true).build();

		User userExpected3 = User.builder().username("user3").firstName("first name").lastName("last name")
				.password("user").email("user@email.com").isEnabled(true).build();

		MangaTranslation mangaTranslationEnExpected = MangaTranslation.builder().title("English title")
				.description("English description").build();
		MangaTranslation mangaTranslationPlExpected = MangaTranslation.builder().title("Polish title")
				.description("Polish description").build();

		MangaTranslation mangaTranslationEnExpected2 = MangaTranslation.builder().title("English title 2")
				.description("English description").build();
		MangaTranslation mangaTranslationPlExpected2 = MangaTranslation.builder().title("Polish title 2")
				.description("Polish description").build();

		MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());
		MockMultipartFile image2 = new MockMultipartFile("image2.jpg", "file bytes".getBytes());

		MangaGenre genreExpected = new MangaGenre("genre");
		MangaGenre genreExpected2 = new MangaGenre("genre 2");

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

		List<Manga> recommendationsExpected = new ArrayList<>();
		recommendationsExpected.add(mangaExpected);
		recommendationsExpected.add(mangaExpected2);

		when(mangaService.findAll()).thenReturn(recommendationsExpected);
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
	@DisplayName("when recommend manga for logged in user - rating")
	public void when_recommend_manga_for_logged_in_user_with_manga_rated_should_return_unknown_manga()
			throws IOException {

		User userExpected = User.builder().username("user").firstName("first name").lastName("last name")
				.password("user").email("user@email.com").isEnabled(true).build();

		User userExpected2 = User.builder().username("user2").firstName("first name").lastName("last name")
				.password("user").email("user@email.com").isEnabled(true).build();

		User userExpected3 = User.builder().username("user3").firstName("first name").lastName("last name")
				.password("user").email("user@email.com").isEnabled(true).build();

		MangaTranslation mangaTranslationEnExpected = MangaTranslation.builder().title("English title")
				.description("English description").build();
		MangaTranslation mangaTranslationPlExpected = MangaTranslation.builder().title("Polish title")
				.description("Polish description").build();

		MangaTranslation mangaTranslationEnExpected2 = MangaTranslation.builder().title("English title 2")
				.description("English description").build();
		MangaTranslation mangaTranslationPlExpected2 = MangaTranslation.builder().title("Polish title 2")
				.description("Polish description").build();

		MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());
		MockMultipartFile image2 = new MockMultipartFile("image2.jpg", "file bytes".getBytes());

		MangaGenre genreExpected = new MangaGenre("genre");
		MangaGenre genreExpected2 = new MangaGenre("genre 2");

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

		List<Manga> recommendationsExpected = new ArrayList<>();
		recommendationsExpected.add(mangaExpected);
		recommendationsExpected.add(mangaExpected2);

		when(mangaService.findAll()).thenReturn(recommendationsExpected);
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
	@DisplayName("when recommend manga for logged in user with all the manga already known")
	public void when_recommend_manga_for_logged_in_user_with_all_the_manga_already_known_should_return_empty_list()
			throws IOException {

		User userExpected = User.builder().username("user").firstName("first name").lastName("last name")
				.password("user").email("user@email.com").isEnabled(true).build();

		User userExpected2 = User.builder().username("user2").firstName("first name").lastName("last name")
				.password("user").email("user@email.com").isEnabled(true).build();

		User userExpected3 = User.builder().username("user3").firstName("first name").lastName("last name")
				.password("user").email("user@email.com").isEnabled(true).build();

		MangaTranslation mangaTranslationEnExpected = MangaTranslation.builder().title("English title")
				.description("English description").build();
		MangaTranslation mangaTranslationPlExpected = MangaTranslation.builder().title("Polish title")
				.description("Polish description").build();

		MangaTranslation mangaTranslationEnExpected2 = MangaTranslation.builder().title("English title 2")
				.description("English description").build();
		MangaTranslation mangaTranslationPlExpected2 = MangaTranslation.builder().title("Polish title 2")
				.description("Polish description").build();

		MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());
		MockMultipartFile image2 = new MockMultipartFile("image2.jpg", "file bytes".getBytes());

		MangaGenre genreExpected = new MangaGenre("genre");
		MangaGenre genreExpected2 = new MangaGenre("genre 2");

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

		List<Manga> recommendationsExpected = new ArrayList<>();
		recommendationsExpected.add(mangaExpected);
		recommendationsExpected.add(mangaExpected2);

		when(mangaService.findAll()).thenReturn(recommendationsExpected);
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
