package com.NowakArtur97.WorldOfManga.testUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;

@Configuration
@TestPropertySource("classpath:/application.properties")
public class SeleniumTestConfiguration {

	@Value("${test.screenshot.format}")
	private String screenshotFormat;

	@Value("${test.screenshot.path}")
	private String screenshotPath;

	@Bean
	public ScreenshotUtil screenshotUtil() {

		ScreenshotUtil screenshotUtil = new ScreenshotUtil();
		
		screenshotUtil.setScreenshotFormat(screenshotFormat);
		screenshotUtil.setScreenshotPath(screenshotPath);

		return screenshotUtil;
	}
}
