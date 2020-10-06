package com.NowakArtur97.WorldOfManga.feature.author;

import com.NowakArtur97.WorldOfManga.testUtil.generator.NameWithSpacesGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("AuthorService_Tests")
class AuthorServiceTest {

    private AuthorService authorService;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper authorMapper;

    @BeforeEach
    void setUp() {

        authorService = new AuthorService(authorRepository, authorMapper);
    }

    @Test
    void when_full_name_is_already_in_database_should_return_true() {

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
    void when_full_name_is_not_already_in_database_should_return_true() {

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
    void when_add_author_should_save_author() {

        String fullName = "Firstname LastName";

        AuthorDTO authorDTOExpected = new AuthorDTO(fullName);

        Author authorExpected = new Author(fullName);

        when(authorMapper.mapAuthorDTOToAuthor(authorDTOExpected)).thenReturn(authorExpected);
        when(authorRepository.save(authorExpected)).thenReturn(authorExpected);

        Author authorActual = authorService.addOrUpdate(authorDTOExpected);

        assertAll(
                () -> assertEquals(authorExpected.getFullName(), authorActual.getFullName(),
                        () -> "should return author with full name: " + authorExpected.getFullName() + ", but was: "
                                + authorActual.getFullName()),
                () -> verify(authorMapper, times(1)).mapAuthorDTOToAuthor(authorDTOExpected),
                () -> verify(authorRepository, times(1)).save(authorActual));
    }

    @Test
    void when_save_author_should_save_author() {

        String fullName = "Firstname LastName";

        Author authorExpected = new Author(fullName);

        when(authorRepository.save(authorExpected)).thenReturn(authorExpected);

        Author authorActual = authorService.save(authorExpected);

        assertAll(
                () -> assertEquals(authorExpected.getFullName(), authorActual.getFullName(),
                        () -> "should return author with full name: " + authorExpected.getFullName() + ", but was: "
                                + authorActual.getFullName()),
                () -> verify(authorRepository, times(1)).save(authorActual));
    }

    @Test
    void when_find_all_should_return_list_of_authors() {

        List<Author> authorsExpected = new ArrayList<>();
        Author authorExpected = new Author("FirstName LastName");
        Author authorExpected2 = new Author("FirstName2 LastName2");
        authorsExpected.add(authorExpected);
        authorsExpected.add(authorExpected2);

        when(authorRepository.findAll()).thenReturn(authorsExpected);

        List<Author> authorsActual = authorService.findAll();

        assertAll(
                () -> assertEquals(authorsExpected.size(), authorsActual.size(), "should return list with all authors"),
                () -> assertTrue(authorsActual.contains(authorExpected),
                        () -> "should contain author, but was: " + authorsActual.contains(authorExpected)),
                () -> assertTrue(authorsActual.contains(authorExpected2),
                        () -> "should contain author, but was: " + authorsActual.contains(authorExpected2)),
                () -> verify(authorRepository, times(1)).findAll());
    }
}
