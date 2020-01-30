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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.NowakArtur97.WorldOfManga.controller.unloggedUser.seleniumPageObjectModel.LoginControllerSeleniumPOM;
import com.NowakArtur97.WorldOfManga.testUtils.SeleniumUITest;
import com.NowakArtur97.WorldOfManga.testUtils.LanguageVersion;
import com.NowakArtur97.WorldOfManga.testUtils.ScreenshotWatcher;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(ScreenshotWatcher.class)
@TestPropertySource("classpath:/pageContent/messages_en.properties")
@DisplayName("Registration Controller UI Eng Tests")
@Tag("RegistrationControllerUIEng_Tests")
public class LoginControllerUIEngTest extends SeleniumUITest {

	private LoginControllerSeleniumPOM loginPage;

	@Value("${form.login.afterRegistration}")
	private String afterRegistrationMessage;

	@Value("${form.login.logout}")
	private String logoutMessage;

	@Value("${form.login.badCredentials}")
	private String badCredentialsMessage;

	@Value("${form.login.accountDisabled}")
	private String accountDisabledMessage;

	@BeforeEach
	public void setupDriver() {

		if (webDriver != null) {

			webDriver.quit();
		}

		webDriver = new ChromeDriver();

		loginPage = new LoginControllerSeleniumPOM(webDriver);
	}

	@Test
	@DisplayName("when correct login")
	public void when_correct_login_should_sing_in_user() {

		loginPage.loadLoginView(LanguageVersion.ENG);

		loginPage.fillMandatoryLoginFields("user", "user");
	}
}
