package com.NowakArtur97.WorldOfManga.service.api;

import com.NowakArtur97.WorldOfManga.dto.AuthorDTO;
import com.NowakArtur97.WorldOfManga.model.Author;

public interface AuthorService {

	Author addOrUpdate(AuthorDTO authorDTO);

	boolean isAuthorAlreadyInDatabase(String fullName);
}
