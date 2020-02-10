package com.NowakArtur97.WorldOfManga.controller.unloggedUser.seleniumPOM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.NowakArtur97.WorldOfManga.testUtil.enums.LanguageVersion;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumPageObjectModel;

public class LoginControllerSeleniumPOM extends SeleniumPageObjectModel {

	public static final String RESOURCE_PATH = "/user/login";

	private static final String FORM_BOX_CLASS = "form__box";
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private static final String SUBMIT_CLASS = "form__submit";

	@FindBy(name = USERNAME)
	private WebElement usernameInput;

	@FindBy(name = PASSWORD)
	private WebElement passwordInput;

	@FindBy(className = SUBMIT_CLASS)
	private WebElement submitButton;

	@FindBy(className = FORM_BOX_CLASS)
	private WebElement formBox;

	public LoginControllerSeleniumPOM(WebDriver webDriver) {

		super(webDriver);
	}

	public void loadLoginView(LanguageVersion ver) {

		super.connectTo(RESOURCE_PATH + ver.getLangUrl());
	}

	public void setUsername(String username) {

		usernameInput.sendKeys(username);
	}

	public void setPassword(String password) {

		passwordInput.sendKeys(password);
	}

	public void clickSubmitLoginButton() {

		submitButton.click();
	}

	public String getFormBoxText() {

		return formBox.getText();
	}

	public void fillMandatoryLoginFields(String username, String password) {

		setUsername(username);

		setPassword(password);

		clickSubmitLoginButton();
	}
}
