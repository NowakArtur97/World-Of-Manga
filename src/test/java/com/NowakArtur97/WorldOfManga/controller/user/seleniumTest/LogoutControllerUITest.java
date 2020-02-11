package com.NowakArtur97.WorldOfManga.controller.user.seleniumTest;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;

import com.NowakArtur97.WorldOfManga.controller.main.seleniumPOM.MainControllerSeleniumPOM;
import com.NowakArtur97.WorldOfManga.controller.unloggedUser.seleniumPOM.LoginControllerSeleniumPOM;
import com.NowakArtur97.WorldOfManga.controller.user.seleniumPOM.LogoutControllerSeleniumPOM;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumUITest;

public class LogoutControllerUITest extends SeleniumUITest {

	@Value("${header.myMangaWorld}")
	protected String userLoggedInMangaListOption;

	@Value("${header.signOut}")
	protected String userLoggedInSignOutOption;

	@Value("${header.signUp}")
	protected String headerRegistrationOption;

	@Value("${header.signIn}")
	protected String headerLoginOption;

	protected LogoutControllerSeleniumPOM logoutPage;

	protected LoginControllerSeleniumPOM loginPage;

	protected MainControllerSeleniumPOM mainPage;

	@BeforeEach
	public void setupPOM() {

		logoutPage = new LogoutControllerSeleniumPOM(webDriver);
		loginPage = new LoginControllerSeleniumPOM(webDriver);
		mainPage = new MainControllerSeleniumPOM(webDriver);
	}
}