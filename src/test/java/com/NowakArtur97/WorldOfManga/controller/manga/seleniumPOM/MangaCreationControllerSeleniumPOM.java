package com.NowakArtur97.WorldOfManga.controller.manga.seleniumPOM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.NowakArtur97.WorldOfManga.testUtil.enums.LanguageVersion;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumPageObjectModel;

public class MangaCreationControllerSeleniumPOM extends SeleniumPageObjectModel {

	public static final String RESOURCE_PATH = "/admin/addOrUpdateManga";

	private static final String TITLE_EN = "enTranslation.title";
	private static final String DESCRIPTION_EN = "enTranslation.description";
	private static final String TITLE_PL = "plTranslation.title";
	private static final String DESCRIPTION_PL = "plTranslation.description";
	private static final String SUBMIT_CLASS = "form__submit";
	private static final String FORM_BOX_CLASS = "form__box";

	@FindBy(className = TITLE_EN)
	private WebElement titleEnInput;

	@FindBy(className = DESCRIPTION_EN)
	private WebElement descriptionEnInput;

	@FindBy(className = TITLE_PL)
	private WebElement titlePlInput;

	@FindBy(className = DESCRIPTION_PL)
	private WebElement descriptionPlInput;

	@FindBy(className = FORM_BOX_CLASS)
	private WebElement formBox;

	@FindBy(className = SUBMIT_CLASS)
	private WebElement submitButton;

	public MangaCreationControllerSeleniumPOM(WebDriver webDriver) {

		super(webDriver);
	}

	public void loadMangaForm(LanguageVersion ver) {

		super.connectTo(RESOURCE_PATH + ver.getLangUrl());
	}

	public void setEnTitle(String enTitle) {

		titleEnInput.sendKeys(enTitle);
	}

	public void setEnDescription(String enDescription) {

		descriptionEnInput.sendKeys(enDescription);
	}

	public void setPlTitle(String plTitle) {

		titlePlInput.sendKeys(plTitle);
	}

	public void setPlDescription(String plDescription) {

		descriptionPlInput.sendKeys(plDescription);
	}

	public void clickSubmitMangaFormButton() {

		submitButton.click();
	}

	public String getFormBoxText() {

		return formBox.getText();
	}

	public void fillMandatoryLoginFormFields(String enTitle, String enDescription, String plTitle,
			String plDescription) {

		setEnTitle(enTitle);
		
		setEnDescription(enDescription);
		
		setPlTitle(plTitle);
		
		setPlDescription(plDescription);

		clickSubmitMangaFormButton();
	}
}
