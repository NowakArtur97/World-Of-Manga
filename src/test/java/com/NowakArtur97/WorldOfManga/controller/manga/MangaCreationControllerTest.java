package com.NowakArtur97.WorldOfManga.controller.manga;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.NowakArtur97.WorldOfManga.dto.MangaDTO;

@ExtendWith(MockitoExtension.class)
@DisplayName("Manga Creation Controller Tests")
@Tag("MangaCreationController_Tests")
public class MangaCreationControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private MangaCreationController mangaCreationController;

	@BeforeEach
	public void setUp() {

		mockMvc = MockMvcBuilders.standaloneSetup(mangaCreationController).build();
	}

	@Test
	@DisplayName("when load add or update manga page")
	public void when_load_add_or_update_manga_page_should_show_manga_form() {

		assertAll(() -> mockMvc.perform(get("/admin/addOrUpdateManga")).andExpect(status().isOk())
				.andExpect(view().name("views/manga-form")).andExpect(model().attribute("mangaDTO", new MangaDTO())));
	}
}
