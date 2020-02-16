package com.NowakArtur97.WorldOfManga.mapper;

import com.NowakArtur97.WorldOfManga.dto.MangaDTO;
import com.NowakArtur97.WorldOfManga.model.Manga;

public interface MangaMapper {

	Manga mapMangaDTOToManga(MangaDTO mangaDTO);
}
