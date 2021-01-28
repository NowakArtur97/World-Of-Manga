package com.NowakArtur97.WorldOfManga.feature.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class RoleService {

    private final RoleRepository roleRepository;

    Role findByName(String name) throws RoleNotFoundException {

        return roleRepository.findByName(name)
                .orElseThrow(() -> new RoleNotFoundException("Role with name: " + name + " not found"));
    }
}
