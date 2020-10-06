package com.NowakArtur97.WorldOfManga.validation.author;

import com.NowakArtur97.WorldOfManga.dto.AuthorDTO;
import com.NowakArtur97.WorldOfManga.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
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

            errors.rejectValue("fullName", "author.fullName.alreadySaved");
        }
    }
}
