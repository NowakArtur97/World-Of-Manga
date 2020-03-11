package com.NowakArtur97.WorldOfManga.mapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import com.NowakArtur97.WorldOfManga.dto.MangaDTO;
import com.NowakArtur97.WorldOfManga.mapper.manga.MangaMapperImpl;
import com.NowakArtur97.WorldOfManga.model.Author;
import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaGenre;
import com.NowakArtur97.WorldOfManga.model.MangaTranslation;

@DisplayName("Manga Mapper Impl Tests")
@Tag("MangaMapperIml_Test")
public class MangaMapperImplTest {

	private MangaMapperImpl mangaMapperImpl;

	@BeforeEach
	public void setUp() {

		mangaMapperImpl = new MangaMapperImpl();
	}

	@Test
	@DisplayName("when map manga dto to entity")
	public void when_map_manga_dto_to_entity_should_return_manga_entity() {

		MangaTranslation mangaEnTranslationExpected = MangaTranslation.builder().title("English title")
				.description("English description").build();

		MangaTranslation mangaPlTranslationExpected = MangaTranslation.builder().title("Polish title")
				.description("Polish description").build();

		Author authorExpected = new Author("FirtName LastName");

		MockMultipartFile mockMultipartFile = new MockMultipartFile("manga.jpg", "file bytes".getBytes());

		MangaGenre mangaGenreExpected = new MangaGenre("genre");
		MangaGenre mangaGenreExpected2 = new MangaGenre("genre2");

		MangaDTO mangaDTOExpected = new MangaDTO();

		Set<Author> authorsExpected = new HashSet<>();
		authorsExpected.add(authorExpected);

		Set<MangaGenre> genresExpected = new HashSet<>();
		genresExpected.add(mangaGenreExpected);
		genresExpected.add(mangaGenreExpected2);

		mangaDTOExpected.setAuthors(authorsExpected);

		mangaDTOExpected.setGenres(genresExpected);

		mangaDTOExpected.setImage(mockMultipartFile);

		Manga mangaExpected = new Manga();
		mangaExpected.addTranslation(mangaEnTranslationExpected);
		mangaExpected.addTranslation(mangaPlTranslationExpected);

		Manga mangaActual = mangaMapperImpl.mapMangaDTOToManga(mangaExpected, mangaDTOExpected);

		assertAll(
				() -> assertEquals(mangaExpected.getTranslations().size(), mangaActual.getTranslations().size(),
						() -> "should return manga with two translation, but was: " + mangaActual.getTranslations()),
				() -> assertEquals(authorsExpected.size(), mangaActual.getAuthors().size(),
						() -> "should return manga with one author, but was: " + mangaActual.getAuthors()),
				() -> assertEquals(genresExpected.size(), mangaActual.getGenres().size(),
						() -> "should return manga with two genres, but was: " + mangaActual.getGenres()),
				() -> assertNotNull(mangaActual.getImage(),
						() -> "should return manga with image, but was: " + mangaActual.getImage()));
	}

	@Test
	@DisplayName("when map manga entity to dto")
	public void when_map_manga_entity_to_dto_should_return_manga_dto() {

		String enTitle = "English title";
		String plTitle = "Polish title";

		String description = "Description...";

		MangaTranslation mangaEnTranslationExpected = MangaTranslation.builder().title(enTitle).description(description)
				.build();

		MangaTranslation mangaPlTranslationExpected = MangaTranslation.builder().title(plTitle).description(description)
				.build();

		Author authorExpected = new Author("FirtName LastName");

		MangaGenre mangaGenreExpected = new MangaGenre("genre");
		MangaGenre mangaGenreExpected2 = new MangaGenre("genre2");

		Manga mangaExpected = new Manga();

		mangaExpected.addTranslation(mangaEnTranslationExpected);
		mangaExpected.addTranslation(mangaPlTranslationExpected);

		mangaExpected.addAuthor(authorExpected);

		mangaExpected.addGenre(mangaGenreExpected);
		mangaExpected.addGenre(mangaGenreExpected2);

		MangaDTO mangaDTOActual = mangaMapperImpl.mapMangaToDTO(mangaExpected);

		assertAll(
				() -> assertEquals(mangaExpected.getTranslations().get(Manga.EN_TRANSLATION_INDEX).getTitle(),
						mangaDTOActual.getEnTranslation().getTitle(),
						() -> "should return manga dto with english title, but was: "
								+ mangaDTOActual.getEnTranslation().getTitle()),
				() -> assertEquals(mangaExpected.getTranslations().get(Manga.EN_TRANSLATION_INDEX).getDescription(),
						mangaDTOActual.getEnTranslation().getDescription(),
						() -> "should return manga dto with english description, but was: "
								+ mangaDTOActual.getEnTranslation().getDescription()),
				() -> assertEquals(mangaExpected.getTranslations().get(Manga.PL_TRANSLATION_INDEX).getTitle(),
						mangaDTOActual.getPlTranslation().getTitle(),
						() -> "should return manga dto with polish title, but was: "
								+ mangaDTOActual.getEnTranslation().getTitle()),
				() -> assertEquals(mangaExpected.getTranslations().get(Manga.PL_TRANSLATION_INDEX).getDescription(),
						mangaDTOActual.getPlTranslation().getDescription(),
						() -> "should return manga dto with polish description, but was: "
								+ mangaDTOActual.getEnTranslation().getDescription()),
				() -> assertEquals(mangaExpected.getAuthors().size(), mangaDTOActual.getAuthors().size(),
						() -> "should return manga dto with one author, but was: " + mangaDTOActual.getAuthors()),
				() -> assertEquals(mangaExpected.getGenres().size(), mangaDTOActual.getGenres().size(),
						() -> "should return manga to with two genres, but was: " + mangaDTOActual.getGenres()));
	}
}
