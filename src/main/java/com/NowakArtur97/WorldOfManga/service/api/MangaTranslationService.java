package com.NowakArtur97.WorldOfManga.service.api;

import com.NowakArtur97.WorldOfManga.dto.MangaDTO;
import com.NowakArtur97.WorldOfManga.exception.LanguageNotFoundException;
import com.NowakArtur97.WorldOfManga.exception.MangaNotFoundException;
import com.NowakArtur97.WorldOfManga.model.Manga;

public interface MangaTranslationService {

	boolean isTitleAlreadyInUse(String title);

	Manga addOrUpdate(MangaDTO mangaDTO) throws LanguageNotFoundException, MangaNotFoundException;
}
