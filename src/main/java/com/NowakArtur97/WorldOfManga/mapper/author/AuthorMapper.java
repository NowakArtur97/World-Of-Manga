package com.NowakArtur97.WorldOfManga.mapper.author;

import com.NowakArtur97.WorldOfManga.dto.AuthorDTO;
import com.NowakArtur97.WorldOfManga.model.Author;

public interface AuthorMapper {

	Author mapAuthorDTOToAuthor(AuthorDTO authorDTO);
}
