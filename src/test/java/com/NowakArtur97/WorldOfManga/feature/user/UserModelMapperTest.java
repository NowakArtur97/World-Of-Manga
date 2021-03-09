package com.NowakArtur97.WorldOfManga.feature.user;

import com.NowakArtur97.WorldOfManga.testUtil.generator.NameWithSpacesGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("User_Tests")
@Tag("Unit_Tests")
@Tag("ModelMapper_Tests")
class UserModelMapperTest {

    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {

        modelMapper = new ModelMapper();
    }

    @Test
    void when_map_user_dto_to_entity_should_return_entity() {

        String username = "username";
        String firstName = "first name";
        String lastName = "last name";
        String password = "password1";
        String email = "user@email.com";

        UserDTO userDTOExpected = UserDTO.builder().username(username).firstName(firstName).lastName(lastName)
                .userPasswordDTO(UserPasswordDTO.builder().password(password).matchingPassword(password).build())
                .email(email).areTermsAccepted(true).build();

        User userExpected = User.builder().username(username).firstName(firstName).lastName(lastName).password(password)
                .email(email).isEnabled(true).build();

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
}
