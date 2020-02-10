package com.NowakArtur97.WorldOfManga.controller.main.seleniumTest;

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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import com.NowakArtur97.WorldOfManga.controller.main.seleniumPOM.MainControllerSeleniumPOM;
import com.NowakArtur97.WorldOfManga.testUtil.enums.LanguageVersion;
import com.NowakArtur97.WorldOfManga.testUtil.extension.ScreenshotWatcher;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(ScreenshotWatcher.class)
@TestPropertySource("classpath:/pageContent/messages_pl.properties")
@DisplayName("Main Controller UI Pl Tests")
@Tag("MainControllerUIPl_Tests")
@DirtiesContext
public class MainControllerUIPlTest extends MainControllerUITest {

	private MainControllerSeleniumPOM mainPage;

	@BeforeEach
	public void setupDriver() {

		if (webDriver != null) {

			webDriver.quit();
		}

		webDriver = new ChromeDriver();

		mainPage = new MainControllerSeleniumPOM(webDriver);
	}

	@Test
	@DisplayName("when load main page")
	public void when_load_main_page_should_load_all_page_content() {

		mainPage.loadMainView(LanguageVersion.PL);

		assertAll(() -> assertTrue(mainPage.getHeaderText().contains(headerLogo), () -> "should load header logo"),
				() -> assertTrue(mainPage.getHeaderText().contains(headerRegistrationOption.toUpperCase()),
						() -> "should load header sing up option"),
				() -> assertTrue(mainPage.getHeaderText().contains(headerLoginOption.toUpperCase()),
						() -> "should load header sign in option"),
				() -> assertTrue(mainPage.getHeaderText().contains(headerLanguageOption.toUpperCase()),
						() -> "should load header language option"),
				() -> assertNotNull(mainPage.getFooterText(), () -> "should load footer fragment text"),
				() -> assertNotNull(mainPage.getMainPageText(), () -> "should load main fragment text"));
	}
}
