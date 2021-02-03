package com.NowakArtur97.WorldOfManga.feature.mainPage;

import com.NowakArtur97.WorldOfManga.feature.manga.details.Manga;
import com.NowakArtur97.WorldOfManga.feature.manga.details.MangaService;
import com.NowakArtur97.WorldOfManga.feature.manga.genre.MangaGenre;
import com.NowakArtur97.WorldOfManga.feature.manga.inUserList.MangaInUserList;
import com.NowakArtur97.WorldOfManga.feature.manga.rating.MangaRating;
import com.NowakArtur97.WorldOfManga.feature.user.User;
import com.NowakArtur97.WorldOfManga.feature.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class RecommendationService {

    @Value("${world-of-manga.recommendations:10}")
    private int numberOfRecommendedManga;

    private final MangaService mangaService;

    private final UserService userService;

    private final Comparator<Manga> SORT_BY_LIKES = (manga1, manga2) ->
            manga2.getUserWithMangaInFavourites().size()
                    - manga1.getUserWithMangaInFavourites().size();

    List<Manga> recommendManga() {

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

            findMoreRecommendations(user, recommendations, allManga);
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
                .filter(manga -> isNotMangaAlreadyRatedByUser(user, manga)
                        && isNotMangaAlreadyInUsersFavourites(user, manga)
                        && isNotMangaAlreadyInUsersMangaList(user, manga))
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

    private void findMoreRecommendations(User user, List<Manga> recommendations, List<Manga> allManga) {

        int howManyToFind = numberOfRecommendedManga - recommendations.size();

        recommendations.addAll(allManga.stream()
                .filter(manga -> isNotMangaAlreadyRatedByUser(user, manga)
                        && isNotMangaAlreadyInUsersFavourites(user, manga) && isNotMangaAlreadyInUsersMangaList(user, manga)
                        && !isMangaAlreadyRecommended(recommendations, manga))
                .limit(howManyToFind).sorted(SORT_BY_LIKES).collect(Collectors.toList()));

    }

    private boolean isMangaAlreadyRecommended(List<Manga> recommendations, Manga manga) {
        return recommendations.contains(manga);
    }

    private boolean isNotMangaAlreadyInUsersFavourites(User user, Manga manga) {
        return !user.getFavouriteMangas().contains(manga);
    }

    private boolean isNotMangaAlreadyRatedByUser(User user, Manga manga) {
        return user.getMangasRatings().stream().noneMatch(rating -> rating.getManga().equals(manga));
    }

    private boolean isNotMangaAlreadyInUsersMangaList(User user, Manga manga) {
        return user.getMangaList().stream().noneMatch(mangaInList -> mangaInList.getManga().equals(manga));
    }
}
