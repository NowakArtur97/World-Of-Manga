package com.NowakArtur97.WorldOfManga.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NowakArtur97.WorldOfManga.dto.MangaDTO;
import com.NowakArtur97.WorldOfManga.exception.LanguageNotFoundException;
import com.NowakArtur97.WorldOfManga.exception.MangaNotFoundException;
import com.NowakArtur97.WorldOfManga.mapper.mangaTranslation.MangaTranslationMapper;
import com.NowakArtur97.WorldOfManga.model.Language;
import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaTranslation;
import com.NowakArtur97.WorldOfManga.repository.MangaTranslationRepository;
import com.NowakArtur97.WorldOfManga.service.api.LanguageService;
import com.NowakArtur97.WorldOfManga.service.api.MangaService;
import com.NowakArtur97.WorldOfManga.service.api.MangaTranslationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MangaTranslationServiceImpl implements MangaTranslationService {

	private final MangaTranslationRepository mangaTranslationRepository;

	private final MangaTranslationMapper mangaTranslationMapper;

	private final LanguageService languageService;

	private final MangaService mangaService;

	@Override
	public boolean isTitleAlreadyInUse(String title) {

		return mangaTranslationRepository.existsMangaTranslationByTitle(title);
	}

	@Override
	public Manga addOrUpdate(MangaDTO mangaDTO) throws LanguageNotFoundException, MangaNotFoundException {

		Manga manga;

		MangaTranslation mangaTranslationEn = mangaTranslationMapper
				.mapMangaTranslationDTOToMangaTranslation(mangaDTO.getEnTranslation());

		MangaTranslation mangaTranslationPl = mangaTranslationMapper
				.mapMangaTranslationDTOToMangaTranslation(mangaDTO.getPlTranslation());

		if (mangaDTO.getId() != null) {

			manga = editTranslation(mangaDTO, mangaTranslationEn, mangaTranslationPl);

		} else {

			manga = addTranslation(mangaTranslationEn, mangaTranslationPl);
		}

		return manga;
	}

	private Manga addTranslation(MangaTranslation mangaTranslationEn,
			MangaTranslation mangaTranslationPl) throws LanguageNotFoundException {

		Language en = languageService.findByLocale("en");
		Language pl = languageService.findByLocale("pl");

		Manga manga = new Manga();

		en.addTranslation(mangaTranslationEn);
		pl.addTranslation(mangaTranslationPl);

		manga.addTranslation(mangaTranslationEn);
		manga.addTranslation(mangaTranslationPl);

		return manga;
	}

	private Manga editTranslation(MangaDTO mangaDTO, MangaTranslation mangaTranslationEn,
			MangaTranslation mangaTranslationPl) throws MangaNotFoundException {

		Manga manga = mangaService.findById(mangaDTO.getId());

		MangaTranslation enTranslation = manga.getTranslations().get(Manga.EN_TRANSLATION_INDEX);
		MangaTranslation plTranslation = manga.getTranslations().get(Manga.PL_TRANSLATION_INDEX);

		enTranslation.setTitle(mangaTranslationEn.getTitle());
		enTranslation.setDescription(mangaTranslationEn.getDescription());
		plTranslation.setTitle(mangaTranslationPl.getTitle());
		plTranslation.setDescription(mangaTranslationPl.getDescription());

		return manga;
	}
}
