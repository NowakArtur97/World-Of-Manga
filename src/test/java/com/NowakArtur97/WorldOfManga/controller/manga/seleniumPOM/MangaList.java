package com.NowakArtur97.WorldOfManga.controller.manga.seleniumPOM;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.NowakArtur97.WorldOfManga.testUtil.enums.LanguageVersion;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumPageObjectModel;

public class MangaList extends SeleniumPageObjectModel {

	public static final String RESOURCE_PATH = "/";

	private static final int INDEX_FOR_SKIPPING_RECOMMENDATIONS_STATUSES = 50;
	private static final int INDEX_FOR_SKIPPING_RECOMMENDATIONS_RATINGS_OR_FAVOURITES = 20;

	private static final String MANGA_LIST_CLASS = "manga_list";
	private static final String MANGA_CARD_CLASS = "manga_card";
	private static final String MANGA_STAR_CLASS = "manga_card__icon--star";
	private static final String MANGA_RATING_CLASS = "manga_card_rating";
	private static final String MANGA_FAVOURITE_COUNTER_CLASS = "manga_card__likes";
	private static final String MANGA_FAVOURITE_CLASS = "manga_card__icon--heart";
	private static final String MANGA_STATUS_CLASS = "manga_card__icon--status";
	private static final String MANGA_ADMIN_CLASS = "manga_card__icon--admin";
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
	private List<WebElement> mangaRating;

	@FindBy(className = MANGA_FAVOURITE_CLASS)
	private List<WebElement> mangaFavouriteLink;

	@FindBy(className = MANGA_FAVOURITE_COUNTER_CLASS)
	private List<WebElement> mangaFavouritesCounter;

	@FindBy(className = MANGA_STATUS_CLASS)
	private List<WebElement> mangaStatuses;

	@FindBy(className = MANGA_ADMIN_CLASS)
	private List<WebElement> adminOptions;

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

		useJavaScriptToClickElement(mangaUserListLink);
	}

	public void chooseManga(int mangaIndex) {

		mangaCards.get(mangaIndex + 10).click();
	}

	public void chooseLastManga() {

		mangaCards.get(mangaCards.size() - 1).click();
	}

	public void rateFirstManga(int rating) {

		useJavaScriptToClickElement(mangaStars.get(INDEX_FOR_SKIPPING_RECOMMENDATIONS_STATUSES + rating - 1));
	}

	public void rateLastManga(int rating) {

		useJavaScriptToClickElement(mangaStars.get((mangaStars.size() - 5) + rating - 1));
	}

	public String getFirstMangaRating() {

		return mangaRating.get(INDEX_FOR_SKIPPING_RECOMMENDATIONS_RATINGS_OR_FAVOURITES).getText();
	}

	public void addOrRemoveFirstMangaFromFavourites() {

		useJavaScriptToClickElement(mangaFavouriteLink.get(INDEX_FOR_SKIPPING_RECOMMENDATIONS_RATINGS_OR_FAVOURITES));
	}

	public void addOrRemoveLastMangaFromFavourites() {

		useJavaScriptToClickElement(mangaFavouriteLink.get(mangaFavouriteLink.size() - 1));
	}

	public String getLastMangaFavouritesCounter() {

		return mangaFavouritesCounter.get(mangaFavouritesCounter.size() - 2).getText();
	}

	public void addFirstMangaToList(int mangaStatus) {

		mangaStatuses.get(INDEX_FOR_SKIPPING_RECOMMENDATIONS_STATUSES + mangaStatus).click();
	}

	public void addLastMangaToList(int mangaStatus) {

		mangaStatuses.get((mangaStatuses.size() - 5) + mangaStatus).click();
	}

	public void chooseFavouritesManga() {

		useJavaScriptToClickElement(mangaListType.get(0));
	}

	public void chooseRatedManga() {

		useJavaScriptToClickElement(mangaListType.get(1));
	}

	public void chooseCurrentlyReadingManga() {

		useJavaScriptToClickElement(mangaListType.get(2));
	}

	public void chooseCompletedManga() {

		useJavaScriptToClickElement(mangaListType.get(3));
	}

	public void choosePlanToReadManga() {

		useJavaScriptToClickElement(mangaListType.get(4));
	}

	public void chooseOnHoldManga() {

		useJavaScriptToClickElement(mangaListType.get(5));
	}

	public void chooseDroppedManga() {

		useJavaScriptToClickElement(mangaListType.get(6));
	}

	public void editFirstManga() {

		useJavaScriptToClickElement(adminOptions.get(0));
	}

	public void deleteLastManga() {

		useJavaScriptToClickElement(adminOptions.get(adminOptions.size() - 1));
	}
}
