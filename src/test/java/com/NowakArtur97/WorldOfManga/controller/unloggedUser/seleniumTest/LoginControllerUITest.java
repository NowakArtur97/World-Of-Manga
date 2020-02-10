package com.NowakArtur97.WorldOfManga.controller.unloggedUser.seleniumTest;

import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.LocalServerPort;

import com.NowakArtur97.WorldOfManga.controller.main.seleniumPOM.MainControllerSeleniumPOM;
import com.NowakArtur97.WorldOfManga.controller.unloggedUser.seleniumPOM.LoginControllerSeleniumPOM;
import com.NowakArtur97.WorldOfManga.service.api.UserService;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumUITest;

public class LoginControllerUITest extends SeleniumUITest {

	@Value("${form.login.badCredentials}")
	protected String badCredentialsMessage;

	@Value("${form.login.accountDisabled}")
	protected String accountDisabledMessage;

	@Value("${header.myMangaWorld}")
	protected String userLoggedInMangaListOption;

	@Value("${header.signOut}")
	protected String userLoggedInSignOutOption;
	
	protected LoginControllerSeleniumPOM loginPage;

	protected MainControllerSeleniumPOM mainPage;

	@LocalServerPort
	private int serverPort;
	
	@Autowired
	protected UserService userService;
	
	@BeforeEach
	public void setupDriver() {

		if (webDriver != null) {

			webDriver.quit();
		}

		webDriver = new ChromeDriver();

		loginPage = new LoginControllerSeleniumPOM(webDriver, serverPort);
		mainPage = new MainControllerSeleniumPOM(webDriver, serverPort);
	}
}
