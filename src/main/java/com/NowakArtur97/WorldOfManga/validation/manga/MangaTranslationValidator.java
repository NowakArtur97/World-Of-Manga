package com.NowakArtur97.WorldOfManga.validation.manga;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.NowakArtur97.WorldOfManga.dto.MangaDTO;
import com.NowakArtur97.WorldOfManga.dto.MangaTranslationDTO;
import com.NowakArtur97.WorldOfManga.service.api.MangaTranslationService;

@Component
public class MangaTranslationValidator implements Validator {

	private final MangaTranslationService mangaTranslationService;

	@Autowired
	public MangaTranslationValidator(MangaTranslationService mangaTranslationService) {

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

		boolean isMangaTitleInUse = mangaTranslationService.isTitleAlreadyInUse(mangaTranslationDTOEn.getTitle())
				|| mangaTranslationService.isTitleAlreadyInUse(mangaTranslationDTOPl.getTitle());

		if (isMangaTitleInUse) {

			errors.rejectValue("title", "mangaTranslation.title.inUse");
		}
	}
}
