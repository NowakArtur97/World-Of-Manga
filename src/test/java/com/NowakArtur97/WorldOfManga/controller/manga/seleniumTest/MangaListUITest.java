package com.NowakArtur97.WorldOfManga.controller.manga.seleniumTest;

import com.NowakArtur97.WorldOfManga.controller.manga.seleniumPOM.MangaFormPage;
import com.NowakArtur97.WorldOfManga.controller.manga.seleniumPOM.MangaList;
import com.NowakArtur97.WorldOfManga.controller.unloggedUser.seleniumPOM.LoginPage;
import com.NowakArtur97.WorldOfManga.testUtil.enums.LanguageVersion;
import com.NowakArtur97.WorldOfManga.testUtil.extension.ScreenshotWatcher;
import com.NowakArtur97.WorldOfManga.testUtil.generator.NameWithSpacesGenerator;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumUITest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(ScreenshotWatcher.class)
@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("MangaListUI_Tests")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@DisabledOnOs(OS.LINUX)
public class MangaListUITest extends SeleniumUITest {

    private MangaList mangaList;

    private MangaFormPage mangaFormPage;

    private LoginPage loginPage;

    @BeforeEach
    public void setupPOM() {

        mangaList = new MangaList(webDriver);

        loginPage = new LoginPage(webDriver);

        mangaFormPage = new MangaFormPage(webDriver);
    }

    @ParameterizedTest(name = "{index}: Language Version: {0}")
    @EnumSource(LanguageVersion.class)
    public void when_added_new_manga_should_show_manga_title_on_manga_list(LanguageVersion languageVersion) {

        String englishTitle = "English title";
        String polishTitle = "Polish title";
        String englishDescription = "English description";
        String polishDescription = "Polish description";
        boolean selectAuthor = true;
        boolean selectGenre = true;
        boolean addImage = true;

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("admin", "admin");

        mangaFormPage.clickAddOrUpdateMangaLinkButton();

        mangaFormPage.fillMandatoryMangaFormFields(englishTitle, englishDescription, polishTitle, polishDescription,
                selectAuthor, selectGenre, addImage);

        mangaList.clickMangaListLink();

        String title = languageVersion.name().equals("ENG") ? englishTitle : polishTitle;

        assertAll(
                () -> assertTrue(mangaList.getLastMangaCardText().contains(title),
                        () -> "should show new manga with title: " + title + ", but was: "
                                + mangaList.getLastMangaCardText()),
                () -> assertTrue(mangaList.countMangaCards() >= 1, () -> "should show at least one manga"),
                () -> assertNotNull(mangaList.getMangaListText(), () -> "should load manga list fragment text"));
    }

    @ParameterizedTest(name = "{index}: Language Version: {0}")
    @EnumSource(LanguageVersion.class)
    public void when_added_new_manga_should_show_manga_description_on_manga_list(LanguageVersion languageVersion) {

        String englishTitle = "English title";
        String polishTitle = "Polish title";
        String englishDescription = "English description";
        String polishDescription = "Polish description";
        boolean selectAuthor = true;
        boolean selectGenre = true;
        boolean addImage = true;

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("admin", "admin");

        mangaFormPage.clickAddOrUpdateMangaLinkButton();

        mangaFormPage.fillMandatoryMangaFormFields(englishTitle, englishDescription, polishTitle, polishDescription,
                selectAuthor, selectGenre, addImage);

        mangaList.clickMangaListLink();

        mangaList.chooseLastManga();

        String description = languageVersion.name().equals("ENG") ? englishDescription : polishDescription;

        assertAll(
                () -> assertTrue(mangaList.getLastMangaCardText().contains(description),
                        () -> "should show new manga with description: " + description + ", but was: "
                                + mangaList.getLastMangaCardText()),
                () -> assertTrue(mangaList.countMangaCards() >= 1, () -> "should show at least one manga"),
                () -> assertNotNull(mangaList.getMangaListText(), () -> "should load manga list fragment text"));
    }
}
