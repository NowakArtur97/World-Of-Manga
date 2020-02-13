package com.NowakArtur97.WorldOfManga.service.api;

import java.util.Set;

import com.NowakArtur97.WorldOfManga.dto.MangaDTO;
import com.NowakArtur97.WorldOfManga.exception.LanguageNotFoundException;
import com.NowakArtur97.WorldOfManga.model.MangaTranslation;

public interface MangaTranslationService {

	boolean isTitleAlreadyInUse(String title);

	Set<MangaTranslation> addOrUpdate(MangaDTO mangaDTO) throws LanguageNotFoundException;
}
