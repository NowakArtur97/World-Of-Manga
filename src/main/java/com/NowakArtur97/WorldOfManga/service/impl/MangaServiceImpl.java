package com.NowakArtur97.WorldOfManga.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NowakArtur97.WorldOfManga.repository.MangaRepository;
import com.NowakArtur97.WorldOfManga.service.api.MangaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MangaServiceImpl implements MangaService {

	private final MangaRepository mangaRepository;
}
