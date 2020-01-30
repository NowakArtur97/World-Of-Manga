package com.NowakArtur97.WorldOfManga.controller.unloggedUser.seleniumTests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import com.NowakArtur97.WorldOfManga.controller.unloggedUser.seleniumPageObjectModel.RegistrationControllerSeleniumPOM;
import com.NowakArtur97.WorldOfManga.testUtils.ControllerUITest;
import com.NowakArtur97.WorldOfManga.testUtils.ScreenshotWatcher;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(ScreenshotWatcher.class)
@DisplayName("Registration Controller UI Tests")
@Tag("RegistrationControllerUI_Tests")
public class RegistrationControllerUITest extends ControllerUITest {

	private RegistrationControllerSeleniumPOM registrationPage;

	@BeforeEach
	public void setupDriver() {

		if (webDriver != null) {

			webDriver.quit();
		}

		webDriver = new ChromeDriver();

		registrationPage = new RegistrationControllerSeleniumPOM(webDriver);
	}

	@Test
	@DisplayName("when correct registration with all fields")
	public void when_correct_registration_with_all_fields_should_register_user() {

		registrationPage.loadRegistrationView();

		registrationPage.fillAllRegistrationFields("username", "password", "password", "email@email.com", "firstName",
				"lastName");

	}
}