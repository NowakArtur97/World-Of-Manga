package com.NowakArtur97.WorldOfManga.validation.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.NowakArtur97.WorldOfManga.dto.AuthorDTO;
import com.NowakArtur97.WorldOfManga.service.api.AuthorService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorValidator implements Validator {

	private final AuthorService authorService;

	@Override
	public boolean supports(Class<?> clazz) {

		return AuthorDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		AuthorDTO authorDTO = (AuthorDTO) target;

		boolean isAuthorAlreadyInDatabase = authorService.isAuthorAlreadyInDatabase(authorDTO.getFullName());

		if (isAuthorAlreadyInDatabase) {
			
			errors.reject("fullName", "author.fullName.alreadySaved");
		}
	}
}
