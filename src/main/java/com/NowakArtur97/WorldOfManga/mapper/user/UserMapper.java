package com.NowakArtur97.WorldOfManga.mapper.user;

import com.NowakArtur97.WorldOfManga.dto.UserDTO;
import com.NowakArtur97.WorldOfManga.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    public User mapUserDTOToUser(UserDTO userDTO) {

        return modelMapper.map(userDTO, User.class);
    }

}
