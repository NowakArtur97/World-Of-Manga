package com.NowakArtur97.WorldOfManga.controller.manga.seleniumTest.mangaRating;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
@DisplayName("Manga Rating UI En Tests")
@Tag("MangaRatingUIEn_Tests")
@DirtiesContext
@DisabledOnOs(OS.LINUX)
public class MangaRatingUIEnTest extends MangaRatingUITest {

	@Test
	@DisplayName("when rate manga")
	public void when_rate_manga_should_show_updated_rating() {

		loginPage.loadLoginView(LanguageVersion.ENG);

		loginPage.fillMandatoryLoginFields("user", "user");

		mangaList.chooseManga(0);;

		int mangaRating = 5;

		mangaList.rateFirstManga(mangaRating);

		assertAll(() -> assertTrue(mangaList.getFirstMangaRating().contains(String.valueOf(mangaRating)),
				() -> "should show manga rating"));
	}

	@Test
	@DisplayName("when rate manga")
	public void when_rate_manga_should_show_rating_on_user_manga_list() {

		loginPage.loadLoginView(LanguageVersion.ENG);

		loginPage.fillMandatoryLoginFields("user", "user");

		mangaList.chooseManga(0);;

		int mangaRating = 5;

		mangaList.rateFirstManga(mangaRating);

		mangaList.clickMangaUserListLink();

		mangaList.chooseRatedManga();

		assertAll(() -> assertTrue(mangaList.getFirstMangaRating().contains(String.valueOf(mangaRating)),
				() -> "should show manga rating on manga list"));
	}

	@Test
	@DisplayName("when rate manga second time")
	public void when_rate_second_time_manga_should_show_updated_rating() {

		loginPage.loadLoginView(LanguageVersion.ENG);

		loginPage.fillMandatoryLoginFields("user", "user");

		mangaList.chooseManga(0);;

		int firstMangaRating = 5;

		mangaList.rateFirstManga(firstMangaRating);

		mangaList.chooseManga(0);;

		int secondMangaRating = 4;

		mangaList.rateFirstManga(secondMangaRating);

		assertAll(
				() -> assertFalse(mangaList.getFirstMangaRating().contains(String.valueOf(firstMangaRating)),
						() -> "shouldn`t show old manga rating"),
				() -> assertTrue(mangaList.getFirstMangaRating().contains(String.valueOf(secondMangaRating)),
						() -> "should show updated manga rating"));
	}

	@Test
	@DisplayName("when user not logged rating manga")
	public void when_user_not_logged_rating_manga_should_show_login_form() {

		mangaList.loadMangaList(LanguageVersion.ENG);

		mangaList.chooseManga(0);;

		int mangaRating = 5;

		mangaList.rateFirstManga(mangaRating);

		assertAll(() -> assertTrue(loginPage.isUserOnLoginPage(), () -> "should show login page"));
	}
}
