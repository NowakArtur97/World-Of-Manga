package com.NowakArtur97.WorldOfManga.feature.manga.seleniumTest;

import com.NowakArtur97.WorldOfManga.feature.manga.seleniumPOM.MangaFormPage;
import com.NowakArtur97.WorldOfManga.feature.manga.seleniumPOM.MangaList;
import com.NowakArtur97.WorldOfManga.feature.user.seleniumPOM.LoginPage;
import com.NowakArtur97.WorldOfManga.testUtil.enums.Browser;
import com.NowakArtur97.WorldOfManga.testUtil.extension.ScreenshotWatcher;
import com.NowakArtur97.WorldOfManga.testUtil.generator.NameWithSpacesGenerator;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumUITest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(ScreenshotWatcher.class)
@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("MangaListUI_Tests")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class MangaListUITest extends SeleniumUITest {

    private MangaList mangaList;

    private MangaFormPage mangaFormPage;

    private LoginPage loginPage;

    private void launchBrowser(Browser browser, String language) {

        setUp(browser, language);

        mangaList = new MangaList(webDriver, mainUrl + localServerPort);

        loginPage = new LoginPage(webDriver, mainUrl + localServerPort);

        mangaFormPage = new MangaFormPage(webDriver, mainUrl + localServerPort, exampleImagePath);
    }

    @ParameterizedTest(name = "{index}: Browser: {0} | Language Version: {1}")
    @CsvSource({"Firefox, ENG", "Firefox, PL", "Chrome, ENG", "Chrome, PL"})
    void when_added_new_manga_with_title_should_show_manga_title_on_manga_list(Browser browser, String language) {

        String englishTitle = "Manga english title";
        String polishTitle = "Manga polish title";
        String englishDescription = "English description";
        String polishDescription = "Polish description";
        boolean selectAuthor = true;
        boolean selectGenre = true;
        boolean addImage = true;

        launchBrowser(browser, language);

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("admin", "admin");

        mangaFormPage.clickAddOrUpdateMangaLinkButton();

        mangaFormPage.fillMandatoryMangaFormFields(englishTitle, englishDescription, polishTitle, polishDescription,
                selectAuthor, selectGenre, addImage);

        mangaList.clickMangaListLink();

        String title = languageVersion.name().equals("ENG") ? englishTitle : polishTitle;

        assertAll(
                () -> assertTrue(title.equals(mangaList.getLastMangaTitle())
                                || mangaList.getMangaListText().contains(title),
                        () -> "should show new manga with title: " + title + ", but was: "
                                + mangaList.getLastMangaCardText()),
                () -> assertTrue(mangaList.countMangaCards() >= 1, () -> "should show at least one manga"),
                () -> assertNotNull(mangaList.getMangaListText(), () -> "should load manga list fragment text"));
    }

    @ParameterizedTest(name = "{index}: Browser: {0} | Language Version: {1}")
    @CsvSource({"Firefox, ENG", "Firefox, PL", "Chrome, ENG", "Chrome, PL"})
    void when_added_new_manga_with_description_should_show_manga_description_on_manga_list(Browser browser, String language) {

        String englishTitle = "Manga with english title";
        String polishTitle = "Manga with polish title";
        String englishDescription = "English description";
        String polishDescription = "Polish description";
        boolean selectAuthor = true;
        boolean selectGenre = true;
        boolean addImage = true;

        launchBrowser(browser, language);

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("admin", "admin");

        mangaFormPage.clickAddOrUpdateMangaLinkButton();

        mangaFormPage.fillMandatoryMangaFormFields(englishTitle, englishDescription, polishTitle, polishDescription,
                selectAuthor, selectGenre, addImage);

        mangaList.clickMangaListLink();

        mangaList.chooseLastManga();

        String description = languageVersion.name().equals("ENG") ? englishDescription : polishDescription;

        assertAll(
                () -> assertTrue(mangaList.getLastMangaCardText().contains(description)
                                || mangaList.getMangaListText().contains(description),
                        () -> "should show new manga with description: " + description + ", but was: "
                                + mangaList.getLastMangaCardText()),
                () -> assertTrue(mangaList.countMangaCards() >= 1, () -> "should show at least one manga"),
                () -> assertNotNull(mangaList.getMangaListText(), () -> "should load manga list fragment text"));
    }
}
