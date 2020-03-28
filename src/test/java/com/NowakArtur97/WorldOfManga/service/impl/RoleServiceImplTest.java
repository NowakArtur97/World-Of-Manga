package com.NowakArtur97.WorldOfManga.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.NowakArtur97.WorldOfManga.exception.RoleNotFoundException;
import com.NowakArtur97.WorldOfManga.model.Role;
import com.NowakArtur97.WorldOfManga.repository.RoleRepository;
import com.NowakArtur97.WorldOfManga.testUtil.generator.ReplaceUnderscoresGenerator;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscoresGenerator.class)
@Tag("RoleServiceImpl_Tests")
public class RoleServiceImplTest {

	private RoleServiceImpl roleService;

	@Mock
	private RoleRepository roleRepository;

	@BeforeEach
	void setUp() {

		roleService = new RoleServiceImpl(roleRepository);
	}

	@Test
	public void when_find_by_name_should_return_role_by_name() throws RoleNotFoundException {

		String roleName = "ROLE_USER";

		Role roleExpected = Role.builder().name(roleName).build();

		when(roleRepository.findByName(roleName)).thenReturn(Optional.of(roleExpected));

		Role roleActual = roleService.findByName(roleName);

		assertAll(() -> assertEquals(roleExpected.getName(), roleActual.getName(),
				() -> "should return role with name: " + roleExpected.getName() + ", but was: " + roleActual.getName()),
				() -> verify(roleRepository, times(1)).findByName(roleName));
	}

	@Test
	public void when_role_not_found_should_throw_exception() {

		String roleName = "UNKNOWN_ROLE";

		Class<RoleNotFoundException> expectedException = RoleNotFoundException.class;

		when(roleRepository.findByName(roleName)).thenReturn(Optional.empty());

		assertAll(
				() -> assertThrows(expectedException, () -> roleService.findByName(roleName),
						() -> "should throw RoleNotFoundException, but nothing was thrown"),
				() -> verify(roleRepository, times(1)).findByName(roleName));
	}
}
