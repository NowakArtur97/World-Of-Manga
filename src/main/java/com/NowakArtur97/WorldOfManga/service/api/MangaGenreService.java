package com.NowakArtur97.WorldOfManga.service.api;

import java.util.List;

import com.NowakArtur97.WorldOfManga.exception.MangaGenreNotFoundException;
import com.NowakArtur97.WorldOfManga.model.MangaGenre;

public interface MangaGenreService {

	MangaGenre findByEnglishTranslation(String englishTranslation) throws MangaGenreNotFoundException;
	
	List<MangaGenre> findAll();
}
