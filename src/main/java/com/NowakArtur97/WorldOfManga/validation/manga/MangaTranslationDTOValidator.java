package com.NowakArtur97.WorldOfManga.validation.manga;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.NowakArtur97.WorldOfManga.dto.MangaTranslationDTO;
import com.NowakArtur97.WorldOfManga.service.api.MangaTranslationService;

@Component
public class MangaTranslationDTOValidator implements Validator {

	private final MangaTranslationService mangaTranslationService;

	@Autowired
	public MangaTranslationDTOValidator(MangaTranslationService mangaTranslationService) {

		this.mangaTranslationService = mangaTranslationService;
	}

	@Override
	public boolean supports(Class<?> clazz) {

		return MangaTranslationDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		MangaTranslationDTO mangaTranslationDTO = (MangaTranslationDTO)target;
		
		boolean isMangaTitleInUse = mangaTranslationService.isMangaTitleAlreadyInUse(mangaTranslationDTO.getTitle());
		
		if(isMangaTitleInUse) {
			
			errors.rejectValue("title", "mangaTranslation.title.inUse");
		}
	}
}
