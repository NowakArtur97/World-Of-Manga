package com.NowakArtur97.WorldOfManga.feature.author;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class AuthorMapper {

    private final ModelMapper modelMapper;

    Author mapAuthorDTOToAuthor(AuthorDTO authorDTO) {

        return modelMapper.map(authorDTO, Author.class);
    }
}
