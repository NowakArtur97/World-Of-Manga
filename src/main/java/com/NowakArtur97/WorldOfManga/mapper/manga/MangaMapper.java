package com.NowakArtur97.WorldOfManga.mapper.manga;

import com.NowakArtur97.WorldOfManga.dto.MangaDTO;
import com.NowakArtur97.WorldOfManga.model.Manga;

public interface MangaMapper {

	Manga mapMangaDTOToManga(Manga manga, MangaDTO mangaDTO);

	MangaDTO mapMangaToDTO(Manga manga);
}
