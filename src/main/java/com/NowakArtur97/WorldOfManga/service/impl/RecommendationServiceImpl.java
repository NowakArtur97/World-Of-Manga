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

	private final Comparator<Manga> SORT_BY_LIKES = new Comparator<Manga>() {

		@Override
		public int compare(Manga manga1, Manga manga2) {

			return manga2.getUserWithMangaInFavourites().size() - manga1.getUserWithMangaInFavourites().size();
		}
	};

	@Override
	public List<Manga> recommendManga() {

		List<Manga> allManga = mangaService.findAll();

		List<Manga> recommendations = allManga;

		if (userService.isUserLoggedIn()) {

			recommendations = recommendMangaForLoggedInUser(recommendations);
		}

		return recommendations.stream().sorted(SORT_BY_LIKES).limit(10).collect(Collectors.toList());
	}

	private List<Manga> recommendMangaForLoggedInUser(List<Manga> recommendations) {

		User user = userService.loadLoggedInUsername();

		MangaGenre mostLikedGenre = findUsersInterests(user);

		recommendations = removeMangaThatIsAlreadyInUserList(recommendations, user);

		if (mostLikedGenre != null) {

			recommendations = recommendMangaByGenre(recommendations, mostLikedGenre);
		}

		return recommendations;
	}

	private MangaGenre findUsersInterests(User user) {

		Set<Manga> usersFavourites = user.getFavouriteMangas();
		Set<MangaRating> usersRatedList = user.getMangasRatings();

		Map<MangaGenre, Integer> genreGroups = new HashMap<>();

		for (Manga manga : usersFavourites) {
			groupMangaByGenre(genreGroups, manga.getGenres());
		}

		for (MangaRating rating : usersRatedList) {
			groupMangaByGenre(genreGroups, rating.getManga().getGenres());
		}

		Entry<MangaGenre, Integer> mostOccurrences = findMostCommonGenre(genreGroups);

		return mostOccurrences != null ? mostOccurrences.getKey() : null;
	}

	private List<Manga> removeMangaThatIsAlreadyInUserList(List<Manga> recommendations, User user) {

		return recommendations.stream()
				.filter(manga -> !user.getMangasRatings().stream().anyMatch(rating -> rating.getManga().equals(manga))
						&& !user.getFavouriteMangas().contains(manga))
				.collect(Collectors.toList());
	}

	private List<Manga> recommendMangaByGenre(List<Manga> recommendations, MangaGenre mostLikedGenre) {

		return recommendations.stream().filter(manga -> manga.getGenres().contains(mostLikedGenre))
				.collect(Collectors.toList());
	}

	private void groupMangaByGenre(Map<MangaGenre, Integer> genreGroups, Set<MangaGenre> genres) {

		for (MangaGenre genre : genres) {
			Integer occurrence = genreGroups.get(genre);
			genreGroups.put(genre, occurrence == null ? 1 : occurrence + 1);
		}
	}

	private Entry<MangaGenre, Integer> findMostCommonGenre(Map<MangaGenre, Integer> genreGroups) {

		Entry<MangaGenre, Integer> mostOccurrences = null;

		for (Entry<MangaGenre, Integer> entry : genreGroups.entrySet()) {
			if (mostOccurrences == null || entry.getValue() > mostOccurrences.getValue()) {
				mostOccurrences = entry;
			}
		}

		return mostOccurrences;
	}
}
