package com.NowakArtur97.WorldOfManga.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NowakArtur97.WorldOfManga.exception.RoleNotFoundException;
import com.NowakArtur97.WorldOfManga.model.Role;
import com.NowakArtur97.WorldOfManga.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleServiceImpl implements RoleService {

	private final RoleRepository roleRepository;

	@Override
	public Role findByName(String name) throws RoleNotFoundException {

		return roleRepository.findByName(name)
				.orElseThrow(() -> new RoleNotFoundException("Role with name: " + name + " not found"));
	}
}
