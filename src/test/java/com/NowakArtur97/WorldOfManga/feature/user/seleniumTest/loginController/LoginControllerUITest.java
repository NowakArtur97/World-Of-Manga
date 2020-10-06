package com.NowakArtur97.WorldOfManga.feature.user.seleniumTest.loginController;

import com.NowakArtur97.WorldOfManga.controller.main.seleniumPOM.MainPage;
import com.NowakArtur97.WorldOfManga.feature.user.UserService;
import com.NowakArtur97.WorldOfManga.feature.user.seleniumPOM.LoginPage;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumUITest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

class LoginControllerUITest extends SeleniumUITest {

    @Value("${form.login.badCredentials}")
    String badCredentialsMessage;

    @Value("${header.myMangaWorld}")
    String userLoggedInMangaListOption;

    @Value("${header.signOut}")
    String userLoggedInSignOutOption;

    @Value("${header.addManga}")
    String adminAddMangaOption;

    protected LoginPage loginPage;

    protected MainPage mainPage;

    @Autowired
    protected UserService userService;

    @BeforeEach
    public void setupPOM() {

        loginPage = new LoginPage(webDriver);
        mainPage = new MainPage(webDriver);
    }
}
