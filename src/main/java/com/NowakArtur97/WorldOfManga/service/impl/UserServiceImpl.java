package com.NowakArtur97.WorldOfManga.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.NowakArtur97.WorldOfManga.enums.MangaInUserListStatus;
import com.NowakArtur97.WorldOfManga.exception.MangaNotFoundException;
import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaInUserList;
import com.NowakArtur97.WorldOfManga.model.MangaRating;
import com.NowakArtur97.WorldOfManga.model.Role;
import com.NowakArtur97.WorldOfManga.model.User;
import com.NowakArtur97.WorldOfManga.repository.UserRepository;
import com.NowakArtur97.WorldOfManga.service.api.MangaInUserListService;
import com.NowakArtur97.WorldOfManga.service.api.MangaRatingService;
import com.NowakArtur97.WorldOfManga.service.api.MangaService;
import com.NowakArtur97.WorldOfManga.service.api.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final MangaService mangaService;

	private final MangaRatingService mangaRatingService;

	private final MangaInUserListService mangaInUserListService;

	@Override
	public Optional<User> findByUsername(String username) {

		return userRepository.findByUsername(username);
	}

	@Override
	public boolean isUsernameAlreadyInUse(String username) {

		return userRepository.existsUserByUsername(username);
	}

	@Override
	public boolean isEmailAlreadyInUse(String email) {

		return userRepository.existsUserByEmail(email);
	}

	@Override
	public User save(User user) {

		userRepository.save(user);

		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Invalid username or password"));

		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				user.isEnabled(), accountNonExpired, credentialsNonExpired, accountNonLocked,
				mapRolesToAuthorities(user.getRoles()));

	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> usersRoles) {

		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

		for (Role role : usersRoles) {
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
			authorities.add(authority);
		}

		return authorities;
	}

	@Override
	public User loadLoggedInUsername() throws UsernameNotFoundException {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String username;

		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}

		User loggedInUser = findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Invalid username or password"));

		return loggedInUser;
	}

	@Override
	@Transactional
	public MangaRating rateManga(Long mangaId, int rating) throws MangaNotFoundException {

		Manga manga = mangaService.findById(mangaId);

		User user = loadLoggedInUsername();

		MangaRating mangaRating = mangaRatingService.findByUserAndManga(user, manga);

		if (mangaRating.getRating() == 0) {

			mangaRating = user.addMangaRating(manga, rating);
		} else {

			mangaRating.setRating(rating);
		}

		return mangaRating;
	}

	@Override
	@Transactional
	public Manga addOrRemoveFromFavourites(Long mangaId) throws MangaNotFoundException {

		Manga manga = mangaService.findById(mangaId);

		User user = loadLoggedInUsername();

		if (user.getFavouriteMangas().contains(manga)) {

			user.removeMangaFromFavourites(manga);
		} else {

			user.addMangaToFavourites(manga);
		}

		return manga;
	}

	@Override
	@Transactional
	public MangaInUserList addToList(Long mangaId, int status) throws MangaNotFoundException {

		Manga manga = mangaService.findById(mangaId);

		User user = loadLoggedInUsername();

		MangaInUserListStatus mangaStatus = MangaInUserListStatus.values()[status];

		Optional<MangaInUserList> mangaInListOptional = mangaInUserListService.findByUserAndManga(user, manga);

		MangaInUserList mangaInUserList = null;

		if (mangaInListOptional.isPresent()) {

			mangaInUserList = mangaInListOptional.get();
			mangaInUserList.setStatus(mangaStatus);
		} else {

			user.addMangaToList(manga, mangaStatus);
		}

		return mangaInUserList;
	}
}