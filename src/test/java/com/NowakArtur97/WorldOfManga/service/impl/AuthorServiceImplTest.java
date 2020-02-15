package com.NowakArtur97.WorldOfManga.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import com.NowakArtur97.WorldOfManga.dto.AuthorDTO;
import com.NowakArtur97.WorldOfManga.mapper.AuthorMapper;
import com.NowakArtur97.WorldOfManga.model.Author;
import com.NowakArtur97.WorldOfManga.repository.AuthorRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Author Service Impl Tests")
@Tag("AuthorServiceImpl_Tests")
public class AuthorServiceImplTest {

	@InjectMocks
	private AuthorServiceImpl authorService;

	@Mock
	private AuthorRepository authorRepository;

	@Mock
	private AuthorMapper authorMapper;

	@Test
	@DisplayName("when full name is already in database")
	public void when_full_name_is_already_in_database_should_return_true() {

		boolean isAuthorAlreadyInDatabase = true;

		String fullName = "FirstName LastName";

		when(authorRepository.existsAuthorByFullName(fullName)).thenReturn(isAuthorAlreadyInDatabase);

		boolean isAuthorAlreadyInDatabaseActual = authorService.isAuthorAlreadyInDatabase(fullName);

		assertAll(
				() -> assertTrue(isAuthorAlreadyInDatabaseActual,
						() -> "should return true, but was: " + isAuthorAlreadyInDatabaseActual),
				() -> verify(authorRepository, times(1)).existsAuthorByFullName(fullName));
	}

	@Test
	@DisplayName("when full name isn`t already in database")
	public void when_full_name_is_not_already_in_database_should_return_true() {

		boolean isAuthorAlreadyInDatabase = false;

		String fullName = "FirstName LastName";

		when(authorRepository.existsAuthorByFullName(fullName)).thenReturn(isAuthorAlreadyInDatabase);

		boolean isAuthorAlreadyInDatabaseActual = authorService.isAuthorAlreadyInDatabase(fullName);

		assertAll(
				() -> assertFalse(isAuthorAlreadyInDatabaseActual,
						() -> "should return false, but was: " + isAuthorAlreadyInDatabaseActual),
				() -> verify(authorRepository, times(1)).existsAuthorByFullName(fullName));
	}

	@Test
	@DisplayName("when add author should save author")
	public void when_add_author_should_save_author() {

		String fullName = "Firstname LastName";

		AuthorDTO authorDTOExpected = new AuthorDTO(fullName);

		Author authorExpected = new Author(fullName);

		when(authorMapper.mapAuthorDTOToAuthor(authorDTOExpected)).thenReturn(authorExpected);

		Author authorActual = authorService.addOrUpdate(authorDTOExpected);

		assertAll(
				() -> assertEquals(authorExpected.getFullName(), authorActual.getFullName(),
						() -> "should return author with full name: " + authorExpected.getFullName() + ", but was: "
								+ authorActual.getFullName()),
				() -> verify(authorMapper, times(1)).mapAuthorDTOToAuthor(authorDTOExpected),
				() -> verify(authorRepository, times(1)).save(authorActual));
		;
	}
}
