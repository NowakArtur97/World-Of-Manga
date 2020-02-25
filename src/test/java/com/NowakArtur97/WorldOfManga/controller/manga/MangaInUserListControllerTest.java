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
}
