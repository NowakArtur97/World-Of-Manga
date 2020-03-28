package com.NowakArtur97.WorldOfManga.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import com.NowakArtur97.WorldOfManga.enums.MangaInUserListStatus;
import com.NowakArtur97.WorldOfManga.exception.MangaNotFoundException;
import com.NowakArtur97.WorldOfManga.model.Author;
import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaInUserList;
import com.NowakArtur97.WorldOfManga.model.MangaTranslation;
import com.NowakArtur97.WorldOfManga.model.User;
import com.NowakArtur97.WorldOfManga.repository.MangaInUserListRepository;
import com.NowakArtur97.WorldOfManga.service.api.MangaService;
import com.NowakArtur97.WorldOfManga.service.api.UserService;
import com.NowakArtur97.WorldOfManga.testUtil.generator.ReplaceUnderscoresGenerator;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscoresGenerator.class)
@Tag("MangaInUserListServiceImpl_Tests")
public class MangaInUserListServiceImplTest {

	private MangaInUserListServiceImpl mangaInUserListService;

	@Mock
	private MangaInUserListRepository mangaInUserListRepository;

	@Mock
	private UserService userService;

	@Mock
	private MangaService mangaService;

	@BeforeEach
	void setUp() {

		mangaInUserListService = new MangaInUserListServiceImpl(mangaInUserListRepository, mangaService, userService);
	}

	@Test
	public void whne_find_existing_manga_ins_user_list_by_user_and_manga_should_return_entity() throws IOException {

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

		User userExpected = User.builder().username("username").firstName("first name").lastName("last name")
				.password("user").email("user@email.com").isEnabled(true).build();

		MangaInUserListStatus statusExpected = MangaInUserListStatus.COMPLETED;

		MangaInUserList mangaInUserListExpected = MangaInUserList.builder().manga(mangaExpected).user(userExpected)
				.status(statusExpected).build();

		when(mangaInUserListRepository.findByUserAndManga(userExpected, mangaExpected))
				.thenReturn(Optional.of(mangaInUserListExpected));

		MangaInUserList mangaInUserListActual = mangaInUserListService.findByUserAndManga(userExpected, mangaExpected)
				.get();

		assertAll(
				() -> assertEquals(mangaInUserListExpected.getManga(), mangaInUserListActual.getManga(),
						() -> "should return manga in user list with manga: " + mangaInUserListExpected.getManga()
								+ ", but was: " + mangaInUserListActual.getManga()),
				() -> assertEquals(mangaInUserListExpected.getUser(), mangaInUserListActual.getUser(),
						() -> "should return manga in user list with user: " + mangaInUserListExpected.getUser()
								+ ", but was: " + mangaInUserListActual.getUser()),
				() -> assertEquals(mangaInUserListExpected.getStatus(), mangaInUserListActual.getStatus(),
						() -> "should return manga in user list with status: " + mangaInUserListExpected.getStatus()
								+ ", but was: " + mangaInUserListActual.getStatus()),
				() -> verify(mangaInUserListRepository, times(1)).findByUserAndManga(userExpected, mangaExpected));
	}

	@Test
	public void whne_find_not_existing_manga_ins_user_list_by_user_and_manga_should_return_empty_optional()
			throws IOException {

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

		User userExpected = User.builder().username("username").firstName("first name").lastName("last name")
				.password("user").email("user@email.com").isEnabled(true).build();

		when(mangaInUserListRepository.findByUserAndManga(userExpected, mangaExpected)).thenReturn(Optional.empty());

		Optional<MangaInUserList> mangaInUserListActual = mangaInUserListService.findByUserAndManga(userExpected,
				mangaExpected);

		assertAll(
				() -> assertTrue(mangaInUserListActual.isEmpty(),
						() -> "should return empty Optional, but was: " + mangaInUserListActual),
				() -> verify(mangaInUserListRepository, times(1)).findByUserAndManga(userExpected, mangaExpected));
	}

	@Test
	public void when_add_manga_to_list_for_first_time_should_add_manga_to_list()
			throws IOException, MangaNotFoundException {

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

		String username = "principal";

		User userExpected = User.builder().username(username).firstName("first name").lastName("last name")
				.password("user").email("user@email.com").isEnabled(true).build();

		int statusIntExpected = 0;

		MangaInUserListStatus statusExpected = MangaInUserListStatus.CURRENTLY_READING;

		when(userService.loadLoggedInUsername()).thenReturn(userExpected);
		when(mangaService.findById(mangaId)).thenReturn(mangaExpected);

		MangaInUserList mangaInUserListActual = mangaInUserListService.addOrRemoveFromList(mangaId, statusIntExpected);

		assertAll(
				() -> assertEquals(mangaExpected, mangaInUserListActual.getManga(),
						() -> "should return manga in user list with manga: " + mangaExpected + ", but was: "
								+ mangaInUserListActual.getManga()),
				() -> assertEquals(userExpected, mangaInUserListActual.getUser(),
						() -> "should return manga in user list with user: " + userExpected + ", but was: "
								+ mangaInUserListActual.getUser()),
				() -> assertEquals(statusExpected, mangaInUserListActual.getStatus(),
						() -> "should return manga in user list with status: " + statusExpected + ", but was: "
								+ mangaInUserListActual.getStatus()),
				() -> verify(mangaService, times(1)).findById(mangaId),
				() -> verify(userService, times(1)).loadLoggedInUsername());
	}

	@Test
	public void when_add_change_manga_status_should_update_status() throws IOException, MangaNotFoundException {

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

		String username = "principal";

		User userExpected = User.builder().username(username).firstName("first name").lastName("last name")
				.password("user").email("user@email.com").isEnabled(true).build();

		int statusIntExpected = 0;

		MangaInUserListStatus statusExpected = MangaInUserListStatus.CURRENTLY_READING;

		MangaInUserList mangaInUserListExpected = MangaInUserList.builder().manga(mangaExpected).user(userExpected)
				.status(statusExpected).build();

		when(mangaService.findById(mangaId)).thenReturn(mangaExpected);
		when(userService.loadLoggedInUsername()).thenReturn(userExpected);

		MangaInUserList mangaInUserListActual = mangaInUserListService.addOrRemoveFromList(mangaId, statusIntExpected);

		assertAll(
				() -> assertEquals(mangaInUserListExpected.getManga(), mangaInUserListActual.getManga(),
						() -> "should return manga in user list with manga: " + mangaInUserListExpected.getManga()
								+ ", but was: " + mangaInUserListActual.getManga()),
				() -> assertEquals(mangaInUserListExpected.getUser(), mangaInUserListActual.getUser(),
						() -> "should return manga in user list with user: " + mangaInUserListExpected.getUser()
								+ ", but was: " + mangaInUserListActual.getUser()),
				() -> assertEquals(mangaInUserListExpected.getStatus(), mangaInUserListActual.getStatus(),
						() -> "should return manga in user list with status: " + mangaInUserListExpected.getStatus()
								+ ", but was: " + mangaInUserListActual.getStatus()),
				() -> assertTrue(userExpected.getMangaList().contains(mangaInUserListActual),
						() -> "should not manga be in user list, but was: " + userExpected.getMangaList()),
				() -> verify(mangaService, times(1)).findById(mangaId),
				() -> verify(userService, times(1)).loadLoggedInUsername());
	}

	@Test
	public void when_remove_manga_from_list_should_remove_from_list() throws IOException, MangaNotFoundException {

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

		String username = "principal";

		User userExpected = User.builder().username(username).firstName("first name").lastName("last name")
				.password("user").email("user@email.com").isEnabled(true).build();

		int statusIntExpected = 0;

		MangaInUserListStatus statusExpected = MangaInUserListStatus.CURRENTLY_READING;

		MangaInUserList mangaInUserListExpected = MangaInUserList.builder().manga(mangaExpected).user(userExpected)
				.status(statusExpected).build();

		userExpected.getMangaList().add(mangaInUserListExpected);

		when(mangaService.findById(mangaId)).thenReturn(mangaExpected);
		when(userService.loadLoggedInUsername()).thenReturn(userExpected);
		when(mangaInUserListRepository.findByUserAndManga(userExpected, mangaExpected))
				.thenReturn(Optional.of(mangaInUserListExpected));

		MangaInUserList mangaInUserListActual = mangaInUserListService.addOrRemoveFromList(mangaId, statusIntExpected);

		assertAll(
				() -> assertEquals(mangaInUserListExpected.getManga(), mangaInUserListActual.getManga(),
						() -> "should return manga in user list with manga: " + mangaInUserListExpected.getManga()
								+ ", but was: " + mangaInUserListActual.getManga()),
				() -> assertEquals(mangaInUserListExpected.getUser(), mangaInUserListActual.getUser(),
						() -> "should return manga in user list with user: " + mangaInUserListExpected.getUser()
								+ ", but was: " + mangaInUserListActual.getUser()),
				() -> assertEquals(mangaInUserListExpected.getStatus(), mangaInUserListActual.getStatus(),
						() -> "should return manga in user list with status: " + mangaInUserListExpected.getStatus()
								+ ", but was: " + mangaInUserListActual.getStatus()),
				() -> assertFalse(userExpected.getMangaList().contains(mangaInUserListActual),
						() -> "should not manga be in user list, but was: " + userExpected.getMangaList()),
				() -> verify(mangaService, times(1)).findById(mangaId),
				() -> verify(userService, times(1)).loadLoggedInUsername());
	}

	@Test
	public void when_get_users_manga_list_by_status_should_return_specific_manga_list()
			throws IOException, MangaNotFoundException {

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

		MangaTranslation mangaTranslationEnExpected2 = MangaTranslation.builder().title("English title")
				.description("English description").build();
		MangaTranslation mangaTranslationPlExpected2 = MangaTranslation.builder().title("Polish title")
				.description("Polish description").build();

		Author authorExpected2 = new Author("FirsName LastName");

		MockMultipartFile image2 = new MockMultipartFile("image2.jpg", "file bytes".getBytes());

		Manga mangaExpected2 = new Manga();
		mangaExpected2.addAuthor(authorExpected2);
		mangaExpected2.addTranslation(mangaTranslationEnExpected2);
		mangaExpected2.addTranslation(mangaTranslationPlExpected2);
		mangaExpected2.setImage(image2.getBytes());

		Set<Manga> mangaListExpected = new HashSet<>();
		mangaListExpected.add(mangaExpected);
		mangaListExpected.add(mangaExpected2);

		String username = "principal";

		User userExpected = User.builder().username(username).firstName("first name").lastName("last name")
				.password("user").email("user@email.com").isEnabled(true).build();

		MangaInUserListStatus statusExpected = MangaInUserListStatus.CURRENTLY_READING;
		MangaInUserListStatus statusNotExpected2 = MangaInUserListStatus.COMPLETED;

		userExpected.addMangaToList(mangaExpected, statusExpected);
		userExpected.addMangaToList(mangaExpected2, statusNotExpected2);

		int statusIntExpected = 0;

		when(userService.loadLoggedInUsername()).thenReturn(userExpected);

		Set<Manga> mangaListActual = mangaInUserListService.getUsersMangaListByStatus(statusIntExpected);

		assertAll(
				() -> assertEquals(1, mangaListActual.size(),
						() -> "should return manga list with one item, but was: " + mangaListActual.size()),
				() -> verify(userService, times(1)).loadLoggedInUsername());
	}

	@Test
	public void when_get_users_manga_list_by_status_when_user_have_empty_list_should_return_empty_list()
			throws IOException, MangaNotFoundException {

		String username = "principal";

		User userExpected = User.builder().username(username).firstName("first name").lastName("last name")
				.password("user").email("user@email.com").isEnabled(true).build();

		int statusIntExpected = 0;

		when(userService.loadLoggedInUsername()).thenReturn(userExpected);

		Set<Manga> mangaListActual = mangaInUserListService.getUsersMangaListByStatus(statusIntExpected);

		assertAll(
				() -> assertTrue(mangaListActual.isEmpty(),
						() -> "should return empty manga list, but was: " + mangaListActual.size()),
				() -> verify(userService, times(1)).loadLoggedInUsername());
	}
}
