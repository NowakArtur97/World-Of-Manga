package com.NowakArtur97.WorldOfManga.service;

import com.NowakArtur97.WorldOfManga.exception.LanguageNotFoundException;
import com.NowakArtur97.WorldOfManga.model.Language;
import com.NowakArtur97.WorldOfManga.repository.LanguageRepository;
import com.NowakArtur97.WorldOfManga.testUtil.generator.NameWithSpacesGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("LanguageService_Tests")
public class LanguageServiceTest {

    private LanguageService languageService;

    @Mock
    private LanguageRepository languageRepository;

    @BeforeEach
    void setUp() {

        languageService = new LanguageService(languageRepository);
    }

    @Test
    public void when_find_by_locale_should_return_language_by_locale() throws LanguageNotFoundException {

        String locale = "en";

        Language languageExpected = Language.builder().locale(locale).build();

        when(languageRepository.findByLocale(locale)).thenReturn(Optional.of(languageExpected));

        Language languageActual = languageService.findByLocale(locale);

        assertAll(
                () -> assertEquals(languageExpected.getLocale(), languageActual.getLocale(),
                        () -> "should return role with name: " + languageExpected.getLocale() + ", but was: "
                                + languageActual.getLocale()),
                () -> verify(languageRepository, times(1)).findByLocale(locale));
    }

    @Test
    public void when_locale_not_found_should_throw_exception() {

        String locale = "unknown locale";

        Class<LanguageNotFoundException> expectedException = LanguageNotFoundException.class;

        when(languageRepository.findByLocale(locale)).thenReturn(Optional.empty());

        assertAll(
                () -> assertThrows(expectedException, () -> languageService.findByLocale(locale),
                        () -> "should throw LanguageNotFoundException, but nothing was thrown"),
                () -> verify(languageRepository, times(1)).findByLocale(locale));
    }
}
