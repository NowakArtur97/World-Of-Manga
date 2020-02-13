package com.NowakArtur97.WorldOfManga.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NowakArtur97.WorldOfManga.model.MangaTranslation;
import com.NowakArtur97.WorldOfManga.repository.MangaTranslationRepository;
import com.NowakArtur97.WorldOfManga.service.api.MangaTranslationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MangaTranslationServiceImpl implements MangaTranslationService {

	private final MangaTranslationRepository mangaTranslationRepository;

	@Override
	public boolean isTitleAlreadyInUse(String title) {

		return mangaTranslationRepository.existsMangaTranslationByTitle(title);
	}

	@Override
	public MangaTranslation save(MangaTranslation mangaTranslation) {

		mangaTranslationRepository.save(mangaTranslation);

		return mangaTranslation;
	};
}
