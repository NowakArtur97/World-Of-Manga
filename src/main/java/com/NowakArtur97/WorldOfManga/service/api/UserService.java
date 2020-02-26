package com.NowakArtur97.WorldOfManga.service.api;

import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.NowakArtur97.WorldOfManga.exception.MangaNotFoundException;
import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaInUserList;
import com.NowakArtur97.WorldOfManga.model.MangaRating;
import com.NowakArtur97.WorldOfManga.model.User;

public interface UserService extends UserDetailsService {

	Optional<User> findByUsername(String username);

	boolean isUsernameAlreadyInUse(String username);

	boolean isEmailAlreadyInUse(String email);

	User save(User user);

	MangaRating rateManga(Long mangaId, int rating) throws MangaNotFoundException;

	Manga addOrRemoveFromFavourites(Long mangaId) throws MangaNotFoundException;

	MangaInUserList addToList(Long mangaId, int status) throws MangaNotFoundException;
	
	Set<Manga> getUsersMangaListByStatus(int status);
	
	User loadLoggedInUsername() throws UsernameNotFoundException;
}
