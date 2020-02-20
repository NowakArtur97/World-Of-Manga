package com.NowakArtur97.WorldOfManga.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NowakArtur97.WorldOfManga.model.Language;

public interface LanguageRepository extends JpaRepository<Language, Long> {

	Optional<Language> findByLocale(String locale);
}
