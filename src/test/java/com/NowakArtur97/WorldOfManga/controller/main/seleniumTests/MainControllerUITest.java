package com.NowakArtur97.WorldOfManga.controller.main.seleniumTests;

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
import org.springframework.boot.test.context.SpringBootTest;

import com.NowakArtur97.WorldOfManga.controller.main.seleniumPageObjectModel.MainControllerSeleniumPOM;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DisplayName("Main Controller UI Tests")
@Tag("MainControllerUI_Tests")
public class MainControllerUITest {

	private WebDriver webDriver;
	private MainControllerSeleniumPOM mainPage;

	@BeforeAll
	public static void setUp() {

		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void setupDriver() {

		webDriver = new ChromeDriver();
		mainPage = new MainControllerSeleniumPOM(webDriver);
	}

	@AfterEach
	public void tearDown() {

		if (webDriver != null) {
			webDriver.quit();
		}
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
