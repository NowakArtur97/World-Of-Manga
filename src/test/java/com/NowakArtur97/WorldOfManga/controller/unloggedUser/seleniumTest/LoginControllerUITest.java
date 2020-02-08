package com.NowakArtur97.WorldOfManga.controller.unloggedUser.seleniumTest;

import org.springframework.beans.factory.annotation.Value;

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
}
