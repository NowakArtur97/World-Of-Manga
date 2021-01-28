package com.NowakArtur97.WorldOfManga.feature.manga.genre;

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
