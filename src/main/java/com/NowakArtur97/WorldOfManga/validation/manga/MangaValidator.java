package com.NowakArtur97.WorldOfManga.validation.manga;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.NowakArtur97.WorldOfManga.dto.MangaDTO;

@Component
public class MangaValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {

		return MangaDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

	}
}
