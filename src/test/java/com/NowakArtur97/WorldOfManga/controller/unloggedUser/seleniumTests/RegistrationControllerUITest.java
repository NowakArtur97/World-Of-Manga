package com.NowakArtur97.WorldOfManga.controller.unloggedUser.seleniumTests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import com.NowakArtur97.WorldOfManga.controller.unloggedUser.seleniumPageObjectModel.LoginControllerSeleniumPOM;
import com.NowakArtur97.WorldOfManga.controller.unloggedUser.seleniumPageObjectModel.RegistrationControllerSeleniumPOM;
import com.NowakArtur97.WorldOfManga.testUtils.ControllerUITest;
import com.NowakArtur97.WorldOfManga.testUtils.ScreenshotWatcher;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(ScreenshotWatcher.class)
@DisplayName("Registration Controller UI Tests")
@Tag("RegistrationControllerUI_Tests")
public class RegistrationControllerUITest extends ControllerUITest {

	private RegistrationControllerSeleniumPOM registrationPage;

	private LoginControllerSeleniumPOM loginPage;

	@BeforeEach
	public void setupDriver() {

		if (webDriver != null) {

			webDriver.quit();
		}

		webDriver = new ChromeDriver();

		registrationPage = new RegistrationControllerSeleniumPOM(webDriver);
		loginPage = new LoginControllerSeleniumPOM(webDriver);
	}

	@Test
	@DisplayName("when correct registration with all fields")
	public void when_correct_registration_with_all_fields_should_register_user() {

		registrationPage.loadRegistrationView();

		registrationPage.fillAllRegistrationFields("user name", "password", "password", "email@email.com", "firstName",
				"lastName");

		assertAll(() -> assertNotNull(loginPage.getSuccessMessage()));
	}

	@Test
	@DisplayName("when username is already in use")
	public void when_username_is_already_in_use_should_have_error() {

		registrationPage.loadRegistrationView();

		registrationPage.fillMandatoryRegistrationFields("user", "password", "password", "email@email.com");

		assertAll(() -> assertTrue(registrationPage.countFailureMessages() == 1, () -> "should have error"));
	}
	
	@Test
	@DisplayName("when email is already in use")
	public void when_email_is_already_in_use_should_have_error() throws InterruptedException {

		registrationPage.loadRegistrationView();

		registrationPage.fillMandatoryRegistrationFields("username", "password", "password", "user@email.com");

		Thread.sleep(5000);
		
		assertAll(() -> assertTrue(registrationPage.countFailureMessages() == 1, () -> "should have error"));
	}
	
	@Test
	@DisplayName("when incorrect registration with mandatory fields")
	public void when_incorrect_registration_with_mandatory_fields_should_have_errors() {

		registrationPage.loadRegistrationView();

		registrationPage.fillMandatoryRegistrationFields("", "", "password", "email,");

		assertAll(() -> assertTrue(registrationPage.countFailureMessages() == 3, () -> "should have three errors"));
	}
	
	@Test
	@DisplayName("when incorrect registration with all fields")
	public void when_incorrect_registration_with_all_fields_should_have_errors() {

		registrationPage.loadRegistrationView();

		registrationPage.fillAllRegistrationFields("", "", "password", "email,", "asdfghjklpasdfghjklpasdfghjklpasdfghjklpasdfghjklp", "asdfghjklpasdfghjklpasdfghjklpasdfghjklpasdfghjklp");
		
		assertAll(() -> assertTrue(registrationPage.countFailureMessages() == 5, () -> "should have five errors"));
	}
}