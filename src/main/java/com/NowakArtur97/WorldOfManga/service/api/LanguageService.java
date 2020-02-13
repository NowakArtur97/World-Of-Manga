package com.NowakArtur97.WorldOfManga.service.api;

import com.NowakArtur97.WorldOfManga.exception.LanguageNotFoundException;
import com.NowakArtur97.WorldOfManga.model.Language;

public interface LanguageService {

	Language findByLocale(String locale) throws LanguageNotFoundException;
}
