package com.NowakArtur97.WorldOfManga.feature.author;

import com.NowakArtur97.WorldOfManga.dto.AuthorDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorMapper {

    private final ModelMapper modelMapper;

    public Author mapAuthorDTOToAuthor(AuthorDTO authorDTO) {

        return modelMapper.map(authorDTO, Author.class);
    }
}
