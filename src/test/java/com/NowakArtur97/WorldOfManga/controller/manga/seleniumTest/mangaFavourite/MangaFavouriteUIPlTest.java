package com.NowakArtur97.WorldOfManga.controller.manga.seleniumTest.mangaFavourite;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
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
public class MangaFavouriteUIPlTest extends MangaFavouriteUITest {

	@Test
	@DisplayName("when add manga for the first time")
	public void when_add_manga_for_the_first_time_should_add_manga_to_favourites() {

		loginPage.loadLoginView(LanguageVersion.PL);

		loginPage.fillMandatoryLoginFields("user", "user");

		mangaList.chooseFirstManga();

		mangaList.addOrRemoveFirstMangaFromFavourite();

		assertAll(() -> assertEquals(String.valueOf(1), mangaList.getFirstMangaFavouritesCounter(),
				() -> "should show manga with one heart"));
	}

	@Test
	@DisplayName("when remove manga from favourites")
	public void when_remove_manga_from_favourites_should_remove_manga_from_favourites() {

		loginPage.loadLoginView(LanguageVersion.PL);

		loginPage.fillMandatoryLoginFields("user", "user");

		mangaList.chooseFirstManga();

		mangaList.addOrRemoveFirstMangaFromFavourite();

		mangaList.addOrRemoveFirstMangaFromFavourite();

		assertAll(() -> assertEquals(String.valueOf(0), mangaList.getFirstMangaFavouritesCounter(),
				() -> "should show manga with zero hearts"));
	}

	@Test
	@DisplayName("when user not logged rating manga")
	public void when_user_not_logged_rating_manga_should_show_login_form() {

		mangaList.loadMangaList(LanguageVersion.PL);

		mangaList.chooseFirstManga();

		mangaList.addOrRemoveFirstMangaFromFavourite();

		assertAll(() -> assertTrue(loginPage.isUserOnLoginPage(), () -> "should show login page"));
	}
}
