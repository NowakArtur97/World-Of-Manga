package com.NowakArtur97.WorldOfManga.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NowakArtur97.WorldOfManga.model.MangaGenre;

public interface MangaGenreRepository extends JpaRepository<MangaGenre, Long> {

	MangaGenre findByGenre(String genre);
}
