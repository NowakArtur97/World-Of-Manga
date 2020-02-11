package com.NowakArtur97.WorldOfManga.testUtil.selenium;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.openqa.selenium.WebDriver;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class ScreenshotUtil {

	private final static String projectPath = System.getProperty("user.dir");

	private final String screenshotPath = "\\src\\test\\resources\\screenshots\\";

	private final String screenshotFormat = "jpg";

	private final static String DOT = ".";

	public void takeScreenshot(WebDriver webDriver, String screenshotName) {

		if (webDriver != null) {
			Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
					.takeScreenshot(webDriver);

			try {
				ImageIO.write(screenshot.getImage(), screenshotFormat,
						new File(projectPath + screenshotPath + screenshotName + DOT + screenshotFormat));
			} catch (IOException e) {
				System.out.println("Can`t take screenshot on path: " + projectPath + screenshotPath);
			}
		}
	}
}
