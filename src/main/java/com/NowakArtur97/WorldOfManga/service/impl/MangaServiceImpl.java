package com.NowakArtur97.WorldOfManga.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NowakArtur97.WorldOfManga.dto.MangaDTO;
import com.NowakArtur97.WorldOfManga.exception.MangaNotFoundException;
import com.NowakArtur97.WorldOfManga.mapper.manga.MangaMapper;
import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.repository.MangaRepository;
import com.NowakArtur97.WorldOfManga.service.api.MangaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))

public class MangaServiceImpl implements MangaService {

	private final MangaRepository mangaRepository;

	private final MangaMapper mangaMapper;

	@Override
	public Manga addOrUpdate(MangaDTO mangaDTO, Manga manga) throws MangaNotFoundException {

		if (mangaDTO.getId() != null) {

			manga.setId(mangaDTO.getId());
			manga.removeAllAuthors();
		} 

		manga = mangaMapper.mapMangaDTOToManga(manga, mangaDTO);

		mangaRepository.save(manga);

		return manga;
	}

	@Override
	public Manga deleteManga(Long mangaId) throws MangaNotFoundException {
		
		Manga manga = findById(mangaId);

		mangaRepository.delete(manga);
		
		return manga;
	}
	
	@Override
	public MangaDTO getMangaDTOById(Long mangaId) throws MangaNotFoundException {

		Manga manga = findById(mangaId);

		MangaDTO mangaDTO = mangaMapper.mapMangaToDTO(manga);

		return mangaDTO;
	}

	@Override
	public Manga findById(Long id) throws MangaNotFoundException {

		return mangaRepository.findById(id)
				.orElseThrow(() -> new MangaNotFoundException("Manga with id:" + id + " not found"));
	}

	@Override
	public List<Manga> findAll() {

		return mangaRepository.findAll();
	}
}
