package com.NowakArtur97.WorldOfManga.controller.author.seleniumTest;

import com.NowakArtur97.WorldOfManga.controller.author.seleniumPOM.AuthorFormPage;
import com.NowakArtur97.WorldOfManga.controller.unloggedUser.seleniumPOM.LoginPage;
import com.NowakArtur97.WorldOfManga.service.AuthorService;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumUITest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class AuthorControllerUITest extends SeleniumUITest {

    @Value("${author.fullName.notBlank}")
    protected String authorFulNameNotBlankMessage;

    @Value("${author.fullName.size}")
    protected String authorFullNameSizeMessage;

    @Value("${author.fullName.alreadySaved}")
    protected String authorAlreadySavedMessage;

    protected AuthorFormPage authorFormPage;

    protected LoginPage loginPage;

    @Autowired
    protected AuthorService authorService;

    @BeforeEach
    public void setupPOM() {

        loginPage = new LoginPage(webDriver);

        authorFormPage = new AuthorFormPage(webDriver);
    }
}
