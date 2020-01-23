package com.NowakArtur97.WorldOfManga.service.api;

import com.NowakArtur97.WorldOfManga.exception.RoleNotFoundException;
import com.NowakArtur97.WorldOfManga.model.Role;

public interface RoleService {

	Role findRoleByName(String name) throws RoleNotFoundException;

}
