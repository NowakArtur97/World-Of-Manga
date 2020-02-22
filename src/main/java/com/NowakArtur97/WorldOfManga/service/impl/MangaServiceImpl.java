package com.NowakArtur97.WorldOfManga.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NowakArtur97.WorldOfManga.dto.MangaDTO;
import com.NowakArtur97.WorldOfManga.exception.MangaNotFoundException;
import com.NowakArtur97.WorldOfManga.mapper.manga.MangaMapper;
import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaTranslation;
import com.NowakArtur97.WorldOfManga.repository.MangaRepository;
import com.NowakArtur97.WorldOfManga.service.api.MangaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MangaServiceImpl implements MangaService {

	private final MangaRepository mangaRepository;

	private final MangaMapper mangaMapper;

	@Override
	public Manga addOrUpdate(MangaDTO mangaDTO, Set<MangaTranslation> mangaTranslations) {

		Manga manga = mangaMapper.mapMangaDTOToManga(mangaDTO, mangaTranslations);

		mangaRepository.save(manga);

		return manga;
	}

	@Override
	public Long rateManga(Long id, Long rating) {

		
		
		return null;
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
