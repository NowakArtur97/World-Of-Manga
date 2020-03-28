package com.NowakArtur97.WorldOfManga.controller.user.seleniumTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import com.NowakArtur97.WorldOfManga.testUtil.enums.LanguageVersion;
import com.NowakArtur97.WorldOfManga.testUtil.extension.ScreenshotWatcher;
import com.NowakArtur97.WorldOfManga.testUtil.generator.ReplaceUnderscoresGenerator;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(ScreenshotWatcher.class)
@TestPropertySource("classpath:/pageContent/messages_en.properties")
@DisplayNameGeneration(ReplaceUnderscoresGenerator.class)
@Tag("LogoutControllerUIEn_Tests")
@DirtiesContext
public class LogoutControllerUIEnTest extends LogoutControllerUITest {

	@Test
	public void when_correct_logout_should_sing_out_user() {

		String username = "user";
		String password = "user";

		loginPage.loadLoginView(LanguageVersion.ENG);

		loginPage.fillMandatoryLoginFields(username, password);

		logoutPage.signOut();

		assertAll(
				() -> assertFalse(mainPage.getHeaderText().contains(userLoggedInMangaListOption.toUpperCase()),
						() -> "should not show manga list option"),
				() -> assertFalse(mainPage.getHeaderText().contains(userLoggedInSignOutOption.toUpperCase()),
						() -> "should not show sign out option"),
				() -> assertTrue(mainPage.getHeaderText().contains(headerRegistrationOption.toUpperCase()),
						() -> "should show manga list option"),
				() -> assertTrue(mainPage.getHeaderText().contains(headerLoginOption.toUpperCase()),
						() -> "should show sign out option"));
	}
}
