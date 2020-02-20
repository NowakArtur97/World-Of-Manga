package com.NowakArtur97.WorldOfManga.controller.manga.seleniumTest.mangaList;

import org.junit.jupiter.api.BeforeEach;

import com.NowakArtur97.WorldOfManga.controller.manga.seleniumPOM.MangaFormPage;
import com.NowakArtur97.WorldOfManga.controller.manga.seleniumPOM.MangaList;
import com.NowakArtur97.WorldOfManga.controller.unloggedUser.seleniumPOM.LoginPage;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumUITest;

public class MangaListUITest extends SeleniumUITest {

	protected MangaList mangaList;

	protected MangaFormPage mangaFormPage;

	protected LoginPage loginPage;

	@BeforeEach
	public void setupPOM() {

		mangaList = new MangaList(webDriver);

		loginPage = new LoginPage(webDriver);

		mangaFormPage = new MangaFormPage(webDriver);
	}
}
