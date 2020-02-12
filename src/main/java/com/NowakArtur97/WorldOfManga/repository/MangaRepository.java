package com.NowakArtur97.WorldOfManga.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NowakArtur97.WorldOfManga.model.Manga;

public interface MangaRepository extends JpaRepository<Manga, Long> {

}
