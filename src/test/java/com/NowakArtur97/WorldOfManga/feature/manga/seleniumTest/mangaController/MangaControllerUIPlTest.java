package com.NowakArtur97.WorldOfManga.feature.manga.seleniumTest.mangaController;

import com.NowakArtur97.WorldOfManga.testUtil.enums.Browser;
import com.NowakArtur97.WorldOfManga.testUtil.enums.LanguageVersion;
import com.NowakArtur97.WorldOfManga.testUtil.extension.ScreenshotWatcher;
import com.NowakArtur97.WorldOfManga.testUtil.generator.NameWithSpacesGenerator;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(ScreenshotWatcher.class)
@TestPropertySource({"classpath:/validation/messages_pl.properties", "classpath:/pageContent/messages_pl.properties"})
@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("MangaControllerUIPl_Tests")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class MangaControllerUIPlTest extends MangaControllerUITest {

    @ParameterizedTest(name = "{index}: Browser: {0}")
    @EnumSource(Browser.class)
    void when_correct_manga_creation_with_all_fields_should_add_manga(Browser browserForTest) {

        languageVersion = LanguageVersion.PL;
        browser = browserForTest;

        String englishTitle = "English title 2 " + browser.name();
        String polishTitle = "Polish title 2 " + browser.name();
        boolean selectAuthor = true;
        boolean selectGenre = true;
        boolean addImage = true;

        launchBrowser();

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("admin", "admin");

        mangaFormPage.clickAddOrUpdateMangaLinkButton();

        mangaFormPage.fillMandatoryMangaFormFields(englishTitle, "English description", polishTitle,
                "Polish description", selectAuthor, selectGenre, addImage);

        assertAll(() -> assertTrue(mangaFormPage.isUserOnMangaFormPage(), () -> "should show manga form page"),
                () -> assertEquals(0, mangaFormPage.countFailureMessages(), () -> "shouldn`t have errors"),
                () -> assertTrue(mangaTranslationService.isTitleAlreadyInUse(englishTitle),
                        () -> "should save english manga translation in database"),
                () -> assertTrue(mangaTranslationService.isTitleAlreadyInUse(polishTitle),
                        () -> "should save polish manga translation in database"));
    }

    @ParameterizedTest(name = "{index}: Browser: {0}")
    @EnumSource(Browser.class)
    void when_correct_manga_editing_with_all_fields_should_edit_manga(Browser browserForTest) {

        languageVersion = LanguageVersion.PL;
        browser = browserForTest;

        String englishTitle = "Some english title 2 " + browser.name();
        String polishTitle = "Some polish title 2 " + browser.name();
        boolean selectAuthor = true;
        boolean selectGenre = true;
        boolean addImage = true;

        launchBrowser();

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("admin", "admin");

        mangaList.chooseManga(0);

        mangaList.editFirstManga();

        mangaFormPage.fillMandatoryMangaFormFields(englishTitle, "English description", polishTitle,
                "Polish description", selectAuthor, selectGenre, addImage);

        assertAll(() -> assertTrue(mangaFormPage.isUserOnMangaFormPage(), () -> "should show manga form page"),
                () -> assertEquals(0, mangaFormPage.countFailureMessages(), () -> "shouldn`t have errors"),
                () -> assertTrue(mangaTranslationService.isTitleAlreadyInUse(englishTitle),
                        () -> "should update english manga translation in database"),
                () -> assertTrue(mangaTranslationService.isTitleAlreadyInUse(polishTitle),
                        () -> "should update polish manga translation in database"));
    }

    @ParameterizedTest(name = "{index}: Browser: {0}")
    @EnumSource(Browser.class)
    void when_deleted_manga_is_in_main_page_should_remove_manga_from_list(Browser browserForTest) {

        languageVersion = LanguageVersion.PL;
        browser = browserForTest;

        launchBrowser();

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("admin", "admin");

        int mangasQuantityBefore = mangaList.countMangaCards();

        mangaList.chooseLastManga();

        mangaList.deleteLastManga();

        int mangasQuantityAfter = mangaList.countMangaCards();

        assertAll(() -> assertEquals(mangasQuantityBefore - 2, mangasQuantityAfter, () -> "should show one less manga"));
    }

    @ParameterizedTest(name = "{index}: Browser: {0}")
    @EnumSource(Browser.class)
    void when_deleted_manga_is_in_users_rated_manga_list_should_delete_manga_from_users_rated_manga_list(Browser browserForTest) {

        languageVersion = LanguageVersion.PL;
        browser = browserForTest;

        launchBrowser();

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("admin", "admin");

        mangaList.chooseLastManga();

        int mangaRating = 5;

        mangaList.rateLastManga(mangaRating);

        mangaList.chooseLastManga();

        mangaList.deleteLastManga();

        mangaList.clickMangaUserListLink();

        mangaList.chooseRatedManga();

        assertAll(
                () -> assertEquals(0, mangaList.countMangaCards(), () -> "shouldn`t show manga rating on manga list"));
    }

    @ParameterizedTest(name = "{index}: Browser: {0}")
    @EnumSource(Browser.class)
    void when_deleted_manga_is_in_users_favourites_should_delete_manga_from_favourites(Browser browserForTest) {

        languageVersion = LanguageVersion.PL;
        browser = browserForTest;

        launchBrowser();

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("admin", "admin");

        mangaList.chooseLastManga();

        mangaList.addOrRemoveLastMangaFromFavourites();

        mangaList.chooseLastManga();

        mangaList.deleteLastManga();

        mangaList.clickMangaUserListLink();

        mangaList.chooseFavouritesManga();

        assertAll(() -> assertEquals(0, mangaList.countMangaCards(), () -> "shouldn`t show manga on favourites"));
    }

    @ParameterizedTest(name = "{index}: Browser: {0}")
    @EnumSource(Browser.class)
    void when_deleted_manga_is_in_users_manga_list_should_delete_manga_from_users_manga_list(Browser browserForTest) {

        languageVersion = LanguageVersion.PL;
        browser = browserForTest;

        launchBrowser();

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("admin", "admin");

        mangaList.chooseLastManga();

        int mangaStatus = 0;

        mangaList.addLastMangaToList(mangaStatus);

        mangaList.chooseLastManga();

        mangaList.deleteLastManga();

        mangaList.clickMangaUserListLink();

        mangaList.chooseListByStatus(mangaStatus, languageVersion);

        assertAll(() -> assertEquals(0, mangaList.countMangaCards(), () -> "shouldn`t show manga on list"));
    }

    @ParameterizedTest(name = "{index}: Browser: {0}")
    @EnumSource(Browser.class)
    void when_incorrect_manga_creation_with_all_blank_fields_and_not_selected_author_image_and_genre_should_have_errors(Browser browserForTest) {

        String blankField = "";
        boolean selectAuthor = false;
        boolean selectGenre = false;
        boolean addImage = false;

        languageVersion = LanguageVersion.PL;
        browser = browserForTest;

        launchBrowser();

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("admin", "admin");

        mangaFormPage.clickAddOrUpdateMangaLinkButton();

        mangaFormPage.fillMandatoryMangaFormFields(blankField, blankField, blankField, blankField, selectAuthor,
                selectGenre, addImage);

        assertAll(() -> assertTrue(mangaFormPage.isUserOnMangaFormPage(), () -> "should show manga form page"),
                () -> assertEquals(7, mangaFormPage.countFailureMessages(), () -> "should have seven errors"),
                () -> assertTrue(mangaFormPage.getFormBoxText().contains(mangaTranslationDescriptionNotBlankMessage),
                        () -> "should show title is a required field message twice"),
                () -> assertTrue(mangaFormPage.getFormBoxText().contains(mangaTranslationTitleNotBlankMessage),
                        () -> "should show description is a required field message twice"),
                () -> assertTrue(mangaFormPage.getFormBoxText().contains(mangaAuthorsRequiredMessage),
                        () -> "should show author is a required field message"),
                () -> assertTrue(mangaFormPage.getFormBoxText().contains(mangaGenresRequiredMessage),
                        () -> "should show genre is a required field message"),
                () -> assertTrue(mangaFormPage.getFormBoxText().contains(mangaImageRequiredMessage),
                        () -> "should show image is a required field message"),
                () -> assertEquals(blankField, mangaFormPage.getEnTitle(), () -> "should show incorrect english title"),
                () -> assertEquals(blankField, mangaFormPage.getEnDescription(),
                        () -> "should show incorrect english description"),
                () -> assertEquals(blankField, mangaFormPage.getPlTitle(), () -> "should show incorrect polish title"),
                () -> assertEquals(blankField, mangaFormPage.getPlDescription(),
                        () -> "should show incorrect polish description"),
                () -> assertFalse(mangaFormPage.isFirstAuthorCheckboxSelected(), () -> "shouldn`t author be selected"),
                () -> assertFalse(mangaFormPage.isSecondGenreCheckboxSelected(), () -> "shouldn`t genre be selected"));
    }

    @ParameterizedTest(name = "{index}: Browser: {0}")
    @EnumSource(Browser.class)
    void when_incorrect_manga_creation_with_too_long_field_sizes_and_selected_author_and_image_should_have_errors(Browser browserForTest) {

        String longTitleText = "asdfghjklpasdfghjklpasdfghjklpasdfghjklpasdfghjklp!@#$%";
        String longDescriptionText = "asdfghjklpasdfghjklpasdfghjklpasdfghjklpasdfghjklp!@#$%".repeat(30);
        boolean selectAuthor = true;
        boolean selectGenre = true;
        boolean addImage = true;

        languageVersion = LanguageVersion.PL;
        browser = browserForTest;

        launchBrowser();

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("admin", "admin");

        mangaFormPage.clickAddOrUpdateMangaLinkButton();

        mangaFormPage.fillMandatoryMangaFormFields(longTitleText, longDescriptionText, longTitleText,
                longDescriptionText, selectAuthor, selectGenre, addImage);

        assertAll(() -> assertTrue(mangaFormPage.isUserOnMangaFormPage(), () -> "should show manga form page"),
                () -> assertEquals(4, mangaFormPage.countFailureMessages(), () -> "should have four errors"),
                () -> assertTrue(mangaFormPage.getFormBoxText().contains(mangaTranslationTitleSizeMessage),
                        () -> "should show title is too long message twice"),
                () -> assertTrue(mangaFormPage.getFormBoxText().contains(mangaTranslationDescriptionSizeMessage),
                        () -> "should show description is too long message twice"),
                () -> assertEquals(longTitleText, mangaFormPage.getEnTitle(),
                        () -> "should show incorrect english title"),
                () -> assertEquals(longDescriptionText, mangaFormPage.getEnDescription(),
                        () -> "should show incorrect english description"),
                () -> assertEquals(longTitleText, mangaFormPage.getPlTitle(),
                        () -> "should show incorrect polish title"),
                () -> assertEquals(longDescriptionText, mangaFormPage.getPlDescription(),
                        () -> "should show incorrect polish description"),
                () -> assertTrue(mangaFormPage.isSecondAuthorCheckboxSelected(), () -> "should author be selected"),
                () -> assertTrue(mangaFormPage.isSecondGenreCheckboxSelected(), () -> "should genre be selected"));
    }
}
