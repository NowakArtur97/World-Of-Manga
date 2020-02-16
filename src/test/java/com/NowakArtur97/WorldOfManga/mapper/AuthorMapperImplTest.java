package com.NowakArtur97.WorldOfManga.mapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.NowakArtur97.WorldOfManga.dto.AuthorDTO;
import com.NowakArtur97.WorldOfManga.mapper.author.AuthorMapperImpl;
import com.NowakArtur97.WorldOfManga.model.Author;

@ExtendWith(MockitoExtension.class)
@DisplayName("Author Mapper Impl Tests")
@Tag("AuthorMapperImpl_Tests")
public class AuthorMapperImplTest {

	@InjectMocks
	private AuthorMapperImpl authorMapper;

	@Mock
	private ModelMapper modelMapper;

	@Test
	@DisplayName("when map author dto to entity")
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
