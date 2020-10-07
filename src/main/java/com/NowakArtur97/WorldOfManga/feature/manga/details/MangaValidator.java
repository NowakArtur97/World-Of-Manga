package com.NowakArtur97.WorldOfManga.feature.manga.details;

import com.NowakArtur97.WorldOfManga.feature.manga.translation.MangaTranslationDTO;
import com.NowakArtur97.WorldOfManga.feature.manga.translation.MangaTranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class MangaValidator implements Validator {

    private final MangaTranslationService mangaTranslationService;

    @Override
    public boolean supports(Class<?> clazz) {

        return MangaDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        MangaDTO mangaDTO = (MangaDTO) target;

        MangaTranslationDTO mangaTranslationDTOEn = mangaDTO.getEnTranslation();
        MangaTranslationDTO mangaTranslationDTOPl = mangaDTO.getPlTranslation();

        boolean isMangaNew = mangaDTO.getId() == null;
        boolean isMangaEnTitleInUse = mangaTranslationService.isTitleAlreadyInUse(mangaTranslationDTOEn.getTitle());
        boolean isMangaPlTitleInUse = mangaTranslationService.isTitleAlreadyInUse(mangaTranslationDTOPl.getTitle());

        if (isMangaEnTitleInUse && isMangaNew) {

            errors.rejectValue("enTranslation.title", "mangaTranslation.titleEn.inUse");
        }

        if (isMangaPlTitleInUse && isMangaNew) {

            errors.rejectValue("plTranslation.title", "mangaTranslation.titlePl.inUse");
        }

        if (mangaDTO.getImage() == null || mangaDTO.getImage().isEmpty()) {

            errors.rejectValue("image", "manga.image.notEmpty");
        }
    }
}
