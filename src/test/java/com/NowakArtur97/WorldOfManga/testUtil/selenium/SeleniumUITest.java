package com.NowakArtur97.WorldOfManga.testUtil.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class SeleniumUITest {

    @Value("${app.selenium.remote-web-driver-url}")
    private String remoteUrl;

    @Getter
    protected static WebDriver webDriver;

    @BeforeEach
    public void setUp() throws MalformedURLException {

        WebDriverManager.chromedriver().setup();

        if (webDriver != null) {

            webDriver.quit();
        }

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        capabilities.setBrowserName("chrome");

        webDriver = new RemoteWebDriver(new URL(remoteUrl), capabilities);
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

//		webDriver = new ChromeDriver();
    }

    @AfterAll
    public static void tearDown() {

        if (webDriver != null) {

            webDriver.quit();
        }
    }
}
