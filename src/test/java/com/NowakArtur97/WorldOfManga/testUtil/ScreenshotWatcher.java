package com.NowakArtur97.WorldOfManga.testUtil;

import java.util.Optional;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

public class ScreenshotWatcher implements TestWatcher {

	private final ScreenshotUtil screenshotUtil = new ScreenshotUtil();

	@Override
	public void testFailed(ExtensionContext context, Throwable cause) {

		Optional<Object> testInstance = context.getTestInstance();

		if (testInstance.isPresent()) {

			var webDriver = SeleniumUITest.webDriver;

			screenshotUtil.takeScreenshot(webDriver, context.getDisplayName());
		}
	}
}
