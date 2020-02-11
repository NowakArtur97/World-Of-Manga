package com.NowakArtur97.WorldOfManga.controller.main.seleniumTest;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;

import com.NowakArtur97.WorldOfManga.controller.main.seleniumPOM.MainControllerSeleniumPOM;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumUITest;

public class MainControllerUITest extends SeleniumUITest {

	@Value("${header.logo}")
	protected String headerLogo;

	@Value("${header.signUp}")
	protected String headerRegistrationOption;

	@Value("${header.signIn}")
	protected String headerLoginOption;

	@Value("${header.language}")
	protected String headerLanguageOption;

	protected MainControllerSeleniumPOM mainPage;
	
	@BeforeEach
	public void setupPOM() {

		mainPage = new MainControllerSeleniumPOM(webDriver);
	}
}
