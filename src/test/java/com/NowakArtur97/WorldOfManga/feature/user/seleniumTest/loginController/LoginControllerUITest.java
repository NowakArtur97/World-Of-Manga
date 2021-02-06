package com.NowakArtur97.WorldOfManga.feature.user.seleniumTest.loginController;

import com.NowakArtur97.WorldOfManga.feature.mainPage.seleniumPOM.MainPage;
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

    LoginPage loginPage;

    MainPage mainPage;

    @Autowired
    UserService userService;

    @BeforeEach
    void setupPOM() {

        loginPage = new LoginPage(webDriver, mainUrl + serverPort);
        mainPage = new MainPage(webDriver, mainUrl + serverPort);
    }
}
