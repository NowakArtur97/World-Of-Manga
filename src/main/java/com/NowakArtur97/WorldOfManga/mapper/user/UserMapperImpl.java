package com.NowakArtur97.WorldOfManga.mapper.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.NowakArtur97.WorldOfManga.dto.UserDTO;
import com.NowakArtur97.WorldOfManga.model.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserMapperImpl implements UserMapper {

	private final ModelMapper modelMapper;
	
	@Override
	public User mapUserDTOToUser(UserDTO userDTO) {

		return modelMapper.map(userDTO, User.class);
	}

}
