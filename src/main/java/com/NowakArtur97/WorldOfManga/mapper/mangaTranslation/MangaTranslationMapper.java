package com.NowakArtur97.WorldOfManga.mapper.mangaTranslation;

import com.NowakArtur97.WorldOfManga.dto.MangaTranslationDTO;
import com.NowakArtur97.WorldOfManga.model.MangaTranslation;

public interface MangaTranslationMapper {

	MangaTranslation mapMangaTranslationDTOToMangaTranslation(MangaTranslationDTO mangaTranslationDTO);
}
