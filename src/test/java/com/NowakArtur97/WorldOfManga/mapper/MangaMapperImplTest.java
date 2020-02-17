package com.NowakArtur97.WorldOfManga.mapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import com.NowakArtur97.WorldOfManga.dto.MangaDTO;
import com.NowakArtur97.WorldOfManga.mapper.manga.MangaMapperImpl;
import com.NowakArtur97.WorldOfManga.model.Author;
import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaTranslation;

@DisplayName("Manga Mapper Impl Tests")
@Tag("MangaMapperIml_Test")
public class MangaMapperImplTest {

	private MangaMapperImpl mangaMapperImpl = new MangaMapperImpl();

	@Test
	@DisplayName("when map manga dto to entity")
	public void when_map_manga_dto_to_entity_should_return_manga_entity() {

		MangaTranslation mangaEnTranslationExpected = MangaTranslation.builder().title("English title")
				.description("English description").build();

		MangaTranslation mangaPlTranslationExpected = MangaTranslation.builder().title("Polish title")
				.description("Polish description").build();

		Author authorExpected = new Author("FirtName LastName");

		MockMultipartFile mockMultipartFile = new MockMultipartFile("manga.jpg", "file bytes".getBytes());

		MangaDTO mangaDTOExpected = new MangaDTO();

		Set<MangaTranslation> translationsExpected = new HashSet<>();
		translationsExpected.add(mangaEnTranslationExpected);
		translationsExpected.add(mangaPlTranslationExpected);

		Set<Author> authorsExpected = new HashSet<>();
		authorsExpected.add(authorExpected);
		mangaDTOExpected.setAuthors(authorsExpected);

		mangaDTOExpected.setImage(mockMultipartFile);

		Manga mangaActual = mangaMapperImpl.mapMangaDTOToManga(mangaDTOExpected, translationsExpected);

		assertAll(
				() -> assertEquals(translationsExpected.size(), mangaActual.getTranslations().size(),
						() -> "should return manga with two translation, but was: "
								+ mangaActual.getTranslations().size()),
				() -> assertEquals(authorsExpected.size(), mangaActual.getAuthors().size(),
						() -> "should return manga with one author, but was: " + mangaActual.getAuthors().size()),
				() -> assertNotNull(mangaActual.getImage(),
						() -> "should return manga with image, but was: " + mangaActual.getImage()));
	}
}
