package com.NowakArtur97.WorldOfManga.controller.manga.seleniumTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
@DisplayName("Manga Creation Controller UI Pl Tests")
@Tag("MangaCreationControllerUIPl_Tests")
@DirtiesContext
public class MangaCreationControllerUIPlTest extends MangaCreationControllerUITest {

	@Test
	@DisplayName("when correct manga creation with all fields")
	public void when_correct_manga_creation_with_all_fields_should_add_manga() {

		String englishTitle = "English title";
		String polishTitle = "Polish title";

		loginPage.loadLoginView(LanguageVersion.PL);

		loginPage.fillMandatoryLoginFields("admin", "admin");

		mangaFormPage.clickAddOrUpdateMangaLinkButton();

		mangaFormPage.fillMandatoryLoginFormFields(englishTitle, "English description", polishTitle,
				"Polish description");

		assertAll(
				() -> assertTrue(mangaTranslationService.isTitleAlreadyInUse(englishTitle),
						() -> "should save manga translation in database"),
				() -> assertTrue(mangaTranslationService.isTitleAlreadyInUse(polishTitle),
						() -> "should save manga translation in database"));
	}

	@Test
	@DisplayName("when incorrect manga creation with all blank fields")
	public void when_incorrect_manga_creation_with_all_blank_fields_should_have_errors() {

		String blankField = "";

		loginPage.loadLoginView(LanguageVersion.PL);

		loginPage.fillMandatoryLoginFields("admin", "admin");

		mangaFormPage.clickAddOrUpdateMangaLinkButton();

		mangaFormPage.fillMandatoryLoginFormFields(blankField, blankField, blankField, blankField);

		assertAll(() -> assertTrue(mangaFormPage.countFailureMessages() == 4, () -> "should have four errors"),
				() -> assertTrue(mangaFormPage.getFormBoxText().contains(mangaTranslationDescriptionNotBlankMessage),
						() -> "should show title is a required field message twice"),
				() -> assertTrue(mangaFormPage.getFormBoxText().contains(mangaTranslationTitleNotBlankMessage),
						() -> "should show description is a required field message twice"));
	}

	@Test
	@DisplayName("when incorrect manga creation with too long field sizes")
	public void when_incorrect_manga_creation_with_too_long_field_sizes_should_have_errors() {

		String longText = "asdfghjklpasdfghjklpasdfghjklpasdfghjklpasdfghjklp!@#$%";

		loginPage.loadLoginView(LanguageVersion.PL);

		loginPage.fillMandatoryLoginFields("admin", "admin");

		mangaFormPage.clickAddOrUpdateMangaLinkButton();

		mangaFormPage.fillMandatoryLoginFormFields(longText, longText, longText, longText);

		assertAll(() -> assertTrue(mangaFormPage.countFailureMessages() == 4, () -> "should have four errors"),
				() -> assertTrue(mangaFormPage.getFormBoxText().contains(mangaTranslationTitleSizeMessage),
						() -> "should show title is too long message twice"),
				() -> assertTrue(mangaFormPage.getFormBoxText().contains(mangaTranslationDescriptionSizeMessage),
						() -> "should show description is too long message twice"));
	}
}
