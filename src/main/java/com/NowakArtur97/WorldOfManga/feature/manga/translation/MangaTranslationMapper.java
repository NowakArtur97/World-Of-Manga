package com.NowakArtur97.WorldOfManga.feature.manga.translation;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class MangaTranslationMapper {

    private final ModelMapper modelMapper;

    MangaTranslation mapMangaTranslationDTOToMangaTranslation(MangaTranslationDTO mangaTranslationDTO) {

        return modelMapper.map(mangaTranslationDTO, MangaTranslation.class);
    }
}
