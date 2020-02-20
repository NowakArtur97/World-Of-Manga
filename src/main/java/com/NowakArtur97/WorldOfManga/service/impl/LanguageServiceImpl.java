package com.NowakArtur97.WorldOfManga.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NowakArtur97.WorldOfManga.exception.LanguageNotFoundException;
import com.NowakArtur97.WorldOfManga.model.Language;
import com.NowakArtur97.WorldOfManga.repository.LanguageRepository;
import com.NowakArtur97.WorldOfManga.service.api.LanguageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LanguageServiceImpl implements LanguageService {

	private final LanguageRepository languageRepository;

	@Override
	public Language findByLocale(String locale) throws LanguageNotFoundException {

		Language language = languageRepository.findByLocale(locale)
				.orElseThrow(() -> new LanguageNotFoundException("Language with locale: " + locale + " not found"));

		return language;
	}
}
