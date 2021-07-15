package com.NowakArtur97.WorldOfManga.feature.manga.translation;

import com.NowakArtur97.WorldOfManga.testUtil.generator.NameWithSpacesGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("Manga_Tests")
@Tag("Unit_Tests")
@Tag("MangaTranslationMapper_Tests")
class MangaTranslationMapperTest {

    private MangaTranslationMapper mangaTranslationMapper;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {

        mangaTranslationMapper = new MangaTranslationMapper(modelMapper);
    }

    @Test
    void when_map_manga_translation_dto_to_entity_should_return_manga_translation_entity() {

        String title = "Manga title";
        String description = "Manga description";

        MangaTranslationDTO mangaTranslationDTOExpected = MangaTranslationDTO.builder().title(title)
                .description(description).build();

        MangaTranslation mangaTranslationExpected = MangaTranslation.builder().title(title).description(description)
                .build();

        when(mangaTranslationMapper.mapMangaTranslationDTOToMangaTranslation(mangaTranslationDTOExpected))
                .thenReturn(mangaTranslationExpected);

        MangaTranslation mangaTranslationActual = mangaTranslationMapper
                .mapMangaTranslationDTOToMangaTranslation(mangaTranslationDTOExpected);

        assertAll(
                () -> assertEquals(mangaTranslationExpected.getTitle(), mangaTranslationActual.getTitle(),
                        () -> "should return manga translation with title: " + mangaTranslationExpected.getTitle()
                                + ", but was: " + mangaTranslationActual.getTitle()),
                () -> assertEquals(mangaTranslationExpected.getDescription(), mangaTranslationActual.getDescription(),
                        () -> "should return manga translation with description: "
                                + mangaTranslationExpected.getDescription() + ", but was: "
                                + mangaTranslationActual.getDescription()),
                () -> verify(modelMapper, times(1)).map(mangaTranslationDTOExpected, MangaTranslation.class));
    }
}
