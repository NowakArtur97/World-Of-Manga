package com.NowakArtur97.WorldOfManga.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NowakArtur97.WorldOfManga.dto.AuthorDTO;
import com.NowakArtur97.WorldOfManga.mapper.author.AuthorMapper;
import com.NowakArtur97.WorldOfManga.model.Author;
import com.NowakArtur97.WorldOfManga.repository.AuthorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorServiceImpl implements AuthorService {

	private final AuthorRepository authorRepository;

	private final AuthorMapper authorMapper;

	@Override
	public Author addOrUpdate(AuthorDTO authorDTO) {

		Author author = authorMapper.mapAuthorDTOToAuthor(authorDTO);

		return save(author);
	}

	@Override
	public boolean isAuthorAlreadyInDatabase(String fullName) {

		return authorRepository.existsAuthorByFullName(fullName);
	}

	@Override
	public List<Author> findAll() {

		return authorRepository.findAll();
	}

	@Override
	public Author save(Author author) {
		
		return authorRepository.save(author);
	}
}
