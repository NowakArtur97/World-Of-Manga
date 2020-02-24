package com.NowakArtur97.WorldOfManga.controller.manga.seleniumPOM;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.NowakArtur97.WorldOfManga.testUtil.enums.LanguageVersion;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumPageObjectModel;

public class MangaList extends SeleniumPageObjectModel {

	public static final String RESOURCE_PATH = "/";

	private static final String MANGA_LIST_CLASS = "manga_list";
	private static final String MANGA_CARD_CLASS = "manga_card";
	private static final String MANGA_STAR_CLASS = "manga_card__icon--star";
	private static final String MANGA_RATING_CLASS = "manga_card_rating";
	private static final String MANGA_LIST_LINK = "//a[@href='/']";

	@FindBy(className = MANGA_LIST_CLASS)
	private WebElement mangaList;

	@FindBy(className = MANGA_CARD_CLASS)
	private List<WebElement> mangaCards;

	@FindBy(className = MANGA_STAR_CLASS)
	private List<WebElement> mangaStars;
	
	@FindBy(className = MANGA_RATING_CLASS)
	private WebElement mangaRating;
	
	@FindBy(xpath = MANGA_LIST_LINK)
	private WebElement mangaListLink;

	public MangaList(WebDriver webDriver) {

		super(webDriver);
	}

	public void loadMangaList(LanguageVersion ver) {

		super.connectTo(RESOURCE_PATH + ver.getLangUrl());
	}

	public String getMangaListText() {

		return mangaList.getText();
	}

	public int countMangaCards() {

		return mangaCards.size();
	}

	public String getLastMangaCardText() {

		return mangaCards.get(mangaCards.size() - 1).getText();
	}

	public void clickMangaListButton() {

		JavascriptExecutor executor = (JavascriptExecutor) webDriver;
		executor.executeScript("arguments[0].click();", mangaListLink);
	}

	public void chooseFirstManga() {

		mangaCards.get(0).click();
	}

	public void rateManga(int rating) {

		mangaStars.get(rating - 1).click();
	}
	
	public String getFirstMangaRating() {
		
		return mangaRating.getText();
	}
}
