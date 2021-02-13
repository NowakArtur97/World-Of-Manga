package com.NowakArtur97.WorldOfManga.feature.mainPage.seleniumTest;

import com.NowakArtur97.WorldOfManga.feature.mainPage.seleniumPOM.MainPage;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumUITest;
import org.springframework.beans.factory.annotation.Value;

class MainControllerUITest extends SeleniumUITest {

    @Value("${header.logo}")
    String headerLogo;

    @Value("${header.signUp}")
    String headerRegistrationOption;

    @Value("${header.signIn}")
    String headerLoginOption;

    @Value("${header.language}")
    String headerLanguageOption;

    MainPage mainPage;

    void launchBrowser(String browserName, String language) {

        setUp(browserName, language);

        mainPage = new MainPage(webDriver, mainUrl + localServerPort);
    }
}
