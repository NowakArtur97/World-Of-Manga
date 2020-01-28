package com.NowakArtur97.WorldOfManga.testUtils;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.openqa.selenium.WebDriver;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class ScreenshotUtil {

	private final static String SCREENSHOT_FORMAT = "jpg";
	private final static String SCREENSHOT_PATH = System.getProperty("user.dir")
			+ "\\src\\test\\resources\\screenshots\\";
	private final static String DOT = ".";

	public void takeScreenshot(WebDriver webDriver, String screenshotName) {

		Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
				.takeScreenshot(webDriver);

		try {
			ImageIO.write(screenshot.getImage(), SCREENSHOT_FORMAT,
					new File(SCREENSHOT_PATH + screenshotName + DOT + SCREENSHOT_FORMAT));
		} catch (IOException e) {
			System.out.println("Can`t take screenshot on path: " + SCREENSHOT_PATH);
		}
	}
}
