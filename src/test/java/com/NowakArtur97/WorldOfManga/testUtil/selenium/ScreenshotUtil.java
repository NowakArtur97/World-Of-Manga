package com.NowakArtur97.WorldOfManga.testUtil.selenium;

import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ScreenshotUtil {

    private final static String PROJECT_PATH = System.getProperty("user.dir");

    private final static String SCREENSHOT_PATH = "\\src\\test\\resources\\screenshots\\";

    private final static String SCREENSHOT_FORMAT = "jpg";

    private final static String DOT = ".";

    public void takeScreenshot(WebDriver webDriver, String screenshotName) {

        if (webDriver != null) {
            Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
                    .takeScreenshot(webDriver);

            try {
                ImageIO.write(screenshot.getImage(), SCREENSHOT_FORMAT,
                        new File(PROJECT_PATH + SCREENSHOT_PATH + screenshotName + DOT + SCREENSHOT_FORMAT));
            } catch (IOException e) {
                System.out.println("Can`t take screenshot on path: " + PROJECT_PATH + SCREENSHOT_PATH);
            }

            webDriver.close();
        }
    }
}
