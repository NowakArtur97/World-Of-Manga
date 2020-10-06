package com.NowakArtur97.WorldOfManga.service;

import com.NowakArtur97.WorldOfManga.exception.LanguageNotFoundException;
import com.NowakArtur97.WorldOfManga.model.Language;
import com.NowakArtur97.WorldOfManga.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LanguageService {

    private final LanguageRepository languageRepository;

    public Language findByLocale(String locale) throws LanguageNotFoundException {

        return languageRepository.findByLocale(locale)
                .orElseThrow(() -> new LanguageNotFoundException("Language with locale: " + locale + " not found"));
    }
}
