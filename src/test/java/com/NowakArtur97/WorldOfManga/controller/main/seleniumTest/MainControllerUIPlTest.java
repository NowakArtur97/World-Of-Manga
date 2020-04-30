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
		
		assertAll(
				() -> assertTrue(mainPage.getHeaderTextInLowerCase().contains(headerLogo.toLowerCase()),
						() -> "should load header logo: " + headerLogo.toLowerCase() + ", but was: "
								+ mainPage.getHeaderTextInLowerCase()),
				() -> assertTrue(mainPage.getHeaderTextInLowerCase().contains(headerRegistrationOption.toLowerCase()),
						() -> "should load header sing up option: " + headerRegistrationOption.toLowerCase()
								+ ", but was: " + mainPage.getHeaderTextInLowerCase()),
				() -> assertTrue(mainPage.getHeaderTextInLowerCase().contains(headerLoginOption.toLowerCase()),
						() -> "should load header sign in option: " + headerLoginOption.toLowerCase() + ", but was: "
								+ mainPage.getHeaderTextInLowerCase()),
				() -> assertTrue(mainPage.getHeaderTextInLowerCase().contains(headerLanguageOption.toLowerCase()),
						() -> "should load header language option: " + headerLanguageOption.toLowerCase()
								+ ", but was: " + mainPage.getHeaderTextInLowerCase()),
				() -> assertNotNull(mainPage.getFooterText(),
						() -> "should load footer fragment text, but wasn`t found"),
				() -> assertNotNull(mainPage.getMainPageText(),
						() -> "should load main fragment text, but wasn`t found"));
	}
}
