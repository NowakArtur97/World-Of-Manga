package com.NowakArtur97.WorldOfManga.testUtil.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;

public class SeleniumUITest {

	@Getter
	protected static WebDriver webDriver;

	@BeforeEach
	public void setUp() {

		WebDriverManager.chromedriver().setup();
	}

	@AfterEach
	public void tearDown() {

		if (webDriver != null) {

			webDriver.quit();
		}
	}
}
