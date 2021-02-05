package com.NowakArtur97.WorldOfManga.testUtil.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

;

public class SeleniumUITest {

    @Value("${world-of-manga.selenium.remote-web-driver-url:http://192.168.99.100:4444/wd/hub}")
    private String remoteUrl;

    @Value("${world-of-manga.selenium.is-remotely:false}")
    private boolean isRemotely;

    @Value("${world-of-manga.selenium.is-on-circle-ci:false}")
    private boolean isOnCircleCi;

    @Getter
    protected static WebDriver webDriver;

    @BeforeEach
    public void setUp() throws MalformedURLException {

        WebDriverManager.chromedriver().setup();

        if (webDriver != null) {

            webDriver.quit();
        }

        if (isRemotely) {
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            capabilities.setBrowserName("chrome");

            if (isOnCircleCi) {

                webDriver = new RemoteWebDriver(capabilities);
            } else {

                webDriver = new RemoteWebDriver(new URL(remoteUrl), capabilities);
            }

            webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        } else {
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            capabilities.setBrowserName("chrome");

            webDriver = new ChromeDriver(capabilities);
        }
    }

    @AfterAll
    public static void tearDown() {

        if (webDriver != null) {

            webDriver.quit();
        }
    }
}
