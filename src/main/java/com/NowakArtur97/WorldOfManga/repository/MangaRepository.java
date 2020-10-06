package com.NowakArtur97.WorldOfManga.repository;

import com.NowakArtur97.WorldOfManga.model.Manga;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MangaRepository extends JpaRepository<Manga, Long> {

}
