package com.NowakArtur97.WorldOfManga.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import com.NowakArtur97.WorldOfManga.enums.MangaInUserListStatus;
import com.NowakArtur97.WorldOfManga.model.Author;
import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaInUserList;
import com.NowakArtur97.WorldOfManga.model.MangaTranslation;
import com.NowakArtur97.WorldOfManga.model.User;
import com.NowakArtur97.WorldOfManga.repository.MangaInUserListRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Manga In User List Service Impl Tests")
@Tag("MangaInUserListServiceImpl_Tests")
public class MangaInUserListServiceImplTest {

	@InjectMocks
	private MangaInUserListServiceImpl mangaInUserListService;

	@Mock
	private MangaInUserListRepository mangaInUserListRepository;

	@Test
	@DisplayName("when find existing manga in user list by user and manga")
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
	@DisplayName("when find not existing manga in user list by user and manga")
	public void whne_find_not_existing_manga_ins_user_list_by_user_and_manga_should_return_new_manga_in_list()
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
						() -> "should return empty optional, but was: " + mangaInUserListActual),
				() -> verify(mangaInUserListRepository, times(1)).findByUserAndManga(userExpected, mangaExpected));
	}
}
