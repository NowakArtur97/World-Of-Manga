package com.NowakArtur97.WorldOfManga.testUtil.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class SeleniumPageObjectModel {

	private final static String MAIN_URL_PATH = "http://localhost:";

	private final static int TIME_TO_WAIT = 10;

	protected final WebDriver webDriver;

	protected final int SERVER_PORT;

	public SeleniumPageObjectModel(WebDriver webDriver, int serverPort) {

		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(webDriver, TIME_TO_WAIT);
		PageFactory.initElements(factory, this);
		this.webDriver = webDriver;
		this.SERVER_PORT = serverPort;
	}

	public void connectTo(final String RESOURCE_PATH) {

		webDriver.navigate().to(MAIN_URL_PATH + SERVER_PORT + RESOURCE_PATH);
	}
}
