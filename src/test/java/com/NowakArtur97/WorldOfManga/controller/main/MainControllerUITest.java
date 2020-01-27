package com.NowakArtur97.WorldOfManga.controller.main;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

@DisplayName("Main Controller UI Tests")
@Tag("MainControllerUI_Tests")
public class MainControllerUITest {

	private WebDriver webDriver;
	private WebDriverWait webDriverWait;

	private final int TIME_TO_WAIT = 10;

	private static final String MAIN_PAGE = "http://localhost:8080/";
	private static final String MAIN_PAGE_CLASS = "page";
	private static final String HEADER_CLASS = "header";
	private static final String FOOTER_CLASS = "footer";

	@BeforeAll
	public static void setUp() {

		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void setupDriver() {

		webDriver = new ChromeDriver();
		webDriverWait = new WebDriverWait(webDriver, TIME_TO_WAIT);
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

		webDriver.navigate().to(MAIN_PAGE);

		webDriver.manage().window().maximize();

		WebElement header = webDriverWait
				.until(ExpectedConditions.visibilityOfElementLocated(By.className(HEADER_CLASS)));
		WebElement footer = webDriverWait
				.until(ExpectedConditions.visibilityOfElementLocated(By.className(FOOTER_CLASS)));
		WebElement main_page = webDriverWait
				.until(ExpectedConditions.visibilityOfElementLocated(By.className(MAIN_PAGE_CLASS)));

		assertAll(() -> assertNotNull(header, () -> "should load header fragment"),
				() -> assertNotNull(footer, () -> "should load footer fragment"),
				() -> assertNotNull(main_page, () -> "should load main fragment"));
	}
}
