package com.NowakArtur97.WorldOfManga.controller.main;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.LocaleResolver;

import com.NowakArtur97.WorldOfManga.model.Author;
import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaTranslation;
import com.NowakArtur97.WorldOfManga.service.api.MangaService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Main Controller Tests")
@Tag("MainController_Tests")
public class MainControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private MainController mainController;

	@Mock
	private MangaService mangaService;

	@Mock
	private LocaleResolver cookieLocaleResolver;

	@BeforeEach
	public void setUp() {

		mockMvc = MockMvcBuilders.standaloneSetup(mainController).build();
	}

	@Test
	@DisplayName("when load main page")
	public void when_load_main_page_should_show_main_page() throws IOException {

		MangaTranslation mangaTranslationEnExpected = MangaTranslation.builder().title("English title")
				.description("English description").build();
		MangaTranslation mangaTranslationPlExpected = MangaTranslation.builder().title("Polish title")
				.description("Polish description").build();

		Set<MangaTranslation> mangaTranslationsExpected = new HashSet<>();
		mangaTranslationsExpected.add(mangaTranslationEnExpected);
		mangaTranslationsExpected.add(mangaTranslationPlExpected);

		Author authorExpected = new Author("FirsName LastName");

		MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());

		Locale locale = new Locale("en");

		Manga mangaExpected = new Manga();
		mangaExpected.addAuthor(authorExpected);
		mangaExpected.addTranslation(mangaTranslationEnExpected);
		mangaExpected.addTranslation(mangaTranslationPlExpected);
		mangaExpected.setImage(image.getBytes());

		List<Manga> mangas = new ArrayList<>();
		mangas.add(mangaExpected);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/").locale(locale)
				.flashAttr("mangas", mangas).flashAttr("locale", locale);

		when(mangaService.findAll()).thenReturn(mangas);
		
		assertAll(() -> mockMvc.perform(mockRequest).andExpect(status().isOk()).andExpect(view().name("views/main"))
				.andExpect(model().attribute("mangas", mangas)));
	}

	@Test
	@DisplayName("when load main page with locale")
	public void when_load_main_page_with_locale_should_load_locale() throws Exception {

		Locale locale = new Locale("en");

		MockHttpServletRequest mockRequest = this.mockMvc.perform(MockMvcRequestBuilders.get("/").locale(locale))
				.andReturn().getRequest();
		
		assertAll(
				() -> assertEquals(locale, mockRequest.getLocale(),
						() -> "should load locale: " + locale + ", but was: " + mockRequest.getLocale()),
				() -> verify(mangaService, times(1)).findAll(),
				() -> verify(cookieLocaleResolver, times(1)).resolveLocale(mockRequest));
	}
}
