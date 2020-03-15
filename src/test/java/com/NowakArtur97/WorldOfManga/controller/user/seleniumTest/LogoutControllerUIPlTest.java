package com.NowakArtur97.WorldOfManga.controller.user.seleniumTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;

import com.NowakArtur97.WorldOfManga.testUtil.enums.LanguageVersion;
import com.NowakArtur97.WorldOfManga.testUtil.extension.ScreenshotWatcher;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(ScreenshotWatcher.class)
@TestPropertySource("classpath:/pageContent/messages_pl.properties")
@DisplayName("Logout Controller UI Pl Tests")
@Tag("LogoutControllerUIPl_Tests")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class LogoutControllerUIPlTest extends LogoutControllerUITest {

	@Test
	@DisplayName("when correct logout")
	public void when_correct_logout_should_sing_out_user() {

		String username = "user";
		String password = "user";

		loginPage.loadLoginView(LanguageVersion.PL);

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
