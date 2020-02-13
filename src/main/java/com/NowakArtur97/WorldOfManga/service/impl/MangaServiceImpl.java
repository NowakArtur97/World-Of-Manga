package com.NowakArtur97.WorldOfManga.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NowakArtur97.WorldOfManga.dto.MangaDTO;
import com.NowakArtur97.WorldOfManga.exception.LanguageNotFoundException;
import com.NowakArtur97.WorldOfManga.mapper.MangaTranslationMapper;
import com.NowakArtur97.WorldOfManga.model.Language;
import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaTranslation;
import com.NowakArtur97.WorldOfManga.repository.MangaRepository;
import com.NowakArtur97.WorldOfManga.service.api.LanguageService;
import com.NowakArtur97.WorldOfManga.service.api.MangaService;
import com.NowakArtur97.WorldOfManga.service.api.MangaTranslationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MangaServiceImpl implements MangaService {

	private final MangaRepository mangaRepository;

	private final LanguageService languageService;

	private final MangaTranslationMapper mangaTranslationMapper;

	private final MangaTranslationService mangaTranslationService;

	@Override
	public Manga addOrUpdate(MangaDTO mangaDTO) throws LanguageNotFoundException {

		Language en = languageService.findByLocale("en");
		Language pl = languageService.findByLocale("pl");

		MangaTranslation mangaTranslationEn = mangaTranslationMapper
				.mapMangaTranslationDTOToMangaTranslation(mangaDTO.getEnTranslation());
		MangaTranslation mangaTranslationPl = mangaTranslationMapper
				.mapMangaTranslationDTOToMangaTranslation(mangaDTO.getPlTranslation());

		mangaTranslationService.save(mangaTranslationEn);
		mangaTranslationService.save(mangaTranslationPl);

		en.addTranslation(mangaTranslationEn);
		pl.addTranslation(mangaTranslationPl);

		Manga manga = new Manga();

		manga.addTranslation(mangaTranslationEn);
		manga.addTranslation(mangaTranslationPl);

		mangaRepository.save(manga);

		return manga;
	}
}
