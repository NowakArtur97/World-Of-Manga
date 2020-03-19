package com.NowakArtur97.WorldOfManga.service.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.NowakArtur97.WorldOfManga.dto.MangaDTO;
import com.NowakArtur97.WorldOfManga.exception.MangaNotFoundException;
import com.NowakArtur97.WorldOfManga.model.Manga;

public interface MangaService {

	Manga addOrUpdate(MangaDTO mangaDTO, Manga manga) throws MangaNotFoundException;

	Manga deleteManga(Long mangaId) throws MangaNotFoundException;

	Manga addOrRemoveFromFavourites(Long mangaId) throws MangaNotFoundException;

	MangaDTO getMangaDTOById(Long mangaId) throws MangaNotFoundException;

	Manga findById(Long id) throws MangaNotFoundException;

	List<Manga> findAll();

	Page<Manga> findAllDividedIntoPages(Pageable pageable);
}
