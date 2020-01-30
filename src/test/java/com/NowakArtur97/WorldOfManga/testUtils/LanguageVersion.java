package com.NowakArtur97.WorldOfManga.testUtils;

public enum LanguageVersion {

	ENG("/?lang=eng"), PL("/?lang=pl");

	public final String langUrl;

	private LanguageVersion(String langUrl) {
		this.langUrl = langUrl;
	}
}
