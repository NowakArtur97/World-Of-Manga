package com.NowakArtur97.WorldOfManga.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NowakArtur97.WorldOfManga.dto.AuthorDTO;
import com.NowakArtur97.WorldOfManga.model.Author;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorMapperImpl implements AuthorMapper {

	private final ModelMapper modelMapper;

	@Override
	public Author mapAuthorDTOToAuthor(AuthorDTO authorDTO) {

		Author author = modelMapper.map(authorDTO, Author.class);

		return author;
	}
}
