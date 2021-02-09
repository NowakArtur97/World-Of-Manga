package com.NowakArtur97.WorldOfManga.testUtil.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.LocalServerPort;

import java.net.MalformedURLException;
import java.net.URL;
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

    @Value("${world-of-manga.images.example-image-path:\\src\\main\\resources\\static\\images\\backgrounds\\samurai.jpg}")
    protected String exampleImagePath;

    @Getter
    protected static WebDriver webDriver;

    @BeforeEach
    protected void setUp() throws MalformedURLException {

        WebDriverManager.chromedriver().setup();

        if (webDriver != null) {

            webDriver.quit();
        }

        if (activeProfile.equals(TEST_PROFILE)) {

            localServerPort = remoteAppServerPort;
        }

        if (isRemotely) {

            ChromeOptions capabilities = new ChromeOptions();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);

            if (isOnCircleCi) {

                webDriver = new RemoteWebDriver(capabilities);
            } else {

                webDriver = new RemoteWebDriver(new URL(remoteUrl), capabilities);
            }

        } else {

            webDriver = new ChromeDriver();
        }

        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterAll
    protected static void tearDown() {

        if (webDriver != null) {

            webDriver.quit();
        }
    }
}
