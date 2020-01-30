package com.NowakArtur97.WorldOfManga.controller.unloggedUser.seleniumPageObjectModel;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.NowakArtur97.WorldOfManga.testUtils.SeleniumPageObjectModel;

public class LoginControllerSeleniumPOM extends SeleniumPageObjectModel {

	public static final String RESOURCE_PATH = "/user/login";

	private static final String FORM_MESSAGE_SUCCESS_CLASS = "form__message--success";
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private static final String SUBMIT_CLASS = "form__submit";

	@FindBy(className = FORM_MESSAGE_SUCCESS_CLASS)
	private WebElement successMessage;

	@FindBy(name = USERNAME)
	private WebElement usernameInput;

	@FindBy(name = PASSWORD)
	private WebElement passwordInput;

	@FindBy(className = SUBMIT_CLASS)
	private WebElement submitButton;

	public LoginControllerSeleniumPOM(WebDriver webDriver) {

		super(webDriver);
	}

	public String getSuccessMessage() {

		return successMessage.getText();
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

	public void fillMandatoryLoginFields(String username, String password) {

		setUsername(username);

		setPassword(password);

		clickSubmitLoginButton();
	}
}