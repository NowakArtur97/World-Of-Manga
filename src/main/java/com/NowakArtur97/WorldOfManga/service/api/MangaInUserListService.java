package com.NowakArtur97.WorldOfManga.service.api;

import java.util.Optional;

import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaInUserList;
import com.NowakArtur97.WorldOfManga.model.User;

public interface MangaInUserListService {

	Optional<MangaInUserList> findByUserAndManga(User user, Manga manga);
}
