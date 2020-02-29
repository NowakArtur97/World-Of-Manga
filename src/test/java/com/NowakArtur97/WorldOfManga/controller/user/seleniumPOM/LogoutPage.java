package com.NowakArtur97.WorldOfManga.controller.user.seleniumPOM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumPageObjectModel;

public class LogoutPage extends SeleniumPageObjectModel {

	private static final String LOGOUT_LINK = "//a[@href='/auth/logout']";

	@FindBy(xpath = LOGOUT_LINK)
	private WebElement logoutLink;

	public LogoutPage(WebDriver webDriver) {

		super(webDriver);
	}

	public void signOut() {

		useJavaScriptToClickElement(logoutLink);
	}
}
