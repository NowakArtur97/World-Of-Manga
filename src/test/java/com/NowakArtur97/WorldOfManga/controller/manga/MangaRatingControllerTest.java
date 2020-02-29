package com.NowakArtur97.WorldOfManga.controller.manga;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.NowakArtur97.WorldOfManga.service.api.MangaRatingService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Manga Rating Controller Tests")
@Tag("MangaRatingController_Tests")
public class MangaRatingControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private MangaRatingController mangaRatingController;

	@Mock
	private MangaRatingService mangaRatingService;

	@BeforeEach
	public void setUp() {

		mockMvc = MockMvcBuilders.standaloneSetup(mangaRatingController).build();
	}

	@Test
	@DisplayName("when rate manga")
	public void when_rate_manga_should_rate_manga_and_redirect_to_last_page() {

		Long mangaId = 1L;
		int rating = 5;

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/auth/rateManga")
				.param("id", mangaId.toString()).param("rating", String.valueOf(rating)).header("Referer", "/");

		assertAll(
				() -> mockMvc.perform(mockRequest).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/")),
				() -> verify(mangaRatingService, times(1)).rateManga(mangaId, rating));
	}
}
