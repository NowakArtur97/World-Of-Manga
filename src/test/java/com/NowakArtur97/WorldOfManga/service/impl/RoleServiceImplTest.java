package com.NowakArtur97.WorldOfManga.service.impl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.NowakArtur97.WorldOfManga.model.Role;
import com.NowakArtur97.WorldOfManga.repository.RoleRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Role Service Impl Tests")
@Tag("RoleServiceImpl_Tests")
public class RoleServiceImplTest {

	@InjectMocks
	private RoleServiceImpl roleService;

	@Mock
	private RoleRepository roleRepository;

	@Test
	@DisplayName("when find by name")
	public void when_find_by_name_should_return_role_by_name() {

		String roleName = "ROLE_USER";

		Role roleExpected = Role.builder().name(roleName).build();

		when(roleRepository.findByName(roleName)).thenReturn(Optional.of(roleExpected));

		Role roleActual = roleService.findByName(roleName).get();

		assertAll(() -> assertEquals(roleExpected.getName(), roleActual.getName(), () -> "should return role by name"),
				() -> verify(roleRepository, times(1)).findByName(roleName));

	}
}
