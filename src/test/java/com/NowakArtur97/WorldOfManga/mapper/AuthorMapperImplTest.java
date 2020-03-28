package com.NowakArtur97.WorldOfManga.mapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.NowakArtur97.WorldOfManga.dto.AuthorDTO;
import com.NowakArtur97.WorldOfManga.mapper.author.AuthorMapperImpl;
import com.NowakArtur97.WorldOfManga.model.Author;
import com.NowakArtur97.WorldOfManga.testUtil.generator.ReplaceUnderscoresGenerator;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscoresGenerator.class)
@Tag("AuthorMapperImpl_Tests")
public class AuthorMapperImplTest {

	private AuthorMapperImpl authorMapper;

	@Mock
	private ModelMapper modelMapper;

	@BeforeEach
	public void setUp() {

		authorMapper = new AuthorMapperImpl(modelMapper);
	}

	@Test
	public void when_map_authorr_dto_should_return_author_entity() {

		String fullName = "Firstname LastName";

		AuthorDTO authorDTOExpected = new AuthorDTO(fullName);

		Author authorExpected = new Author(fullName);

		when(authorMapper.mapAuthorDTOToAuthor(authorDTOExpected)).thenReturn(authorExpected);

		Author authorActual = authorMapper.mapAuthorDTOToAuthor(authorDTOExpected);

		assertAll(
				() -> assertEquals(authorExpected.getFullName(), authorActual.getFullName(),
						() -> "should return author with full name: " + authorExpected.getFullName() + ", but was: "
								+ authorActual.getFullName()),
				() -> verify(modelMapper, times(1)).map(authorDTOExpected, Author.class));
	}
}
