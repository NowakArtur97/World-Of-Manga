package com.NowakArtur97.WorldOfManga.feature.mainPage.seleniumTest;

import com.NowakArtur97.WorldOfManga.testUtil.enums.Browser;
import com.NowakArtur97.WorldOfManga.testUtil.enums.LanguageVersion;
import com.NowakArtur97.WorldOfManga.testUtil.extension.ScreenshotWatcher;
import com.NowakArtur97.WorldOfManga.testUtil.generator.NameWithSpacesGenerator;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(ScreenshotWatcher.class)
@TestPropertySource("classpath:/pageContent/messages_en.properties")
@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("MainControllerUIEn_Tests")
@DirtiesContext
class MainControllerUIEnTest extends MainControllerUITest {

    @ParameterizedTest(name = "{index}: Browser: {0}")
    @EnumSource(Browser.class)
    void when_load_main_page_should_load_all_page_content(Browser browser) {

        languageVersion = LanguageVersion.ENG;

        launchBrowser(browser.name(), languageVersion.name());

        mainPage.loadMainView(languageVersion);

        assertAll(
                () -> assertTrue(mainPage.getHeaderText().contains(headerLogo),
                        () -> "should load header logo: " + headerLogo + ", but was: " + mainPage.getHeaderText()),
                () -> assertTrue(mainPage.getHeaderText().contains(headerRegistrationOption),
                        () -> "should load header sing up option: " + headerRegistrationOption + ", but was: "
                                + mainPage.getHeaderText()),
                () -> assertTrue(mainPage.getHeaderText().contains(headerLoginOption),
                        () -> "should load header sign in option: " + headerLoginOption + ", but was: "
                                + mainPage.getHeaderText()),
                () -> assertTrue(mainPage.getHeaderText().contains(headerLanguageOption),
                        () -> "should load header language option: " + headerLanguageOption + ", but was: "
                                + mainPage.getHeaderText()),
                () -> assertNotNull(mainPage.getFooterText(), () -> "should load footer fragment text"),
                () -> assertNotNull(mainPage.getMainPageText(), () -> "should load mainPage fragment text"));
    }
}
