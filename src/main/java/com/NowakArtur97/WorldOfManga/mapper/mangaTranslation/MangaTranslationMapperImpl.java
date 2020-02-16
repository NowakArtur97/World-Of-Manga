package com.NowakArtur97.WorldOfManga.mapper.mangaTranslation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NowakArtur97.WorldOfManga.dto.MangaTranslationDTO;
import com.NowakArtur97.WorldOfManga.model.MangaTranslation;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MangaTranslationMapperImpl implements MangaTranslationMapper {

	private final ModelMapper modelMapper;

	@Override
	public MangaTranslation mapMangaTranslationDTOToMangaTranslation(MangaTranslationDTO mangaTranslationDTO) {

		MangaTranslation mangaTranslation = modelMapper.map(mangaTranslationDTO, MangaTranslation.class);

		return mangaTranslation;
	}
}
