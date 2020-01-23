package com.NowakArtur97.WorldOfManga.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NowakArtur97.WorldOfManga.model.Role;
import com.NowakArtur97.WorldOfManga.repository.RoleRepository;
import com.NowakArtur97.WorldOfManga.service.api.RoleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleServiceImpl implements RoleService {

	private final RoleRepository roleRepository;

	@Override
	public Optional<Role> findRoleByName(String name) {

		return roleRepository.findRoleByName(name);
	}

}
