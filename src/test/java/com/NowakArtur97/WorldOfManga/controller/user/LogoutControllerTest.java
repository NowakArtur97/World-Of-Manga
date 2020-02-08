package com.NowakArtur97.WorldOfManga.controller.user;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.Cookie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.NowakArtur97.WorldOfManga.model.User;

@ExtendWith(MockitoExtension.class)
@DisplayName("Logout Controller Tests")
@Tag("LogoutController_Tests")
public class LogoutControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private LogoutController logoutController;

	@BeforeEach
	public void setUp() {

		mockMvc = MockMvcBuilders.standaloneSetup(logoutController).build();
	}

	@Test
	@DisplayName("when sign out user")
	public void when_sign_out_user_should_invalidate_session() {

		MockHttpSession mockSession = new MockHttpSession();

		Cookie[] cookies = { new Cookie("cookie", "cookie") };

		User user = User.builder().username("username").firstName("first name").lastName("last name")
				.password("password1").email("user@email.com").isEnabled(true).build();

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/auth/logout").session(mockSession)
				.cookie(cookies).flashAttr("user", user);

		assertAll(() -> mockMvc.perform(mockRequest).andExpect(status().isOk()).andExpect(forwardedUrl("views/main")),
				() -> assertTrue(mockSession.isInvalid(), () -> "session should has already been invalidated"));
	}
}
