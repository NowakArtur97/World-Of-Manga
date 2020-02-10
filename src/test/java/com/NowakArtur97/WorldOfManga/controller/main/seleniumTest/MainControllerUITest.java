package com.NowakArtur97.WorldOfManga.controller.main.seleniumTest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.LocalServerPort;

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
	
	@LocalServerPort
	protected int serverPort;
}
