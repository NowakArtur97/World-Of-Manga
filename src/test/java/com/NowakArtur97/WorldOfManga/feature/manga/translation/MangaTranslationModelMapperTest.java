package com.NowakArtur97.WorldOfManga.feature.manga.translation;

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
class MangaTranslationModelMapperTest {

    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {

        modelMapper = new ModelMapper();
    }

    @Test
    void when_map_manga_translation_dto_to_entity_should_return_entity() {

        String title = "Manga title";
        String description = "Manga description";

        MangaTranslationDTO mangaTranslationDTOExpected = MangaTranslationDTO.builder().title(title)
                .description(description).build();

        MangaTranslation mangaTranslationExpected = MangaTranslation.builder().title(title).description(description)
                .build();

        MangaTranslation mangaTranslationActual = modelMapper.map(mangaTranslationDTOExpected, MangaTranslation.class);

        assertAll(
                () -> assertEquals(mangaTranslationExpected.getTitle(), mangaTranslationActual.getTitle(),
                        () -> "should return manga translation with title: " + mangaTranslationExpected.getTitle()
                                + ", but was: " + mangaTranslationActual.getTitle()),
                () -> assertEquals(mangaTranslationExpected.getDescription(), mangaTranslationActual.getDescription(),
                        () -> "should return manga translation with description: "
                                + mangaTranslationExpected.getDescription() + ", but was: "
                                + mangaTranslationActual.getDescription()));
    }
}
