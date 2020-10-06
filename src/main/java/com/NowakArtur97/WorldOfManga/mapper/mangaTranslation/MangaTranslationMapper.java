package com.NowakArtur97.WorldOfManga.mapper.mangaTranslation;

import com.NowakArtur97.WorldOfManga.dto.MangaTranslationDTO;
import com.NowakArtur97.WorldOfManga.model.MangaTranslation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MangaTranslationMapper {

    private final ModelMapper modelMapper;

    public MangaTranslation mapMangaTranslationDTOToMangaTranslation(MangaTranslationDTO mangaTranslationDTO) {

        return modelMapper.map(mangaTranslationDTO, MangaTranslation.class);
    }
}
