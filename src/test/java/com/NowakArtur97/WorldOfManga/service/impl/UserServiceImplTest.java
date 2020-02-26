package com.NowakArtur97.WorldOfManga.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.NowakArtur97.WorldOfManga.enums.MangaInUserListStatus;
import com.NowakArtur97.WorldOfManga.exception.MangaNotFoundException;
import com.NowakArtur97.WorldOfManga.model.Author;
import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaInUserList;
import com.NowakArtur97.WorldOfManga.model.MangaRating;
import com.NowakArtur97.WorldOfManga.model.MangaTranslation;
import com.NowakArtur97.WorldOfManga.model.User;
import com.NowakArtur97.WorldOfManga.repository.UserRepository;
import com.NowakArtur97.WorldOfManga.service.api.MangaInUserListService;
import com.NowakArtur97.WorldOfManga.service.api.MangaRatingService;
import com.NowakArtur97.WorldOfManga.service.api.MangaService;

@ExtendWith(MockitoExtension.class)
@DisplayName("User Service Impl Tests")
@Tag("UserServiceImpl_Tests")
public class UserServiceImplTest {

	@InjectMocks
	private UserServiceImpl userService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private MangaService mangaService;

	@Mock
	private MangaRatingService mangaRatingService;

	@Mock
	private MangaInUserListService mangaInUserListService;

	@Mock
	private Authentication authentication;

	@Mock
	private SecurityContext securityContext;

	@Mock
	private Principal principal;

	@Nested
	@DisplayName("User Service Integration Tests")
	@Tag("UserServiceIntegration_Tests")
	class UserServiceIntegrationTest {

		@Test
		@DisplayName("when find by username")
		public void when_find_by_username_should_return_user_by_username() {

			String username = "user";

			User userExpected = User.builder().username(username).firstName("first name").lastName("last name")
					.password("password1").email("user@email.com").isEnabled(true).build();

			when(userRepository.findByUsername(username)).thenReturn(Optional.of(userExpected));

			User userActual = userService.findByUsername(username).get();

			assertAll(
					() -> assertEquals(userExpected.getUsername(), userActual.getUsername(),
							() -> "should return user with username: " + userExpected.getUsername() + ", but was: "
									+ userActual.getUsername()),
					() -> assertEquals(userExpected.getFirstName(), userActual.getFirstName(),
							() -> "should return user with first name: " + userExpected.getFirstName() + ", but was: "
									+ userActual.getFirstName()),
					() -> assertEquals(userExpected.getLastName(), userActual.getLastName(),
							() -> "should return user with last name: " + userExpected.getLastName() + ", but was: "
									+ userActual.getLastName()),
					() -> assertEquals(userExpected.getPassword(), userActual.getPassword(),
							() -> "should return user with password: " + userExpected.getPassword() + ", but was: "
									+ userActual.getPassword()),
					() -> assertEquals(userExpected.getEmail(), userActual.getEmail(),
							() -> "should return user with email: " + userExpected.getEmail() + ", but was: "
									+ userActual.getEmail()),
					() -> assertEquals(userExpected.isEnabled(), userActual.isEnabled(),
							() -> "should return user which is enabled: " + userExpected.isEnabled() + ", but was: "
									+ userActual.isEnabled()),
					() -> verify(userRepository, times(1)).findByUsername(username));
		}

		@Test
		@DisplayName("when find not existsing user by username")
		public void when_find_not_existing_user_by_username_should_return_empty_optional() {

			String username = "user";

			when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

			Optional<User> userActual = userService.findByUsername(username);

			assertAll(
					() -> assertTrue(userActual.isEmpty(),
							() -> "should return empty Optional, but was: " + userActual),
					() -> verify(userRepository, times(1)).findByUsername(username));
		}

		@Test
		@DisplayName("when username is already in use")
		public void when_username_is_already_in_use_should_return_true() {

			boolean isUsernameInUse = true;

			String username = "username";

			when(userRepository.existsUserByUsername(username)).thenReturn(isUsernameInUse);

			boolean isUsernameInUseActual = userService.isUsernameAlreadyInUse(username);

			assertAll(
					() -> assertTrue(isUsernameInUseActual,
							() -> "should return true, but was: " + isUsernameInUseActual),
					() -> verify(userRepository, times(1)).existsUserByUsername(username));
		}

		@Test
		@DisplayName("when username isn`t in use")
		public void when_username_is_not_in_use_should_return_false() {

			boolean isUsernameInUse = false;

			String username = "username";

			when(userRepository.existsUserByUsername(username)).thenReturn(isUsernameInUse);

			boolean isUsernameInUseActual = userService.isUsernameAlreadyInUse(username);

			assertAll(
					() -> assertFalse(isUsernameInUseActual,
							() -> "should return false, but was: " + isUsernameInUseActual),
					() -> verify(userRepository, times(1)).existsUserByUsername(username));
		}

		@Test
		@DisplayName("when email is already in use")
		public void when_email_is_already_in_use_should_return_true() {

			boolean isEmailInUse = true;

			String email = "username@email.com";

			when(userRepository.existsUserByEmail(email)).thenReturn(isEmailInUse);

			boolean isEmailInUseActual = userService.isEmailAlreadyInUse(email);

			assertAll(() -> assertTrue(isEmailInUseActual, () -> "should return true, but was: " + isEmailInUseActual),
					() -> verify(userRepository, times(1)).existsUserByEmail(email));
		}

		@Test
		@DisplayName("when email isn`t in use")
		public void when_email_is_not_in_use_should_return_false() {

			boolean isEmailInUse = false;

			String email = "username@email.com";

			when(userRepository.existsUserByEmail(email)).thenReturn(isEmailInUse);

			boolean isEmailInUseActual = userService.isEmailAlreadyInUse(email);

			assertAll(
					() -> assertFalse(isEmailInUseActual, () -> "should return false, but was: " + isEmailInUseActual),
					() -> verify(userRepository, times(1)).existsUserByEmail(email));
		}

		@Test
		@DisplayName("when save user")
		public void when_save_user_should_save_and_return_user() {

			User userExpected = User.builder().username("username").firstName("first name").lastName("last name")
					.password("password1").email("user@email.com").isEnabled(true).build();

			when(userRepository.save(userExpected)).thenReturn(userExpected);

			User userActual = userService.save(userExpected);

			assertAll(
					() -> assertEquals(userExpected.getUsername(), userActual.getUsername(),
							() -> "should return user with username: " + userExpected.getUsername() + ", but was: "
									+ userActual.getUsername()),
					() -> assertEquals(userExpected.getFirstName(), userActual.getFirstName(),
							() -> "should return user with first name: " + userExpected.getFirstName() + ", but was: "
									+ userActual.getFirstName()),
					() -> assertEquals(userExpected.getLastName(), userActual.getLastName(),
							() -> "should return user with last name: " + userExpected.getLastName() + ", but was: "
									+ userActual.getLastName()),
					() -> assertEquals(userExpected.getPassword(), userActual.getPassword(),
							() -> "should return user with password: " + userExpected.getPassword() + ", but was: "
									+ userActual.getPassword()),
					() -> assertEquals(userExpected.getEmail(), userActual.getEmail(),
							() -> "should return user with email: " + userExpected.getEmail() + ", but was: "
									+ userActual.getEmail()),
					() -> assertEquals(userExpected.isEnabled(), userActual.isEnabled(),
							() -> "should return user which is enabled: " + userExpected.isEnabled() + ", but was: "
									+ userActual.isEnabled()),
					() -> verify(userRepository, times(1)).save(userExpected));
		}

		@Test
		@DisplayName("when rate manga for first time")
		public void when_rate_manga_for_first_time_should_update_rating() throws IOException, MangaNotFoundException {

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

			int ratingExpected = 5;

			MangaRating mangaRatingExpected = new MangaRating();

			SecurityContextHolder.setContext(securityContext);
			when(mangaService.findById(mangaId)).thenReturn(mangaExpected);
			when(mangaRatingService.findByUserAndManga(userExpected, mangaExpected)).thenReturn(mangaRatingExpected);
			when(securityContext.getAuthentication()).thenReturn(authentication);
			when(authentication.getPrincipal()).thenReturn(principal);
			when(userRepository.findByUsername(username)).thenReturn(Optional.of(userExpected));

			MangaRating mangaRatingActual = userService.rateManga(mangaId, ratingExpected);

			assertAll(
					() -> assertEquals(mangaExpected, mangaRatingActual.getManga(),
							() -> "should return manga rating with manga: " + mangaExpected + ", but was: "
									+ mangaRatingActual.getManga()),
					() -> assertEquals(userExpected, mangaRatingActual.getUser(),
							() -> "should return manga rating with user: " + userExpected + ", but was: "
									+ mangaRatingActual.getUser()),
					() -> assertEquals(ratingExpected, mangaRatingActual.getRating(),
							() -> "should return manga rating with rating: " + mangaRatingExpected.getRating()
									+ ", but was: " + mangaRatingActual.getRating()),
					() -> verify(mangaService, times(1)).findById(mangaId),
					() -> verify(mangaRatingService, times(1)).findByUserAndManga(userExpected, mangaExpected),
					() -> verify(userRepository, times(1)).findByUsername(username),
					() -> verify(securityContext, times(1)).getAuthentication(),
					() -> verify(authentication, times(1)).getPrincipal());
		}

		@Test
		@DisplayName("when rate manga for another time")
		public void when_rate_manga_for_another_time_should_update_rating() throws IOException, MangaNotFoundException {

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

			int ratingExpected = 5;

			MangaRating mangaRatingExpected = MangaRating.builder().manga(mangaExpected).user(userExpected)
					.rating(ratingExpected).build();

			SecurityContextHolder.setContext(securityContext);
			when(mangaService.findById(mangaId)).thenReturn(mangaExpected);
			when(mangaRatingService.findByUserAndManga(userExpected, mangaExpected)).thenReturn(mangaRatingExpected);
			when(securityContext.getAuthentication()).thenReturn(authentication);
			when(authentication.getPrincipal()).thenReturn(principal);
			when(userRepository.findByUsername(username)).thenReturn(Optional.of(userExpected));

			MangaRating mangaRatingActual = userService.rateManga(mangaId, ratingExpected);

			assertAll(
					() -> assertEquals(mangaRatingExpected.getManga(), mangaRatingActual.getManga(),
							() -> "should return manga rating with manga: " + mangaRatingExpected.getManga()
									+ ", but was: " + mangaRatingActual.getManga()),
					() -> assertEquals(mangaRatingExpected.getUser(), mangaRatingActual.getUser(),
							() -> "should return manga rating with user: " + mangaRatingExpected.getUser()
									+ ", but was: " + mangaRatingActual.getUser()),
					() -> assertEquals(mangaRatingExpected.getRating(), mangaRatingActual.getRating(),
							() -> "should return manga rating with rating: " + mangaRatingExpected.getRating()
									+ ", but was: " + mangaRatingActual.getRating()),
					() -> verify(mangaService, times(1)).findById(mangaId),
					() -> verify(mangaRatingService, times(1)).findByUserAndManga(userExpected, mangaExpected),
					() -> verify(userRepository, times(1)).findByUsername(username),
					() -> verify(securityContext, times(1)).getAuthentication(),
					() -> verify(authentication, times(1)).getPrincipal());
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

			String username = "principal";

			User userExpected = User.builder().username(username).firstName("first name").lastName("last name")
					.password("user").email("user@email.com").isEnabled(true).build();

			SecurityContextHolder.setContext(securityContext);
			when(mangaService.findById(mangaId)).thenReturn(mangaExpected);
			when(securityContext.getAuthentication()).thenReturn(authentication);
			when(authentication.getPrincipal()).thenReturn(principal);
			when(userRepository.findByUsername(username)).thenReturn(Optional.of(userExpected));

			Manga mangaActual = userService.addOrRemoveFromFavourites(mangaId);

			assertAll(
					() -> assertEquals(mangaExpected, mangaActual,
							() -> "should return added to list manga: " + mangaExpected + ", but was: " + mangaActual),
					() -> assertTrue(userExpected.getFavouriteMangas().contains(mangaActual),
							() -> "should manga be in users favourites but wasn`t: "
									+ userExpected.getFavouriteMangas()),
					() -> assertTrue(mangaActual.getUserWithMangaInFavourites().contains(userExpected),
							() -> "should user be one of the people with the manga in favorites but wasn`t: "
									+ userExpected.getFavouriteMangas()),
					() -> verify(mangaService, times(1)).findById(mangaId),
					() -> verify(userRepository, times(1)).findByUsername(username),
					() -> verify(securityContext, times(1)).getAuthentication(),
					() -> verify(authentication, times(1)).getPrincipal());
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

			String username = "principal";

			User userExpected = User.builder().username(username).firstName("first name").lastName("last name")
					.password("user").email("user@email.com").isEnabled(true).build();

			userExpected.addMangaToFavourites(mangaExpected);

			SecurityContextHolder.setContext(securityContext);
			when(mangaService.findById(mangaId)).thenReturn(mangaExpected);
			when(securityContext.getAuthentication()).thenReturn(authentication);
			when(authentication.getPrincipal()).thenReturn(principal);
			when(userRepository.findByUsername(username)).thenReturn(Optional.of(userExpected));

			Manga mangaActual = userService.addOrRemoveFromFavourites(mangaId);

			assertAll(
					() -> assertEquals(mangaExpected, mangaActual,
							() -> "should return remove from list manga: " + mangaExpected + ", but was: "
									+ mangaActual),
					() -> assertFalse(userExpected.getFavouriteMangas().contains(mangaActual),
							() -> "shouldn`t manga be in users favourites but was: "
									+ userExpected.getFavouriteMangas()),
					() -> assertFalse(mangaActual.getUserWithMangaInFavourites().contains(userExpected),
							() -> "shouldn`t  user be one of the people with the manga in favorites but was: "
									+ userExpected.getFavouriteMangas()),
					() -> verify(mangaService, times(1)).findById(mangaId),
					() -> verify(userRepository, times(1)).findByUsername(username),
					() -> verify(securityContext, times(1)).getAuthentication(),
					() -> verify(authentication, times(1)).getPrincipal());
		}

		@Test
		@DisplayName("when add manga to list for first time")
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

			Optional<MangaInUserList> mangaInUserListExpected = Optional.empty();

			SecurityContextHolder.setContext(securityContext);
			when(mangaService.findById(mangaId)).thenReturn(mangaExpected);
			when(mangaInUserListService.findByUserAndManga(userExpected, mangaExpected))
					.thenReturn(mangaInUserListExpected);
			when(securityContext.getAuthentication()).thenReturn(authentication);
			when(authentication.getPrincipal()).thenReturn(principal);
			when(userRepository.findByUsername(username)).thenReturn(Optional.of(userExpected));

			MangaInUserList mangaInUserListActual = userService.addToList(mangaId, statusIntExpected);

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
					() -> verify(mangaInUserListService, times(1)).findByUserAndManga(userExpected, mangaExpected),
					() -> verify(userRepository, times(1)).findByUsername(username),
					() -> verify(securityContext, times(1)).getAuthentication(),
					() -> verify(authentication, times(1)).getPrincipal());
		}

		@Test
		@DisplayName("when add manga to list for another time")
		public void when_add_manga_to_list_for_another_time_should_update_status()
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

			MangaInUserListStatus statusExpected = MangaInUserListStatus.COMPLETED;

			MangaInUserList mangaInUserListExpected = MangaInUserList.builder().manga(mangaExpected).user(userExpected)
					.status(statusExpected).build();

			SecurityContextHolder.setContext(securityContext);
			when(mangaService.findById(mangaId)).thenReturn(mangaExpected);
			when(mangaInUserListService.findByUserAndManga(userExpected, mangaExpected))
					.thenReturn(Optional.of(mangaInUserListExpected));
			when(securityContext.getAuthentication()).thenReturn(authentication);
			when(authentication.getPrincipal()).thenReturn(principal);
			when(userRepository.findByUsername(username)).thenReturn(Optional.of(userExpected));

			MangaInUserList mangaInUserListActual = userService.addToList(mangaId, statusIntExpected);

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
					() -> verify(mangaService, times(1)).findById(mangaId),
					() -> verify(mangaInUserListService, times(1)).findByUserAndManga(userExpected, mangaExpected),
					() -> verify(userRepository, times(1)).findByUsername(username),
					() -> verify(securityContext, times(1)).getAuthentication(),
					() -> verify(authentication, times(1)).getPrincipal());
		}

		@Test
		@DisplayName("when get users manga list by status")
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

			SecurityContextHolder.setContext(securityContext);
			when(securityContext.getAuthentication()).thenReturn(authentication);
			when(authentication.getPrincipal()).thenReturn(principal);
			when(userRepository.findByUsername(username)).thenReturn(Optional.of(userExpected));

			Set<Manga> mangaListActual = userService.getUsersMangaListByStatus(statusIntExpected);

			assertAll(
					() -> assertEquals(1, mangaListActual.size(),
							() -> "should return manga list with one item, but was: " + mangaListActual.size()),
					() -> verify(userRepository, times(1)).findByUsername(username),
					() -> verify(securityContext, times(1)).getAuthentication(),
					() -> verify(authentication, times(1)).getPrincipal());
		}

		@Test
		@DisplayName("when get users manga list by status when user have empty list")
		public void when_get_users_manga_list_by_status_when_user_have_empty_list_should_return_empty_list()
				throws IOException, MangaNotFoundException {

			String username = "principal";

			User userExpected = User.builder().username(username).firstName("first name").lastName("last name")
					.password("user").email("user@email.com").isEnabled(true).build();

			int statusIntExpected = 0;

			SecurityContextHolder.setContext(securityContext);
			when(securityContext.getAuthentication()).thenReturn(authentication);
			when(authentication.getPrincipal()).thenReturn(principal);
			when(userRepository.findByUsername(username)).thenReturn(Optional.of(userExpected));

			Set<Manga> mangaListActual = userService.getUsersMangaListByStatus(statusIntExpected);

			assertAll(
					() -> assertTrue(mangaListActual.isEmpty(),
							() -> "should return empty manga list, but was: " + mangaListActual.size()),
					() -> verify(userRepository, times(1)).findByUsername(username),
					() -> verify(securityContext, times(1)).getAuthentication(),
					() -> verify(authentication, times(1)).getPrincipal());
		}
	}

	@Nested
	@DisplayName("User Details Integration Tests")
	@Tag("UserDetailsIntegration_Tests")
	class UserDetailsIntegrationTest {

		@Test
		@DisplayName("when load user details by username")
		public void when_load_user_details_by_username_should_return_user_details() {

			String username = "user";

			User userExpected = User.builder().username(username).firstName("first name").lastName("last name")
					.password("password1").email("user@email.com").isEnabled(true).build();

			boolean accountNonExpired = true;
			boolean credentialsNonExpired = true;
			boolean accountNonLocked = true;

			UserDetails userDetailsExpected = new org.springframework.security.core.userdetails.User(
					userExpected.getUsername(), userExpected.getPassword(), userExpected.isEnabled(), accountNonExpired,
					credentialsNonExpired, accountNonLocked, new ArrayList<SimpleGrantedAuthority>());

			when(userRepository.findByUsername(username)).thenReturn(Optional.of(userExpected));

			UserDetails userDetailsActual = userService.loadUserByUsername(username);

			assertAll(
					() -> assertEquals(userDetailsExpected.getUsername(), userDetailsActual.getUsername(),
							() -> "should return user details with username: " + userDetailsExpected.getUsername()
									+ ", but was: " + userDetailsActual.getUsername()),
					() -> assertEquals(userDetailsExpected.getPassword(), userDetailsActual.getPassword(),
							() -> "should return user details with password: " + userDetailsExpected.getPassword()
									+ ", but was: " + userDetailsActual.getPassword()),
					() -> assertEquals(userDetailsExpected.getAuthorities(), userDetailsActual.getAuthorities(),
							() -> "should return user details with authorities: " + userDetailsExpected.getAuthorities()
									+ ", but was: " + userDetailsActual.getAuthorities()),
					() -> assertEquals(userDetailsExpected.isEnabled(), userDetailsActual.isEnabled(),
							() -> "should return user details which is enabled: " + userDetailsExpected.isEnabled()
									+ ", but was: " + userDetailsActual.isEnabled()),
					() -> verify(userRepository, times(1)).findByUsername(username));
		}

		@Test
		@DisplayName("when load logged in user")
		public void when_load_logged_in_user_should_return_user() {

			String username = "principal";

			User userExpected = User.builder().username(username).firstName("first name").lastName("last name")
					.password("password1").email("user@email.com").isEnabled(true).build();

			when(securityContext.getAuthentication()).thenReturn(authentication);
			when(authentication.getPrincipal()).thenReturn(principal);
			when(userRepository.findByUsername(username)).thenReturn(Optional.of(userExpected));

			SecurityContextHolder.setContext(securityContext);

			User userActual = userService.loadLoggedInUsername();

			assertAll(
					() -> assertEquals(userExpected.getUsername(), userActual.getUsername(),
							() -> "should return user with username: " + userExpected.getUsername() + ", but was: "
									+ userActual.getUsername()),
					() -> assertEquals(userExpected.getFirstName(), userActual.getFirstName(),
							() -> "should return user with first name: " + userExpected.getFirstName() + ", but was: "
									+ userActual.getFirstName()),
					() -> assertEquals(userExpected.getLastName(), userActual.getLastName(),
							() -> "should return user with last name: " + userExpected.getLastName() + ", but was: "
									+ userActual.getLastName()),
					() -> assertEquals(userExpected.getPassword(), userActual.getPassword(),
							() -> "should return user with password: " + userExpected.getPassword() + ", but was: "
									+ userActual.getPassword()),
					() -> assertEquals(userExpected.getEmail(), userActual.getEmail(),
							() -> "should return user with email: " + userExpected.getEmail() + ", but was: "
									+ userActual.getEmail()),
					() -> assertEquals(userExpected.isEnabled(), userActual.isEnabled(),
							() -> "should return user which is enabled: " + userExpected.isEnabled() + ", but was: "
									+ userActual.isEnabled()),
					() -> verify(securityContext, times(1)).getAuthentication(),
					() -> verify(authentication, times(1)).getPrincipal(),
					() -> verify(userRepository, times(1)).findByUsername(username));
		}

		@Test
		@DisplayName("when load logged in user when isn`t logged")
		public void when_load_logged_in_user_when_isn_not_logged_should_return_user() {

			String username = "user not exists";

			Class<UsernameNotFoundException> expectedException = UsernameNotFoundException.class;

			when(userRepository.findByUsername(username)).thenThrow(expectedException);

			assertAll(
					() -> assertThrows(expectedException, () -> userService.loadUserByUsername(username),
							() -> "should throw UsernameNotFoundException, but nothing was thrown"),
					() -> verify(userRepository, times(1)).findByUsername(username));
		}

		@Test
		@DisplayName("when load user details by username doesn`t find user")
		public void when_load_user_details_by_username_does_not_find_user_should_throw_UsernameNotFoundException() {

			String username = "user";

			Class<UsernameNotFoundException> expectedException = UsernameNotFoundException.class;

			when(userRepository.findByUsername(username)).thenThrow(expectedException);

			assertAll(
					() -> assertThrows(expectedException, () -> userService.loadUserByUsername(username),
							() -> "should throw UsernameNotFoundException, but nothing was thrown"),
					() -> verify(userRepository, times(1)).findByUsername(username));
		}
	}
}
