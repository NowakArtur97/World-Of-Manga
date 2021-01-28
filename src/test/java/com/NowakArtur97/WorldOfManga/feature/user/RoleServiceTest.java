package com.NowakArtur97.WorldOfManga.feature.user;

import com.NowakArtur97.WorldOfManga.testUtil.generator.NameWithSpacesGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("RoleService_Tests")
class RoleServiceTest {

    private RoleService roleService;

    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {

        roleService = new RoleService(roleRepository);
    }

    @Test
    void when_find_by_name_should_return_role_by_name() throws RoleNotFoundException {

        String roleName = "ROLE_USER";

        Role roleExpected = Role.builder().name(roleName).build();

        when(roleRepository.findByName(roleName)).thenReturn(Optional.of(roleExpected));

        Role roleActual = roleService.findByName(roleName);

        assertAll(() -> assertEquals(roleExpected.getName(), roleActual.getName(),
                () -> "should return role with name: " + roleExpected.getName() + ", but was: " + roleActual.getName()),
                () -> verify(roleRepository, times(1)).findByName(roleName));
    }

    @Test
    void when_role_not_found_should_throw_exception() {

        String roleName = "UNKNOWN_ROLE";

        Class<RoleNotFoundException> expectedException = RoleNotFoundException.class;

        when(roleRepository.findByName(roleName)).thenReturn(Optional.empty());

        assertAll(
                () -> assertThrows(expectedException, () -> roleService.findByName(roleName),
                        () -> "should throw RoleNotFoundException, but nothing was thrown"),
                () -> verify(roleRepository, times(1)).findByName(roleName));
    }
}
