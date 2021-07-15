package com.NowakArtur97.WorldOfManga.testUtil.extension;

import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumUITest;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.WebDriver;

import java.util.Optional;

public class ScreenshotWatcher implements TestWatcher {

    private final ScreenshotUtil screenshotUtil = new ScreenshotUtil();

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {

        Optional<Object> testInstance = context.getTestInstance();

        if (testInstance.isPresent()) {

            WebDriver webDriver = SeleniumUITest.getWebDriver();

            screenshotUtil.takeScreenshot(webDriver, context.getRequiredTestMethod().getName());

            SeleniumUITest.closeDriver();
        }
    }

    @Override
    public void testSuccessful(ExtensionContext context) {

        SeleniumUITest.closeDriver();
    }
}
