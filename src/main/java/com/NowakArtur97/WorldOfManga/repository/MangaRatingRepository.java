package com.NowakArtur97.WorldOfManga.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaRating;
import com.NowakArtur97.WorldOfManga.model.User;

public interface MangaRatingRepository extends JpaRepository<MangaRating, Long> {

	Optional<MangaRating> findByUserAndManga(User user, Manga manga);
}
