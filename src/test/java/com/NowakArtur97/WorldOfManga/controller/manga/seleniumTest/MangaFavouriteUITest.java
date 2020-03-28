package com.NowakArtur97.WorldOfManga.controller.manga.seleniumTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

import com.NowakArtur97.WorldOfManga.controller.manga.seleniumPOM.MangaList;
import com.NowakArtur97.WorldOfManga.controller.unloggedUser.seleniumPOM.LoginPage;
import com.NowakArtur97.WorldOfManga.testUtil.enums.LanguageVersion;
import com.NowakArtur97.WorldOfManga.testUtil.extension.ScreenshotWatcher;
import com.NowakArtur97.WorldOfManga.testUtil.generator.ReplaceUnderscoresGenerator;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumUITest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(ScreenshotWatcher.class)
@DisplayNameGeneration(ReplaceUnderscoresGenerator.class)
@Tag("MangaFavouriteUI_Tests")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@DisabledOnOs(OS.LINUX)
public class MangaFavouriteUITest extends SeleniumUITest {

	private MangaList mangaList;

	private LoginPage loginPage;

	@BeforeEach
	public void setupPOM() {

		mangaList = new MangaList(webDriver);

		loginPage = new LoginPage(webDriver);
	}

	@ParameterizedTest(name = "{index}: Language Version: {0}")
	@EnumSource(LanguageVersion.class)
	public void when_add_manga_for_the_first_time_should_add_manga_to_favourites(LanguageVersion languageVersion) {

		loginPage.loadLoginView(languageVersion);

		loginPage.fillMandatoryLoginFields("user", "user");

		mangaList.chooseManga(0);

		mangaList.addOrRemoveLastMangaFromFavourites();

		assertAll(() -> assertTrue(mangaList.getLastMangaFavouritesCounter().contains("1"),
				() -> "should show manga with one heart, but was: " + mangaList.getLastMangaFavouritesCounter()));
	}

	@ParameterizedTest(name = "{index}: Language Version: {0}")
	@EnumSource(LanguageVersion.class)
	public void when_add_manga_for_the_first_time_should_show_manga_in_favourites(LanguageVersion languageVersion) {

		loginPage.loadLoginView(languageVersion);

		loginPage.fillMandatoryLoginFields("user", "user");

		mangaList.chooseManga(0);

		mangaList.addOrRemoveLastMangaFromFavourites();

		mangaList.clickMangaUserListLink();

		mangaList.chooseFavouritesManga();

		assertAll(
				() -> assertTrue(mangaList.getLastMangaCardText().contains("Black Clover"),
						() -> "should show new manga in favourites"),
				() -> assertTrue(mangaList.countMangaCards() >= 1, () -> "should show at least one manga"),
				() -> assertNotNull(mangaList.getMangaListText(), () -> "should load manga list fragment text"));
	}

	@ParameterizedTest(name = "{index}: Language Version: {0}")
	@EnumSource(LanguageVersion.class)
	public void when_remove_manga_from_favourites_should_remove_manga_from_favourites(LanguageVersion languageVersion) {

		loginPage.loadLoginView(languageVersion);

		loginPage.fillMandatoryLoginFields("user", "user");

		mangaList.chooseManga(0);

		mangaList.addOrRemoveLastMangaFromFavourites();

		mangaList.chooseManga(0);

		mangaList.addOrRemoveLastMangaFromFavourites();

		assertAll(() -> assertTrue(mangaList.getLastMangaFavouritesCounter().contains("0"),
				() -> "should show manga with zero hearts, but was: " + mangaList.getLastMangaFavouritesCounter()));
	}

	@ParameterizedTest(name = "{index}: Language Version: {0}")
	@EnumSource(LanguageVersion.class)
	public void when_remove_manga_from_favourites_should_not_show_manga_in_list(LanguageVersion languageVersion) {

		loginPage.loadLoginView(languageVersion);

		loginPage.fillMandatoryLoginFields("user", "user");

		mangaList.chooseManga(0);

		mangaList.addOrRemoveFirstMangaFromFavourites();

		mangaList.chooseManga(0);

		mangaList.addOrRemoveFirstMangaFromFavourites();

		mangaList.clickMangaUserListLink();

		mangaList.chooseFavouritesManga();

		assertAll(
				() -> assertFalse(mangaList.getLastMangaCardText().contains("Tokyo Ghoul"),
						() -> "should not show manga in favourites"),
				() -> assertEquals(0, mangaList.countMangaCards(), () -> "should not show any manga"),
				() -> assertNotNull(mangaList.getMangaListText(), () -> "should load manga list fragment text"));
	}

	@ParameterizedTest(name = "{index}: Language Version: {0}")
	@EnumSource(LanguageVersion.class)
	public void when_user_not_logged_rating_manga_should_show_login_form(LanguageVersion languageVersion) {

		mangaList.loadMangaList(languageVersion);

		mangaList.chooseManga(0);

		mangaList.addOrRemoveFirstMangaFromFavourites();

		assertAll(() -> assertTrue(loginPage.isUserOnLoginPage(), () -> "should show login page"));
	}
}
