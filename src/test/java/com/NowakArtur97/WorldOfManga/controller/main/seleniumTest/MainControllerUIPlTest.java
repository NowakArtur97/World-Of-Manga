package com.NowakArtur97.WorldOfManga.controller.main.seleniumTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
@TestPropertySource("classpath:/pageContent/messages_pl.properties")
@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("MainControllerUIPl_Tests")
@DirtiesContext
class MainControllerUIPlTest extends MainControllerUITest {

	@Test
	void when_load_main_page_should_load_all_page_content() {

		mainPage.loadMainView(LanguageVersion.PL);

		System.out.println(mainPage.getHeaderText());
		System.out.println(headerRegistrationOption);

		assertAll(
				() -> assertTrue(mainPage.getHeaderText().contains(headerLogo),
						() -> "should load header logo: " + headerLogo + ", but was: " + mainPage.getHeaderText()),
				() -> assertTrue(mainPage.getHeaderText().contains(headerRegistrationOption),
						() -> "should load header sing up option: " + headerRegistrationOption + ", but was: "
								+ mainPage.getHeaderText()),
				() -> assertTrue(mainPage.getHeaderText().contains(headerLoginOption),
						() -> "should load header sign in option: " + headerLoginOption + ", but was: "
								+ mainPage.getHeaderText()),
				() -> assertTrue(mainPage.getHeaderText().contains(headerLanguageOption),
						() -> "should load header language option: " + headerLanguageOption + ", but was: "
								+ mainPage.getHeaderText()),
				() -> assertNotNull(mainPage.getFooterText(), () -> "should load footer fragment text"),
				() -> assertNotNull(mainPage.getMainPageText(), () -> "should load main fragment text"));
	}
}
