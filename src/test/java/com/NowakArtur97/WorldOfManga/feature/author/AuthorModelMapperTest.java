package com.NowakArtur97.WorldOfManga.feature.author;

import com.NowakArtur97.WorldOfManga.testUtil.generator.NameWithSpacesGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("ModelMapper_Tests")
class AuthorModelMapperTest {

    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {

        modelMapper = new ModelMapper();
    }

    @Test
    void when_map_author_dto_to_entity_should_return_entity() {

        String fullName = "Firstname Lastname";

        AuthorDTO authorDTOExpected = new AuthorDTO(fullName);

        Author authorExpected = new Author(fullName);

        AuthorDTO authorActual = modelMapper.map(authorDTOExpected, AuthorDTO.class);

        assertAll(() -> assertEquals(authorExpected.getFullName(), authorActual.getFullName(),
                () -> "should return author with full name: " + authorExpected.getFullName() + ", but was: "
                        + authorActual.getFullName()));
    }
}
