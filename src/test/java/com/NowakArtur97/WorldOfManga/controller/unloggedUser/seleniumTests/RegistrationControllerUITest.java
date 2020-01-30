package com.NowakArtur97.WorldOfManga.controller.unloggedUser.seleniumTests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.NowakArtur97.WorldOfManga.controller.unloggedUser.seleniumPageObjectModel.LoginControllerSeleniumPOM;
import com.NowakArtur97.WorldOfManga.controller.unloggedUser.seleniumPageObjectModel.RegistrationControllerSeleniumPOM;
import com.NowakArtur97.WorldOfManga.service.api.UserService;
import com.NowakArtur97.WorldOfManga.testUtils.ControllerUITest;
import com.NowakArtur97.WorldOfManga.testUtils.LanguageVersion;
import com.NowakArtur97.WorldOfManga.testUtils.ScreenshotWatcher;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(ScreenshotWatcher.class)
@DisplayName("Registration Controller UI Tests")
@Tag("RegistrationControllerUI_Tests")
public class RegistrationControllerUITest extends ControllerUITest {

	private RegistrationControllerSeleniumPOM registrationPage;

	private LoginControllerSeleniumPOM loginPage;

	@Autowired
	private UserService userService;

	@BeforeEach
	public void setupDriver() {

		if (webDriver != null) {

			webDriver.quit();
		}

		webDriver = new ChromeDriver();

		registrationPage = new RegistrationControllerSeleniumPOM(webDriver);
		loginPage = new LoginControllerSeleniumPOM(webDriver);
	}

	@Nested
	@DisplayName("Registration Controller UI Eng Tests")
	@Tag("RegistrationControllerUIEng_Tests")
	class RegistrationControllerUIEngTest {

		@Test
		@DisplayName("when correct registration with all fields")
		public void when_correct_registration_with_all_fields_should_register_user() {

			String username = "user name 123";

			registrationPage.loadRegistrationView(LanguageVersion.ENG);

			registrationPage.fillAllRegistrationFields(username, "password", "password", "email@email.com", "firstName",
					"lastName");

			assertAll(() -> assertNotNull(loginPage.getSuccessMessage(), "should show success registration message"),
					() -> assertTrue(userService.isUsernameAlreadyInUse(username),
							() -> "should save user in database"));
		}

		@Test
		@DisplayName("when username is already in use (eng ver)")
		public void when_username_is_already_in_use_should_have_error_ENG() {

			registrationPage.loadRegistrationView(LanguageVersion.ENG);

			registrationPage.fillMandatoryRegistrationFields("user", "password", "password", "email@email.com");

			assertAll(() -> assertTrue(registrationPage.countFailureMessages() == 1, () -> "should have error"),
					() -> assertTrue(registrationPage.getFormBoxText().contains("Username already in use"),
							() -> "should show username already in use message"));
		}

		@Test
		@DisplayName("when email is already in use (eng ver)")
		public void when_email_is_already_in_use_should_have_error_ENG() {

			registrationPage.loadRegistrationView(LanguageVersion.ENG);

			registrationPage.fillMandatoryRegistrationFields("username", "password", "password", "user@email.com");

			assertAll(() -> assertTrue(registrationPage.countFailureMessages() == 1, () -> "should have error"),
					() -> assertTrue(registrationPage.getFormBoxText().contains("Email already in use"),
							() -> "should show email already in use message"));
		}

		@Test
		@DisplayName("when incorrect registration with mandatory fields (eng ver)")
		public void when_incorrect_registration_with_mandatory_fields_should_have_errors_ENG() {

			registrationPage.loadRegistrationView(LanguageVersion.ENG);

			registrationPage.fillMandatoryRegistrationFields("", "", "password", "email,");

			assertAll(() -> assertTrue(registrationPage.countFailureMessages() == 3, () -> "should have three errors"),
					() -> assertTrue(registrationPage.getFormBoxText().contains("Username is a required field"),
							() -> "should show username is a required field message"),
					() -> assertTrue(registrationPage.getFormBoxText().contains("Password fields must match"),
							() -> "should password fields must match message"),
					() -> assertTrue(registrationPage.getFormBoxText().contains("Invalid email format"),
							() -> "should show invalid email format message"));
		}

		@Test
		@DisplayName("when incorrect registration with all fields (eng ver)")
		public void when_incorrect_registration_with_all_fields_should_have_errors_ENG() {

			registrationPage.loadRegistrationView(LanguageVersion.ENG);

			registrationPage.fillAllRegistrationFields("", "", "", "email,",
					"asdfghjklpasdfghjklpasdfghjklpasdfghjklpasdfghjklp",
					"asdfghjklpasdfghjklpasdfghjklpasdfghjklpasdfghjklp");

			assertAll(() -> assertTrue(registrationPage.countFailureMessages() == 6, () -> "should have five errors"),
					() -> assertTrue(registrationPage.getFormBoxText().contains("Username is a required field"),
							() -> "should show username is a required field message"),
					() -> assertTrue(registrationPage.getFormBoxText().contains("Password is a required field"),
							() -> "should password fields must match message"),
					() -> assertTrue(registrationPage.getFormBoxText().contains("Invalid email format"),
							() -> "should show invalid email format message"),
					() -> assertTrue(registrationPage.getFormBoxText().contains("Maximal length of first name is %1$s"),
							() -> "should show exceeded maximal length of first name message"),
					() -> assertTrue(registrationPage.getFormBoxText().contains("Maximal length of last name is %1$s"),
							() -> "should show exceeded maximal length of last name message"));
		}
	}
}