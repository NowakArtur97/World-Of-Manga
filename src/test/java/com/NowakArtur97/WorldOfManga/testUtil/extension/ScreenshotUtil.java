package com.NowakArtur97.WorldOfManga.testUtil.extension;

import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;

class ScreenshotUtil {

    private Format simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

    private final static String PROJECT_PATH = System.getProperty("user.dir");

    private final static String SCREENSHOT_PATH = "\\src\\test\\resources\\screenshots\\";

    private final static String SCREENSHOT_FORMAT = "jpg";

    private final static String DOT = ".";

    void takeScreenshot(WebDriver webDriver, String screenshotName) {

        try {
            if (webDriver != null) {
                Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
                        .takeScreenshot(webDriver);

                try {
                    String dateAsString = simpleDateFormat.format(Calendar.getInstance().getTime());
                    ImageIO.write(screenshot.getImage(), SCREENSHOT_FORMAT,
                            new File(PROJECT_PATH + SCREENSHOT_PATH + screenshotName + " " + dateAsString
                                    + DOT + SCREENSHOT_FORMAT));
                } catch (IOException e) {
                    System.out.println("Can`t take screenshot on path: " + PROJECT_PATH + SCREENSHOT_PATH);
                    System.out.println("Message: " + e.getMessage());
                }
            }
        } catch (NoSuchSessionException e) {
            System.out.println("Session ID is null");
            System.out.println("Message: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception when taking screenshot");
            System.out.println("Message: " + e.getMessage());
        }
    }
}