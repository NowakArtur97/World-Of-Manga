package com.NowakArtur97.WorldOfManga.feature.manga.seleniumPOM;

import com.NowakArtur97.WorldOfManga.feature.mainPage.seleniumPOM.MainPage;
import com.NowakArtur97.WorldOfManga.testUtil.enums.LanguageVersion;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumPageObjectModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

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
    private static final String MANGA_STATUS_LINK = "[href*='auth/addToList']";
    private static final String MANGA_ADMIN_LINK = "[href*='admin']";
    private static final String MANGA_LIST_TYPE_CLASS = "manga_list_types__type";
    private static final String MANGA_LIST_LINK = "//a[@href='/']";
    private static final String MANGA_WORLD_LINK = "//a[@href='/auth/sortMangaList/5']";
    private static final String STATUS_LIST_LINK = "auth/sortMangaList/";

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

    @FindBy(css = MANGA_STATUS_LINK)
    private List<WebElement> mangaStatuses;

    @FindBy(css = MANGA_ADMIN_LINK)
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

        return mangaList.getAttribute("textContent");
    }

    public String getMangaTitle(int index) {

        return mangaTitles.get(index + mangaCards.size() / 2).getText();
    }

    public String getLastMangaTitle() {

        return mangaTitles.get(mangaCards.size() - 1).getAttribute("textContent");
    }

    public int countMangaCards() {

        webDriver.navigate().refresh();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className(MainPage.MAIN_PAGE_CLASS)));

        return webDriver.findElements(By.className(MANGA_CARD_CLASS)).size();
    }

    public String getLastMangaCardText() {

        webDriver.navigate().refresh();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className(MANGA_CARD_CLASS)));

        mangaCards = webDriver.findElements(By.className(MANGA_CARD_CLASS));

        if (mangaCards.size() > 0) {
            return mangaCards.get(mangaCards.size() - 1).getText();
        } else {
            return "";
        }
    }

    public String getLastMangaCardTextInMainList() {

        webDriver.navigate().refresh();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className(MainPage.MAIN_PAGE_CLASS)));

        mangaCards = webDriver.findElements(By.className(MANGA_CARD_CLASS));

        if (mangaCards.size() > 0) {
            return mangaCards.get(mangaCards.size() - 1).getText();
        } else {
            return "";
        }
    }

    public void clickMangaListLink() {

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(MANGA_LIST_LINK)));

        useJavaScriptToClickElement(mangaListLink);
    }

    public void clickMangaUserListLink() {

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className(MANGA_LIST_CLASS)));
        webDriver.navigate().refresh();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className(MANGA_RATING_CLASS)));

        useJavaScriptToClickElement(mangaUserListLink);
    }

    public void chooseManga(int mangaIndex) {

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className(MANGA_RATING_CLASS)));

        useJavaScriptToClickElement(mangaCards.get(mangaIndex + mangaCards.size() / 2));
    }

    public void chooseLastManga() {

        webDriver.navigate().refresh();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className(MANGA_LIST_CLASS)));
        webDriverWait.until(ExpectedConditions.visibilityOf(mangaCards.get(mangaCards.size() - 1)));

        useJavaScriptToClickElement(mangaCards.get(mangaCards.size() - 1));
        mangaCards.get(mangaCards.size() - 1).click();
    }

    public void rateFirstManga(int rating) {

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className(MANGA_CARD_CLASS)));

        useJavaScriptToClickElement(mangaStars.get(rating + mangaCards.size() / 2 * NUMBER_OF_MANGA_STARS - 1));
    }

    public void rateLastManga(int rating) {

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className(MANGA_CARD_CLASS)));

        useJavaScriptToClickElement(mangaStars.get(rating + mangaStars.size() - NUMBER_OF_MANGA_STARS - 1));
    }

    public String getLastMangaRating() {

        webDriver.navigate().refresh();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className(MANGA_CARD_CLASS)));
        webDriverWait.until(ExpectedConditions.visibilityOf(mangaRatings.get(mangaCards.size() - 1)));
        webDriverWait.until(ExpectedConditions.attributeToBeNotEmpty(mangaRatings.get(mangaCards.size() - 1), "textContent"));

        return webDriver.findElements(By.className(MANGA_RATING_CLASS)).get(mangaRatings.size() - 1).getAttribute("textContent");
    }

    public void addOrRemoveFirstMangaFromFavourites() {

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className(MANGA_CARD_CLASS)));

        useJavaScriptToClickElement(mangaFavouriteLinks.get(mangaFavouriteLinks.size() / 2));
    }

    public void addOrRemoveLastMangaFromFavourites() {

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className(MANGA_LIST_CLASS)));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className(MANGA_CARD_CLASS)));
        webDriverWait.until(ExpectedConditions.visibilityOf(mangaFavouriteLinks.get(mangaFavouriteLinks.size() - 1)));

        useJavaScriptToClickElement(mangaFavouriteLinks.get(mangaFavouriteLinks.size() - 1));
    }

    public String getLastMangaFavouritesCounter() {

        webDriver.navigate().refresh();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className(MANGA_CARD_CLASS)));

        return mangaFavouritesCounters.get(mangaFavouritesCounters.size() - 2).getAttribute("textContent");
    }

    public void addOrRemoveLastMangaFromList(int mangaStatus) {

        webDriver.navigate().refresh();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className(MANGA_LIST_CLASS)));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className(MANGA_CARD_CLASS)));
        webDriverWait.until(ExpectedConditions.visibilityOf(mangaCards.get(mangaCards.size() - 1)));

        useJavaScriptToClickElement(mangaStatuses.get(mangaStatus + mangaStatuses.size() - NUMBER_OF_MANGA_STATUSES));
    }

    public void addLastMangaToList(int mangaStatus) {

        useJavaScriptToClickElement(mangaStatuses.get((mangaStatuses.size() - 5) + mangaStatus));
    }

    public void chooseFavouritesManga() {

        useJavaScriptToClickElement(mangaListTypes.get(0));
    }

    public void chooseRatedManga() {

        useJavaScriptToClickElement(mangaListTypes.get(1));
    }

    public void chooseListByStatus(int status, LanguageVersion ver) {

        super.connectTo(RESOURCE_PATH + STATUS_LIST_LINK + status + ver.getLangUrl());
    }

    public void editFirstManga() {

        useJavaScriptToClickElement(adminOptions.get(0));
    }

    public void deleteLastManga() {

        useJavaScriptToClickElement(adminOptions.get(adminOptions.size() - 1));

        webDriver.navigate().refresh();
    }
}
