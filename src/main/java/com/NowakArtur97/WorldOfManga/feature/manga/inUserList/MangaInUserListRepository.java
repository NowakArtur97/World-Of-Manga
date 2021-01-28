package com.NowakArtur97.WorldOfManga.feature.manga.inUserList;

import com.NowakArtur97.WorldOfManga.feature.manga.details.Manga;
import com.NowakArtur97.WorldOfManga.feature.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface MangaInUserListRepository extends JpaRepository<MangaInUserList, Long> {

	Optional<MangaInUserList> findByUserAndManga(User user, Manga manga);
}
