package com.NowakArtur97.WorldOfManga.feature.user.seleniumTest.registrationController;

import com.NowakArtur97.WorldOfManga.feature.user.UserService;
import com.NowakArtur97.WorldOfManga.feature.user.seleniumPOM.RegistrationPage;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumUITest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

class UserControllerUITest extends SeleniumUITest {

    @Value("${user.username.notBlank}")
    String usernameNotBlankMessage;

    @Value("${user.username.size}")
    String usernameSizeMessage;

    @Value("${user.username.inUse}")
    String usernameInUseMessage;

    @Value("${user.email.notBlank}")
    String emailNotBlankMessage;

    @Value("${user.email.inUse}")
    String emailInUseMessage;

    @Value("${user.email.size}")
    String emailSizeMessage;

    @Value("${user.email.emailFormat}")
    String emailFormatMessage;

    @Value("${user.firstName.size}")
    String firstNameSizeMessage;

    @Value("${user.lastName.size}")
    String lastNameSizeMessage;

    @Value("${user.areTermsAccepted.assertTrue}")
    String usernameTermsMessage;

    @Value("${userPassword.password.matchingFields}")
    String passwordMatchingFieldsMessage;

    @Value("${userPassword.password.notBlank}")
    String passwordFieldsNotBlankMessage;

    @Value("${form.login.afterRegistration}")
    String afterRegistrationMessage;

    RegistrationPage registrationPage;

    @Autowired
    UserService userService;

    void launchBrowser(String browserName, String language) {

        setUp(browserName, language);

        registrationPage = new RegistrationPage(webDriver, mainUrl + localServerPort);
    }
}
