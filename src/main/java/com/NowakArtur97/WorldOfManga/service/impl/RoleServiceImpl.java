package com.NowakArtur97.WorldOfManga.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NowakArtur97.WorldOfManga.exception.RoleNotFoundException;
import com.NowakArtur97.WorldOfManga.model.Role;
import com.NowakArtur97.WorldOfManga.repository.RoleRepository;
import com.NowakArtur97.WorldOfManga.service.api.RoleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleServiceImpl implements RoleService {

	private final RoleRepository roleRepository;
	
	@Override
	public Role findRoleByName(String name) throws RoleNotFoundException {

		Role role = roleRepository.findRoleByName(name).orElseThrow(() -> new RoleNotFoundException("Role " + name + " not found"));
		
		return role;
	}

}
