package com.NowakArtur97.WorldOfManga.feature.manga.seleniumTest;

import com.NowakArtur97.WorldOfManga.feature.manga.seleniumPOM.MangaList;
import com.NowakArtur97.WorldOfManga.feature.user.seleniumPOM.LoginPage;
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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(ScreenshotWatcher.class)
@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("MangaFavouriteUI_Tests")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class MangaFavouriteUITest extends SeleniumUITest {

    private MangaList mangaList;

    private LoginPage loginPage;

    private void launchBrowser(String browserName, String language) {

        setUp(browserName, language);

        mangaList = new MangaList(webDriver, mainUrl + localServerPort);

        loginPage = new LoginPage(webDriver, mainUrl + localServerPort);
    }

    @ParameterizedTest(name = "{index}: Browser: {0} | Language Version: {1}")
    @CsvSource({"Chrome, ENG", "Chrome, PL"})
    void when_add_manga_for_the_first_time_on_main_page_should_add_manga_to_favourites(String browserName, String language) {

        launchBrowser(browserName, language);

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("user", "user");

        mangaList.chooseLastManga();

        mangaList.addOrRemoveLastMangaFromFavourites();

        assertAll(() -> assertTrue(mangaList.getMangaListText().contains("1"),
                () -> "should show manga with one heart, but was: " + mangaList.getLastMangaCardText()));
    }

//    @ParameterizedTest(name = "{index}: Browser: {0} | Language Version: {1}")
//    @CsvSource({ "Chrome, ENG", "Chrome, PL"})
//    void when_add_manga_for_the_first_time_should_show_manga_in_favourites(String browserName, String language) {
//
//        launchBrowser(browserName, language);
//
//        loginPage.loadLoginView(languageVersion);
//
//        loginPage.fillMandatoryLoginFields("user", "user");
//
//        String firstMangaTitle = mangaList.getMangaTitle(0);
//
//        mangaList.chooseManga(0);
//
//        mangaList.addOrRemoveFirstMangaFromFavourites();
//
//        mangaList.clickMangaUserListLink();
//
//        mangaList.chooseFavouritesManga();
//
//        assertAll(
//                () -> assertTrue(mangaList.getLastMangaCardText().contains(firstMangaTitle),
//                        () -> "should show new manga: " + firstMangaTitle + " in favourites"),
//                () -> assertTrue(mangaList.countMangaCards() >= 1, () -> "should show at least one manga"),
//                () -> assertNotNull(mangaList.getMangaListText(), () -> "should load manga list fragment text"));
//    }
//
//    @ParameterizedTest(name = "{index}: Browser: {0} | Language Version: {1}")
//    @CsvSource({ "Chrome, ENG", "Chrome, PL"})
//    void when_remove_manga_from_favourites_on_main_page_should_remove_manga_from_favourites(String browserName, String language) {
//
//        launchBrowser(browserName, language);
//
//        loginPage.loadLoginView(languageVersion);
//
//        loginPage.fillMandatoryLoginFields("user", "user");
//
//        mangaList.chooseLastManga();
//
//        mangaList.addOrRemoveLastMangaFromFavourites();
//
//        mangaList.chooseLastManga();
//
//        mangaList.addOrRemoveLastMangaFromFavourites();
//
//        assertAll(() -> assertTrue(mangaList.getLastMangaFavouritesCounter().contains("0"),
//                () -> "should show manga with zero hearts, but was: " + mangaList.getLastMangaFavouritesCounter()));
//    }
//
//    @ParameterizedTest(name = "{index}: Browser: {0} | Language Version: {1}")
//    @CsvSource({ "Chrome, ENG", "Chrome, PL"})
//    void when_remove_manga_from_favourites_should_not_show_manga_in_list(String browserName, String language) {
//
//        launchBrowser(browserName, language);
//
//        loginPage.loadLoginView(languageVersion);
//
//        loginPage.fillMandatoryLoginFields("user", "user");
//
//        mangaList.chooseLastManga();
//
//        mangaList.addOrRemoveLastMangaFromFavourites();
//
//        mangaList.chooseLastManga();
//
//        mangaList.addOrRemoveLastMangaFromFavourites();
//
//        mangaList.clickMangaUserListLink();
//
//        mangaList.chooseFavouritesManga();
//
//        assertAll(
//                () -> assertEquals(0, mangaList.countMangaCards(), () -> "should not show any manga"),
//                () -> assertNotNull(mangaList.getMangaListText(), () -> "should load manga list fragment text"));
//    }

    @ParameterizedTest(name = "{index}: Browser: {0} | Language Version: {1}")
    @CsvSource({"Chrome, ENG", "Chrome, PL"})
    void when_user_not_logged_rating_manga_should_show_login_form(String browserName, String language) {

        launchBrowser(browserName, language);

        mangaList.loadMangaList(languageVersion);

        mangaList.chooseManga(0);

        mangaList.addOrRemoveFirstMangaFromFavourites();

        assertAll(() -> assertTrue(loginPage.isUserOnLoginPage(), () -> "should show login page"));
    }
}
