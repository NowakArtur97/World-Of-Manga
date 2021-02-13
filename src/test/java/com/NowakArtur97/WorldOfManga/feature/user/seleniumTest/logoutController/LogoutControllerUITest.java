package com.NowakArtur97.WorldOfManga.feature.user.seleniumTest.logoutController;

import com.NowakArtur97.WorldOfManga.feature.mainPage.seleniumPOM.MainPage;
import com.NowakArtur97.WorldOfManga.feature.user.seleniumPOM.LoginPage;
import com.NowakArtur97.WorldOfManga.feature.user.seleniumPOM.LogoutPage;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumUITest;
import org.springframework.beans.factory.annotation.Value;

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

    void launchBrowser(String browserName, String language) {

        setUp(browserName, language);

        logoutPage = new LogoutPage(webDriver, mainUrl + localServerPort);

        loginPage = new LoginPage(webDriver, mainUrl + localServerPort);

        mainPage = new MainPage(webDriver, mainUrl + localServerPort);
    }
}