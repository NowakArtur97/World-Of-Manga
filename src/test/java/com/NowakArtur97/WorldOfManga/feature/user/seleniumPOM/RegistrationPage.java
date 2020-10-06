package com.NowakArtur97.WorldOfManga.feature.user.seleniumPOM;

import com.NowakArtur97.WorldOfManga.testUtil.enums.LanguageVersion;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumPageObjectModel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class RegistrationPage extends SeleniumPageObjectModel {

    public static final String RESOURCE_PATH = "/user/register";

    private static final String USERNAME = "username";
    private static final String FIRSTNAME = "firstName";
    private static final String LASTNAME = "lastName";
    private static final String PASSWORD = "userPasswordDTO.password";
    private static final String MATCHING_PASSWORD = "userPasswordDTO.matchingPassword";
    private static final String EMAIL = "email";
    private static final String TERMS = "areTermsAccepted";
    private static final String SUBMIT_CLASS = "form__submit";
    private static final String FORM_MESSAGE_FAILURE_CLASS = "form__message--failure";
    private static final String FORM_BOX_CLASS = "form__box";

    @FindBy(name = USERNAME)
    private WebElement usernameInput;

    @FindBy(name = FIRSTNAME)
    private WebElement firstNameInput;

    @FindBy(name = LASTNAME)
    private WebElement lastNameInput;

    @FindBy(name = PASSWORD)
    private WebElement passwordInput;

    @FindBy(name = MATCHING_PASSWORD)
    private WebElement matchingPasswordInput;

    @FindBy(name = EMAIL)
    private WebElement emailInput;

    @FindBy(name = TERMS)
    private WebElement terms;

    @FindBy(className = FORM_MESSAGE_FAILURE_CLASS)
    private List<WebElement> failrueMessages;

    @FindBy(className = FORM_BOX_CLASS)
    private WebElement formBox;

    @FindBy(className = SUBMIT_CLASS)
    private WebElement submitButton;

    public RegistrationPage(WebDriver webDriver) {

        super(webDriver);
    }

    public void loadRegistrationView(LanguageVersion ver) {

        super.connectTo(RESOURCE_PATH + ver.getLangUrl());
    }

    public String getUsername() {

        return usernameInput.getAttribute("value");
    }

    public void setUsername(String username) {

        usernameInput.sendKeys(username);
    }

    public String getFirstName() {

        return firstNameInput.getAttribute("value");
    }

    public void setFirstName(String firstName) {

        firstNameInput.sendKeys(firstName);
    }

    public String getLastName() {

        return lastNameInput.getAttribute("value");
    }

    public void setLastName(String lastName) {

        lastNameInput.sendKeys(lastName);
    }

    public void setPassword(String password) {

        passwordInput.sendKeys(password);
    }

    public void setMatchingPassword(String matchingPassword) {

        matchingPasswordInput.sendKeys(matchingPassword);
    }

    public String getEmail() {

        return emailInput.getAttribute("value");
    }

    public void setEmail(String email) {

        emailInput.sendKeys(email);
    }

    public void setConfirmTerms() {

        terms.click();
    }

    public boolean getConfirmTerms() {

        return terms.isSelected();
    }

    public void clickSubmitRegistrationButton() {

        submitButton.click();
    }

    public int countFailureMessages() {

        return failrueMessages.size();
    }

    public String getFormBoxText() {

        return formBox.getText();
    }

    public void fillMandatoryRegistrationFields(String username, String password, String matchingPassword, String email,
                                                boolean areTermsConfirmed) {

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
