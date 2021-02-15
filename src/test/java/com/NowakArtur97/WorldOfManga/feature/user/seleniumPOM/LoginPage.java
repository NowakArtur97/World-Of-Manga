package com.NowakArtur97.WorldOfManga.feature.user.seleniumPOM;

import com.NowakArtur97.WorldOfManga.feature.mainPage.seleniumPOM.MainPage;
import com.NowakArtur97.WorldOfManga.testUtil.enums.LanguageVersion;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumPageObjectModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends SeleniumPageObjectModel {

    private static final String RESOURCE_PATH = "/user/login";

    private static final String FORM_BOX_CLASS = "form__box";
    private static final String USERNAME_NAME = "username";
    private static final String PASSWORD_NAME = "password";
    private static final String SUBMIT_CLASS = "form__submit";

    @FindBy(name = USERNAME_NAME)
    private WebElement usernameInput;

    @FindBy(name = PASSWORD_NAME)
    private WebElement passwordInput;

    @FindBy(className = SUBMIT_CLASS)
    private WebElement submitButton;

    @FindBy(className = FORM_BOX_CLASS)
    private WebElement formBox;

    public LoginPage(WebDriver webDriver, String mainUrl) {

        super(webDriver, mainUrl);
    }

    public void loadLoginView(LanguageVersion ver) {

        super.connectTo(RESOURCE_PATH + ver.getLangUrl());
    }

    private void setUsername(String username) {

        usernameInput.click();
        usernameInput.clear();
        usernameInput.sendKeys(username);
    }

    private void setPassword(String password) {

        passwordInput.click();
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    private void clickSubmitLoginButton() {

        submitButton.click();
    }

    public String getFormBoxText() {

        return formBox.getText();
    }

    public void fillMandatoryLoginFields(String username, String password) {

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className(SUBMIT_CLASS)));

        setUsername(username);

        setPassword(password);

        clickSubmitLoginButton();
    }

    public boolean isUserOnLoginPage() {

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className(MainPage.MAIN_PAGE_CLASS)));

        return webDriver.getCurrentUrl().contains(RESOURCE_PATH) || isElementFound(formBox);
    }
}
