package com.NowakArtur97.WorldOfManga.testUtil.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class SeleniumPageObjectModel {

	private final static String MAIN_URL_PATH = "http://localhost:8000";

	private final static int TIME_TO_WAIT = 15;

	protected final WebDriver webDriver;

	public SeleniumPageObjectModel(WebDriver webDriver) {

		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(webDriver, TIME_TO_WAIT);
		PageFactory.initElements(factory, this);
		this.webDriver = webDriver;
	}

	public void connectTo(final String RESOURCE_PATH) {

		webDriver.navigate().to(MAIN_URL_PATH + RESOURCE_PATH);
	}
}
