package com.NowakArtur97.WorldOfManga.feature.author.seleniumTest;

import com.NowakArtur97.WorldOfManga.feature.author.AuthorService;
import com.NowakArtur97.WorldOfManga.feature.author.seleniumPOM.AuthorFormPage;
import com.NowakArtur97.WorldOfManga.feature.user.seleniumPOM.LoginPage;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumUITest;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Tag("Author_Tests")
class AuthorControllerUITest extends SeleniumUITest {

    @Value("${author.fullName.notBlank}")
    String authorFulNameNotBlankMessage;

    @Value("${author.fullName.size}")
    String authorFullNameSizeMessage;

    @Value("${author.fullName.alreadySaved}")
    String authorAlreadySavedMessage;

    AuthorFormPage authorFormPage;

    LoginPage loginPage;

    @Autowired
    AuthorService authorService;

    void launchBrowser() {

        setUpWebDriver();

        loginPage = new LoginPage(webDriver, mainUrl + localServerPort);

        authorFormPage = new AuthorFormPage(webDriver, mainUrl + localServerPort);
    }
}
