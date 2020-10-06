package com.NowakArtur97.WorldOfManga.repository;

import com.NowakArtur97.WorldOfManga.model.MangaTranslation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MangaTranslationRepository extends JpaRepository<MangaTranslation, Long> {

	boolean existsMangaTranslationByTitle(String title);

}
