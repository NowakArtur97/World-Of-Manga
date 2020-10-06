package com.NowakArtur97.WorldOfManga.repository;

import com.NowakArtur97.WorldOfManga.feature.user.User;
import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MangaRatingRepository extends JpaRepository<MangaRating, Long> {

	Optional<MangaRating> findByUserAndManga(User user, Manga manga);
}
