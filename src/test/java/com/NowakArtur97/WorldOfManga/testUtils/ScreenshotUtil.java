package com.NowakArtur97.WorldOfManga.testUtils;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

import lombok.Setter;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

@Component
public class ScreenshotUtil {

	private final static String projectPath = System.getProperty("user.dir");

	@Setter
	private String screenshotPath;

	@Setter
	private String screenshotFormat;

	private final static String DOT = ".";

	public void takeScreenshot(WebDriver webDriver, String screenshotName) {
		
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
