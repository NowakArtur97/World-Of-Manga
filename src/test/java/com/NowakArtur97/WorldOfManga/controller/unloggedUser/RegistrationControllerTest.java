package com.NowakArtur97.WorldOfManga.controller.unloggedUser;

import com.NowakArtur97.WorldOfManga.dto.UserDTO;
import com.NowakArtur97.WorldOfManga.dto.UserPasswordDTO;
import com.NowakArtur97.WorldOfManga.service.UserRegistrationService;
import com.NowakArtur97.WorldOfManga.testUtil.generator.NameWithSpacesGenerator;
import com.NowakArtur97.WorldOfManga.validation.user.UserRegistrationValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(NameWithSpacesGenerator.class)
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
    public void when_load_registration_page_should_show_registration_page() {

        assertAll(() -> mockMvc.perform(get("/user/register")).andExpect(status().isOk())
                .andExpect(view().name("views/user-registration"))
                .andExpect(model().attribute("userDTO", new UserDTO())));
    }

    @Test
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
