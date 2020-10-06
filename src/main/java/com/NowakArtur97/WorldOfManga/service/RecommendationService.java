package com.NowakArtur97.WorldOfManga.service;

import com.NowakArtur97.WorldOfManga.feature.user.User;
import com.NowakArtur97.WorldOfManga.feature.user.UserService;
import com.NowakArtur97.WorldOfManga.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final int numberOfRecommendedManga = 10;

    private final MangaService mangaService;

    private final UserService userService;

    private final Comparator<Manga> SORT_BY_LIKES = (manga1, manga2) -> manga2.getUserWithMangaInFavourites().size()
            - manga1.getUserWithMangaInFavourites().size();

    public List<Manga> recommendManga() {

        List<Manga> allManga = mangaService.findAll();

        List<Manga> recommendations = allManga;

        if (userService.isUserLoggedIn()) {

            recommendations = recommendMangaForLoggedInUser(recommendations, allManga);
        }

        return recommendations.stream().sorted(SORT_BY_LIKES).limit(numberOfRecommendedManga)
                .collect(Collectors.toList());
    }

    private List<Manga> recommendMangaForLoggedInUser(List<Manga> recommendations, List<Manga> allManga) {

        User user = userService.loadLoggedInUsername();

        MangaGenre mostLikedGenre = findUsersInterests(user);

        recommendations = removeMangaThatIsAlreadyInUserList(recommendations, user);

        if (mostLikedGenre != null) {

            recommendations = recommendMangaByGenre(recommendations, mostLikedGenre);
        }

        if (recommendations.size() < numberOfRecommendedManga) {

            recommendations = findMoreRecommendations(user, recommendations, allManga);
        }

        return recommendations;
    }

    private MangaGenre findUsersInterests(User user) {

        Set<Manga> usersFavourites = user.getFavouriteMangas();
        Set<MangaRating> usersRatedList = user.getMangasRatings();
        Set<MangaInUserList> usersMangaList = user.getMangaList();

        Map<MangaGenre, Integer> genreGroups = new HashMap<>();

        for (Manga manga : usersFavourites) {
            groupMangaByGenre(genreGroups, manga.getGenres());
        }

        for (MangaRating rating : usersRatedList) {
            groupMangaByGenre(genreGroups, rating.getManga().getGenres());
        }

        for (MangaInUserList mangaInList : usersMangaList) {
            groupMangaByGenre(genreGroups, mangaInList.getManga().getGenres());
        }

        Entry<MangaGenre, Integer> mostOccurrences = findMostCommonGenre(genreGroups);

        return mostOccurrences != null ? mostOccurrences.getKey() : null;
    }

    private List<Manga> removeMangaThatIsAlreadyInUserList(List<Manga> recommendations, User user) {

        return recommendations.stream()
                .filter(manga -> !isMangaAlreadyRatedByUser(user, manga)
                        && !isMangaAlreadyInUsersFavourites(user, manga)
                        && !isMangaAlreadyInUsersMangaList(user, manga))
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

    private List<Manga> findMoreRecommendations(User user, List<Manga> recommendations, List<Manga> allManga) {

        int howManyToFind = numberOfRecommendedManga - recommendations.size();

        recommendations.addAll(allManga.stream()
                .filter(manga -> !isMangaAlreadyRatedByUser(user, manga)
                        && !isMangaAlreadyInUsersFavourites(user, manga) && !isMangaAlreadyInUsersMangaList(user, manga)
                        && !isMangaAlreadyRecommended(recommendations, manga))
                .limit(howManyToFind).sorted(SORT_BY_LIKES).collect(Collectors.toList()));

        return recommendations;
    }

    private boolean isMangaAlreadyRecommended(List<Manga> recommendations, Manga manga) {
        return recommendations.contains(manga);
    }

    private boolean isMangaAlreadyInUsersFavourites(User user, Manga manga) {
        return user.getFavouriteMangas().contains(manga);
    }

    private boolean isMangaAlreadyRatedByUser(User user, Manga manga) {
        return user.getMangasRatings().stream().anyMatch(rating -> rating.getManga().equals(manga));
    }

    private boolean isMangaAlreadyInUsersMangaList(User user, Manga manga) {
        return user.getMangaList().stream().anyMatch(mangaInList -> mangaInList.getManga().equals(manga));
    }
}
