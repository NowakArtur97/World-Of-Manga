package com.NowakArtur97.WorldOfManga.controller.manga.seleniumTest.mangaList;

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
@DisplayName("Manga List UI En Tests")
@Tag("MangaListUIEn_Tests")
@DirtiesContext
@DisabledOnOs(OS.LINUX)
public class MangaListUIEnTest extends MangaListUITest {

	@Test
	@DisplayName("when added new manga")
	public void when_added_new_manga_should_show_manga_on_manga_list() {

		String englishTitle = "English title";
		String polishTitle = "Polish title";
		boolean selectAuthor = true;
		boolean addImage = true;

		loginPage.loadLoginView(LanguageVersion.ENG);

		loginPage.fillMandatoryLoginFields("admin", "admin");

		mangaFormPage.clickAddOrUpdateMangaLinkButton();

		mangaFormPage.fillMandatoryMangaFormFields(englishTitle, "English description", polishTitle,
				"Polish description", selectAuthor, addImage);

		mangaList.clickMangaListButton();

		assertAll(
				() -> assertTrue(mangaList.getLastMangaCardText().contains(englishTitle),
						() -> "should show new manga with english title"),
				() -> assertTrue(mangaList.countMangaCards() >= 1, () -> "should show at least one manga"),
				() -> assertNotNull(mangaList.getMangaListText(), () -> "should load manga list fragment text"));
	}
}
