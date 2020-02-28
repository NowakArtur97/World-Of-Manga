package com.NowakArtur97.WorldOfManga.mapper.manga;

import java.util.Set;

import com.NowakArtur97.WorldOfManga.dto.MangaDTO;
import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaTranslation;

public interface MangaMapper {

	Manga mapMangaDTOToManga(Manga manga, MangaDTO mangaDTO, Set<MangaTranslation> mangaTranslations);

	MangaDTO mapMangaToDTO(Manga manga);
}
