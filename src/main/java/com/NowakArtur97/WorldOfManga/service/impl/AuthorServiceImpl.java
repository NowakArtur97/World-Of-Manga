package com.NowakArtur97.WorldOfManga.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NowakArtur97.WorldOfManga.dto.AuthorDTO;
import com.NowakArtur97.WorldOfManga.mapper.MangaTranslationMapper;
import com.NowakArtur97.WorldOfManga.model.Author;
import com.NowakArtur97.WorldOfManga.repository.AuthorRepository;
import com.NowakArtur97.WorldOfManga.repository.MangaTranslationRepository;
import com.NowakArtur97.WorldOfManga.service.api.AuthorService;
import com.NowakArtur97.WorldOfManga.service.api.LanguageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorServiceImpl implements AuthorService {

	private final AuthorRepository authorRepository;
	
	@Override
	public Author addOrUpdate(AuthorDTO authorDTO) {

		return null;
	}

}
