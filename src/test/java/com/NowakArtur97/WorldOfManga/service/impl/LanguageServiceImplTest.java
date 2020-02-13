package com.NowakArtur97.WorldOfManga.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.NowakArtur97.WorldOfManga.exception.LanguageNotFoundException;
import com.NowakArtur97.WorldOfManga.model.Language;
import com.NowakArtur97.WorldOfManga.repository.LanguageRepoitory;

@ExtendWith(MockitoExtension.class)
@DisplayName("Language Service Impl Tests")
@Tag("LanguageServiceImpl_Tests")
public class LanguageServiceImplTest {

	@InjectMocks
	private LanguageServiceImpl languageService;

	@Mock
	private LanguageRepoitory languageRepoitory;

	@Test
	@DisplayName("when find by locale")
	public void when_find_by_locale_should_return_language_by_locale() throws LanguageNotFoundException {

		String locale = "en";

		Language languageExpected = Language.builder().locale(locale).build();

		when(languageRepoitory.findByLocale(locale)).thenReturn(Optional.of(languageExpected));

		Language languageActual = languageService.findByLocale(locale);

		assertAll(
				() -> assertEquals(languageExpected.getLocale(), languageActual.getLocale(),
						() -> "should return role with name: " + languageExpected.getLocale() + ", but was: "
								+ languageActual.getLocale()),
				() -> verify(languageRepoitory, times(1)).findByLocale(locale));
	}

	@Test
	@DisplayName("when locale not found")
	public void when_locale_not_found_should_throw_exception() {

		String locale = "unknown locale";

		Class<LanguageNotFoundException> expectedException = LanguageNotFoundException.class;

		when(languageRepoitory.findByLocale(locale)).thenReturn(Optional.empty());

		assertAll(
				() -> assertThrows(expectedException, () -> languageService.findByLocale(locale),
						() -> "should throw LanguageNotFoundException, but nothing was thrown"),
				() -> verify(languageRepoitory, times(1)).findByLocale(locale));
	}
}
