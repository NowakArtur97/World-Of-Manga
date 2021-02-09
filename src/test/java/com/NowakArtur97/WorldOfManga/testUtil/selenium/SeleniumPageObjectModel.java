package com.NowakArtur97.WorldOfManga.testUtil.selenium;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class SeleniumPageObjectModel {

    private final static int TIME_TO_WAIT = 15;
    private final String mainUrl;

    protected final WebDriver webDriver;

    protected SeleniumPageObjectModel(WebDriver webDriver, String mainUrl) {

        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(webDriver, TIME_TO_WAIT);
        PageFactory.initElements(factory, this);
        this.webDriver = webDriver;
        this.mainUrl = mainUrl;
    }

    protected void connectTo(final String RESOURCE_PATH) {

        webDriver.navigate().to(mainUrl + RESOURCE_PATH);
    }

    protected void useJavaScriptToClickElement(WebElement element) {

        JavascriptExecutor executor = (JavascriptExecutor) webDriver;
        executor.executeScript("arguments[0].click();", element);
    }

    protected void waitFor(long millis) {

        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
