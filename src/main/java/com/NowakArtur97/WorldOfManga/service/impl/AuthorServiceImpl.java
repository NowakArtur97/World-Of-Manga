package com.NowakArtur97.WorldOfManga.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NowakArtur97.WorldOfManga.dto.AuthorDTO;
import com.NowakArtur97.WorldOfManga.mapper.AuthorMapper;
import com.NowakArtur97.WorldOfManga.model.Author;
import com.NowakArtur97.WorldOfManga.repository.AuthorRepository;
import com.NowakArtur97.WorldOfManga.service.api.AuthorService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorServiceImpl implements AuthorService {

	private final AuthorRepository authorRepository;

	private final AuthorMapper authorMapper;

	@Override
	public Author addOrUpdate(AuthorDTO authorDTO) {

		Author author = authorMapper.mapAuthorDTOToAuthor(authorDTO);

		authorRepository.save(author);

		return author;
	}

	@Override
	public boolean isAuthorAlreadyInDatabase(String fullName) {

		return authorRepository.existsAuthorByFullName(fullName);
	}
}
