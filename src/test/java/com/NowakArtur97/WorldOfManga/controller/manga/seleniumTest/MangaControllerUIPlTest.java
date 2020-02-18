package com.NowakArtur97.WorldOfManga.controller.manga.seleniumTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import com.NowakArtur97.WorldOfManga.testUtil.enums.LanguageVersion;
import com.NowakArtur97.WorldOfManga.testUtil.extension.ScreenshotWatcher;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(ScreenshotWatcher.class)
@TestPropertySource({ "classpath:/validation/messages_pl.properties", "classpath:/pageContent/messages_pl.properties" })
@DisplayName("Manga Controller UI Pl Tests")
@Tag("MangaControllerUIPl_Tests")
@DirtiesContext
@Disabled
public class MangaControllerUIPlTest extends MangaControllerUITest {

	@Test
	@DisplayName("when correct manga creation with all fields")
	public void when_correct_manga_creation_with_all_fields_should_add_manga() {

		String englishTitle = "English title";
		String polishTitle = "Polish title";
		boolean selectAuthor = true;
		boolean addImage = true;

		loginPage.loadLoginView(LanguageVersion.PL);

		loginPage.fillMandatoryLoginFields("admin", "admin");

		mangaFormPage.clickAddOrUpdateMangaLinkButton();

		mangaFormPage.fillMandatoryMangaFormFields(englishTitle, "English description", polishTitle,
				"Polish description", selectAuthor, addImage);

		assertAll(() -> assertTrue(mangaFormPage.countFailureMessages() == 0, () -> "shouldn`t have errors"),
				() -> assertTrue(mangaTranslationService.isTitleAlreadyInUse(englishTitle),
						() -> "should save manga translation in database"),
				() -> assertTrue(mangaTranslationService.isTitleAlreadyInUse(polishTitle),
						() -> "should save manga translation in database"));
	}

	@Test
	@DisplayName("when incorrect manga creation with all blank fields and not selected author or image")
	public void when_incorrect_manga_creation_with_all_blank_fields_and_not_selected_author_or_image_should_have_errors() {

		String blankField = "";
		boolean selectAuthor = false;
		boolean addImage = false;

		loginPage.loadLoginView(LanguageVersion.PL);

		loginPage.fillMandatoryLoginFields("admin", "admin");

		mangaFormPage.clickAddOrUpdateMangaLinkButton();

		mangaFormPage.fillMandatoryMangaFormFields(blankField, blankField, blankField, blankField, selectAuthor,
				addImage);

		assertAll(() -> assertTrue(mangaFormPage.countFailureMessages() == 6, () -> "should have six errors"),
				() -> assertTrue(mangaFormPage.getFormBoxText().contains(mangaTranslationDescriptionNotBlankMessage),
						() -> "should show title is a required field message twice"),
				() -> assertTrue(mangaFormPage.getFormBoxText().contains(mangaTranslationTitleNotBlankMessage),
						() -> "should show description is a required field message twice"),
				() -> assertTrue(mangaFormPage.getFormBoxText().contains(mangaAuthorsRequiredMessage),
						() -> "should show author is a required field message"),
				() -> assertTrue(mangaFormPage.getFormBoxText().contains(mangaImageRequiredMessage),
						() -> "should show image is a required field message"),
				() -> assertEquals(blankField, mangaFormPage.getEnTitle(), () -> "should show incorrect english title"),
				() -> assertEquals(blankField, mangaFormPage.getEnDescription(),
						() -> "should show incorrect english description"),
				() -> assertEquals(blankField, mangaFormPage.getPlTitle(), () -> "should show incorrect polish title"),
				() -> assertEquals(blankField, mangaFormPage.getPlDescription(),
						() -> "should show incorrect polish description"),
				() -> assertFalse(mangaFormPage.isFirstAuthorCheckboxSelected(), () -> "shouldn`t author be selected"));
	}

	@Test
	@DisplayName("when incorrect manga creation with too long field sizes and selected author and image")
	public void when_incorrect_manga_creation_with_too_long_field_sizes_and_selected_author_and_image_should_have_errors() {

		String longText = "asdfghjklpasdfghjklpasdfghjklpasdfghjklpasdfghjklp!@#$%";
		boolean selectAuthor = true;
		boolean addImage = true;

		loginPage.loadLoginView(LanguageVersion.PL);

		loginPage.fillMandatoryLoginFields("admin", "admin");

		mangaFormPage.clickAddOrUpdateMangaLinkButton();

		mangaFormPage.fillMandatoryMangaFormFields(longText, longText, longText, longText, selectAuthor, addImage);

		assertAll(() -> assertTrue(mangaFormPage.countFailureMessages() == 4, () -> "should have four errors"),
				() -> assertTrue(mangaFormPage.getFormBoxText().contains(mangaTranslationTitleSizeMessage),
						() -> "should show title is too long message twice"),
				() -> assertTrue(mangaFormPage.getFormBoxText().contains(mangaTranslationDescriptionSizeMessage),
						() -> "should show description is too long message twice"),
				() -> assertEquals(longText, mangaFormPage.getEnTitle(), () -> "should show incorrect english title"),
				() -> assertEquals(longText, mangaFormPage.getEnDescription(),
						() -> "should show incorrect english description"),
				() -> assertEquals(longText, mangaFormPage.getPlTitle(), () -> "should show incorrect polish title"),
				() -> assertEquals(longText, mangaFormPage.getPlDescription(),
						() -> "should show incorrect polish description"),
				() -> assertTrue(mangaFormPage.isFirstAuthorCheckboxSelected(), () -> "should author be selected"));
	}
}
