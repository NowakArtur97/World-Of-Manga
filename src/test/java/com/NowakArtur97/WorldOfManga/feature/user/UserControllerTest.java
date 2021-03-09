package com.NowakArtur97.WorldOfManga.feature.user;

import com.NowakArtur97.WorldOfManga.testUtil.generator.NameWithSpacesGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.Cookie;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("User_Tests")
@Tag("Unit_Tests")
@Tag("RegistrationController_Tests")
class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserRegistrationService userRegistrationService;

    @Mock
    private UserRegistrationValidator userRegistrationValidator;

    @BeforeEach
    void setUp() {

        UserController userController = new UserController(userRegistrationService, userRegistrationValidator);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void when_load_login_page_should_show_login_page() {

        assertAll(() -> mockMvc.perform(get("/user/login")).andExpect(status().isOk())
                .andExpect(view().name("views/user-login")));
    }

    @Test
    void when_load_registration_page_should_show_registration_page() {

        assertAll(() -> mockMvc.perform(get("/user/register")).andExpect(status().isOk())
                .andExpect(view().name("views/user-registration"))
                .andExpect(model().attribute("userDTO", new UserDTO())));
    }

    @Test
    void when_process_registration_with_correct_data_should_show_login_page_with_message() {

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
    void when_process_registration_with_incorrect_data_should_show_registration_page() {

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

    @Test
    void when_sign_out_user_should_invalidate_session() {

        MockHttpSession mockSession = new MockHttpSession();

        Cookie[] cookies = {new Cookie("cookie", "cookie")};

        User user = User.builder().username("username").firstName("first name").lastName("last name")
                .password("password1").email("user@email.com").isEnabled(true).build();

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/auth/logout").session(mockSession)
                .cookie(cookies).flashAttr("user", user);

        assertAll(
                () -> mockMvc.perform(mockRequest).andExpect(redirectedUrl("/")).andExpect(status().is3xxRedirection()),
                () -> assertTrue(mockSession.isInvalid(), () -> "session should has already been invalidated"));
    }
}
