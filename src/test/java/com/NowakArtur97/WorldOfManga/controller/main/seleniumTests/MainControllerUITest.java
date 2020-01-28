package com.NowakArtur97.WorldOfManga.controller.main;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.NowakArtur97.WorldOfManga.controller.main.seleniumPageObjectModel.MainControllerSeleniumPOM;

import io.github.bonigarcia.wdm.WebDriverManager;

@DisplayName("Main Controller UI Tests")
@Tag("MainControllerUI_Tests")
public class MainControllerUITest {

	private WebDriver webDriver;
	private MainControllerSeleniumPOM mainPOM;

	@BeforeAll
	public static void setUp() {

		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void setupDriver() {

		webDriver = new ChromeDriver();
		mainPOM = new MainControllerSeleniumPOM(webDriver);
	}

	@AfterEach
	public void tearDown() {

		if (webDriver != null) {
			webDriver.quit();
		}
	}

	@Test
	@DisplayName("when load main page should")
	public void when_load_main_page_should_load_all_page_content() {

		mainPOM.connectTo(MainControllerSeleniumPOM.RESOURCE_PATH);

		assertAll(() -> assertNotNull(mainPOM.getHeaderText(), () -> "should load header fragment text"),
				() -> assertNotNull(mainPOM.getFooterText(), () -> "should load footer fragment text"),
				() -> assertNotNull(mainPOM.getMainPageText(), () -> "should load main fragment text"));
	}
}
