package com.NowakArtur97.WorldOfManga.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.NowakArtur97.WorldOfManga.repository.LanguageRepoitory;

@ExtendWith(MockitoExtension.class)
@DisplayName("Language Service Impl Tests")
@Tag("LanguageServiceImpl_Tests")
public class LanguageServiceImplTest {

	@InjectMocks
	private LanguageServiceImpl languageServiceImpl;

	@Mock
	private LanguageRepoitory languageRepoitory;
}
