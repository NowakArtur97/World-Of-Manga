package com.NowakArtur97.WorldOfManga.feature.author.seleniumTest;

import com.NowakArtur97.WorldOfManga.feature.author.AuthorService;
import com.NowakArtur97.WorldOfManga.feature.author.seleniumPOM.AuthorFormPage;
import com.NowakArtur97.WorldOfManga.feature.user.seleniumPOM.LoginPage;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumUITest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

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

    @BeforeEach
    void setupPOM() {

        loginPage = new LoginPage(webDriver, mainUrl + serverPort);

        authorFormPage = new AuthorFormPage(webDriver, mainUrl + serverPort);
    }
}
