package com.NowakArtur97.WorldOfManga.service.api;

import java.util.List;

import com.NowakArtur97.WorldOfManga.exception.MangaGenreNotFoundException;
import com.NowakArtur97.WorldOfManga.model.MangaGenre;

public interface MangaGenreService {

	MangaGenre findByGenre(String genre) throws MangaGenreNotFoundException;
	
	List<MangaGenre> findAll();
}
