package com.NowakArtur97.WorldOfManga.service;

import com.NowakArtur97.WorldOfManga.exception.MangaGenreNotFoundException;
import com.NowakArtur97.WorldOfManga.model.MangaGenre;
import com.NowakArtur97.WorldOfManga.repository.MangaGenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MangaGenreService {

    private final MangaGenreRepository mangaGenreRepository;

    public MangaGenre findByEnglishTranslation(String englishTranslation) throws MangaGenreNotFoundException {

        return mangaGenreRepository.findByEnglishTranslation(englishTranslation).orElseThrow(
                () -> new MangaGenreNotFoundException("Manga genre: " + englishTranslation + " not found"));
    }

    public List<MangaGenre> findAll() {

        return mangaGenreRepository.findAll();
    }
}
