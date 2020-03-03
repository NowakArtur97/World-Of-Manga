package com.NowakArtur97.WorldOfManga.controller.manga.seleniumTest.mangaInUserList;

import static org.junit.jupiter.api.Assertions.assertAll;
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
@DisplayName("Manga In User List UI En Tests")
@Tag("MangaInUserListUIEn_Tests")
@DirtiesContext
@DisabledOnOs(OS.LINUX)
public class MangaInUserListUIEnTest extends MangaInUserListUITest {

	@Test
	@DisplayName("when add to currently reading list")
	public void when_add_to_currently_reading_list_should_add_to_list() {

		loginPage.loadLoginView(LanguageVersion.ENG);

		loginPage.fillMandatoryLoginFields("user", "user");

		mangaList.loadMangaList(LanguageVersion.ENG);

		mangaList.chooseManga(0);

		int mangaStatus = 0;

		mangaList.addFirstMangaToList(mangaStatus);

		mangaList.clickMangaUserListLink();

		mangaList.chooseCurrentlyReadingManga();

		assertAll(
				() -> assertTrue(mangaList.getLastMangaCardText().contains("Tokyo Ghoul"),
						() -> "should show new manga in currently reading list"),
				() -> assertTrue(mangaList.countMangaCards() >= 1, () -> "should show at least one manga"),
				() -> assertNotNull(mangaList.getMangaListText(), () -> "should load manga list fragment text"));
	}

	@Test
	@DisplayName("when add to completed list")
	public void when_add_to_completed_list_should_add_to_list() {

		loginPage.loadLoginView(LanguageVersion.ENG);

		loginPage.fillMandatoryLoginFields("user", "user");

		mangaList.loadMangaList(LanguageVersion.ENG);

		mangaList.chooseManga(0);

		int mangaStatus = 1;

		mangaList.addFirstMangaToList(mangaStatus);

		mangaList.clickMangaUserListLink();

		mangaList.chooseCompletedManga();

		assertAll(
				() -> assertTrue(mangaList.getLastMangaCardText().contains("Tokyo Ghoul"),
						() -> "should show new manga in completed list"),
				() -> assertTrue(mangaList.countMangaCards() >= 1, () -> "should show at least one manga"),
				() -> assertNotNull(mangaList.getMangaListText(), () -> "should load manga list fragment text"));
	}

	@Test
	@DisplayName("when add to plan to read list")
	public void when_add_to_plan_to_read_list_should_add_to_list() {

		loginPage.loadLoginView(LanguageVersion.ENG);

		loginPage.fillMandatoryLoginFields("user", "user");

		mangaList.loadMangaList(LanguageVersion.ENG);

		mangaList.chooseManga(0);

		int mangaStatus = 2;

		mangaList.addFirstMangaToList(mangaStatus);

		mangaList.clickMangaUserListLink();

		mangaList.choosePlanToReadManga();

		assertAll(
				() -> assertTrue(mangaList.getLastMangaCardText().contains("Tokyo Ghoul"),
						() -> "should show new manga in plan to read list"),
				() -> assertTrue(mangaList.countMangaCards() >= 1, () -> "should show at least one manga"),
				() -> assertNotNull(mangaList.getMangaListText(), () -> "should load manga list fragment text"));
	}

	@Test
	@DisplayName("when add to on hold list")
	public void when_add_to_on_hold_list_should_add_to_list() {

		loginPage.loadLoginView(LanguageVersion.ENG);

		loginPage.fillMandatoryLoginFields("user", "user");

		mangaList.loadMangaList(LanguageVersion.ENG);

		mangaList.chooseManga(0);

		int mangaStatus = 3;

		mangaList.addFirstMangaToList(mangaStatus);

		mangaList.clickMangaUserListLink();

		mangaList.chooseOnHoldManga();

		assertAll(
				() -> assertTrue(mangaList.getLastMangaCardText().contains("Tokyo Ghoul"),
						() -> "should show new manga in on hold list"),
				() -> assertTrue(mangaList.countMangaCards() >= 1, () -> "should show at least one manga"),
				() -> assertNotNull(mangaList.getMangaListText(), () -> "should load manga list fragment text"));
	}

	@Test
	@DisplayName("when add to dropped list")
	public void when_add_to_dropped_list_should_add_to_list() {

		loginPage.loadLoginView(LanguageVersion.ENG);

		loginPage.fillMandatoryLoginFields("user", "user");

		mangaList.loadMangaList(LanguageVersion.ENG);

		mangaList.chooseManga(0);

		int mangaStatus = 4;

		mangaList.addFirstMangaToList(mangaStatus);

		mangaList.clickMangaUserListLink();

		mangaList.chooseDroppedManga();

		assertAll(
				() -> assertTrue(mangaList.getLastMangaCardText().contains("Tokyo Ghoul"),
						() -> "should show new manga in dropped list"),
				() -> assertTrue(mangaList.countMangaCards() >= 1, () -> "should show at least one manga"),
				() -> assertNotNull(mangaList.getMangaListText(), () -> "should load manga list fragment text"));
	}

	@Test
	@DisplayName("when user not logged adding manga to list")
	public void when_user_not_logged_adding_manga_to_list_should_show_login_form() {

		mangaList.loadMangaList(LanguageVersion.ENG);

		mangaList.chooseManga(0);

		int mangaStatus = 0;

		mangaList.addFirstMangaToList(mangaStatus);

		assertAll(() -> assertTrue(loginPage.isUserOnLoginPage(), () -> "should show login page"));
	}
}
