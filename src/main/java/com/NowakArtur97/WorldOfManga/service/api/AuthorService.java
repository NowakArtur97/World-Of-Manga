package com.NowakArtur97.WorldOfManga.service.api;

import java.util.List;

import com.NowakArtur97.WorldOfManga.dto.AuthorDTO;
import com.NowakArtur97.WorldOfManga.model.Author;

public interface AuthorService {

	Author addOrUpdate(AuthorDTO authorDTO);

	boolean isAuthorAlreadyInDatabase(String fullName);

	List<Author> findAll();

	Author save(Author author);
}
