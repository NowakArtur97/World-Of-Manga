package com.NowakArtur97.WorldOfManga.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaRating;
import com.NowakArtur97.WorldOfManga.model.User;
import com.NowakArtur97.WorldOfManga.repository.MangaRatingRepository;
import com.NowakArtur97.WorldOfManga.service.api.MangaRatingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MangaRatingServiceImpl implements MangaRatingService {

	private final MangaRatingRepository mangaRatingRepository;

	@Override
	public MangaRating findByUserAndManga(User user, Manga manga) {

		return mangaRatingRepository.findByUserAndManga(user, manga).orElseGet(MangaRating::new);
	}
}
