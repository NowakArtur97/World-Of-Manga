package com.NowakArtur97.WorldOfManga.feature.manga.rating;

import com.NowakArtur97.WorldOfManga.feature.manga.details.Manga;
import com.NowakArtur97.WorldOfManga.feature.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MangaRatingRepository extends JpaRepository<MangaRating, Long> {

	Optional<MangaRating> findByUserAndManga(User user, Manga manga);
}
