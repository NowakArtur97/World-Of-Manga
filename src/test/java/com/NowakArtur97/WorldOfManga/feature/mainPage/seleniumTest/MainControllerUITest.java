package com.NowakArtur97.WorldOfManga.feature.mainPage.seleniumTest;

import com.NowakArtur97.WorldOfManga.feature.mainPage.seleniumPOM.MainPage;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumUITest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;

class MainControllerUITest extends SeleniumUITest {

    @Value("${header.logo}")
    protected String headerLogo;

    @Value("${header.signUp}")
    protected String headerRegistrationOption;

    @Value("${header.signIn}")
    protected String headerLoginOption;

    @Value("${header.language}")
    protected String headerLanguageOption;

    protected MainPage mainPage;

    @BeforeEach
    public void setupPOM() {

        mainPage = new MainPage(webDriver);
    }
}
