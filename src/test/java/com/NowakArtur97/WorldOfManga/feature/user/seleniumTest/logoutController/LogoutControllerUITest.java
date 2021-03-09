package com.NowakArtur97.WorldOfManga.feature.user.seleniumTest.logoutController;

import com.NowakArtur97.WorldOfManga.feature.mainPage.seleniumPOM.MainPage;
import com.NowakArtur97.WorldOfManga.feature.user.seleniumPOM.LoginPage;
import com.NowakArtur97.WorldOfManga.feature.user.seleniumPOM.LogoutPage;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumUITest;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Value;

@Tag("User_Tests")
class LogoutControllerUITest extends SeleniumUITest {

    @Value("${header.myMangaWorld}")
    String userLoggedInMangaListOption;

    @Value("${header.signOut}")
    String userLoggedInSignOutOption;

    @Value("${header.signUp}")
    String headerRegistrationOption;

    @Value("${header.signIn}")
    String headerLoginOption;

    LogoutPage logoutPage;

    LoginPage loginPage;

    MainPage mainPage;

    void launchBrowser() {

        setUpWebDriver();

        logoutPage = new LogoutPage(webDriver, mainUrl + localServerPort);

        loginPage = new LoginPage(webDriver, mainUrl + localServerPort);

        mainPage = new MainPage(webDriver, mainUrl + localServerPort);
    }
}