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
	private static final String MANGA_FAVOURITE_COUNTER_CLASS = "manga_card__likes";
	private static final String MANGA_FAVOURITE_CLASS = "manga_card__icon--heart";
	private static final String MANGA_STATUS_CLASS = "manga_card__icon--status";
	private static final String MANGA_LIST_TYPE_CLASS = "manga_list_types__type";
	private static final String MANGA_LIST_LINK = "//a[@href='/']";
	private static final String MANGA_WORLD_LINK = "//a[@href='/auth/sortMangaList/5']";

	@FindBy(className = MANGA_LIST_CLASS)
	private WebElement mangaList;

	@FindBy(className = MANGA_CARD_CLASS)
	private List<WebElement> mangaCards;

	@FindBy(className = MANGA_STAR_CLASS)
	private List<WebElement> mangaStars;

	@FindBy(className = MANGA_RATING_CLASS)
	private WebElement mangaRating;

	@FindBy(className = MANGA_FAVOURITE_CLASS)
	private List<WebElement> mangaFavouriteLink;

	@FindBy(className = MANGA_FAVOURITE_COUNTER_CLASS)
	private WebElement mangaFavouritesCounter;

	@FindBy(className = MANGA_STATUS_CLASS)
	private List<WebElement> mangaStatuses;

	@FindBy(className = MANGA_LIST_TYPE_CLASS)
	private List<WebElement> mangaListType;

	@FindBy(xpath = MANGA_LIST_LINK)
	private WebElement mangaListLink;

	@FindBy(xpath = MANGA_WORLD_LINK)
	private WebElement mangaUserListLink;

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

		if (mangaCards.size() > 0) {
			return mangaCards.get(mangaCards.size() - 1).getText();
		} else {
			return "";
		}
	}

	public void clickMangaListLink() {

		mangaListLink.click();
	}

	public void clickMangaUserListLink() {

		JavascriptExecutor executor = (JavascriptExecutor) webDriver;
		executor.executeScript("arguments[0].click();", mangaUserListLink);
	}

	public void chooseFirstManga() {

		mangaCards.get(0).click();
	}

	public void rateFirstManga(int rating) {

		JavascriptExecutor executor = (JavascriptExecutor) webDriver;
		executor.executeScript("arguments[0].click();", mangaStars.get(rating - 1));
	}

	public String getFirstMangaRating() {

		return mangaRating.getText();
	}

	public void addOrRemoveFirstMangaFromFavourite() {

		JavascriptExecutor executor = (JavascriptExecutor) webDriver;
		executor.executeScript("arguments[0].click();", mangaFavouriteLink.get(0));
	}

	public String getFirstMangaFavouritesCounter() {

		return mangaFavouritesCounter.getText();
	}

	public void addFirstMangaToList(int mangaStatus) {

		mangaStatuses.get(mangaStatus).click();
	}

	public void choseFavouritesManga() {

		JavascriptExecutor executor = (JavascriptExecutor) webDriver;
		executor.executeScript("arguments[0].click();", mangaListType.get(0));
	}

	public void choseRatedManga() {

		JavascriptExecutor executor = (JavascriptExecutor) webDriver;
		executor.executeScript("arguments[0].click();", mangaListType.get(1));
	}

	public void choseCurrentlyReadingManga() {

		JavascriptExecutor executor = (JavascriptExecutor) webDriver;
		executor.executeScript("arguments[0].click();", mangaListType.get(2));
	}

	public void choseCompletedManga() {

		JavascriptExecutor executor = (JavascriptExecutor) webDriver;
		executor.executeScript("arguments[0].click();", mangaListType.get(3));
	}

	public void chosePlanToReadManga() {

		JavascriptExecutor executor = (JavascriptExecutor) webDriver;
		executor.executeScript("arguments[0].click();", mangaListType.get(4));
	}

	public void choseOnHoldManga() {

		JavascriptExecutor executor = (JavascriptExecutor) webDriver;
		executor.executeScript("arguments[0].click();", mangaListType.get(5));
	}

	public void choseDroppedManga() {

		JavascriptExecutor executor = (JavascriptExecutor) webDriver;
		executor.executeScript("arguments[0].click();", mangaListType.get(6));
	}
}
