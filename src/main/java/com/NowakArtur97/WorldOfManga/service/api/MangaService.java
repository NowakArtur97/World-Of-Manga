package com.NowakArtur97.WorldOfManga.service.api;

import com.NowakArtur97.WorldOfManga.dto.MangaDTO;
import com.NowakArtur97.WorldOfManga.exception.LanguageNotFoundException;
import com.NowakArtur97.WorldOfManga.model.Manga;

public interface MangaService {

	Manga addOrUpdate(MangaDTO mangaDTO) throws LanguageNotFoundException;

}
