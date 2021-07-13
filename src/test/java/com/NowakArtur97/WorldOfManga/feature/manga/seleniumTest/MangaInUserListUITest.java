package com.NowakArtur97.WorldOfManga.feature.manga.seleniumTest;

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
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(ScreenshotWatcher.class)
@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("Manga_Tests")
@Tag("MangaInUserListUI_Tests")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class MangaInUserListUITest extends SeleniumUITest {

    private MangaList mangaList;

    private LoginPage loginPage;

    private void launchBrowser(Browser browser, String language) {

        setUp(browser, language);

        mangaList = new MangaList(webDriver, mainUrl + localServerPort);

        loginPage = new LoginPage(webDriver, mainUrl + localServerPort);
    }

    @ParameterizedTest(name = "{index}: Browser: {0} | Language Version: {1}")
    @MethodSource("setBrowserAndLanguageBasedOnProfile")
    void when_add_to_currently_reading_list_should_add_to_list(Browser browser, String language) {

        launchBrowser(browser, language);

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("user", "user");

        mangaList.loadMangaList(languageVersion);

        String mangaTitle = mangaList.getLastMangaTitle();

        int mangaStatus = 0;

        mangaList.chooseLastManga();

        mangaList.addOrRemoveLastMangaFromList(mangaStatus);

        mangaList.clickMangaUserListLink();

        mangaList.chooseListByStatus(mangaStatus, languageVersion);

        assertAll(
                () -> assertTrue(mangaList.getLastMangaCardTextInMainList().contains(mangaTitle),
                        () -> "should show new manga in currently reading list"),
                () -> assertTrue(mangaList.countMangaCards() >= 1, () -> "should show at least one manga"),
                () -> assertNotNull(mangaList.getMangaListText(), () -> "should load manga list fragment text"));
    }

    @ParameterizedTest(name = "{index}: Browser: {0} | Language Version: {1}")
    @MethodSource("setBrowserAndLanguageBasedOnProfile")
    void when_remove_from_currently_reading_list_should_remove_from_list(Browser browser, String language) {

        launchBrowser(browser, language);

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("user", "user");

        mangaList.loadMangaList(languageVersion);

        String mangaTitle = mangaList.getLastMangaTitle();

        int mangaStatus = 0;

        mangaList.chooseLastManga();

        mangaList.addOrRemoveLastMangaFromList(mangaStatus);

        mangaList.chooseLastManga();

        mangaList.addOrRemoveLastMangaFromList(mangaStatus);

        mangaList.clickMangaUserListLink();

        mangaList.chooseListByStatus(mangaStatus, languageVersion);

        assertAll(
                () -> assertFalse(mangaList.getLastMangaCardTextInMainList().contains(mangaTitle),
                        () -> "should not show new manga in currently reading list"),
                () -> assertEquals(mangaList.countMangaCards(), 0, () -> "should not show any manga"),
                () -> assertNotNull(mangaList.getMangaListText(), () -> "should load manga list fragment text"));
    }

    @ParameterizedTest(name = "{index}: Browser: {0} | Language Version: {1}")
    @MethodSource("setBrowserAndLanguageBasedOnProfile")
    void when_add_to_completed_list_should_add_to_list(Browser browser, String language) {

        launchBrowser(browser, language);

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("user", "user");

        mangaList.loadMangaList(languageVersion);

        String mangaTitle = mangaList.getLastMangaTitle();

        mangaList.chooseLastManga();

        int mangaStatus = 1;

        mangaList.addOrRemoveLastMangaFromList(mangaStatus);

        mangaList.clickMangaUserListLink();

        mangaList.chooseListByStatus(mangaStatus, languageVersion);

        assertAll(
                () -> assertTrue(mangaList.getLastMangaCardTextInMainList().contains(mangaTitle),
                        () -> "should show new manga in completed list"),
                () -> assertTrue(mangaList.countMangaCards() >= 1, () -> "should show at least one manga"),
                () -> assertNotNull(mangaList.getMangaListText(), () -> "should load manga list fragment text"));
    }

    @ParameterizedTest(name = "{index}: Browser: {0} | Language Version: {1}")
    @MethodSource("setBrowserAndLanguageBasedOnProfile")
    void when_remove_from_completed_list_should_remove_from_list(Browser browser, String language) {

        launchBrowser(browser, language);

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("user", "user");

        mangaList.loadMangaList(languageVersion);

        String mangaTitle = mangaList.getLastMangaTitle();

        int mangaStatus = 1;

        mangaList.chooseLastManga();

        mangaList.addOrRemoveLastMangaFromList(mangaStatus);

        mangaList.chooseLastManga();

        mangaList.addOrRemoveLastMangaFromList(mangaStatus);

        mangaList.clickMangaUserListLink();

        mangaList.chooseListByStatus(mangaStatus, languageVersion);

        assertAll(
                () -> assertFalse(mangaList.getLastMangaCardTextInMainList().contains(mangaTitle),
                        () -> "should not show new manga in completed list"),
                () -> assertEquals(mangaList.countMangaCards(), 0, () -> "should not show any manga"),
                () -> assertNotNull(mangaList.getMangaListText(), () -> "should load manga list fragment text"));
    }

    @ParameterizedTest(name = "{index}: Browser: {0} | Language Version: {1}")
    @MethodSource("setBrowserAndLanguageBasedOnProfile")
    void when_add_to_plan_to_read_list_should_add_to_list(Browser browser, String language) {

        launchBrowser(browser, language);

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("user", "user");

        mangaList.loadMangaList(languageVersion);

        String mangaTitle = mangaList.getLastMangaTitle();

        mangaList.chooseLastManga();

        int mangaStatus = 2;

        mangaList.addOrRemoveLastMangaFromList(mangaStatus);

        mangaList.clickMangaUserListLink();

        mangaList.chooseListByStatus(mangaStatus, languageVersion);

        assertAll(
                () -> assertTrue(mangaList.getLastMangaCardTextInMainList().contains(mangaTitle),
                        () -> "should show new manga in plan to read list"),
                () -> assertTrue(mangaList.countMangaCards() >= 1, () -> "should show at least one manga"),
                () -> assertNotNull(mangaList.getMangaListText(), () -> "should load manga list fragment text"));
    }

    @ParameterizedTest(name = "{index}: Browser: {0} | Language Version: {1}")
    @MethodSource("setBrowserAndLanguageBasedOnProfile")
    void when_remove_from_plan_to_read_list_should_remove_from_list(Browser browser, String language) {

        launchBrowser(browser, language);

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("user", "user");

        mangaList.loadMangaList(languageVersion);

        int mangaStatus = 2;

        String mangaTitle = mangaList.getLastMangaTitle();

        mangaList.chooseLastManga();

        mangaList.addOrRemoveLastMangaFromList(mangaStatus);

        mangaList.chooseLastManga();

        mangaList.addOrRemoveLastMangaFromList(mangaStatus);

        mangaList.clickMangaUserListLink();

        mangaList.chooseListByStatus(mangaStatus, languageVersion);

        assertAll(
                () -> assertFalse(mangaList.getLastMangaCardTextInMainList().contains(mangaTitle),
                        () -> "should not show new manga in plan to read list"),
                () -> assertEquals(mangaList.countMangaCards(), 0, () -> "should not show any manga"),
                () -> assertNotNull(mangaList.getMangaListText(), () -> "should load manga list fragment text"));
    }

    @ParameterizedTest(name = "{index}: Browser: {0} | Language Version: {1}")
    @MethodSource("setBrowserAndLanguageBasedOnProfile")
    void when_add_to_on_hold_list_should_add_to_list(Browser browser, String language) {

        launchBrowser(browser, language);

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("user", "user");

        mangaList.loadMangaList(languageVersion);

        String mangaTitle = mangaList.getLastMangaTitle();

        mangaList.chooseLastManga();

        int mangaStatus = 3;

        mangaList.addOrRemoveLastMangaFromList(mangaStatus);

        mangaList.clickMangaUserListLink();

        mangaList.chooseListByStatus(mangaStatus, languageVersion);

        assertAll(
                () -> assertTrue(mangaList.getLastMangaCardTextInMainList().contains(mangaTitle),
                        () -> "should show new manga in on hold list"),
                () -> assertTrue(mangaList.countMangaCards() >= 1, () -> "should show at least one manga"),
                () -> assertNotNull(mangaList.getMangaListText(), () -> "should load manga list fragment text"));
    }

    @ParameterizedTest(name = "{index}: Browser: {0} | Language Version: {1}")
    @MethodSource("setBrowserAndLanguageBasedOnProfile")
    void when_remove_from_on_hold_list_should_remove_from_list(Browser browser, String language) {

        launchBrowser(browser, language);

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("user", "user");

        mangaList.loadMangaList(languageVersion);

        String mangaTitle = mangaList.getLastMangaTitle();

        int mangaStatus = 3;

        mangaList.chooseLastManga();

        mangaList.addOrRemoveLastMangaFromList(mangaStatus);

        mangaList.chooseLastManga();

        mangaList.addOrRemoveLastMangaFromList(mangaStatus);

        mangaList.clickMangaUserListLink();

        mangaList.chooseListByStatus(mangaStatus, languageVersion);

        assertAll(
                () -> assertFalse(mangaList.getLastMangaCardTextInMainList().contains(mangaTitle),
                        () -> "should not show new manga in on hold list"),
                () -> assertEquals(mangaList.countMangaCards(), 0, () -> "should not show any manga"),
                () -> assertNotNull(mangaList.getMangaListText(), () -> "should load manga list fragment text"));
    }

    @ParameterizedTest(name = "{index}: Browser: {0} | Language Version: {1}")
    @MethodSource("setBrowserAndLanguageBasedOnProfile")
    void when_add_to_dropped_list_should_add_to_list(Browser browser, String language) {

        launchBrowser(browser, language);

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("user", "user");

        mangaList.loadMangaList(languageVersion);

        String mangaTitle = mangaList.getLastMangaTitle();

        mangaList.chooseLastManga();

        int mangaStatus = 4;

        mangaList.addOrRemoveLastMangaFromList(mangaStatus);

        mangaList.clickMangaUserListLink();

        mangaList.chooseListByStatus(mangaStatus, languageVersion);

        assertAll(
                () -> assertTrue(mangaList.getLastMangaCardTextInMainList().contains(mangaTitle),
                        () -> "should show new manga in dropped list"),
                () -> assertTrue(mangaList.countMangaCards() >= 1, () -> "should show at least one manga"),
                () -> assertNotNull(mangaList.getMangaListText(), () -> "should load manga list fragment text"));
    }

    @ParameterizedTest(name = "{index}: Browser: {0} | Language Version: {1}")
    @MethodSource("setBrowserAndLanguageBasedOnProfile")
    void when_remove_from_dropped_list_should_remove_from_list(Browser browser, String language) {

        launchBrowser(browser, language);

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("user", "user");

        mangaList.loadMangaList(languageVersion);

        String mangaTitle = mangaList.getLastMangaTitle();

        int mangaStatus = 4;

        mangaList.chooseLastManga();

        mangaList.addOrRemoveLastMangaFromList(mangaStatus);

        mangaList.chooseLastManga();

        mangaList.addOrRemoveLastMangaFromList(mangaStatus);

        mangaList.clickMangaUserListLink();

        mangaList.chooseListByStatus(mangaStatus, languageVersion);

        assertAll(
                () -> assertFalse(mangaList.getLastMangaCardTextInMainList().contains(mangaTitle),
                        () -> "should not show new manga in dropped list"),
                () -> assertEquals(mangaList.countMangaCards(), 0, () -> "should not show any manga"),
                () -> assertNotNull(mangaList.getMangaListText(), () -> "should load manga list fragment text"));
    }

    @ParameterizedTest(name = "{index}: Browser: {0} | Language Version: {1}")
    @MethodSource("setBrowserAndLanguageBasedOnProfile")
    void when_user_not_logged_adding_manga_to_list_should_show_login_form(Browser browser, String language) {

        launchBrowser(browser, language);

        mangaList.loadMangaList(languageVersion);

        mangaList.chooseLastManga();

        int mangaStatus = 0;

        mangaList.addOrRemoveLastMangaFromList(mangaStatus);

        assertAll(() -> assertTrue(loginPage.isUserOnLoginPage(), () -> "should show login page"));
    }
}
