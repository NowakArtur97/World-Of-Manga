package com.NowakArtur97.WorldOfManga.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaInUserList;
import com.NowakArtur97.WorldOfManga.feature.user.User;

public interface MangaInUserListRepository extends JpaRepository<MangaInUserList, Long> {

	Optional<MangaInUserList> findByUserAndManga(User user, Manga manga);
}
