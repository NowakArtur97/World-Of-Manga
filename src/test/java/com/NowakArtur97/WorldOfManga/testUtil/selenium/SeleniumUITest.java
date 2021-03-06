package com.NowakArtur97.WorldOfManga.testUtil.selenium;

import com.NowakArtur97.WorldOfManga.testUtil.enums.Browser;
import com.NowakArtur97.WorldOfManga.testUtil.enums.LanguageVersion;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
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

public class SeleniumUITest {

    private final static String TEST_PROFILE = "test";

    @Value("${spring.profiles.active:prod}")
    private String activeProfile;

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

    @Value("${world-of-manga.selenium.browser-on-ci}")
    private String chosenBrowser;

    @Value("${world-of-manga.images.example-image-path:\\src\\main\\resources\\static\\images\\backgrounds\\samurai.jpg}")
    protected String exampleImagePath;

    @Getter
    protected static RemoteWebDriver webDriver;

    protected LanguageVersion languageVersion;
    protected Browser browser;

    protected void setUp(String browserName, String language) {

        if (chosenBrowser.equals("DEFAULT")) {
            chosenBrowser = browserName;
        }

        browser = Arrays.stream(Browser.values())
                .filter(brow -> brow.name().equalsIgnoreCase(chosenBrowser))
                .findFirst()
                .orElse(Browser.CHROME);

        languageVersion = Arrays.stream(LanguageVersion.values())
                .filter(lang -> lang.name().equalsIgnoreCase(language))
                .findFirst()
                .orElse(LanguageVersion.ENG);

        setUpWebDriver();
    }

    @AfterAll
    protected static void tearDown() {

        if (webDriver != null) {

            webDriver.quit();
        }
    }

    @SneakyThrows
    protected void setUpWebDriver() {

        if (activeProfile.equals(TEST_PROFILE)) {

            localServerPort = remoteAppServerPort;
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

                WebDriverManager.firefoxdriver().setup();

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
}
