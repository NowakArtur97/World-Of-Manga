package com.NowakArtur97.WorldOfManga.controller.main.seleniumTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import com.NowakArtur97.WorldOfManga.controller.main.seleniumPOM.MainControllerSeleniumPOM;
import com.NowakArtur97.WorldOfManga.testUtil.enums.LanguageVersion;
import com.NowakArtur97.WorldOfManga.testUtil.extension.ScreenshotWatcher;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(ScreenshotWatcher.class)
@TestPropertySource("classpath:/pageContent/messages_en.properties")
@DisplayName("Main Controller UI Eng Tests")
@Tag("MainControllerUIEng_Tests")
@DirtiesContext
public class MainControllerUIEngTest extends MainControllerUITest {

	private MainControllerSeleniumPOM mainPage;

	@BeforeEach
	public void setupDriver() throws MalformedURLException {

		if (webDriver != null) {

			webDriver.quit();
		}

		String nodeURL = "http://localhost:4444/wd/hub";

		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		capabilities.setBrowserName("chrome");

		webDriver = new RemoteWebDriver(new URL(nodeURL), capabilities);
		webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		mainPage = new MainControllerSeleniumPOM(webDriver);
	}

	@Test
	@DisplayName("when load main page")
	public void when_load_main_page_should_load_all_page_content() {

		mainPage.loadMainView(LanguageVersion.ENG);

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
