package com.NowakArtur97.WorldOfManga.testUtil.selenium;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DownloadFileUtil {

	private final static String projectPath = System.getProperty("user.dir");
		
	public void downloadImage(String imageUrl) {

		try (InputStream inputStream = new URL(imageUrl).openStream()) {
//			Files.copy(inputStream, Paths.get(projectPath  + "\\image.jpg"));
			Files.copy(inputStream, Paths.get(projectPath  + "/image.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
