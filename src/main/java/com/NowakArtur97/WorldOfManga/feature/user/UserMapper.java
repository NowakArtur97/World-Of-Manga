package com.NowakArtur97.WorldOfManga.feature.user;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class UserMapper {

    private final ModelMapper modelMapper;

    User mapUserDTOToUser(UserDTO userDTO) {

        return modelMapper.map(userDTO, User.class);
    }
}
