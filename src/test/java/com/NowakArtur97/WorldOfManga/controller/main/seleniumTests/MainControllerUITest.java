package com.NowakArtur97.WorldOfManga.controller.main.seleniumTests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import com.NowakArtur97.WorldOfManga.controller.main.seleniumPageObjectModel.MainControllerSeleniumPOM;
import com.NowakArtur97.WorldOfManga.testUtils.SeleniumUITest;
import com.NowakArtur97.WorldOfManga.testUtils.ScreenshotWatcher;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(ScreenshotWatcher.class)
@DisplayName("Main Controller UI Tests")
@Tag("MainControllerUI_Tests")
public class MainControllerUITest extends SeleniumUITest {

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

		mainPage.loadMainView();

		assertAll(() -> assertNotNull(mainPage.getHeaderText(), () -> "should load header fragment text"),
				() -> assertNotNull(mainPage.getFooterText(), () -> "should load footer fragment text"),
				() -> assertNotNull(mainPage.getMainPageText(), () -> "should load main fragment text"));
	}
}
