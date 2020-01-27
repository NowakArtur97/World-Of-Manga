package com.NowakArtur97.WorldOfManga.controller.unloggedUser;

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
@DisplayName("Login Controller Tests")
@Tag("LoginController_Tests")
public class LoginControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private LoginController loginController;

	@BeforeEach
	public void setUp() {

		mockMvc = MockMvcBuilders.standaloneSetup(loginController).dispatchOptions(true).build();
	}

	@Test
	@DisplayName("when load main page")
	public void when_load_main_page_should_show_main_page() throws Exception {

		mockMvc.perform(get("/user/login")).andExpect(status().isOk()).andExpect(view().name("views/user-login"));
	}
}
