package com.NowakArtur97.WorldOfManga.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaGenre;
import com.NowakArtur97.WorldOfManga.model.User;
import com.NowakArtur97.WorldOfManga.service.api.MangaService;
import com.NowakArtur97.WorldOfManga.service.api.RecommendationService;
import com.NowakArtur97.WorldOfManga.service.api.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class RecommendationServiceImpl implements RecommendationService {

	private final MangaService mangaService;

	private final UserService userService;

	@Override
	public List<Manga> recommendManga() {

		List<Manga> allManga = mangaService.findAll();

		List<Manga> recommendations = allManga;

		if (userService.isUserLoggedIn()) {

			User user = userService.loadLoggedInUsername();

			MangaGenre mostLikedGenre = findUsersInterests(user);

//			recommendations = allManga.stream()
//					.filter(manga -> manga.getGenres().contains(mostLikedGenre)
//							&& !user.getFavouriteMangas().contains(manga)
//							&& !user.getMangasRatings().stream().anyMatch(rating -> !rating.getManga().equals(manga)))
//					.collect(Collectors.toList());
			recommendations = allManga.stream().sorted(SORT_MANGA_BY_LIKES).limit(10).collect(Collectors.toList());

		} else {

			recommendations = allManga.stream().sorted(SORT_MANGA_BY_LIKES).limit(10).collect(Collectors.toList());
		}

		return recommendations;
	}

	Comparator<Manga> SORT_MANGA_BY_LIKES = new Comparator<Manga>() {

		@Override
		public int compare(Manga manga1, Manga manga2) {

			return manga2.getUserWithMangaInFavourites().size() - manga1.getUserWithMangaInFavourites().size();
		}

	};

	private MangaGenre findUsersInterests(User user) {

		Set<Manga> usersFavourites = user.getFavouriteMangas();

		return null;
	}
}
