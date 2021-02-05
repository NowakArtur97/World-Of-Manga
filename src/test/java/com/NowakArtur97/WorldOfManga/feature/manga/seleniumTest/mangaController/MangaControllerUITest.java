package com.NowakArtur97.WorldOfManga.feature.manga.seleniumTest.mangaController;

import com.NowakArtur97.WorldOfManga.feature.manga.seleniumPOM.MangaFormPage;
import com.NowakArtur97.WorldOfManga.feature.manga.seleniumPOM.MangaList;
import com.NowakArtur97.WorldOfManga.feature.manga.translation.MangaTranslationService;
import com.NowakArtur97.WorldOfManga.feature.user.seleniumPOM.LoginPage;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumUITest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

class MangaControllerUITest extends SeleniumUITest {

    @Value("${mangaTranslation.titleEn.inUse}")
    protected String mangaTranslationTitleEnInUseMessage;

    @Value("${mangaTranslation.titlePl.inUse}")
    protected String mangaTranslationTitlePlInUseMessage;

    @Value("${mangaTranslation.title.notBlank}")
    protected String mangaTranslationTitleNotBlankMessage;

    @Value("${mangaTranslation.title.size}")
    protected String mangaTranslationTitleSizeMessage;

    @Value("${mangaTranslation.description.notBlank}")
    protected String mangaTranslationDescriptionNotBlankMessage;

    @Value("${mangaTranslation.description.size}")
    protected String mangaTranslationDescriptionSizeMessage;

    @Value("${manga.authors.notEmpty}")
    protected String mangaAuthorsRequiredMessage;

    @Value("${manga.genres.notEmpty}")
    protected String mangaGenresRequiredMessage;

    @Value("${manga.image.notEmpty}")
    protected String mangaImageRequiredMessage;

    protected MangaFormPage mangaFormPage;

    protected LoginPage loginPage;

    protected MangaList mangaList;

    @Autowired
    protected MangaTranslationService mangaTranslationService;

    @BeforeEach
    void setupPOM() {

        loginPage = new LoginPage(webDriver);

        mangaFormPage = new MangaFormPage(webDriver);

        mangaList = new MangaList(webDriver);
    }
}
