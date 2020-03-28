package com.NowakArtur97.WorldOfManga.controller.manga.seleniumTest.mangaController;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;

import com.NowakArtur97.WorldOfManga.testUtil.enums.LanguageVersion;
import com.NowakArtur97.WorldOfManga.testUtil.extension.ScreenshotWatcher;
import com.NowakArtur97.WorldOfManga.testUtil.generator.NameWithSpacesGenerator;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(ScreenshotWatcher.class)
@TestPropertySource({ "classpath:/validation/messages_en.properties", "classpath:/pageContent/messages_en.properties" })
@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("MangaControllerUlEn_Tests")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@DisabledOnOs(OS.LINUX)
public class MangaControllerUIEnTest extends MangaControllerUITest {

	@Test
	public void when_correct_manga_creation_with_all_fields_should_add_manga() {

		String englishTitle = "English title";
		String polishTitle = "Polish title";
		boolean selectAuthor = true;
		boolean selectGenre = true;
		boolean addImage = true;

		loginPage.loadLoginView(LanguageVersion.PL);

		loginPage.fillMandatoryLoginFields("admin", "admin");

		mangaFormPage.clickAddOrUpdateMangaLinkButton();

		mangaFormPage.fillMandatoryMangaFormFields(englishTitle, "English description", polishTitle,
				"Polish description", selectAuthor, selectGenre, addImage);

		assertAll(() -> assertTrue(mangaFormPage.isUserOnMangaFormPage(), () -> "should show manga form page"),
				() -> assertTrue(mangaFormPage.countFailureMessages() == 0, () -> "shouldn`t have errors"),
				() -> assertTrue(mangaTranslationService.isTitleAlreadyInUse(englishTitle),
						() -> "should save english manga translation in database"),
				() -> assertTrue(mangaTranslationService.isTitleAlreadyInUse(polishTitle),
						() -> "should save polish manga translation in database"));
	}

	@Test
	public void when_correct_manga_editing_with_all_fields_should_add_manga() {

		String englishTitle = "Some english title";
		String polishTitle = "Some polish title";
		boolean selectAuthor = true;
		boolean selectGenre = true;
		boolean addImage = true;

		loginPage.loadLoginView(LanguageVersion.PL);

		loginPage.fillMandatoryLoginFields("admin", "admin");

		mangaList.chooseManga(0);

		mangaList.editFirstManga();

		mangaFormPage.fillMandatoryMangaFormFields(englishTitle, "English description", polishTitle,
				"Polish description", selectAuthor, selectGenre, addImage);

		assertAll(() -> assertTrue(mangaFormPage.isUserOnMangaFormPage(), () -> "should show manga form page"),
				() -> assertTrue(mangaFormPage.countFailureMessages() == 0, () -> "shouldn`t have errors"),
				() -> assertTrue(mangaTranslationService.isTitleAlreadyInUse(englishTitle),
						() -> "should update english manga translation in database"),
				() -> assertTrue(mangaTranslationService.isTitleAlreadyInUse(polishTitle),
						() -> "should update polish manga translation in database"));
	}

	@Test
	public void when_manga_deleting_should_remove_manga() {

		loginPage.loadLoginView(LanguageVersion.PL);

		loginPage.fillMandatoryLoginFields("admin", "admin");

		int mangasQuantityBefore = mangaList.countMangaCards();

		mangaList.chooseLastManga();

		mangaList.deleteLastManga();

		int mangasQuantityAfter = mangaList.countMangaCards();

		assertAll(() -> assertTrue((mangasQuantityAfter == mangasQuantityBefore - 1),
				() -> "should show one less manga"));
	}

	@Test
	public void when_manga_deleting_should_not_show_manga_in_rated_manga_list() {

		loginPage.loadLoginView(LanguageVersion.PL);

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

	@Test
	public void when_manga_deleting_should_not_show_manga_in_favourites() {

		loginPage.loadLoginView(LanguageVersion.PL);

		loginPage.fillMandatoryLoginFields("admin", "admin");

		mangaList.chooseLastManga();

		mangaList.addOrRemoveLastMangaFromFavourites();

		mangaList.chooseLastManga();

		mangaList.deleteLastManga();

		mangaList.clickMangaUserListLink();

		mangaList.chooseFavouritesManga();

		assertAll(() -> assertEquals(0, mangaList.countMangaCards(), () -> "shouldn`t show manga on favourites"));
	}

	@Test
	public void when_manga_deleting_should_not_show_manga_in_users_manga_list() {

		loginPage.loadLoginView(LanguageVersion.PL);

		loginPage.fillMandatoryLoginFields("admin", "admin");

		mangaList.chooseLastManga();

		int mangaStatus = 0;

		mangaList.addLastMangaToList(mangaStatus);

		mangaList.chooseLastManga();

		mangaList.deleteLastManga();

		mangaList.clickMangaUserListLink();

		mangaList.chooseCurrentlyReadingManga();

		assertAll(() -> assertEquals(0, mangaList.countMangaCards(), () -> "shouldn`t show manga on list"));
	}

	@Test
	public void when_incorrect_manga_creation_with_all_blank_fields_and_not_selected_author_image_and_genre_should_have_errors() {

		String blankField = "";
		boolean selectAuthor = false;
		boolean selectGenre = false;
		boolean addImage = false;

		loginPage.loadLoginView(LanguageVersion.PL);

		loginPage.fillMandatoryLoginFields("admin", "admin");

		mangaFormPage.clickAddOrUpdateMangaLinkButton();

		mangaFormPage.fillMandatoryMangaFormFields(blankField, blankField, blankField, blankField, selectAuthor,
				selectGenre, addImage);

		assertAll(() -> assertTrue(mangaFormPage.isUserOnMangaFormPage(), () -> "should show manga form page"),
				() -> assertTrue(mangaFormPage.countFailureMessages() == 7, () -> "should have seven errors"),
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

	@Test
	public void when_incorrect_manga_creation_with_too_long_field_sizes_and_selected_author_and_image_should_have_errors() {

		String longTitleText = "asdfghjklpasdfghjklpasdfghjklpasdfghjklpasdfghjklp!@#$%";
		String longDescriptionText = "asdfghjklpasdfghjklpasdfghjklpasdfghjklpasdfghjklp!@#$%".repeat(30);
		boolean selectAuthor = true;
		boolean selectGenre = true;
		boolean addImage = true;

		loginPage.loadLoginView(LanguageVersion.PL);

		loginPage.fillMandatoryLoginFields("admin", "admin");

		mangaFormPage.clickAddOrUpdateMangaLinkButton();

		mangaFormPage.fillMandatoryMangaFormFields(longTitleText, longDescriptionText, longTitleText,
				longDescriptionText, selectAuthor, selectGenre, addImage);

		assertAll(() -> assertTrue(mangaFormPage.isUserOnMangaFormPage(), () -> "should show manga form page"),
				() -> assertTrue(mangaFormPage.countFailureMessages() == 4, () -> "should have four errors"),
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
				() -> assertTrue(mangaFormPage.isSecodnAuthorCheckboxSelected(), () -> "should author be selected"),
				() -> assertTrue(mangaFormPage.isSecondGenreCheckboxSelected(), () -> "should genre be selected"));
	}
}
