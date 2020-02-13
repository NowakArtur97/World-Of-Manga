package com.NowakArtur97.WorldOfManga.service.api;

import com.NowakArtur97.WorldOfManga.model.MangaTranslation;

public interface MangaTranslationService {

	boolean isTitleAlreadyInUse(String title);

	MangaTranslation save(MangaTranslation mangaTranslation);
}
