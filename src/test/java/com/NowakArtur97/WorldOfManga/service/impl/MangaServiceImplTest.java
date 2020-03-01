package com.NowakArtur97.WorldOfManga.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import com.NowakArtur97.WorldOfManga.dto.MangaDTO;
import com.NowakArtur97.WorldOfManga.exception.MangaNotFoundException;
import com.NowakArtur97.WorldOfManga.mapper.manga.MangaMapper;
import com.NowakArtur97.WorldOfManga.model.Author;
import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaTranslation;
import com.NowakArtur97.WorldOfManga.model.User;
import com.NowakArtur97.WorldOfManga.repository.MangaRepository;
import com.NowakArtur97.WorldOfManga.service.api.UserService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Manga Service Impl Test Tests")
@Tag("MangaServiceImpl_Tests")
public class MangaServiceImplTest {

	@InjectMocks
	private MangaServiceImpl mangaService;

	@Mock
	private MangaRepository mangaRepository;

	@Mock
	private MangaMapper mangaMapper;

	@Mock
	private UserService userService;

	@Test
	@DisplayName("when add manga should save manga")
	public void when_add_manga_should_save_manga() throws IOException, MangaNotFoundException {

		MangaDTO mangaDTO = new MangaDTO();

		MangaTranslation mangaTranslationEnExpected = MangaTranslation.builder().title("English title")
				.description("English description").build();
		MangaTranslation mangaTranslationPlExpected = MangaTranslation.builder().title("Polish title")
				.description("Polish description").build();

		Set<Author> authorsExpected = new HashSet<>();
		Author authorExpected = new Author("FirsName LastName");
		authorsExpected.add(authorExpected);

		mangaDTO.setAuthors(authorsExpected);

		MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());
		mangaDTO.setImage(image);

		Manga mangaExpected = new Manga();
		mangaExpected.addAuthor(authorExpected);
		mangaExpected.addTranslation(mangaTranslationEnExpected);
		mangaExpected.addTranslation(mangaTranslationPlExpected);
		mangaExpected.setImage(image.getBytes());

		when(mangaMapper.mapMangaDTOToManga(mangaExpected, mangaDTO)).thenReturn(mangaExpected);

		Manga mangaActual = mangaService.addOrUpdate(mangaDTO, mangaExpected);

		assertAll(
				() -> assertEquals(authorsExpected.size(), mangaActual.getAuthors().size(),
						() -> "should contain one author: " + authorExpected + " but was: "
								+ mangaActual.getAuthors().size()),
				() -> assertTrue(mangaActual.getAuthors().contains(authorExpected),
						() -> "should contain author: " + authorExpected + " but was: " + mangaActual.getAuthors()),
				() -> assertEquals(2, mangaActual.getTranslations().size(),
						() -> "should contain both translations, but was: " + mangaActual.getTranslations()),
				() -> assertTrue(mangaActual.getTranslations().contains(mangaTranslationEnExpected),
						() -> "should contain en translation: " + mangaTranslationEnExpected + " but was: "
								+ mangaActual.getTranslations()),
				() -> assertTrue(mangaActual.getTranslations().contains(mangaTranslationEnExpected),
						() -> "should contain en translation: " + mangaTranslationEnExpected + " but was: "
								+ mangaActual.getTranslations()),
				() -> assertNotNull(mangaActual.getImage(),
						() -> "should save manga with image, but was: " + mangaActual.getImage()),
				() -> verify(mangaMapper, times(1)).mapMangaDTOToManga(mangaExpected, mangaDTO),
				() -> verify(mangaRepository, times(1)).save(mangaActual));
	}

	@Test
	@DisplayName("when edit manga should update manga")
	public void when_edit_manga_should_update_manga() throws IOException, MangaNotFoundException {

		Long mangaId = 1L;

		MangaTranslation mangaTranslationEnExpected = MangaTranslation.builder().title("English title")
				.description("English description").build();
		MangaTranslation mangaTranslationPlExpected = MangaTranslation.builder().title("Polish title")
				.description("Polish description").build();

		MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());

		Set<Author> authorsExpected = new HashSet<>();
		Author authorExpected = new Author("FirsName LastName");
		authorsExpected.add(authorExpected);

		MangaDTO mangaDTO = new MangaDTO();

		mangaDTO.setId(mangaId);
		mangaDTO.setImage(image);
		mangaDTO.setAuthors(authorsExpected);

		Manga mangaExpected = new Manga();
		mangaExpected.setId(mangaId);
		mangaExpected.addAuthor(authorExpected);
		mangaExpected.addTranslation(mangaTranslationEnExpected);
		mangaExpected.addTranslation(mangaTranslationPlExpected);
		mangaExpected.setImage(image.getBytes());

		Manga mangaAfterMapperExpected = new Manga();
		mangaAfterMapperExpected.setId(mangaId);
		mangaAfterMapperExpected.addAuthor(authorExpected);
		mangaAfterMapperExpected.addTranslation(mangaTranslationEnExpected);
		mangaAfterMapperExpected.addTranslation(mangaTranslationPlExpected);
		mangaAfterMapperExpected.setImage(image.getBytes());

		when(mangaMapper.mapMangaDTOToManga(mangaExpected, mangaDTO)).thenReturn(mangaAfterMapperExpected);

		Manga mangaActual = mangaService.addOrUpdate(mangaDTO, mangaExpected);

		assertAll(
				() -> assertEquals(1, mangaActual.getAuthors().size(),
						() -> "should contain one author: " + authorExpected + " but was: "
								+ mangaActual.getAuthors().size()),
				() -> assertTrue(mangaActual.getAuthors().contains(authorExpected),
						() -> "should contain author: " + authorExpected + " but was: " + mangaActual.getAuthors()),
				() -> assertEquals(2, mangaActual.getTranslations().size(),
						() -> "should contain both translations, but was: " + mangaActual.getTranslations()),
				() -> assertTrue(mangaActual.getTranslations().contains(mangaTranslationEnExpected),
						() -> "should contain en translation: " + mangaTranslationEnExpected + " but was: "
								+ mangaActual.getTranslations()),
				() -> assertTrue(mangaActual.getTranslations().contains(mangaTranslationEnExpected),
						() -> "should contain en translation: " + mangaTranslationEnExpected + " but was: "
								+ mangaActual.getTranslations()),
				() -> assertNotNull(mangaActual.getImage(),
						() -> "should save manga with image, but was: " + mangaActual.getImage()),
				() -> verify(mangaMapper, times(1)).mapMangaDTOToManga(mangaExpected, mangaDTO),
				() -> verify(mangaRepository, times(1)).save(mangaActual));
	}

	@Test
	@DisplayName("when add manga to favourites for the first time")
	public void when_add_manga_to_favourites_for_first_time_should_add_manga_to_favourites()
			throws MangaNotFoundException, IOException {

		Long mangaId = 1L;

		MangaTranslation mangaTranslationEnExpected = MangaTranslation.builder().title("English title")
				.description("English description").build();
		MangaTranslation mangaTranslationPlExpected = MangaTranslation.builder().title("Polish title")
				.description("Polish description").build();

		Author authorExpected = new Author("FirsName LastName");

		MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());

		Manga mangaExpected = new Manga();
		mangaExpected.addAuthor(authorExpected);
		mangaExpected.addTranslation(mangaTranslationEnExpected);
		mangaExpected.addTranslation(mangaTranslationPlExpected);
		mangaExpected.setImage(image.getBytes());

		User userExpected = User.builder().username("user").firstName("first name").lastName("last name")
				.password("user").email("user@email.com").isEnabled(true).build();

		when(mangaRepository.findById(mangaId)).thenReturn(Optional.of(mangaExpected));
		when(userService.loadLoggedInUsername()).thenReturn(userExpected);

		Manga mangaActual = mangaService.addOrRemoveFromFavourites(mangaId);

		assertAll(
				() -> assertEquals(mangaExpected, mangaActual,
						() -> "should return added to list manga: " + mangaExpected + ", but was: " + mangaActual),
				() -> assertTrue(userExpected.getFavouriteMangas().contains(mangaActual),
						() -> "should manga be in users favourites but wasn`t: " + userExpected.getFavouriteMangas()),
				() -> assertTrue(mangaActual.getUserWithMangaInFavourites().contains(userExpected),
						() -> "should user be one of the people with the manga in favorites but wasn`t: "
								+ userExpected.getFavouriteMangas()),
				() -> verify(mangaRepository, times(1)).findById(mangaId),
				() -> verify(userService, times(1)).loadLoggedInUsername());
	}

	@Test
	@DisplayName("when remove manga from favourites")
	public void when_remove_manga_from_favourites_should_remove_manga_from_favourites()
			throws MangaNotFoundException, IOException {

		Long mangaId = 1L;

		MangaTranslation mangaTranslationEnExpected = MangaTranslation.builder().title("English title")
				.description("English description").build();
		MangaTranslation mangaTranslationPlExpected = MangaTranslation.builder().title("Polish title")
				.description("Polish description").build();

		Author authorExpected = new Author("FirsName LastName");

		MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());

		Manga mangaExpected = new Manga();
		mangaExpected.addAuthor(authorExpected);
		mangaExpected.addTranslation(mangaTranslationEnExpected);
		mangaExpected.addTranslation(mangaTranslationPlExpected);
		mangaExpected.setImage(image.getBytes());

		User userExpected = User.builder().username("user").firstName("first name").lastName("last name")
				.password("user").email("user@email.com").isEnabled(true).build();

		userExpected.addMangaToFavourites(mangaExpected);

		when(mangaRepository.findById(mangaId)).thenReturn(Optional.of(mangaExpected));
		when(userService.loadLoggedInUsername()).thenReturn(userExpected);

		Manga mangaActual = mangaService.addOrRemoveFromFavourites(mangaId);

		assertAll(
				() -> assertEquals(mangaExpected, mangaActual,
						() -> "should return deleted manga from list manga: " + mangaExpected + ", but was: " + mangaActual),
				() -> assertFalse(userExpected.getFavouriteMangas().contains(mangaActual),
						() -> "shouldn`t manga be in users favourites but was: " + userExpected.getFavouriteMangas()),
				() -> assertFalse(mangaActual.getUserWithMangaInFavourites().contains(userExpected),
						() -> "shouldn`t  user be one of the people with the manga in favorites but was: "
								+ mangaActual.getUserWithMangaInFavourites()),
				() -> verify(mangaRepository, times(1)).findById(mangaId),
				() -> verify(userService, times(1)).loadLoggedInUsername());
	}

	@Test
	@DisplayName("when find all")
	public void when_find_all_should_return_list_of_mangas() throws IOException {

		MangaTranslation mangaTranslationEnExpected = MangaTranslation.builder().title("English title")
				.description("English description").build();
		MangaTranslation mangaTranslationPlExpected = MangaTranslation.builder().title("Polish title")
				.description("Polish description").build();

		MangaTranslation mangaTranslationEnExpected2 = MangaTranslation.builder().title("English title 2")
				.description("English description").build();
		MangaTranslation mangaTranslationPlExpected2 = MangaTranslation.builder().title("Polish title 2")
				.description("Polish description").build();

		Set<Author> authorsExpected = new HashSet<>();
		Author authorExpected = new Author("FirsName LastName");
		authorsExpected.add(authorExpected);

		MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());
		MockMultipartFile image2 = new MockMultipartFile("image.jpg", "file bytes".getBytes());

		Manga mangaExpected = new Manga();
		mangaExpected.addAuthor(authorExpected);
		mangaExpected.addTranslation(mangaTranslationEnExpected);
		mangaExpected.addTranslation(mangaTranslationPlExpected);
		mangaExpected.setImage(image.getBytes());

		Manga mangaExpected2 = new Manga();
		mangaExpected.addAuthor(authorExpected);
		mangaExpected.addTranslation(mangaTranslationEnExpected2);
		mangaExpected.addTranslation(mangaTranslationPlExpected2);
		mangaExpected.setImage(image2.getBytes());

		List<Manga> mangasExpected = new ArrayList<>();
		mangasExpected.add(mangaExpected);
		mangasExpected.add(mangaExpected2);

		when(mangaRepository.findAll()).thenReturn(mangasExpected);

		List<Manga> mangasActual = mangaService.findAll();

		assertAll(() -> assertEquals(mangasExpected.size(), mangasActual.size(), "should return list with all authors"),
				() -> assertTrue(mangasActual.contains(mangaExpected),
						() -> "should contain author, but was: " + mangasActual.contains(mangaExpected)),
				() -> assertTrue(mangasActual.contains(mangaExpected2),
						() -> "should contain author, but was: " + mangasActual.contains(mangaExpected2)),
				() -> verify(mangaRepository, times(1)).findAll());
	}

	@Test
	@DisplayName("when find by id found manga")
	public void when_find_by_id_found_manga_should_return_manga_with_id() throws IOException, MangaNotFoundException {

		MangaTranslation mangaTranslationEnExpected = MangaTranslation.builder().title("English title")
				.description("English description").build();
		MangaTranslation mangaTranslationPlExpected = MangaTranslation.builder().title("Polish title")
				.description("Polish description").build();

		Author authorExpected = new Author("FirsName LastName");

		MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());

		Manga mangaExpected = new Manga();
		mangaExpected.addAuthor(authorExpected);
		mangaExpected.addTranslation(mangaTranslationEnExpected);
		mangaExpected.addTranslation(mangaTranslationPlExpected);
		mangaExpected.setImage(image.getBytes());

		Long id = 1L;

		when(mangaRepository.findById(id)).thenReturn(Optional.of(mangaExpected));

		Manga mangaActual = mangaService.findById(id);

		assertAll(
				() -> assertEquals(mangaExpected.getTranslations(), mangaActual.getTranslations(),
						"should return manga with translations"),
				() -> assertEquals(mangaExpected.getAuthors(), mangaActual.getAuthors(),
						"should return manga with authors"),
				() -> assertEquals(mangaExpected.getImage(), mangaActual.getImage(), "should return manga with image"),
				() -> verify(mangaRepository, times(1)).findById(id));
	}

	@Test
	@DisplayName("when find by id but cannot find manga")
	public void when_find_by_id_bu_cannot_find_manga_should_return_manga_with_id() throws IOException {

		Long id = 0L;

		Class<MangaNotFoundException> expectedException = MangaNotFoundException.class;

		when(mangaRepository.findById(id)).thenReturn(Optional.empty());

		assertAll(
				() -> assertThrows(expectedException, () -> mangaService.findById(id),
						() -> "should throw MangaNotFoundException, but nothing was thrown"),
				() -> verify(mangaRepository, times(1)).findById(id));
	}
}
