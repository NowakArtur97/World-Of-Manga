package com.NowakArtur97.WorldOfManga.feature.manga.seleniumTest;

import com.NowakArtur97.WorldOfManga.feature.manga.seleniumPOM.MangaList;
import com.NowakArtur97.WorldOfManga.feature.user.seleniumPOM.LoginPage;
import com.NowakArtur97.WorldOfManga.testUtil.enums.LanguageVersion;
import com.NowakArtur97.WorldOfManga.testUtil.extension.ScreenshotWatcher;
import com.NowakArtur97.WorldOfManga.testUtil.generator.NameWithSpacesGenerator;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumUITest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(ScreenshotWatcher.class)
@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("MangaInUserListUI_Tests")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class MangaInUserListUITest extends SeleniumUITest {

    private MangaList mangaList;

    private LoginPage loginPage;

    @BeforeEach
    void setupPOM() {

        mangaList = new MangaList(webDriver, mainUrl + appServerPort);

        loginPage = new LoginPage(webDriver, mainUrl + appServerPort);
    }

    @ParameterizedTest(name = "{index}: Language Version: {0}")
    @EnumSource(LanguageVersion.class)
    void when_add_to_currently_reading_list_should_add_to_list(LanguageVersion languageVersion) {

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("user", "user");

        mangaList.loadMangaList(languageVersion);

        mangaList.chooseManga(0);

        int mangaStatus = 0;

        mangaList.addOrRemoveFirstMangaFromList(mangaStatus);

        mangaList.clickMangaUserListLink();

        mangaList.chooseCurrentlyReadingManga();

        assertAll(
                () -> assertTrue(mangaList.getLastMangaCardText().contains("Tokyo Ghoul"),
                        () -> "should show new manga in currently reading list"),
                () -> assertTrue(mangaList.countMangaCards() >= 1, () -> "should show at least one manga"),
                () -> assertNotNull(mangaList.getMangaListText(), () -> "should load manga list fragment text"));
    }

    @ParameterizedTest(name = "{index}: Language Version: {0}")
    @EnumSource(LanguageVersion.class)
    void when_remove_from_currently_reading_list_should_remove_from_list(LanguageVersion languageVersion) {

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("user", "user");

        mangaList.loadMangaList(languageVersion);

        int mangaStatus = 0;

        mangaList.chooseManga(0);

        mangaList.addOrRemoveFirstMangaFromList(mangaStatus);

        mangaList.chooseManga(0);

        mangaList.addOrRemoveFirstMangaFromList(mangaStatus);

        mangaList.clickMangaUserListLink();

        mangaList.chooseCompletedManga();

        assertAll(
                () -> assertFalse(mangaList.getLastMangaCardText().contains("Tokyo Ghoul"),
                        () -> "should not show new manga in currently reading list"),
                () -> assertTrue(mangaList.countMangaCards() == 0, () -> "should not show any manga"),
                () -> assertNotNull(mangaList.getMangaListText(), () -> "should load manga list fragment text"));
    }

    @ParameterizedTest(name = "{index}: Language Version: {0}")
    @EnumSource(LanguageVersion.class)
    void when_add_to_completed_list_should_add_to_list(LanguageVersion languageVersion) {

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("user", "user");

        mangaList.loadMangaList(languageVersion);

        mangaList.chooseManga(0);

        int mangaStatus = 1;

        mangaList.addOrRemoveFirstMangaFromList(mangaStatus);

        mangaList.clickMangaUserListLink();

        mangaList.chooseCompletedManga();

        assertAll(
                () -> assertTrue(mangaList.getLastMangaCardText().contains("Tokyo Ghoul"),
                        () -> "should show new manga in completed list"),
                () -> assertTrue(mangaList.countMangaCards() >= 1, () -> "should show at least one manga"),
                () -> assertNotNull(mangaList.getMangaListText(), () -> "should load manga list fragment text"));
    }

    @ParameterizedTest(name = "{index}: Language Version: {0}")
    @EnumSource(LanguageVersion.class)
    void when_remove_from_completed_list_should_remove_from_list(LanguageVersion languageVersion) {

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("user", "user");

        mangaList.loadMangaList(languageVersion);

        int mangaStatus = 1;

        mangaList.chooseManga(0);

        mangaList.addOrRemoveFirstMangaFromList(mangaStatus);

        mangaList.chooseManga(0);

        mangaList.addOrRemoveFirstMangaFromList(mangaStatus);

        mangaList.clickMangaUserListLink();

        mangaList.chooseCompletedManga();

        assertAll(
                () -> assertFalse(mangaList.getLastMangaCardText().contains("Tokyo Ghoul"),
                        () -> "should not show new manga in completed list"),
                () -> assertTrue(mangaList.countMangaCards() == 0, () -> "should not show any manga"),
                () -> assertNotNull(mangaList.getMangaListText(), () -> "should load manga list fragment text"));
    }

    @ParameterizedTest(name = "{index}: Language Version: {0}")
    @EnumSource(LanguageVersion.class)
    void when_add_to_plan_to_read_list_should_add_to_list(LanguageVersion languageVersion) {

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("user", "user");

        mangaList.loadMangaList(languageVersion);

        mangaList.chooseManga(0);

        int mangaStatus = 2;

        mangaList.addOrRemoveFirstMangaFromList(mangaStatus);

        mangaList.clickMangaUserListLink();

        mangaList.choosePlanToReadManga();

        assertAll(
                () -> assertTrue(mangaList.getLastMangaCardText().contains("Tokyo Ghoul"),
                        () -> "should show new manga in plan to read list"),
                () -> assertTrue(mangaList.countMangaCards() >= 1, () -> "should show at least one manga"),
                () -> assertNotNull(mangaList.getMangaListText(), () -> "should load manga list fragment text"));
    }

    @ParameterizedTest(name = "{index}: Language Version: {0}")
    @EnumSource(LanguageVersion.class)
    void when_remove_from_plan_to_read_list_should_remove_from_list(LanguageVersion languageVersion) {

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("user", "user");

        mangaList.loadMangaList(languageVersion);

        int mangaStatus = 2;

        mangaList.chooseManga(0);

        mangaList.addOrRemoveFirstMangaFromList(mangaStatus);

        mangaList.chooseManga(0);

        mangaList.addOrRemoveFirstMangaFromList(mangaStatus);

        mangaList.clickMangaUserListLink();

        mangaList.chooseCompletedManga();

        assertAll(
                () -> assertFalse(mangaList.getLastMangaCardText().contains("Tokyo Ghoul"),
                        () -> "should not show new manga in plan to read list"),
                () -> assertTrue(mangaList.countMangaCards() == 0, () -> "should not show any manga"),
                () -> assertNotNull(mangaList.getMangaListText(), () -> "should load manga list fragment text"));
    }

    @ParameterizedTest(name = "{index}: Language Version: {0}")
    @EnumSource(LanguageVersion.class)
    void when_add_to_on_hold_list_should_add_to_list(LanguageVersion languageVersion) {

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("user", "user");

        mangaList.loadMangaList(languageVersion);

        mangaList.chooseManga(0);

        int mangaStatus = 3;

        mangaList.addOrRemoveFirstMangaFromList(mangaStatus);

        mangaList.clickMangaUserListLink();

        mangaList.chooseOnHoldManga();

        assertAll(
                () -> assertTrue(mangaList.getLastMangaCardText().contains("Tokyo Ghoul"),
                        () -> "should show new manga in on hold list"),
                () -> assertTrue(mangaList.countMangaCards() >= 1, () -> "should show at least one manga"),
                () -> assertNotNull(mangaList.getMangaListText(), () -> "should load manga list fragment text"));
    }

    @ParameterizedTest(name = "{index}: Language Version: {0}")
    @EnumSource(LanguageVersion.class)
    void when_remove_from_completed_on_hold_list_should_remove_from_list(LanguageVersion languageVersion) {

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("user", "user");

        mangaList.loadMangaList(languageVersion);

        int mangaStatus = 3;

        mangaList.chooseManga(0);

        mangaList.addOrRemoveFirstMangaFromList(mangaStatus);

        mangaList.chooseManga(0);

        mangaList.addOrRemoveFirstMangaFromList(mangaStatus);

        mangaList.clickMangaUserListLink();

        mangaList.chooseCompletedManga();

        assertAll(
                () -> assertFalse(mangaList.getLastMangaCardText().contains("Tokyo Ghoul"),
                        () -> "should not show new manga in on hold list"),
                () -> assertTrue(mangaList.countMangaCards() == 0, () -> "should not show any manga"),
                () -> assertNotNull(mangaList.getMangaListText(), () -> "should load manga list fragment text"));
    }

    @ParameterizedTest(name = "{index}: Language Version: {0}")
    @EnumSource(LanguageVersion.class)
    void when_add_to_dropped_list_should_add_to_list(LanguageVersion languageVersion) {

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("user", "user");

        mangaList.loadMangaList(languageVersion);

        mangaList.chooseManga(0);

        int mangaStatus = 4;

        mangaList.addOrRemoveFirstMangaFromList(mangaStatus);

        mangaList.clickMangaUserListLink();

        mangaList.chooseDroppedManga();

        assertAll(
                () -> assertTrue(mangaList.getLastMangaCardText().contains("Tokyo Ghoul"),
                        () -> "should show new manga in dropped list"),
                () -> assertTrue(mangaList.countMangaCards() >= 1, () -> "should show at least one manga"),
                () -> assertNotNull(mangaList.getMangaListText(), () -> "should load manga list fragment text"));
    }

    @ParameterizedTest(name = "{index}: Language Version: {0}")
    @EnumSource(LanguageVersion.class)
    void when_remove_from_dropped_list_should_remove_from_list(LanguageVersion languageVersion) {

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("user", "user");

        mangaList.loadMangaList(languageVersion);

        int mangaStatus = 4;

        mangaList.chooseManga(0);

        mangaList.addOrRemoveFirstMangaFromList(mangaStatus);

        mangaList.chooseManga(0);

        mangaList.addOrRemoveFirstMangaFromList(mangaStatus);

        mangaList.clickMangaUserListLink();

        mangaList.chooseCompletedManga();

        assertAll(
                () -> assertFalse(mangaList.getLastMangaCardText().contains("Tokyo Ghoul"),
                        () -> "should not show new manga in dropped list"),
                () -> assertTrue(mangaList.countMangaCards() == 0, () -> "should not show any manga"),
                () -> assertNotNull(mangaList.getMangaListText(), () -> "should load manga list fragment text"));
    }

    @ParameterizedTest(name = "{index}: Language Version: {0}")
    @EnumSource(LanguageVersion.class)
    void when_user_not_logged_adding_manga_to_list_should_show_login_form(LanguageVersion languageVersion) {

        mangaList.loadMangaList(languageVersion);

        mangaList.chooseManga(0);

        int mangaStatus = 0;

        mangaList.addOrRemoveFirstMangaFromList(mangaStatus);

        assertAll(() -> assertTrue(loginPage.isUserOnLoginPage(), () -> "should show login page"));
    }
}
