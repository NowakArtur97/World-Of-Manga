package com.NowakArtur97.WorldOfManga.feature.user.seleniumPOM;

import com.NowakArtur97.WorldOfManga.testUtil.enums.LanguageVersion;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumPageObjectModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class RegistrationPage extends SeleniumPageObjectModel {

    private static final String RESOURCE_PATH = "/user/register";

    private static final String USERNAME_NAME = "username";
    private static final String FIRSTNAME_NAME = "firstName";
    private static final String LASTNAME_NAME = "lastName";
    private static final String PASSWORD_NAME = "userPasswordDTO.password";
    private static final String MATCHING_PASSWORD_NAME = "userPasswordDTO.matchingPassword";
    private static final String EMAIL_NAME = "email";
    private static final String TERMS_NAME = "areTermsAccepted";
    private static final String SUBMIT_CLASS = "form__submit";
    private static final String FORM_MESSAGE_FAILURE_CLASS = "form__message--failure";
    private static final String FORM_BOX_CLASS = "form__box";

    @FindBy(name = USERNAME_NAME)
    private WebElement usernameInput;

    @FindBy(name = FIRSTNAME_NAME)
    private WebElement firstNameInput;

    @FindBy(name = LASTNAME_NAME)
    private WebElement lastNameInput;

    @FindBy(name = PASSWORD_NAME)
    private WebElement passwordInput;

    @FindBy(name = MATCHING_PASSWORD_NAME)
    private WebElement matchingPasswordInput;

    @FindBy(name = EMAIL_NAME)
    private WebElement emailInput;

    @FindBy(name = TERMS_NAME)
    private WebElement terms;

    @FindBy(className = FORM_MESSAGE_FAILURE_CLASS)
    private List<WebElement> failureMessages;

    @FindBy(className = FORM_BOX_CLASS)
    private WebElement formBox;

    @FindBy(className = SUBMIT_CLASS)
    private WebElement submitButton;

    public RegistrationPage(WebDriver webDriver, String mainUrl) {

        super(webDriver, mainUrl);
    }

    public void loadRegistrationView(LanguageVersion ver) {

        super.connectTo(RESOURCE_PATH + ver.getLangUrl());
    }

    public String getUsername() {

        return usernameInput.getAttribute("value");
    }

    private void setUsername(String username) {

        usernameInput.click();
        usernameInput.clear();
        usernameInput.sendKeys(username);
    }

    public String getFirstName() {

        return firstNameInput.getAttribute("value");
    }

    private void setFirstName(String firstName) {

        firstNameInput.click();
        firstNameInput.clear();
        firstNameInput.sendKeys(firstName);
    }

    public String getLastName() {

        return lastNameInput.getAttribute("value");
    }

    private void setLastName(String lastName) {

        lastNameInput.click();
        lastNameInput.clear();
        lastNameInput.sendKeys(lastName);
    }

    private void setPassword(String password) {

        passwordInput.click();
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    private void setMatchingPassword(String matchingPassword) {

        matchingPasswordInput.click();
        matchingPasswordInput.clear();
        matchingPasswordInput.sendKeys(matchingPassword);
    }

    public String getEmail() {

        return emailInput.getAttribute("value");
    }

    private void setEmail(String email) {

        emailInput.click();
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    private void setConfirmTerms() {

        terms.click();
    }

    public boolean getConfirmTerms() {

        return terms.isSelected();
    }

    private void clickSubmitRegistrationButton() {

        submitButton.click();
    }

    public int countFailureMessages() {

        return failureMessages.size();
    }

    public String getFormBoxText() {

        return formBox.getText();
    }

    public void fillMandatoryRegistrationFields(String username, String password, String matchingPassword, String email,
                                                boolean areTermsConfirmed) {

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className(SUBMIT_CLASS)));

        setUsername(username);

        setPassword(password);

        setMatchingPassword(matchingPassword);

        setEmail(email);

        if (areTermsConfirmed) {
            setConfirmTerms();
        }

        clickSubmitRegistrationButton();
    }

    public void fillAllRegistrationFields(String username, String password, String matchingPassword, String email,
                                          String firstName, String lastName, boolean areTermsConfirmed) {

        setFirstName(firstName);

        setLastName(lastName);

        fillMandatoryRegistrationFields(username, password, matchingPassword, email, areTermsConfirmed);
    }

    public boolean isUserOnRegistrationPage() {

        return webDriver.getCurrentUrl().contains(RESOURCE_PATH);
    }
}
