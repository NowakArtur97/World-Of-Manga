package com.NowakArtur97.WorldOfManga.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NowakArtur97.WorldOfManga.exception.MangaNotFoundException;
import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaRating;
import com.NowakArtur97.WorldOfManga.model.User;
import com.NowakArtur97.WorldOfManga.repository.MangaRatingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MangaRatingServiceImpl implements MangaRatingService {

	private final MangaRatingRepository mangaRatingRepository;

	private final MangaService mangaService;

	private final UserService userService;

	@Override
	public MangaRating findByUserAndManga(User user, Manga manga) {

		return mangaRatingRepository.findByUserAndManga(user, manga).orElseGet(MangaRating::new);
	}

	@Override
	@Transactional
	public MangaRating rateManga(Long mangaId, int rating) throws MangaNotFoundException {

		Manga manga = mangaService.findById(mangaId);

		User user = userService.loadLoggedInUsername();

		MangaRating mangaRating = findByUserAndManga(user, manga);

		if (mangaRating.getRating() == 0) {

			mangaRating = user.addMangaRating(manga, rating);
		} else {

			mangaRating.setRating(rating);
		}

		return mangaRating;
	}
}
