package com.NowakArtur97.WorldOfManga.service.impl;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaGenre;
import com.NowakArtur97.WorldOfManga.model.MangaRating;
import com.NowakArtur97.WorldOfManga.model.User;
import com.NowakArtur97.WorldOfManga.service.api.MangaService;
import com.NowakArtur97.WorldOfManga.service.api.RecommendationService;
import com.NowakArtur97.WorldOfManga.service.api.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
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

			recommendations = recommendations.stream().filter(
					manga -> !user.getMangasRatings().stream().anyMatch(rating -> rating.getManga().equals(manga))
							&& !user.getFavouriteMangas().contains(manga))
					.collect(Collectors.toList());

			if (mostLikedGenre != null) {

				recommendations = recommendations.stream().filter(manga -> manga.getGenres().contains(mostLikedGenre))
						.collect(Collectors.toList());
			}
		}
		return recommendations.stream().sorted(SORT_MANGA_BY_LIKES).limit(10).collect(Collectors.toList());
	}

	Comparator<Manga> SORT_MANGA_BY_LIKES = new Comparator<Manga>() {

		@Override
		public int compare(Manga manga1, Manga manga2) {

			return manga2.getUserWithMangaInFavourites().size() - manga1.getUserWithMangaInFavourites().size();
		}
	};

	private MangaGenre findUsersInterests(User user) {

		Set<Manga> usersFavourites = user.getFavouriteMangas();
		Set<MangaRating> usersRatedList = user.getMangasRatings();

		Map<MangaGenre, Integer> genreGroups = new HashMap<>();

		for (Manga manga : usersFavourites) {
			for (MangaGenre genre : manga.getGenres()) {
				Integer occurrence = genreGroups.get(genre);
				genreGroups.put(genre, occurrence == null ? 1 : occurrence + 1);
			}
		}

		for (MangaRating rating : usersRatedList) {
			for (MangaGenre genre : rating.getManga().getGenres()) {
				Integer occurrence = genreGroups.get(genre);
				genreGroups.put(genre, occurrence == null ? 1 : occurrence + 1);
			}
		}

		Entry<MangaGenre, Integer> mostOccurrences = null;

		for (Entry<MangaGenre, Integer> entry : genreGroups.entrySet()) {
			if (mostOccurrences == null || entry.getValue() > mostOccurrences.getValue()) {
				mostOccurrences = entry;
			}
		}

		return mostOccurrences != null ? mostOccurrences.getKey() : null;
	}
}
