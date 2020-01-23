package com.NowakArtur97.WorldOfManga.service.api;

import java.util.Optional;

import com.NowakArtur97.WorldOfManga.model.Role;

public interface RoleService {

	Optional<Role> findRoleByName(String name);
}
