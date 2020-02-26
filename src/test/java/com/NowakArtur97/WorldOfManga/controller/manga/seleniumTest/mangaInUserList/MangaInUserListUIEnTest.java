package com.NowakArtur97.WorldOfManga.controller.manga.seleniumTest.mangaInUserList;

import static org.junit.jupiter.api.Assertions.assertAll;
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
@DisplayName("Manga In User List UI En Tests")
@Tag("MangaInUserListUIEn_Tests")
@DirtiesContext
public class MangaInUserListUIEnTest extends MangaInUserListUITest {

	@Test
	@DisplayName("when user not logged adding manga to list")
	public void when_user_not_logged_adding_manga_to_list_should_show_login_form() {

		mangaList.loadMangaList(LanguageVersion.ENG);

		mangaList.chooseFirstManga();

		int mangaStatus = 0;

		mangaList.addFirstMangaToList(mangaStatus);

		assertAll(() -> assertTrue(loginPage.isUserOnLoginPage(), () -> "should show login page"));
	}
}
