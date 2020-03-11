package com.NowakArtur97.WorldOfManga.controller.unloggedUser;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.NowakArtur97.WorldOfManga.dto.UserDTO;
import com.NowakArtur97.WorldOfManga.dto.UserPasswordDTO;
import com.NowakArtur97.WorldOfManga.service.api.UserRegistrationService;
import com.NowakArtur97.WorldOfManga.validation.user.UserRegistrationValidator;

@ExtendWith(MockitoExtension.class)
@DisplayName("Registration Controller Tests")
@Tag("RegistrationController_Tests")
public class RegistrationControllerTest {

	private MockMvc mockMvc;

	private RegistrationController registrationController;

	@Mock
	private UserRegistrationService userRegistrationService;

	@Mock
	private UserRegistrationValidator userRegistrationValidator;

	@BeforeEach
	public void setUp() {

		registrationController = new RegistrationController(userRegistrationService, userRegistrationValidator);
		mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();
	}

	@Test
	@DisplayName("when load registration page")
	public void when_load_registration_page_should_show_registration_page() {

		assertAll(() -> mockMvc.perform(get("/user/register")).andExpect(status().isOk())
				.andExpect(view().name("views/user-registration"))
				.andExpect(model().attribute("userDTO", new UserDTO())));
	}

	@Test
	@DisplayName("when process registration with correct data")
	public void when_process_registration_with_correct_data_should_show_login_page_with_message() {

		UserDTO userDTO = UserDTO.builder().username("username").firstName("first name").lastName("last name")
				.userPasswordDTO(UserPasswordDTO.builder().password("password1").matchingPassword("password1").build())
				.email("user@email.com").areTermsAccepted(true).build();

		assertAll(
				() -> mockMvc
						.perform(post("/user/register").contentType(MediaType.APPLICATION_FORM_URLENCODED)
								.flashAttr("userDTO", userDTO))
						.andExpect(status().isOk()).andExpect(model().hasNoErrors())
						.andExpect(forwardedUrl("views/user-login")),
				() -> verify(userRegistrationService, times(1)).registerUser(userDTO));
	}

	@Test
	@DisplayName("when process registration with incorrect data")
	public void when_process_registration_with_incorrect_data_should_show_registration_page() {

		String username = "";
		String email = "useremail,";
		boolean areTermsAccepted = false;

		UserPasswordDTO userPasswordDTO = UserPasswordDTO.builder().password("pass").matchingPassword("password1")
				.build();

		UserDTO userDTO = UserDTO.builder().username(username).userPasswordDTO(userPasswordDTO).email(email)
				.areTermsAccepted(areTermsAccepted).build();

		assertAll(
				() -> mockMvc
						.perform(post("/user/register").contentType(MediaType.APPLICATION_FORM_URLENCODED)
								.flashAttr("userDTO", userDTO))
						.andExpect(status().isOk()).andExpect(forwardedUrl("views/user-registration"))
						.andExpect(model().attribute("userDTO", userDTO))
						.andExpect(model().attributeHasFieldErrors("userDTO", "username"))
						.andExpect(model().attributeHasFieldErrors("userDTO", "userPasswordDTO.password"))
						.andExpect(model().attributeHasFieldErrors("userDTO", "email"))
						.andExpect(model().attributeHasFieldErrors("userDTO", "areTermsAccepted"))
						.andExpect(model().attribute("userDTO", hasProperty("username", is(username))))
						.andExpect(model().attribute("userDTO",
								hasProperty("userPasswordDTO",
										hasProperty("password", is(userPasswordDTO.getPassword())))))
						.andExpect(model().attribute("userDTO",
								hasProperty("userPasswordDTO",
										hasProperty("matchingPassword", is(userPasswordDTO.getMatchingPassword())))))
						.andExpect(model().attribute("userDTO", hasProperty("email", is(email))))
						.andExpect(model().attribute("userDTO", hasProperty("areTermsAccepted", is(areTermsAccepted)))),
				() -> verify(userRegistrationService, never()).registerUser(userDTO));
	}
}
