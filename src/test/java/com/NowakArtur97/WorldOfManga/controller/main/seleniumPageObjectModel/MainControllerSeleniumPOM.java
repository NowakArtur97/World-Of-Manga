package com.NowakArtur97.WorldOfManga.controller.main.seleniumPageObjectModel;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.NowakArtur97.WorldOfManga.testUtils.SeleniumPageObjectModel;

public class MainControllerSeleniumPOM extends SeleniumPageObjectModel {

	public static final String RESOURCE_PATH = "/";

	private static final String MAIN_PAGE_CLASS = "page";
	private static final String HEADER_CLASS = "header";
	private static final String FOOTER_CLASS = "footer";

	@FindBy(className = MAIN_PAGE_CLASS)
	private WebElement mainPage;

	@FindBy(className = HEADER_CLASS)
	private WebElement header;

	@FindBy(className = FOOTER_CLASS)
	private WebElement footer;

	public MainControllerSeleniumPOM(WebDriver webDriver) {

		super(webDriver);
	}

	public void loadMainView() {
		
		super.connectTo(RESOURCE_PATH);
	}
	
	public String getHeaderText() {

		return header.getText();
	}

	public String getFooterText() {

		return footer.getText();
	}

	public String getMainPageText() {

		return mainPage.getText();
	}
}
