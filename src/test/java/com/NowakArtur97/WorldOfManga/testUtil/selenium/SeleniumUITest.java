package com.NowakArtur97.WorldOfManga.testUtil.selenium;

import com.NowakArtur97.WorldOfManga.testUtil.enums.Browser;
import com.NowakArtur97.WorldOfManga.testUtil.enums.LanguageVersion;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.provider.Arguments;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.LocalServerPort;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("E2E_Tests")
public class SeleniumUITest {

    private final static String CI_PROFILE = "ci";
    private final static String TEST_PROFILE = "test";
    private final static String DEFAULT_GITHUB_TOKEN = "DEFAULT";

    private static String ACTIVE_PROFILE;
    private static String CHOSEN_BROWSER;

    @LocalServerPort
    protected int localServerPort;

    @Value("${world-of-manga.selenium.server-port:8000}")
    protected int remoteAppServerPort;

    @Value("${world-of-manga.selenium.main-url:http://192.168.99.100:}")
    protected String mainUrl;

    @Value("${world-of-manga.selenium.remote-web-driver-url:http://192.168.99.100:4444/wd/hub}")
    private String remoteUrl;

    @Value("${world-of-manga.selenium.is-remotely:false}")
    private boolean isRemotely;

    @Value("${world-of-manga.selenium.is-on-circle-ci:false}")
    private boolean isOnCircleCi;

    @Value("${world-of-manga.images.example-image-path:\\src\\main\\resources\\static\\images\\backgrounds\\samurai.jpg}")
    protected String exampleImagePath;

    @Value("${world-of-manga.github.token:DEFAULT}")
    protected String githubToken;

    @Getter
    protected static RemoteWebDriver webDriver;

    protected LanguageVersion languageVersion;
    protected Browser browser;

    protected void setUp(Browser browser, String language) {

        this.browser = browser;

        languageVersion = Arrays.stream(LanguageVersion.values())
                .filter(lang -> lang.name().equalsIgnoreCase(language))
                .findFirst()
                .orElse(LanguageVersion.ENG);

        setUpWebDriver();
    }

    @SneakyThrows
    protected void setUpWebDriver() {

        if (ACTIVE_PROFILE.equals(TEST_PROFILE)) {

            localServerPort = remoteAppServerPort;
        }

        if (webDriver != null) {

            webDriver.quit();
        }

        switch (browser) {
            case CHROME:

                WebDriverManager.chromedriver().setup();

                if (isRemotely) {
                    setupRemoteChromeWebDriver();
                } else {
                    webDriver = new ChromeDriver();
                }
                break;

            case FIREFOX:

                if (!githubToken.equals(DEFAULT_GITHUB_TOKEN)) {
                    WebDriverManager.firefoxdriver().gitHubTokenSecret(githubToken).setup();
                } else {
                    WebDriverManager.firefoxdriver().setup();
                }

                if (isRemotely) {
                    setupRemoteFirefoxWebDriver();
                } else {
                    webDriver = new FirefoxDriver();
                }
                break;
        }

        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    private void setupRemoteChromeWebDriver() throws MalformedURLException {

        MutableCapabilities capabilities = new ChromeOptions();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--headless");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        setupRemoteWebDriver(capabilities);
    }

    private void setupRemoteFirefoxWebDriver() throws MalformedURLException {

        MutableCapabilities capabilities = new FirefoxOptions();
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);
        options.addArguments("--start-maximized");
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);

        setupRemoteWebDriver(capabilities);
    }

    private void setupRemoteWebDriver(MutableCapabilities capabilities) throws MalformedURLException {

        if (isOnCircleCi) {

            webDriver = new RemoteWebDriver(capabilities);

        } else {

            webDriver = new RemoteWebDriver(new URL(remoteUrl), capabilities);
        }

        webDriver.setFileDetector(new LocalFileDetector());
    }

    @Value("${spring.profiles.active:ci}")
    public void setPrivateName(String activeProfile) {
        ACTIVE_PROFILE = activeProfile;
    }

    @Value("${world-of-manga.selenium.browser-on-ci:DEFAULT}")
    public void setChosenBrowser(String chosenBrowser) {
        CHOSEN_BROWSER = chosenBrowser;
    }

    protected static Stream<Browser> setBrowserBasedOnProfile() {

        if (ACTIVE_PROFILE.equals(CI_PROFILE)) {
            return Stream.of(
                    Arrays.stream(Browser.values())
                            .filter(brow -> brow.name().equalsIgnoreCase(CHOSEN_BROWSER))
                            .findFirst()
                            .orElse(Browser.CHROME));
        } else {
            return Arrays.stream(Browser.values());
        }
    }

    protected static Stream<Arguments> setBrowserAndLanguageBasedOnProfile() {

        if (ACTIVE_PROFILE.equals(CI_PROFILE)) {
            Browser browser = Arrays.stream(Browser.values())
                    .filter(brow -> brow.name().equalsIgnoreCase(CHOSEN_BROWSER))
                    .findFirst()
                    .orElse(Browser.CHROME);
            return Stream.of(
                    Arguments.of(browser, "ENG"),
                    Arguments.of(browser, "PL")
            );
        } else {
            return Stream.of(
                    Arguments.of(Browser.CHROME, "ENG"),
                    Arguments.of(Browser.CHROME, "PL"),
                    Arguments.of(Browser.FIREFOX, "ENG"),
                    Arguments.of(Browser.FIREFOX, "PL"));
        }
    }
}
