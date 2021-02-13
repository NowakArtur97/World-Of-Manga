package com.NowakArtur97.WorldOfManga.feature.user.seleniumPOM;

import com.NowakArtur97.WorldOfManga.testUtil.enums.LanguageVersion;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumPageObjectModel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends SeleniumPageObjectModel {

    private static final String RESOURCE_PATH = "/user/login";

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

        waitFor(300);

        setUsername(username);

        setPassword(password);

        clickSubmitLoginButton();
    }

    public boolean isUserOnLoginPage() {

        return webDriver.getCurrentUrl().contains(RESOURCE_PATH) || formBox.isDisplayed();
    }
}
