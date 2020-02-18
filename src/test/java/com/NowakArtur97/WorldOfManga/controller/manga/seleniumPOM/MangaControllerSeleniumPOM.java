package com.NowakArtur97.WorldOfManga.controller.manga.seleniumPOM;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.NowakArtur97.WorldOfManga.testUtil.enums.LanguageVersion;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumPageObjectModel;

public class MangaControllerSeleniumPOM extends SeleniumPageObjectModel {

	public static final String RESOURCE_PATH = "/admin/addOrUpdateManga";

	private final static String projectPath = System.getProperty("user.dir");

//	private final static String EXAMPLE_IMAGE_PATH = "\\src\\main\\resources\\static\\images\\samurai.jpg";
	private final static String EXAMPLE_IMAGE_PATH = "/src/main/resources/static/images/samurai.jpg";

	private static final String TITLE_EN = "enTranslation.title";
	private static final String DESCRIPTION_EN = "enTranslation.description";
	private static final String TITLE_PL = "plTranslation.title";
	private static final String DESCRIPTION_PL = "plTranslation.description";
	private static final String IMAGE = "image";
	private static final String AUTHORS = "authors";
	private static final String FORM_MESSAGE_FAILURE_CLASS = "form__message--failure";
	private static final String FORM_BOX_NAME = "addOrUpdateMangaForm";
	private static final String SUBMIT_MANGA = "addOrUpdateMangaSubmitBtn";
	private static final String ADD_OR_UPDATE_MANGA_LINK = "//a[@href='/admin/addOrUpdateManga']";

	@FindBy(name = TITLE_EN)
	private WebElement titleEnInput;

	@FindBy(name = DESCRIPTION_EN)
	private WebElement descriptionEnInput;

	@FindBy(name = TITLE_PL)
	private WebElement titlePlInput;

	@FindBy(name = DESCRIPTION_PL)
	private WebElement descriptionPlInput;

	@FindBy(name = IMAGE)
	private WebElement imageInput;

	@FindBy(name = AUTHORS)
	private List<WebElement> authorsCheckboxes;

	@FindBy(className = FORM_MESSAGE_FAILURE_CLASS)
	private List<WebElement> failrueMessages;

	@FindBy(name = FORM_BOX_NAME)
	private WebElement formBox;

	@FindBy(name = SUBMIT_MANGA)
	private WebElement submitButton;

	@FindBy(xpath = ADD_OR_UPDATE_MANGA_LINK)
	private WebElement addOrUpdateMangaLink;

	public MangaControllerSeleniumPOM(WebDriver webDriver) {

		super(webDriver);
	}

	public void loadMangaForm(LanguageVersion ver) {

		super.connectTo(RESOURCE_PATH + ver.getLangUrl());
	}

	public String getEnTitle() {

		return titleEnInput.getAttribute("value");
	}

	public void setEnTitle(String enTitle) {

		titleEnInput.sendKeys(enTitle);
	}

	public String getEnDescription() {

		return descriptionEnInput.getAttribute("value");
	}

	public void setEnDescription(String enDescription) {

		descriptionEnInput.sendKeys(enDescription);
	}

	public void clickFirstAuthorCheckbox() {

		authorsCheckboxes.get(0).click();
	}

	public boolean isFirstAuthorCheckboxSelected() {

		return authorsCheckboxes.get(0).isSelected();
	}

	public String getPlTitle() {

		return titlePlInput.getAttribute("value");
	}

	public void setPlTitle(String plTitle) {

		titlePlInput.sendKeys(plTitle);
	}

	public String getPlDescription() {

		return descriptionPlInput.getAttribute("value");
	}

	public void setPlDescription(String plDescription) {

		descriptionPlInput.sendKeys(plDescription);
	}

	public void addImage() {

		imageInput.sendKeys(projectPath + EXAMPLE_IMAGE_PATH);
	}

	public void clickSubmitMangaFormButton() {

		submitButton.click();
	}

	public void clickAddOrUpdateMangaLinkButton() {

		JavascriptExecutor executor = (JavascriptExecutor) webDriver;
		executor.executeScript("arguments[0].click();", addOrUpdateMangaLink);
	}

	public String getFormBoxText() {

		return formBox.getText();
	}

	public int countFailureMessages() {

		return failrueMessages.size();
	}

	public void fillMandatoryMangaFormFields(String enTitle, String enDescription, String plTitle, String plDescription,
			boolean selectAuthor, boolean addImage) {

		setEnTitle(enTitle);

		setEnDescription(enDescription);

		setPlTitle(plTitle);

		setPlDescription(plDescription);

		if (selectAuthor) {
			clickFirstAuthorCheckbox();
		}
		
		if (addImage) {
			addImage();
		}

		clickSubmitMangaFormButton();
	}
}
