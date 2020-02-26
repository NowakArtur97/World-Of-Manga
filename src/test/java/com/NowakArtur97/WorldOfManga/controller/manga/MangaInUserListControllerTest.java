package com.NowakArtur97.WorldOfManga.controller.manga;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.IOException;
import java.util.HashSet;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.LocaleResolver;

import com.NowakArtur97.WorldOfManga.model.Author;
import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaTranslation;
import com.NowakArtur97.WorldOfManga.service.api.UserService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Manga In User List Controller Tests")
@Tag("MangaInUserListController_Tests")
public class MangaInUserListControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private MangaInUserListController mangaInUserListController;

	@Mock
	private UserService userService;

	@Mock
	private LocaleResolver cookieLocaleResolver;

	@BeforeEach
	public void setUp() {

		mockMvc = MockMvcBuilders.standaloneSetup(mangaInUserListController).build();
	}

	@Test
	@DisplayName("when add manga to list")
	public void when_add_to_list_manga_should_ad_manga_to_list_and_redirect_to_last_page() {

		Long mangaId = 1L;
		int status = 0;

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/auth/addToList")
				.param("id", mangaId.toString()).param("status", String.valueOf(status)).header("Referer", "/");

		assertAll(
				() -> mockMvc.perform(mockRequest).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/")),
				() -> verify(userService, times(1)).addToList(mangaId, status));
	}

	@Test
	@DisplayName("when load manga list by status")
	public void when_load_manga_list_by_status_should_show_specific_manga_and_redirect_to_last_page()
			throws IOException {

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

		Set<Manga> mangasExpected = new HashSet<>();
		mangasExpected.add(mangaExpected);

		int status = 0;

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/auth/sortMangaList/{id}", status)
				.locale(locale).flashAttr("mangas", mangasExpected).flashAttr("locale", locale).header("Referer", "/");

		when(userService.getUsersMangaListByStatus(status)).thenReturn(mangasExpected);

		assertAll(
				() -> mockMvc.perform(mockRequest).andExpect(status().isOk()).andExpect(view().name("views/manga-list"))
						.andExpect(model().attribute("mangas", mangasExpected)),
				() -> verify(userService, times(1)).getUsersMangaListByStatus(status));
	}
}
