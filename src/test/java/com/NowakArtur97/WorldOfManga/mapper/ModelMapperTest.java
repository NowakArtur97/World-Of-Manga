package com.NowakArtur97.WorldOfManga.mapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.NowakArtur97.WorldOfManga.dto.MangaTranslationDTO;
import com.NowakArtur97.WorldOfManga.dto.UserDTO;
import com.NowakArtur97.WorldOfManga.dto.UserPasswordDTO;
import com.NowakArtur97.WorldOfManga.model.MangaTranslation;
import com.NowakArtur97.WorldOfManga.model.User;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Model Mapper Tests")
@Tag("ModelMapper_Tests")
public class ModelMapperTest {

	@Autowired
	private ModelMapper modelMapper;

	@Test
	@DisplayName("when map user dto to entity")
	public void when_map_user_dto_to_entity_should_return_entity() {

		UserDTO userDTOExpected = UserDTO.builder().username("username").firstName("first name").lastName("last name")
				.userPasswordDTO(UserPasswordDTO.builder().password("password1").matchingPassword("password1").build())
				.email("user@email.com").areTermsAccepted(true).build();

		User userExpected = User.builder().username("username").firstName("first name").lastName("last name")
				.password("password1").email("user@email.com").isEnabled(true).build();

		User userActual = modelMapper.map(userDTOExpected, User.class);

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
								+ userActual.getEmail()));
	}

	@Test
	@DisplayName("when map manga translation dto to entity")
	public void when_map_manga_translation_dto_to_entity_should_return_entity() {

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
