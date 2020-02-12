package com.NowakArtur97.WorldOfManga.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NowakArtur97.WorldOfManga.model.MangaTranslation;

public interface MangaTranslationRepository extends JpaRepository<MangaTranslation, Long> {

	boolean existsMangaTranslationByTitle(String title);

}
