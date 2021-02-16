package com.NowakArtur97.WorldOfManga.feature.manga.seleniumTest.mangaController;

import com.NowakArtur97.WorldOfManga.feature.manga.seleniumPOM.MangaFormPage;
import com.NowakArtur97.WorldOfManga.feature.manga.seleniumPOM.MangaList;
import com.NowakArtur97.WorldOfManga.feature.manga.translation.MangaTranslationService;
import com.NowakArtur97.WorldOfManga.feature.user.seleniumPOM.LoginPage;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumUITest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

class MangaControllerUITest extends SeleniumUITest {

    @Value("${mangaTranslation.titleEn.inUse}")
    String mangaTranslationTitleEnInUseMessage;

    @Value("${mangaTranslation.titlePl.inUse}")
    String mangaTranslationTitlePlInUseMessage;

    @Value("${mangaTranslation.title.notBlank}")
    String mangaTranslationTitleNotBlankMessage;

    @Value("${mangaTranslation.title.size}")
    String mangaTranslationTitleSizeMessage;

    @Value("${mangaTranslation.description.notBlank}")
    String mangaTranslationDescriptionNotBlankMessage;

    @Value("${mangaTranslation.description.size}")
    String mangaTranslationDescriptionSizeMessage;

    @Value("${manga.authors.notEmpty}")
    String mangaAuthorsRequiredMessage;

    @Value("${manga.genres.notEmpty}")
    String mangaGenresRequiredMessage;

    @Value("${manga.image.notEmpty}")
    String mangaImageRequiredMessage;

    MangaFormPage mangaFormPage;

    LoginPage loginPage;

    MangaList mangaList;

    @Autowired
    MangaTranslationService mangaTranslationService;

    void launchBrowser() {

        setUpWebDriver();

        loginPage = new LoginPage(webDriver, mainUrl + localServerPort);

        mangaFormPage = new MangaFormPage(webDriver, mainUrl + localServerPort, exampleImagePath);

        mangaList = new MangaList(webDriver, mainUrl + localServerPort);
    }
}
