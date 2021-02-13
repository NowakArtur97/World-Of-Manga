package com.NowakArtur97.WorldOfManga.feature.user.seleniumTest.logoutController;

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
@Tag("LogoutControllerUIPl_Tests")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class LogoutControllerUIPlTest extends LogoutControllerUITest {

    @ParameterizedTest(name = "{index}: Browser: {0}")
    @ValueSource(strings = {"Firefox", "Chrome"})
    void when_correct_logout_should_sing_out_user(String browserName) {

        String username = "user";
        String password = "user";

        languageVersion = LanguageVersion.PL;

        launchBrowser(browserName, languageVersion.name());

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields(username, password);

        logoutPage.signOut(browser);

        assertAll(
                () -> assertFalse(mainPage.getHeaderText().contains(userLoggedInMangaListOption),
                        () -> "should not show manga list option: " + userLoggedInMangaListOption + ", but was: "
                                + mainPage.getHeaderText()),
                () -> assertFalse(mainPage.getHeaderText().contains(userLoggedInSignOutOption),
                        () -> "should not show sign out option: " + userLoggedInMangaListOption + ", but was: "
                                + mainPage.getHeaderText()),
                () -> assertTrue(mainPage.getHeaderText().contains(headerRegistrationOption),
                        () -> "should show manga list option: " + userLoggedInMangaListOption + ", but was: "
                                + mainPage.getHeaderText()),
                () -> assertTrue(mainPage.getHeaderText().contains(headerLoginOption),
                        () -> "should show sign out option: " + userLoggedInMangaListOption + ", but was: "
                                + mainPage.getHeaderText()));
    }
}
