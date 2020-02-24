package com.NowakArtur97.WorldOfManga.controller.mangaRating.seleniumTest;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import com.NowakArtur97.WorldOfManga.controller.manga.seleniumPOM.MangaList;
import com.NowakArtur97.WorldOfManga.controller.unloggedUser.seleniumPOM.LoginPage;
import com.NowakArtur97.WorldOfManga.service.api.MangaRatingService;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumUITest;

public class MangaRatingUITest extends SeleniumUITest {

	protected MangaList mangaList;

	protected LoginPage loginPage;

	@Autowired
	protected MangaRatingService mangaRatingSevice;

	@BeforeEach
	public void setupPOM() {

		mangaList = new MangaList(webDriver);

		loginPage = new LoginPage(webDriver);
	}
}
