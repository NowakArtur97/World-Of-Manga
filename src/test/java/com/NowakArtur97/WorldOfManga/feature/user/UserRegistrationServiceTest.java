package com.NowakArtur97.WorldOfManga.feature.user;

import com.NowakArtur97.WorldOfManga.feature.user.UserDTO;
import com.NowakArtur97.WorldOfManga.feature.user.UserPasswordDTO;
import com.NowakArtur97.WorldOfManga.exception.RoleNotFoundException;
import com.NowakArtur97.WorldOfManga.feature.user.*;
import com.NowakArtur97.WorldOfManga.testUtil.generator.NameWithSpacesGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("UserRegistrationService_Tests")
public class UserRegistrationServiceTest {

    private UserRegistrationService userRegistrationService;

    @Mock
    private RoleService roleService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserService userService;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    void setUp() {

        userRegistrationService = new UserRegistrationService(roleService, userMapper, userService,
                bCryptPasswordEncoder);
    }

    @Test
    public void when_register_user_should_reigster_user() throws RoleNotFoundException {

        String password = "user";

        UserDTO userDTOExpected = UserDTO.builder().username("username").firstName("first name").lastName("last name")
                .userPasswordDTO(UserPasswordDTO.builder().password(password).matchingPassword(password).build())
                .email("user@email.com").areTermsAccepted(true).build();

        User userExpected = User.builder().username("username").firstName("first name").lastName("last name")
                .password(password).email("user@email.com").isEnabled(true).build();

        String roleName = "ROLE_USER";

        Role roleExpected = Role.builder().name(roleName).build();

        String hashedPassword = "$2a$10$FnkNozkIGR1ec3tsByXTG.PjPlu6Ntj7cW.lgPLIFkigHe6jreSw2";

        when(userMapper.mapUserDTOToUser(userDTOExpected)).thenReturn(userExpected);
        when(roleService.findByName(roleName)).thenReturn(roleExpected);
        when(bCryptPasswordEncoder.encode(userExpected.getPassword())).thenReturn(hashedPassword);

        User userActual = userRegistrationService.registerUser(userDTOExpected);

        assertAll(
                () -> assertEquals(userExpected.getUsername(), userActual.getUsername(),
                        () -> "should return user with username: " + userExpected.getUsername() + " but was: "
                                + userActual.getUsername()),
                () -> assertEquals(userExpected.getFirstName(), userActual.getFirstName(),
                        () -> "should return user with first name: " + userExpected.getFirstName() + " but was: "
                                + userActual.getFirstName()),
                () -> assertEquals(userExpected.getLastName(), userActual.getLastName(),
                        () -> "should return user with last name: " + userExpected.getLastName() + " but was: "
                                + userActual.getLastName()),
                () -> assertEquals(userExpected.getPassword(), userActual.getPassword(),
                        () -> "should return user with password: " + userExpected.getPassword() + " but was: "
                                + userActual.getPassword()),
                () -> assertEquals(userExpected.getEmail(), userActual.getEmail(),
                        () -> "should return user with email: " + userExpected.getEmail() + " but was: "
                                + userActual.getEmail()),
                () -> assertEquals(userExpected.isEnabled(), userActual.isEnabled(),
                        () -> "should return user which is enabled: " + userExpected.isEnabled() + " but was: "
                                + userActual.isEnabled()),
                () -> verify(userMapper, times(1)).mapUserDTOToUser(userDTOExpected),
                () -> verify(roleService, times(1)).findByName(roleName),
                () -> verify(bCryptPasswordEncoder, times(1)).encode(password),
                () -> verify(userService, times(1)).save(userActual));
    }
}
