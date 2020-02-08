package com.NowakArtur97.WorldOfManga.controller.unloggedUser.seleniumTest;

import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.NowakArtur97.WorldOfManga.controller.unloggedUser.seleniumPOM.RegistrationControllerSeleniumPOM;
import com.NowakArtur97.WorldOfManga.service.api.UserService;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumUITest;

public class RegistrationControllerUITest extends SeleniumUITest {

	@Value("${user.username.notBlank}")
	protected String usernameNotBlankMessage;

	@Value("${user.username.size}")
	protected String usernameSizeMessage;

	@Value("${user.username.inUse}")
	protected String usernameInUseMessage;

	@Value("${user.email.notBlank}")
	protected String emailNotBlankMessage;

	@Value("${user.email.inUse}")
	protected String emailInUseMessage;

	@Value("${user.email.size}")
	protected String emailSizeMessage;

	@Value("${user.email.emailFormat}")
	protected String emailFormatMessage;

	@Value("${user.firstName.size}")
	protected String firstNameSizeMessage;

	@Value("${user.lastName.size}")
	protected String lastNameSizeMessage;

	@Value("${user.areTermsAccepted.assertTrue}")
	protected String usernameTermsMessage;

	@Value("${userPassword.password.matchingFields}")
	protected String passwordMatchingFieldsMessage;

	@Value("${userPassword.password.notBlank}")
	protected String passwordFieldsNotBlankMessage;

	@Value("${form.login.afterRegistration}")
	protected String afterRegistrationMessage;
	
	protected RegistrationControllerSeleniumPOM registrationPage;

	@Autowired
	protected UserService userService;

	@BeforeEach
	public void setupDriver() {

		if (webDriver != null) {

			webDriver.quit();
		}

		webDriver = new ChromeDriver();

		registrationPage = new RegistrationControllerSeleniumPOM(webDriver);
	}
}
