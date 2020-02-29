package com.NowakArtur97.WorldOfManga.service.api;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.NowakArtur97.WorldOfManga.exception.MangaNotFoundException;
import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.User;

public interface UserService extends UserDetailsService {

	Optional<User> findByUsername(String username);

	boolean isUsernameAlreadyInUse(String username);

	boolean isEmailAlreadyInUse(String email);

	User save(User user);

	Manga addOrRemoveFromFavourites(Long mangaId) throws MangaNotFoundException;

	User loadLoggedInUsername() throws UsernameNotFoundException;
}
