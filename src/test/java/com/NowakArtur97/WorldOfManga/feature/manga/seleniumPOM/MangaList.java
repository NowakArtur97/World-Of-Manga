package com.NowakArtur97.WorldOfManga.feature.manga.seleniumPOM;

import com.NowakArtur97.WorldOfManga.testUtil.enums.LanguageVersion;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumPageObjectModel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MangaList extends SeleniumPageObjectModel {

    private static final String RESOURCE_PATH = "/";

    private static final int NUMBER_OF_MANGA_STATUSES = 5;
    private static final int NUMBER_OF_MANGA_STARS = 5;

    private static final String MANGA_LIST_CLASS = "manga_list";
    private static final String MANGA_CARD_CLASS = "manga_card";
    private static final String MANGA_STAR_CLASS = "manga_card__icon--star";
    private static final String MANGA_RATING_CLASS = "manga_card_rating";
    private static final String MANGA_TITLE_CLASS = "manga_card__title";
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
    private List<WebElement> mangaRatings;

    @FindBy(className = MANGA_TITLE_CLASS)
    private List<WebElement> mangaTitles;

    @FindBy(className = MANGA_FAVOURITE_CLASS)
    private List<WebElement> mangaFavouriteLinks;

    @FindBy(className = MANGA_FAVOURITE_COUNTER_CLASS)
    private List<WebElement> mangaFavouritesCounters;

    @FindBy(className = MANGA_STATUS_CLASS)
    private List<WebElement> mangaStatuses;

    @FindBy(className = MANGA_ADMIN_CLASS)
    private List<WebElement> adminOptions;

    @FindBy(className = MANGA_LIST_TYPE_CLASS)
    private List<WebElement> mangaListTypes;

    @FindBy(xpath = MANGA_LIST_LINK)
    private WebElement mangaListLink;

    @FindBy(xpath = MANGA_WORLD_LINK)
    private WebElement mangaUserListLink;

    public MangaList(WebDriver webDriver, String mainUrl) {

        super(webDriver, mainUrl);
    }

    public void loadMangaList(LanguageVersion ver) {

        super.connectTo(RESOURCE_PATH + ver.getLangUrl());
    }

    public String getMangaListText() {

        return mangaList.getText();
    }

    public String getLastMangaTitle() {

        return mangaTitles.get(mangaCards.size() - 1).getText();
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

        mangaCards.get(mangaIndex + mangaCards.size() / 2).click();
    }

    public void chooseLastManga() {

        mangaCards.get(mangaCards.size() - 1).click();
    }

    public void rateFirstManga(int rating) {

        useJavaScriptToClickElement(mangaStars.get(rating + mangaCards.size() / 2 * NUMBER_OF_MANGA_STATUSES));
    }

    public void rateLastManga(int rating) {

        useJavaScriptToClickElement(mangaStars.get((mangaStars.size() - NUMBER_OF_MANGA_STARS) + rating - 1));
    }

    public String getFirstMangaRating() {

        return mangaRatings.get(mangaCards.size()).getText();
    }

    public String getFirstMangaRatingOnMangaList() {

        return mangaRatings.get(0).getText();
    }

    public void addOrRemoveFirstMangaFromFavourites() {

        useJavaScriptToClickElement(mangaFavouriteLinks.get(mangaCards.size() / 2));
    }

    public void addOrRemoveLastMangaFromFavourites() {

        useJavaScriptToClickElement(mangaFavouriteLinks.get(mangaFavouriteLinks.size() - 1));
    }

    public String getLastMangaFavouritesCounter() {

        return mangaFavouritesCounters.get(mangaFavouritesCounters.size() - 2).getText();
    }

    public void addOrRemoveFirstMangaFromList(int mangaStatus) {

        mangaStatuses.get(mangaStatus + +mangaCards.size() / 2 * NUMBER_OF_MANGA_STATUSES).click();
    }

    public void addLastMangaToList(int mangaStatus) {

        mangaStatuses.get((mangaStatuses.size() - 5) + mangaStatus).click();
    }

    public void chooseFavouritesManga() {

        useJavaScriptToClickElement(mangaListTypes.get(0));
    }

    public void chooseRatedManga() {

        useJavaScriptToClickElement(mangaListTypes.get(1));
    }

    public void chooseCurrentlyReadingManga() {

        useJavaScriptToClickElement(mangaListTypes.get(2));
    }

    public void chooseCompletedManga() {

        useJavaScriptToClickElement(mangaListTypes.get(3));
    }

    public void choosePlanToReadManga() {

        useJavaScriptToClickElement(mangaListTypes.get(4));
    }

    public void chooseOnHoldManga() {

        useJavaScriptToClickElement(mangaListTypes.get(5));
    }

    public void chooseDroppedManga() {

        useJavaScriptToClickElement(mangaListTypes.get(6));
    }

    public void editFirstManga() {

        useJavaScriptToClickElement(adminOptions.get(0));
    }

    public void deleteLastManga() {

        useJavaScriptToClickElement(adminOptions.get(adminOptions.size() - 1));
    }
}
