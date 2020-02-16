package com.NowakArtur97.WorldOfManga.mapper.user;

import com.NowakArtur97.WorldOfManga.dto.UserDTO;
import com.NowakArtur97.WorldOfManga.model.User;

public interface UserMapper {

	User mapUserDTOToUser(UserDTO userDTO);
}
