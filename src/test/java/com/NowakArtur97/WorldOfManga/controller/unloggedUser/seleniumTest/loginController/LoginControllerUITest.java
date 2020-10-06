package com.NowakArtur97.WorldOfManga.controller.unloggedUser.seleniumTest.loginController;

import com.NowakArtur97.WorldOfManga.controller.main.seleniumPOM.MainPage;
import com.NowakArtur97.WorldOfManga.controller.unloggedUser.seleniumPOM.LoginPage;
import com.NowakArtur97.WorldOfManga.service.UserService;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumUITest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class LoginControllerUITest extends SeleniumUITest {

    @Value("${form.login.badCredentials}")
    protected String badCredentialsMessage;

    @Value("${form.login.accountDisabled}")
    protected String accountDisabledMessage;

    @Value("${header.myMangaWorld}")
    protected String userLoggedInMangaListOption;

    @Value("${header.signOut}")
    protected String userLoggedInSignOutOption;

    @Value("${header.addManga}")
    protected String adminAddMangaOption;

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
