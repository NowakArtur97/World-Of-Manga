package com.NowakArtur97.WorldOfManga.controller.author.seleniumTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
@TestPropertySource({ "classpath:/validation/messages_en.properties", "classpath:/pageContent/messages_en.properties" })
@DisplayName("Author Controller UI En Tests")
@Tag("AuthorControllerUIEn_Tests")
@DirtiesContext
public class AuthorControllerUIEnTest extends AuthorControllerUITest {

	@Test
	@DisplayName("when correct author creation with all fields")
	public void when_correct_author_creation_with_all_fields_should_add_author() {

		String fullName = "FirstName LastName";

		loginPage.loadLoginView(LanguageVersion.ENG);

		loginPage.fillMandatoryLoginFields("admin", "admin");

		authorFormPage.clickAddOrUpdateAuthorLinkButton();

		authorFormPage.fillMandatoryAuthorFormFields(fullName);

		assertAll(() -> assertTrue(authorFormPage.countFailureMessages() == 0, () -> "shouldn`t have errors"),
				() -> assertTrue(authorService.isAuthorAlreadyInDatabase(fullName),
						() -> "should save author in database"));
	}

	@Test
	@DisplayName("when incorrect author creation with blank fields")
	public void when_incorrect_author_creation_with_full_name_blank_should_have_errors() {

		String fullName = "";

		loginPage.loadLoginView(LanguageVersion.ENG);

		loginPage.fillMandatoryLoginFields("admin", "admin");

		authorFormPage.clickAddOrUpdateAuthorLinkButton();

		authorFormPage.fillMandatoryAuthorFormFields(fullName);

		assertAll(() -> assertTrue(authorFormPage.countFailureMessages() == 1, () -> "should have one error"),
				() -> assertTrue(authorFormPage.getFormBoxText().contains(authorFulNameNotBlankMessage),
						() -> "should show full name is a required field message"),
				() -> assertEquals(fullName, authorFormPage.getFullName(), () -> "should show incorrect full name"));
	}

	@Test
	@DisplayName("when incorrect author creation with too long fields")
	public void when_incorrect_author_creation_with_too_long_fields_should_have_errors() {

		String fullName = "asdfghjklpasdfghjklpasdfghjklpasdfghjklpasdfghjklp!@#$%";

		loginPage.loadLoginView(LanguageVersion.ENG);

		loginPage.fillMandatoryLoginFields("admin", "admin");

		authorFormPage.clickAddOrUpdateAuthorLinkButton();

		authorFormPage.fillMandatoryAuthorFormFields(fullName);

		assertAll(() -> assertTrue(authorFormPage.countFailureMessages() == 1, () -> "should have one error"),
				() -> assertTrue(authorFormPage.getFormBoxText().contains(authorFullNameSizeMessage),
						() -> "should show full name is too long message"),
				() -> assertEquals(fullName, authorFormPage.getFullName(), () -> "should show incorrect full name"));
	}
}
