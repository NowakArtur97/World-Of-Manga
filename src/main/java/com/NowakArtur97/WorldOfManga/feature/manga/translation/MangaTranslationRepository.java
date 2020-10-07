package com.NowakArtur97.WorldOfManga.feature.manga.translation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MangaTranslationRepository extends JpaRepository<MangaTranslation, Long> {

	boolean existsMangaTranslationByTitle(String title);

}
