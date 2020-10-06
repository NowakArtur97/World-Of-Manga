package com.NowakArtur97.WorldOfManga.service;

import com.NowakArtur97.WorldOfManga.dto.UserDTO;
import com.NowakArtur97.WorldOfManga.exception.RoleNotFoundException;
import com.NowakArtur97.WorldOfManga.mapper.user.UserMapper;
import com.NowakArtur97.WorldOfManga.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {

    private final RoleService roleService;

    private final UserMapper userMapper;

    private final UserService userService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserRegistrationService(RoleService roleService, UserMapper userMapper, UserService userService,
                                   BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.roleService = roleService;
        this.userMapper = userMapper;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User registerUser(UserDTO userDTO) throws RoleNotFoundException {

        User userToRegister = userMapper.mapUserDTOToUser(userDTO);

        userToRegister.addRole(roleService.findByName("ROLE_USER"));
        userToRegister.setEnabled(true);

        String hashedPassword = bCryptPasswordEncoder.encode(userToRegister.getPassword());
        userToRegister.setPassword(hashedPassword);

        userService.save(userToRegister);

        return userToRegister;
    }
}
