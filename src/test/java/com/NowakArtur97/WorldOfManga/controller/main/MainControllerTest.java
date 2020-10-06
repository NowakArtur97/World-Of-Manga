package com.NowakArtur97.WorldOfManga.controller.main;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.LocaleResolver;

import com.NowakArtur97.WorldOfManga.model.Author;
import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaTranslation;
import com.NowakArtur97.WorldOfManga.model.User;
import com.NowakArtur97.WorldOfManga.testUtil.generator.NameWithSpacesGenerator;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("MainController_Tests")
public class MainControllerTest {

	private MockMvc mockMvc;

	private MainController mainController;

	@Mock
	private MangaService mangaService;

	@Mock
	private UserService userService;

	@Mock
	private RecommendationService recommendationService;

	@Mock
	private LocaleResolver cookieLocaleResolver;

	@BeforeEach
	public void setUp() {

		mainController = new MainController(mangaService, recommendationService, userService, cookieLocaleResolver);
		mockMvc = MockMvcBuilders.standaloneSetup(mainController)
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
	}

	@Test
	public void when_load_main_page_as_not_logged_user_should_show_main_page() throws IOException {

		MangaTranslation mangaTranslationEnExpected = MangaTranslation.builder().title("English title")
				.description("English description").build();
		MangaTranslation mangaTranslationPlExpected = MangaTranslation.builder().title("Polish title")
				.description("Polish description").build();

		Set<MangaTranslation> mangaTranslationsExpected = new HashSet<>();
		mangaTranslationsExpected.add(mangaTranslationEnExpected);
		mangaTranslationsExpected.add(mangaTranslationPlExpected);

		Author authorExpected = new Author("FirsName LastName");

		MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());

		Manga mangaExpected = new Manga();
		mangaExpected.addAuthor(authorExpected);
		mangaExpected.addTranslation(mangaTranslationEnExpected);
		mangaExpected.addTranslation(mangaTranslationPlExpected);
		mangaExpected.setImage(image.getBytes());

		List<Manga> mangas = new ArrayList<>();
		mangas.add(mangaExpected);

		Page<Manga> pageMangas = new PageImpl<Manga>(mangas);

		when(mangaService.findAllDividedIntoPages(PageRequest.of(0, 12))).thenReturn(pageMangas);
		when(recommendationService.recommendManga()).thenReturn(mangas);
		when(userService.isUserLoggedIn()).thenReturn(false);

		assertAll(
				() -> mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("views/main"))
						.andExpect(model().attribute("mangas", pageMangas))
						.andExpect(model().attribute("recommendations", mangas))
						.andExpect(model().attributeDoesNotExist("usersFavourites"))
						.andExpect(model().attributeDoesNotExist("usersRating")),
				() -> verify(recommendationService, times(1)).recommendManga(),
				() -> verify(userService, times(1)).isUserLoggedIn(),
				() -> verify(userService, never()).loadLoggedInUsername());
	}

	@Test
	public void when_load_main_page_as_logged_user_should_show_main_page() throws IOException {

		MangaTranslation mangaTranslationEnExpected = MangaTranslation.builder().title("English title")
				.description("English description").build();
		MangaTranslation mangaTranslationPlExpected = MangaTranslation.builder().title("Polish title")
				.description("Polish description").build();

		Set<MangaTranslation> mangaTranslationsExpected = new HashSet<>();
		mangaTranslationsExpected.add(mangaTranslationEnExpected);
		mangaTranslationsExpected.add(mangaTranslationPlExpected);

		Author authorExpected = new Author("FirsName LastName");

		MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());

		Manga mangaExpected = new Manga();
		mangaExpected.addAuthor(authorExpected);
		mangaExpected.addTranslation(mangaTranslationEnExpected);
		mangaExpected.addTranslation(mangaTranslationPlExpected);
		mangaExpected.setImage(image.getBytes());

		List<Manga> mangas = new ArrayList<>();
		mangas.add(mangaExpected);

		User userExpected = User.builder().username("username").password("password").email("user@email.com")
				.isEnabled(true).build();

		userExpected.addMangaRating(mangaExpected, 4);
		userExpected.addMangaToFavourites(mangaExpected);

		Set<Manga> usersRatings = userExpected.getMangasRatings().stream().map(rating -> rating.getManga())
				.collect(Collectors.toSet());

		Page<Manga> pageMangas = new PageImpl<Manga>(mangas);

		when(mangaService.findAllDividedIntoPages(PageRequest.of(0, 12))).thenReturn(pageMangas);
		when(recommendationService.recommendManga()).thenReturn(mangas);
		when(userService.isUserLoggedIn()).thenReturn(true);
		when(userService.loadLoggedInUsername()).thenReturn(userExpected);

		assertAll(
				() -> mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("views/main"))
						.andExpect(model().attribute("mangas", pageMangas))
						.andExpect(model().attribute("recommendations", mangas))
						.andExpect(model().attribute("usersFavourites", userExpected.getFavouriteMangas()))
						.andExpect(model().attribute("usersRatings", usersRatings)),
				() -> verify(mangaService, times(1)).findAllDividedIntoPages(PageRequest.of(0, 12)),
				() -> verify(recommendationService, times(1)).recommendManga(),
				() -> verify(userService, times(1)).isUserLoggedIn(),
				() -> verify(userService, times(1)).loadLoggedInUsername());
	}

	@Test
	public void when_load_main_page_with_locale_should_load_locale() throws Exception {

		Locale locale = new Locale("en");

		MockHttpServletRequest mockRequest = this.mockMvc.perform(MockMvcRequestBuilders.get("/").locale(locale))
				.andReturn().getRequest();

		assertAll(
				() -> assertEquals(locale, mockRequest.getLocale(),
						() -> "should load locale: " + locale + ", but was: " + mockRequest.getLocale()),
				() -> verify(mangaService, times(1)).findAllDividedIntoPages(PageRequest.of(0, 12)),
				() -> verify(recommendationService, times(1)).recommendManga(),
				() -> verify(cookieLocaleResolver, times(1)).resolveLocale(mockRequest));
	}
}
