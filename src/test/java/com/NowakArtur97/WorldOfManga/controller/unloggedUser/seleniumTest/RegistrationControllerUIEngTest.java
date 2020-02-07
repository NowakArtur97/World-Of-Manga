package com.NowakArtur97.WorldOfManga.controller.unloggedUser.seleniumTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.NowakArtur97.WorldOfManga.controller.unloggedUser.seleniumPOM.RegistrationControllerSeleniumPOM;
import com.NowakArtur97.WorldOfManga.service.api.UserService;
import com.NowakArtur97.WorldOfManga.testUtil.LanguageVersion;
import com.NowakArtur97.WorldOfManga.testUtil.ScreenshotWatcher;
import com.NowakArtur97.WorldOfManga.testUtil.SeleniumUITest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(ScreenshotWatcher.class)
@TestPropertySource({ "classpath:/validation/messages_en.properties", "classpath:/pageContent/messages_en.properties" })
@DisplayName("Registration Controller UI Eng Tests")
@Tag("RegistrationControllerUIEng_Tests")
public class RegistrationControllerUIEngTest extends SeleniumUITest {

	private RegistrationControllerSeleniumPOM registrationPage;

	@Value("${user.username.notBlank}")
	private String usernameNotBlankMessage;

	@Value("${user.username.size}")
	private String usernameSizeMessage;

	@Value("${user.username.inUse}")
	private String usernameInUseMessage;

	@Value("${user.email.notBlank}")
	private String emailNotBlankMessage;

	@Value("${user.email.inUse}")
	private String emailInUseMessage;

	@Value("${user.email.size}")
	private String emailSizeMessage;

	@Value("${user.email.emailFormat}")
	private String emailFormatMessage;

	@Value("${user.firstName.size}")
	private String firstNameSizeMessage;

	@Value("${user.lastName.size}")
	private String lastNameSizeMessage;

	@Value("${user.areTermsAccepted.assertTrue}")
	private String usernameTermsMessage;

	@Value("${userPassword.password.matchingFields}")
	private String passwordMatchingFieldsMessage;

	@Value("${userPassword.password.notBlank}")
	private String passwordFieldsNotBlankMessage;

	@Value("${form.login.afterRegistration}")
	private String afterRegistrationMessage;

	@Autowired
	private UserService userService;

	@BeforeEach
	public void setupDriver() {

		if (webDriver != null) {

			webDriver.quit();
		}

		webDriver = new ChromeDriver();

		registrationPage = new RegistrationControllerSeleniumPOM(webDriver);
	}

	@Test
	@DisplayName("when username is already in use")
	public void when_username_is_already_in_use_should_have_error() {

		registrationPage.loadRegistrationView(LanguageVersion.ENG);

		registrationPage.fillMandatoryRegistrationFields("user", "password", "password", "email@email.com", true);

		assertAll(() -> assertTrue(registrationPage.countFailureMessages() == 1, () -> "should have error"),
				() -> assertTrue(registrationPage.getFormBoxText().contains(usernameInUseMessage),
						() -> "should show username already in use message"));
	}

	@Test
	@DisplayName("when email is already in use")
	public void when_email_is_already_in_use_should_have_error() {

		registrationPage.loadRegistrationView(LanguageVersion.ENG);

		registrationPage.fillMandatoryRegistrationFields("username", "password", "password", "user@email.com", true);

		assertAll(() -> assertTrue(registrationPage.countFailureMessages() == 1, () -> "should have error"),
				() -> assertTrue(registrationPage.getFormBoxText().contains(emailInUseMessage),
						() -> "should show email already in use message"));
	}

	@Test
	@DisplayName("when incorrect registration with mandatory fields")
	public void when_incorrect_registration_with_mandatory_fields_should_have_errors() {

		registrationPage.loadRegistrationView(LanguageVersion.ENG);

		registrationPage.fillMandatoryRegistrationFields("", "", "password", "", true);

		assertAll(() -> assertTrue(registrationPage.countFailureMessages() == 3, () -> "should have three errors"),
				() -> assertTrue(registrationPage.getFormBoxText().contains(usernameNotBlankMessage),
						() -> "should show username is a required field message"),
				() -> assertTrue(registrationPage.getFormBoxText().contains(passwordMatchingFieldsMessage),
						() -> "should password fields must match message"),
				() -> assertTrue(registrationPage.getFormBoxText().contains(emailNotBlankMessage),
						() -> "should show email is a required field message"));
	}

	@Test
	@DisplayName("when incorrect registration fields size with mandatory fields")
	public void when_incorrect_registration_fields_size_with_mandatory_fields_should_have_errors() {

		registrationPage.loadRegistrationView(LanguageVersion.ENG);

		registrationPage.fillMandatoryRegistrationFields("asdfghjklpasdfghjklpasdfghjklpasdfghjklpasdfghjklp", "",
				"password", "email,", false);

		assertAll(() -> assertTrue(registrationPage.countFailureMessages() == 4, () -> "should have four errors"),
				() -> assertTrue(registrationPage.getFormBoxText().contains(usernameSizeMessage),
						() -> "should show username size exceed message"),
				() -> assertTrue(registrationPage.getFormBoxText().contains(passwordMatchingFieldsMessage),
						() -> "should password fields must match message"),
				() -> assertTrue(registrationPage.getFormBoxText().contains(emailFormatMessage),
						() -> "should show invalid email format message"),
				() -> assertTrue(registrationPage.getFormBoxText().contains(usernameTermsMessage),
						() -> "should show terms not accepted message"));
	}

	@Test
	@DisplayName("when incorrect registration with all fields")
	public void when_incorrect_registration_with_all_fields_should_have_errors() {

		registrationPage.loadRegistrationView(LanguageVersion.ENG);

		registrationPage.fillAllRegistrationFields("", "", "", "", "asdfghjklpasdfghjklpasdfghjklpasdfghjklpasdfghjklp",
				"asdfghjklpasdfghjklpasdfghjklpasdfghjklpasdfghjklp", true);

		System.out.println(firstNameSizeMessage);

		assertAll(() -> assertTrue(registrationPage.countFailureMessages() == 6, () -> "should have six errors"),
				() -> assertTrue(registrationPage.getFormBoxText().contains(usernameNotBlankMessage),
						() -> "should show username is a required field message"),
				() -> assertTrue(registrationPage.getFormBoxText().contains(passwordFieldsNotBlankMessage),
						() -> "should password fields must match message"),
				() -> assertTrue(registrationPage.getFormBoxText().contains(firstNameSizeMessage),
						() -> "should show exceeded maximal length of first name message"),
				() -> assertTrue(registrationPage.getFormBoxText().contains(lastNameSizeMessage),
						() -> "should show exceeded maximal length of last name message"),
				() -> assertTrue(registrationPage.getFormBoxText().contains(emailNotBlankMessage),
						() -> "should show email is a required field message"));
	}

	@Test
	@DisplayName("when correct registration with all fields")
	public void when_correct_registration_with_all_fields_should_register_user() {

		String username = "user name 123";

		registrationPage.loadRegistrationView(LanguageVersion.ENG);

		registrationPage.fillAllRegistrationFields(username, "password", "password", "user123@email.com", "firstName",
				"lastName", true);

		assertAll(() -> assertTrue(userService.isUsernameAlreadyInUse(username), () -> "should save user in database"),
				() -> assertTrue(registrationPage.getFormBoxText().contains(afterRegistrationMessage),
						() -> "should show success registration message"));
	}
}