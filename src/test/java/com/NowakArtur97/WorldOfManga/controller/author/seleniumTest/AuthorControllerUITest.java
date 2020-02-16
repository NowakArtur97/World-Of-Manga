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
	protected String authorFulNameSizeMessage;

	@Value("${author.fullName.size}")
	protected String authorFulNameNotBlankMessage;

	@Value("${author.fullName.alreadySaved}")
	protected String authorAlreadySavedMessage;

	protected AuthorControllerSeleniumPOM authorForm;

	protected LoginControllerSeleniumPOM loginPage;

	@Autowired
	protected AuthorService authorService;

	@BeforeEach
	public void setupPOM() {

		loginPage = new LoginControllerSeleniumPOM(webDriver);

		authorForm = new AuthorControllerSeleniumPOM(webDriver);
	}
}
