package com.NowakArtur97.WorldOfManga.controller.manga.seleniumTest.mangaFavourite;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.NowakArtur97.WorldOfManga.testUtil.enums.LanguageVersion;
import com.NowakArtur97.WorldOfManga.testUtil.extension.ScreenshotWatcher;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(ScreenshotWatcher.class)
@DisplayName("Manga Favourite UI Pl Tests")
@Tag("MangaFavouriteUIPl_Tests")
@DirtiesContext
@DisabledOnOs(OS.LINUX)
public class MangaFavouriteUIPlTest extends MangaFavouriteUITest {

	@Test
	@DisplayName("when add manga for the first time")
	public void when_add_manga_for_the_first_time_should_add_manga_to_favourites() {

		loginPage.loadLoginView(LanguageVersion.PL);

		loginPage.fillMandatoryLoginFields("user", "user");

		mangaList.chooseManga(0);

		mangaList.addOrRemoveFirstMangaFromFavourites();

		assertAll(() -> assertTrue(mangaList.getFirstMangaFavouritesCounter().contains("1"),
				() -> "should show manga with one heart, but was: " + mangaList.getFirstMangaFavouritesCounter()));
	}

	@Test
	@DisplayName("when add manga for the first time")
	public void when_add_manga_for_the_first_time_should_show_manga_in_favourites() {

		loginPage.loadLoginView(LanguageVersion.PL);

		loginPage.fillMandatoryLoginFields("user", "user");

		mangaList.chooseManga(0);

		mangaList.addOrRemoveFirstMangaFromFavourites();

		mangaList.clickMangaUserListLink();

		mangaList.choseFavouritesManga();

		assertAll(
				() -> assertTrue(mangaList.getLastMangaCardText().contains("Tokyo Ghoul"),
						() -> "should show new manga in favourites"),
				() -> assertTrue(mangaList.countMangaCards() >= 1, () -> "should show at least one manga"),
				() -> assertNotNull(mangaList.getMangaListText(), () -> "should load manga list fragment text"));
	}

	@Test
	@DisplayName("when remove manga from favourites")
	public void when_remove_manga_from_favourites_should_remove_manga_from_favourites() {

		loginPage.loadLoginView(LanguageVersion.PL);

		loginPage.fillMandatoryLoginFields("user", "user");

		mangaList.chooseManga(0);

		mangaList.addOrRemoveFirstMangaFromFavourites();

		mangaList.chooseManga(0);

		mangaList.addOrRemoveFirstMangaFromFavourites();

		assertAll(() -> assertTrue(mangaList.getFirstMangaFavouritesCounter().contains("0"),
				() -> "should show manga with zero hearts, but was: " + mangaList.getFirstMangaFavouritesCounter()));
	}

	@Test
	@DisplayName("when remove manga from favourites")
	public void when_remove_manga_from_favourites_should_not_show_manga_in_list() {

		loginPage.loadLoginView(LanguageVersion.PL);

		loginPage.fillMandatoryLoginFields("user", "user");

		mangaList.chooseManga(0);

		mangaList.addOrRemoveFirstMangaFromFavourites();

		mangaList.chooseManga(0);

		mangaList.addOrRemoveFirstMangaFromFavourites();

		mangaList.clickMangaUserListLink();

		mangaList.choseFavouritesManga();

		assertAll(
				() -> assertFalse(mangaList.getLastMangaCardText().contains("Tokyo Ghoul"),
						() -> "should not show manga in favourites"),
				() -> assertEquals(0, mangaList.countMangaCards(), () -> "should not show any manga"),
				() -> assertNotNull(mangaList.getMangaListText(), () -> "should load manga list fragment text"));
	}

	@Test
	@DisplayName("when user not logged rating manga")
	public void when_user_not_logged_rating_manga_should_show_login_form() {

		mangaList.loadMangaList(LanguageVersion.PL);

		mangaList.chooseManga(0);

		mangaList.addOrRemoveFirstMangaFromFavourites();

		assertAll(() -> assertTrue(loginPage.isUserOnLoginPage(), () -> "should show login page"));
	}
}
