package com.NowakArtur97.WorldOfManga.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NowakArtur97.WorldOfManga.dto.MangaDTO;
import com.NowakArtur97.WorldOfManga.exception.LanguageNotFoundException;
import com.NowakArtur97.WorldOfManga.mapper.MangaTranslationMapper;
import com.NowakArtur97.WorldOfManga.model.Language;
import com.NowakArtur97.WorldOfManga.model.MangaTranslation;
import com.NowakArtur97.WorldOfManga.repository.MangaTranslationRepository;
import com.NowakArtur97.WorldOfManga.service.api.LanguageService;
import com.NowakArtur97.WorldOfManga.service.api.MangaTranslationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MangaTranslationServiceImpl implements MangaTranslationService {

	private final MangaTranslationRepository mangaTranslationRepository;

	private final MangaTranslationMapper mangaTranslationMapper;

	private final LanguageService languageService;

	@Override
	public boolean isTitleAlreadyInUse(String title) {

		return mangaTranslationRepository.existsMangaTranslationByTitle(title);
	}

	@Override
	public Set<MangaTranslation> addOrUpdate(MangaDTO mangaDTO) throws LanguageNotFoundException {

		Language en = languageService.findByLocale("en");
		Language pl = languageService.findByLocale("pl");

		MangaTranslation mangaTranslationEn = mangaTranslationMapper
				.mapMangaTranslationDTOToMangaTranslation(mangaDTO.getEnTranslation());
		MangaTranslation mangaTranslationPl = mangaTranslationMapper
				.mapMangaTranslationDTOToMangaTranslation(mangaDTO.getPlTranslation());

		mangaTranslationRepository.save(mangaTranslationEn);
		mangaTranslationRepository.save(mangaTranslationPl);

		en.addTranslation(mangaTranslationEn);
		pl.addTranslation(mangaTranslationPl);

		Set<MangaTranslation> mangaTranslations = new HashSet<>();
		mangaTranslations.add(mangaTranslationEn);
		mangaTranslations.add(mangaTranslationPl);

		return mangaTranslations;
	};
}
