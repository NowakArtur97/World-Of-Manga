package com.NowakArtur97.WorldOfManga.mapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.NowakArtur97.WorldOfManga.dto.UserDTO;
import com.NowakArtur97.WorldOfManga.dto.UserPasswordDTO;
import com.NowakArtur97.WorldOfManga.model.User;

@ExtendWith(MockitoExtension.class)
@DisplayName("User Mapper Impl Tests")
@Tag("UserMapperImpl_Tests")
public class UserMapperImplTest {

	@InjectMocks
	private UserMapperImpl userMapper;

	@Mock
	private ModelMapper modelMapper;

	@Test
	@DisplayName("when model user dto")
	public void when_model_user_dto_then_return_user_entity() {

		UserDTO userDTOExpected = UserDTO.builder().username("username").firstName("first name").lastName("last name")
				.userPasswordDTO(UserPasswordDTO.builder().password("password1").matchingPassword("password1").build())
				.email("user@email.com").areTermsAccepted(true).build();

		User userExpected = User.builder().username("username").firstName("first name").lastName("last name")
				.password("password1").email("user@email.com").isEnabled(true).build();

		when(userMapper.mapUserDTOToUser(userDTOExpected)).thenReturn(userExpected);
		
		User userActual = userMapper.mapUserDTOToUser(userDTOExpected);

		assertAll(
				() -> assertEquals(userExpected.getUsername(), userActual.getUsername(),
						() -> "should return user with username"),
				() -> assertEquals(userExpected.getFirstName(), userActual.getFirstName(),
						() -> "should return user with first name"),
				() -> assertEquals(userExpected.getLastName(), userActual.getLastName(),
						() -> "should return user with last name"),
				() -> assertEquals(userExpected.getPassword(), userActual.getPassword(),
						() -> "should return user with password"),
				() -> assertEquals(userExpected.getEmail(), userActual.getEmail(),
						() -> "should return user with email"),
				() -> assertEquals(userExpected.isEnabled(), userActual.isEnabled(),
						() -> "should return user which is enabled"),
				() -> verify(modelMapper, times(1)).map(userDTOExpected, User.class));
	}
}
