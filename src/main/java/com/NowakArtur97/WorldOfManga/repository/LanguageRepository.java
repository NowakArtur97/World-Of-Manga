package com.NowakArtur97.WorldOfManga.repository;

import com.NowakArtur97.WorldOfManga.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LanguageRepository extends JpaRepository<Language, Long> {

	Optional<Language> findByLocale(String locale);
}
