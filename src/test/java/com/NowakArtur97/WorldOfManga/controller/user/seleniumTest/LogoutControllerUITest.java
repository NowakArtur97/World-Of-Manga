package com.NowakArtur97.WorldOfManga.controller.user.seleniumTest;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;

import com.NowakArtur97.WorldOfManga.controller.main.seleniumPOM.MainPage;
import com.NowakArtur97.WorldOfManga.controller.unloggedUser.seleniumPOM.LoginPage;
import com.NowakArtur97.WorldOfManga.controller.user.seleniumPOM.LogoutPage;
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

	protected LogoutPage logoutPage;

	protected LoginPage loginPage;

	protected MainPage mainPage;

	@BeforeEach
	public void setupPOM() {

		logoutPage = new LogoutPage(webDriver);
		loginPage = new LoginPage(webDriver);
		mainPage = new MainPage(webDriver);
	}
}