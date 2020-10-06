package com.NowakArtur97.WorldOfManga.repository;

import com.NowakArtur97.WorldOfManga.model.MangaGenre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MangaGenreRepository extends JpaRepository<MangaGenre, Long> {

	Optional<MangaGenre> findByEnglishTranslation(String englishTranslation);
}
