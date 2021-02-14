package com.NowakArtur97.WorldOfManga.feature.author.seleniumPOM;

import com.NowakArtur97.WorldOfManga.testUtil.enums.LanguageVersion;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumPageObjectModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class AuthorFormPage extends SeleniumPageObjectModel {

    private static final String RESOURCE_PATH = "/admin/addOrUpdateAuthor";

    private static final String FULL_NAME_ID = "fullName";
    private static final String FORM_MESSAGE_FAILURE_CLASS = "form__message--failure";
    private static final String FORM_BOX_NAME = "addOrUpdateAuthorForm";
    private static final String SUBMIT_AUTHOR_NAME = "addOrUpdateAuthorSubmitBtn";
    private static final String ADD_OR_UPDATE_AUTHOR_LINK = "//a[@href='/admin/addOrUpdateManga']";

    @FindBy(id = FULL_NAME_ID)
    private WebElement fullNameInput;

    @FindBy(className = FORM_MESSAGE_FAILURE_CLASS)
    private List<WebElement> failureMessages;

    @FindBy(name = FORM_BOX_NAME)
    private WebElement formBox;

    @FindBy(name = SUBMIT_AUTHOR_NAME)
    private WebElement submitButton;

    @FindBy(xpath = ADD_OR_UPDATE_AUTHOR_LINK)
    private WebElement addOrUpdateAuthorLink;

    public AuthorFormPage(WebDriver webDriver, String mainUrl) {

        super(webDriver, mainUrl);
    }

    public void loadAuthorForm(LanguageVersion ver) {

        super.connectTo(RESOURCE_PATH + ver.getLangUrl());
    }

    private void clickSubmitAuthorFormButton() {

        useJavaScriptToClickElement(submitButton);
    }

    private void setFullName(String fullName) {

        fullNameInput.click();
        fullNameInput.clear();
        fullNameInput.sendKeys(fullName);
    }

    public String getFullName() {

        return fullNameInput.getAttribute("value");
    }

    public String getFormBoxText() {

        return formBox.getText();
    }

    public int countFailureMessages() {

        return failureMessages.size();
    }

    public void clickAddOrUpdateAuthorLinkButton() {

        useJavaScriptToClickElement(addOrUpdateAuthorLink);
    }

    public void fillMandatoryAuthorFormFields(String fullName) {

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(SUBMIT_AUTHOR_NAME)));

        setFullName(fullName);

        clickSubmitAuthorFormButton();
    }
}
