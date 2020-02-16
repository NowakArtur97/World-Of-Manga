package com.NowakArtur97.WorldOfManga.controller.author.seleniumTest;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.NowakArtur97.WorldOfManga.controller.author.seleniumPOM.AuthorControllerSeleniumPOM;
import com.NowakArtur97.WorldOfManga.controller.unloggedUser.seleniumPOM.LoginControllerSeleniumPOM;
import com.NowakArtur97.WorldOfManga.service.api.AuthorService;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumUITest;

public class AuthorControllerUITest extends SeleniumUITest {

	@Value("${author.fullName.notBlank}")
	protected String authorFulNameNotBlankMessage;

	@Value("${author.fullName.size}")
	protected String authorFullNameSizeMessage;

	@Value("${author.fullName.alreadySaved}")
	protected String authorAlreadySavedMessage;

	protected AuthorControllerSeleniumPOM authorFormPage;

	protected LoginControllerSeleniumPOM loginPage;

	@Autowired
	protected AuthorService authorService;

	@BeforeEach
	public void setupPOM() {

		loginPage = new LoginControllerSeleniumPOM(webDriver);

		authorFormPage = new AuthorControllerSeleniumPOM(webDriver);
	}
}
