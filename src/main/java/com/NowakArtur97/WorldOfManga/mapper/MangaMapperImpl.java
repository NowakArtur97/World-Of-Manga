package com.NowakArtur97.WorldOfManga.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NowakArtur97.WorldOfManga.dto.MangaDTO;
import com.NowakArtur97.WorldOfManga.model.Manga;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MangaMapperImpl implements MangaMapper {

	private final ModelMapper modelMapper;

	@Override
	public Manga mapMangaDTOToManga(MangaDTO mangaDTO) {

		Manga manga = modelMapper.map(mangaDTO, Manga.class);

		return manga;
	}
}
