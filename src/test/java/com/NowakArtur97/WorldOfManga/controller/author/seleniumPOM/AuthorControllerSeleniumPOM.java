package com.NowakArtur97.WorldOfManga.controller.author.seleniumPOM;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.NowakArtur97.WorldOfManga.testUtil.enums.LanguageVersion;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumPageObjectModel;

public class AuthorControllerSeleniumPOM extends SeleniumPageObjectModel {

	public static final String RESOURCE_PATH = "/admin/addOrUpdateAuthor";

	private static final String FULL_NAME = "fullName";
	private static final String FORM_MESSAGE_FAILURE_CLASS = "form__message--failure";
	private static final String FORM_BOX_NAME = "addOrUpdateAuthorForm";
	private static final String SUBMIT_AUTHOR = "addOrUpdateAuthorSubmitBtn";
	private static final String ADD_OR_UPDATE_AUTHOR_LINK = "//a[@href='/admin/addOrUpdateManga']";

	@FindBy(name = FULL_NAME)
	private WebElement fullNameInput;

	@FindBy(className = FORM_MESSAGE_FAILURE_CLASS)
	private List<WebElement> failrueMessages;

	@FindBy(name = FORM_BOX_NAME)
	private WebElement formBox;

	@FindBy(name = SUBMIT_AUTHOR)
	private WebElement submitButton;

	@FindBy(xpath = ADD_OR_UPDATE_AUTHOR_LINK)
	private WebElement addOrUpdateAuthorLink;

	public AuthorControllerSeleniumPOM(WebDriver webDriver) {

		super(webDriver);
	}

	public void loadAuthorForm(LanguageVersion ver) {

		super.connectTo(RESOURCE_PATH + ver.getLangUrl());
	}

	public void clickSubmitAuthorFormButton() {

		submitButton.click();
	}

	public void setFullName(String fullName) {

		fullNameInput.sendKeys(fullName);
	}

	public String getFormBoxText() {

		return formBox.getText();
	}

	public int countFailureMessages() {

		return failrueMessages.size();
	}

	public void clickAddOrUpdateAuthorLinkButton() {
		
		JavascriptExecutor executor = (JavascriptExecutor)webDriver;
		executor.executeScript("arguments[0].click();", addOrUpdateAuthorLink);
	}
	
	public void fillMandatoryAuthorFormFields(String fullName) {

		setFullName(fullName);

		clickSubmitAuthorFormButton();
	}
}
