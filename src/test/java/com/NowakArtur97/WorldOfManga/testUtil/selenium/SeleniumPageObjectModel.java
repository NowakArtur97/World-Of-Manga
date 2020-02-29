package com.NowakArtur97.WorldOfManga.testUtil.selenium;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class SeleniumPageObjectModel {

	private final static String MAIN_URL_PATH = "http://localhost:8000";

	private final static int TIME_TO_WAIT = 15;

	protected final WebDriver webDriver;

	protected SeleniumPageObjectModel(WebDriver webDriver) {

		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(webDriver, TIME_TO_WAIT);
		PageFactory.initElements(factory, this);
		this.webDriver = webDriver;
	}

	protected void connectTo(final String RESOURCE_PATH) {

		webDriver.navigate().to(MAIN_URL_PATH + RESOURCE_PATH);
	}
	
	protected void useJavaScriptToClickElement(WebElement element) {
		
		JavascriptExecutor executor = (JavascriptExecutor) webDriver;
		executor.executeScript("arguments[0].click();", element);
	}
}
