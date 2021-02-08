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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(ScreenshotWatcher.class)
@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("MangaRatingUI_Tests")
@DirtiesContext
class MangaRatingUITest extends SeleniumUITest {

    private MangaList mangaList;

    private LoginPage loginPage;

    @BeforeEach
    void setupPOM() {

        mangaList = new MangaList(webDriver, mainUrl + appServerPort);

        loginPage = new LoginPage(webDriver, mainUrl + appServerPort);
    }

    @ParameterizedTest(name = "{index}: Language Version: {0}")
    @EnumSource(LanguageVersion.class)
    void when_rate_manga_should_show_updated_rating(LanguageVersion languageVersion) {

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("user", "user");

        mangaList.chooseManga(0);

        int mangaRating = 5;

        mangaList.rateFirstManga(mangaRating);

        assertAll(() -> assertTrue(mangaList.getFirstMangaRating().contains(String.valueOf(mangaRating)),
                () -> "should show manga rating"));
    }

    @ParameterizedTest(name = "{index}: Language Version: {0}")
    @EnumSource(LanguageVersion.class)
    void when_rate_manga_should_show_rating_on_user_manga_list(LanguageVersion languageVersion) {

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("user", "user");

        mangaList.chooseManga(0);

        int mangaRating = 5;

        mangaList.rateFirstManga(mangaRating);

        mangaList.clickMangaUserListLink();

        mangaList.chooseRatedManga();

        assertAll(() -> assertTrue(mangaList.getFirstMangaRatingOnMangaList().contains(String.valueOf(mangaRating)),
                () -> "should show manga rating on manga list"));
    }

    @ParameterizedTest(name = "{index}: Language Version: {0}")
    @EnumSource(LanguageVersion.class)
    void when_rate_second_time_manga_should_show_updated_rating(LanguageVersion languageVersion) {

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("user", "user");

        mangaList.chooseManga(0);

        int firstMangaRating = 5;

        mangaList.rateFirstManga(firstMangaRating);

        mangaList.chooseManga(0);

        int secondMangaRating = 4;

        mangaList.rateFirstManga(secondMangaRating);

        assertAll(
                () -> assertFalse(mangaList.getFirstMangaRating().contains(String.valueOf(firstMangaRating)),
                        () -> "shouldn`t show old manga rating"),
                () -> assertTrue(mangaList.getFirstMangaRating().contains(String.valueOf(secondMangaRating)),
                        () -> "should show updated manga rating"));
    }

    @ParameterizedTest(name = "{index}: Language Version: {0}")
    @EnumSource(LanguageVersion.class)
    void when_user_not_logged_rating_manga_should_show_login_form(LanguageVersion languageVersion) {

        mangaList.loadMangaList(languageVersion);

        mangaList.chooseManga(0);

        int mangaRating = 5;

        mangaList.rateFirstManga(mangaRating);

        assertAll(() -> assertTrue(loginPage.isUserOnLoginPage(), () -> "should show login page"));
    }
}
