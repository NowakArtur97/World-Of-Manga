package com.NowakArtur97.WorldOfManga.controller.unloggedUser.seleniumTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.NowakArtur97.WorldOfManga.controller.main.seleniumPOM.MainControllerSeleniumPOM;
import com.NowakArtur97.WorldOfManga.controller.unloggedUser.seleniumPOM.LoginControllerSeleniumPOM;
import com.NowakArtur97.WorldOfManga.service.api.UserService;
import com.NowakArtur97.WorldOfManga.testUtil.enums.LanguageVersion;
import com.NowakArtur97.WorldOfManga.testUtil.extension.ScreenshotWatcher;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(ScreenshotWatcher.class)
@TestPropertySource("classpath:/pageContent/messages_pl.properties")
@DisplayName("Registration Controller UI Pl Tests")
@Tag("RegistrationControllerUIPl_Tests")
public class LoginControllerUIPlTest extends LoginControllerUITest {

	private LoginControllerSeleniumPOM loginPage;

	private MainControllerSeleniumPOM mainPage;

	@Autowired
	private UserService userService;
	
	@BeforeEach
	public void setupDriver() {

		if (webDriver != null) {

			webDriver.quit();
		}

		webDriver = new ChromeDriver();

		loginPage = new LoginControllerSeleniumPOM(webDriver);
		mainPage = new MainControllerSeleniumPOM(webDriver);
	}

	@Test
	@DisplayName("when login with bad credentials")
	public void when_login_with_bad_credentials_should_show_bad_credentials_message() {

		String username = "resu";
		String password = "resu";

		loginPage.loadLoginView(LanguageVersion.PL);

		loginPage.fillMandatoryLoginFields(username, password);

		assertAll(
				() -> assertTrue(loginPage.getFormBoxText().contains(badCredentialsMessage),
						() -> "should show bad credentails message"),
				() -> assertFalse(userService.isUsernameAlreadyInUse(username),
						() -> "user with given username shouldn`t exist: " + username));
	}

	@Test
	@DisplayName("when correct login")
	public void when_correct_login_should_sing_in_user() {

		String username = "user";
		String password = "user";

		loginPage.loadLoginView(LanguageVersion.PL);

		loginPage.fillMandatoryLoginFields(username, password);

		assertAll(
				() -> assertTrue(userService.isUsernameAlreadyInUse(username),
						() -> "user with given username should exist: " + username),
				() -> assertTrue(mainPage.getHeaderText().contains(userLoggedInMangaListOption.toUpperCase()),
						() -> "should show manga list option"),
				() -> assertTrue(mainPage.getHeaderText().contains(userLoggedInSignOutOption.toUpperCase()),
						() -> "should show sign out option"));
	}
}
