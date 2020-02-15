package com.NowakArtur97.WorldOfManga.mapper;

import com.NowakArtur97.WorldOfManga.dto.AuthorDTO;
import com.NowakArtur97.WorldOfManga.model.Author;

public interface AuthorMapper {

	Author mapAuthorDTOToAuthor(AuthorDTO authorDTO);
}
