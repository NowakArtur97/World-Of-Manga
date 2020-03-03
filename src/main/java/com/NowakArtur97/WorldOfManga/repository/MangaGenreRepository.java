package com.NowakArtur97.WorldOfManga.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NowakArtur97.WorldOfManga.model.MangaGenre;

public interface MangaGenreRepository extends JpaRepository<MangaGenre, Long> {

	Optional<MangaGenre> findByGenre(String genre);
}
