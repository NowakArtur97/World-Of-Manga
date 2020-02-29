package com.NowakArtur97.WorldOfManga.controller.manga.seleniumTest.mangaController;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.NowakArtur97.WorldOfManga.controller.manga.seleniumPOM.MangaFormPage;
import com.NowakArtur97.WorldOfManga.controller.manga.seleniumPOM.MangaList;
import com.NowakArtur97.WorldOfManga.controller.unloggedUser.seleniumPOM.LoginPage;
import com.NowakArtur97.WorldOfManga.service.api.MangaTranslationService;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumUITest;

public class MangaControllerUITest extends SeleniumUITest {

	@Value("${mangaTranslation.titleEn.inUse}")
	protected String mangaTranslationTitleEnInUseMessage;

	@Value("${mangaTranslation.titlePl.inUse}")
	protected String mangaTranslationTitlePlInUseMessage;

	@Value("${mangaTranslation.title.notBlank}")
	protected String mangaTranslationTitleNotBlankMessage;

	@Value("${mangaTranslation.title.size}")
	protected String mangaTranslationTitleSizeMessage;

	@Value("${mangaTranslation.description.notBlank}")
	protected String mangaTranslationDescriptionNotBlankMessage;

	@Value("${mangaTranslation.description.size}")
	protected String mangaTranslationDescriptionSizeMessage;

	@Value("${manga.authors.notEmpty}")
	protected String mangaAuthorsRequiredMessage;

	@Value("${manga.image.notEmpty}")
	protected String mangaImageRequiredMessage;

	protected MangaFormPage mangaFormPage;

	protected LoginPage loginPage;

	protected MangaList mangaList;

	@Autowired
	protected MangaTranslationService mangaTranslationService;

	@BeforeEach
	public void setupPOM() {

		loginPage = new LoginPage(webDriver);

		mangaFormPage = new MangaFormPage(webDriver);
		
		mangaList = new MangaList(webDriver);
	}
}
