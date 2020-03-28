package com.NowakArtur97.WorldOfManga.controller.unloggedUser;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.NowakArtur97.WorldOfManga.testUtil.generator.NameWithSpacesGenerator;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("LoginController_Tests")
public class LoginControllerTest {

	private MockMvc mockMvc;

	private LoginController loginController;

	@BeforeEach
	public void setUp() {

		loginController = new LoginController();
		mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
	}

	@Test
	public void when_load_login_page_should_show_login_page() {

		assertAll(() -> mockMvc.perform(get("/user/login")).andExpect(status().isOk())
				.andExpect(view().name("views/user-login")));
	}
}
