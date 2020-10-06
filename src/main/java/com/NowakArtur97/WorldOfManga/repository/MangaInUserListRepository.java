package com.NowakArtur97.WorldOfManga.repository;

import com.NowakArtur97.WorldOfManga.feature.user.User;
import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaInUserList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MangaInUserListRepository extends JpaRepository<MangaInUserList, Long> {

	Optional<MangaInUserList> findByUserAndManga(User user, Manga manga);
}
