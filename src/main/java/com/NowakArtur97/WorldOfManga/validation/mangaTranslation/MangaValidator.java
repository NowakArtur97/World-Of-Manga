package com.NowakArtur97.WorldOfManga.validation.mangaTranslation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.NowakArtur97.WorldOfManga.dto.MangaDTO;
import com.NowakArtur97.WorldOfManga.dto.MangaTranslationDTO;
import com.NowakArtur97.WorldOfManga.service.api.MangaTranslationService;

@Component
public class MangaValidator implements Validator {

	private final MangaTranslationService mangaTranslationService;

	@Autowired
	public MangaValidator(MangaTranslationService mangaTranslationService) {

		this.mangaTranslationService = mangaTranslationService;
	}

	@Override
	public boolean supports(Class<?> clazz) {

		return MangaDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		MangaDTO mangaDTO = (MangaDTO) target;

		MangaTranslationDTO mangaTranslationDTOEn = mangaDTO.getEnTranslation();
		MangaTranslationDTO mangaTranslationDTOPl = mangaDTO.getPlTranslation();

		boolean isMangaEnTitleInUse = mangaTranslationService.isTitleAlreadyInUse(mangaTranslationDTOEn.getTitle());
		boolean isMangaPlTitleInUse = mangaTranslationService.isTitleAlreadyInUse(mangaTranslationDTOPl.getTitle());

		if (isMangaEnTitleInUse) {

			errors.rejectValue("enTranslation.title", "mangaTranslation.titleEn.inUse");
		}

		if (isMangaPlTitleInUse) {

			errors.rejectValue("plTranslation.title", "mangaTranslation.titlePl.inUse");
		}
	}
}
