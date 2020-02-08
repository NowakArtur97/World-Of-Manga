package com.NowakArtur97.WorldOfManga.controller.main;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

@ExtendWith(MockitoExtension.class)
@DisplayName("Main Controller Tests")
@Tag("MainController_Tests")
public class MainControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private MainController mainController;

	@BeforeEach
	public void setUp() {

		mockMvc = MockMvcBuilders.standaloneSetup(mainController).build();
	}

	@Test
	@DisplayName("when load main page")
	public void when_load_main_page_should_show_main_page() {

		assertAll(() -> mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("views/main")));
	}
}
