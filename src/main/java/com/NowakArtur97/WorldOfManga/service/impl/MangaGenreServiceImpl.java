package com.NowakArtur97.WorldOfManga.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NowakArtur97.WorldOfManga.exception.MangaGenreNotFoundException;
import com.NowakArtur97.WorldOfManga.model.MangaGenre;
import com.NowakArtur97.WorldOfManga.repository.MangaGenreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MangaGenreServiceImpl implements MangaGenreService {

	private final MangaGenreRepository mangaGenreRepository;

	@Override
	public MangaGenre findByEnglishTranslation(String englishTranslation) throws MangaGenreNotFoundException {

		return mangaGenreRepository.findByEnglishTranslation(englishTranslation).orElseThrow(
				() -> new MangaGenreNotFoundException("Manga genre: " + englishTranslation + " not found"));
	}

	@Override
	public List<MangaGenre> findAll() {

		return mangaGenreRepository.findAll();
	}
}
