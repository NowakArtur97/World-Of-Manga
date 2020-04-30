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
import com.NowakArtur97.WorldOfManga.testUtil.generator.NameWithSpacesGenerator;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(ScreenshotWatcher.class)
@TestPropertySource("classpath:/pageContent/messages_en.properties")
@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("LogoutControllerUIEn_Tests")
@DirtiesContext
class LogoutControllerUIEnTest extends LogoutControllerUITest {

	@Test
	void when_correct_logout_should_sing_out_user() {

		String username = "user";
		String password = "user";

		loginPage.loadLoginView(LanguageVersion.ENG);

		loginPage.fillMandatoryLoginFields(username, password);

		logoutPage.signOut();

		assertAll(
				() -> assertFalse(mainPage.getHeaderText().contains(userLoggedInMangaListOption),
						() -> "should not show manga list option: " + userLoggedInMangaListOption + ", but was: "
								+ mainPage.getHeaderText()),
				() -> assertFalse(mainPage.getHeaderText().contains(userLoggedInSignOutOption),
						() -> "should not show sign out option: " + userLoggedInMangaListOption + ", but was: "
								+ mainPage.getHeaderText()),
				() -> assertTrue(mainPage.getHeaderText().contains(headerRegistrationOption),
						() -> "should show manga list option: " + userLoggedInMangaListOption + ", but was: "
								+ mainPage.getHeaderText()),
				() -> assertTrue(mainPage.getHeaderText().contains(headerLoginOption),
						() -> "should show sign out option: " + userLoggedInMangaListOption + ", but was: "
								+ mainPage.getHeaderText()));
	}
}
