package com.NowakArtur97.WorldOfManga.feature.user.seleniumTest.loginController;

import com.NowakArtur97.WorldOfManga.testUtil.enums.LanguageVersion;
import com.NowakArtur97.WorldOfManga.testUtil.extension.ScreenshotWatcher;
import com.NowakArtur97.WorldOfManga.testUtil.generator.NameWithSpacesGenerator;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(ScreenshotWatcher.class)
@TestPropertySource("classpath:/pageContent/messages_pl.properties")
@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("RegistrationControllerUIPl_Tests")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class LoginControllerUIPlTest extends LoginControllerUITest {

    @ParameterizedTest(name = "{index}: Browser: {0}")
    @ValueSource(strings = {"Firefox", "Chrome"})
    void when_login_with_bad_credentials_should_show_bad_credentials_message(String browserName) {

        String username = "resu";
        String password = "resu";

        languageVersion = LanguageVersion.PL;

        launchBrowser(browserName, languageVersion.name());

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields(username, password);

        assertAll(() -> assertTrue(loginPage.isUserOnLoginPage(), () -> "should show login page"),
                () -> assertTrue(loginPage.getFormBoxText().contains(badCredentialsMessage),
                        () -> "should show bad credentails message"),
                () -> assertFalse(userService.isUsernameAlreadyInUse(username),
                        () -> "user with username: + " + username + " shouldn`t exist"));
    }

    @ParameterizedTest(name = "{index}: Browser: {0}")
    @ValueSource(strings = {"Firefox", "Chrome"})
    void when_correct_user_login_with_user_role_should_sing_in_user(String browserName) {

        String username = "user";
        String password = "user";

        languageVersion = LanguageVersion.PL;

        launchBrowser(browserName, languageVersion.name());

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields(username, password);

        assertAll(() -> assertFalse(loginPage.isUserOnLoginPage(), () -> "shouldn`t show login page"),
                () -> assertTrue(userService.isUsernameAlreadyInUse(username),
                        () -> "user with given username should exist: " + username),
                () -> assertTrue(mainPage.getHeaderText().contains(userLoggedInMangaListOption),
                        () -> "should show manga list option: " + userLoggedInMangaListOption + ", but was: "
                                + mainPage.getHeaderText()),
                () -> assertTrue(mainPage.getHeaderText().contains(userLoggedInSignOutOption),
                        () -> "should show sign out option: " + userLoggedInSignOutOption + ", but was: "
                                + mainPage.getHeaderText()),
                () -> assertFalse(mainPage.getHeaderText().contains(adminAddMangaOption),
                        () -> "shouldn't show add manga option: " + adminAddMangaOption + ", but was: "
                                + mainPage.getHeaderText()));
    }

    @ParameterizedTest(name = "{index}: Browser: {0}")
    @ValueSource(strings = {"Firefox", "Chrome"})
    void when_correct_user_login_with_admin_role_should_sing_in_admin(String browserName) {

        String username = "admin";
        String password = "admin";

        languageVersion = LanguageVersion.PL;

        launchBrowser(browserName, languageVersion.name());

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields(username, password);

        assertAll(() -> assertFalse(loginPage.isUserOnLoginPage(), () -> "shouldn`t show login page"),
                () -> assertTrue(userService.isUsernameAlreadyInUse(username),
                        () -> "user with given username should exist: " + username),
                () -> assertTrue(mainPage.getHeaderText().contains(userLoggedInMangaListOption),
                        () -> "should show manga list option: " + userLoggedInMangaListOption + ", but was: "
                                + mainPage.getHeaderText()),
                () -> assertTrue(mainPage.getHeaderText().contains(userLoggedInSignOutOption),
                        () -> "should show sign out option: " + userLoggedInSignOutOption + ", but was: "
                                + mainPage.getHeaderText()),
                () -> assertTrue(mainPage.getHeaderText().contains(adminAddMangaOption),
                        () -> "should show add manga option: " + adminAddMangaOption + ", but was: "
                                + mainPage.getHeaderText()));
    }
}
