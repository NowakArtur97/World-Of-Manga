package com.NowakArtur97.WorldOfManga.feature.manga.genre;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface MangaGenreRepository extends JpaRepository<MangaGenre, Long> {

    Optional<MangaGenre> findByEnglishTranslation(String englishTranslation);
}
