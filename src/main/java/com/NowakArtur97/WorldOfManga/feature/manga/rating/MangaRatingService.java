package com.NowakArtur97.WorldOfManga.feature.manga.rating;

import com.NowakArtur97.WorldOfManga.exception.MangaNotFoundException;
import com.NowakArtur97.WorldOfManga.feature.manga.details.Manga;
import com.NowakArtur97.WorldOfManga.feature.manga.details.MangaService;
import com.NowakArtur97.WorldOfManga.feature.user.User;
import com.NowakArtur97.WorldOfManga.feature.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MangaRatingService {

    private final MangaRatingRepository mangaRatingRepository;

    private final MangaService mangaService;

    private final UserService userService;

    public MangaRating findByUserAndManga(User user, Manga manga) {

        return mangaRatingRepository.findByUserAndManga(user, manga).orElseGet(MangaRating::new);
    }

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