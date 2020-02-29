package com.NowakArtur97.WorldOfManga.service.api;

import com.NowakArtur97.WorldOfManga.exception.MangaNotFoundException;
import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaRating;
import com.NowakArtur97.WorldOfManga.model.User;

public interface MangaRatingService {

	MangaRating findByUserAndManga(User user, Manga manga);

	MangaRating rateManga(Long mangaId, int rating) throws MangaNotFoundException;
}
