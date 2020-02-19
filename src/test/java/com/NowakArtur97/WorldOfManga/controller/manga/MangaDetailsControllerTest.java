package com.NowakArtur97.WorldOfManga.controller.manga;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.NowakArtur97.WorldOfManga.model.Author;
import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaTranslation;
import com.NowakArtur97.WorldOfManga.service.api.MangaService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Manga Details Controller Tests")
@Tag("MangaDetailsController_Tests")
public class MangaDetailsControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private MangaDetailsController mangaDetailsController;

	@Mock
	private MangaService mangaService;

	@BeforeEach
	public void setUp() {

		mockMvc = MockMvcBuilders.standaloneSetup(mangaDetailsController).build();
	}

	@Test
	@DisplayName("when load manga image")
	public void when_load_managa_image_should_show_manga_image() throws Exception {

		MangaTranslation mangaTranslationEnExpected = MangaTranslation.builder().title("English title")
				.description("English description").build();
		MangaTranslation mangaTranslationPlExpected = MangaTranslation.builder().title("Polish title")
				.description("Polish description").build();

		Author authorExpected = new Author("FirsName LastName");

		MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());

		Manga mangaExpected = new Manga();
		mangaExpected.addAuthor(authorExpected);
		mangaExpected.addTranslation(mangaTranslationEnExpected);
		mangaExpected.addTranslation(mangaTranslationPlExpected);
		mangaExpected.setImage(image.getBytes());

		Long id = 1L;

		String contentType = "image/jpeg";

		when(mangaService.findById(id)).thenReturn(mangaExpected);

		MockHttpServletResponse mockResponse = this.mockMvc
				.perform(MockMvcRequestBuilders.get("/manga/getImage/{id}", id).contentType(contentType)).andReturn()
				.getResponse();

		assertAll(
				() -> assertEquals(contentType, mockResponse.getContentType(),
						() -> "should load content of type: " + contentType + ", but was: "
								+ mockResponse.getContentType()),
				() -> assertEquals(200, mockResponse.getStatus(),
						() -> "should return status: " + 200 + ", but was: " + mockResponse.getStatus()),
				() -> verify(mangaService, times(1)).findById(id));
	}
}