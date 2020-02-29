package com.NowakArtur97.WorldOfManga.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NowakArtur97.WorldOfManga.enums.MangaInUserListStatus;
import com.NowakArtur97.WorldOfManga.exception.MangaNotFoundException;
import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaInUserList;
import com.NowakArtur97.WorldOfManga.model.MangaRating;
import com.NowakArtur97.WorldOfManga.model.User;
import com.NowakArtur97.WorldOfManga.repository.MangaInUserListRepository;
import com.NowakArtur97.WorldOfManga.service.api.MangaInUserListService;
import com.NowakArtur97.WorldOfManga.service.api.MangaService;
import com.NowakArtur97.WorldOfManga.service.api.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MangaInUserListServiceImpl implements MangaInUserListService {

	private final MangaInUserListRepository mangaInUserListRepository;

	private final MangaService mangaService;

	private final UserService userService;

	@Override
	public Optional<MangaInUserList> findByUserAndManga(User user, Manga manga) {

		return mangaInUserListRepository.findByUserAndManga(user, manga);
	}

	@Override
	@Transactional
	public MangaInUserList addToList(Long mangaId, int status) throws MangaNotFoundException {

		Manga manga = mangaService.findById(mangaId);

		User user = userService.loadLoggedInUsername();

		MangaInUserListStatus mangaStatus = MangaInUserListStatus.values()[status];

		Optional<MangaInUserList> mangaInListOptional = findByUserAndManga(user, manga);

		MangaInUserList mangaInUserList = null;

		if (mangaInListOptional.isPresent()) {

			mangaInUserList = mangaInListOptional.get();
			mangaInUserList.setStatus(mangaStatus);
		} else {

			mangaInUserList = user.addMangaToList(manga, mangaStatus);
		}

		return mangaInUserList;
	}

	@Override
	public Set<Manga> getUsersMangaListByStatus(int status) {

		Set<Manga> mangaList = new HashSet<>();

		User user = userService.loadLoggedInUsername();

		if (status >= MangaInUserListStatus.values().length) {

			if (status == 5) {

				mangaList = user.getFavouriteMangas();
			} else {

				mangaList = user.getMangasRatings().stream().map(MangaRating::getManga).collect(Collectors.toSet());
			}
		} else {

			mangaList = getListByStatus(status, user);
		}

		return mangaList;
	}

	private Set<Manga> getListByStatus(int statusId, User user) {

		MangaInUserListStatus status = MangaInUserListStatus.values()[statusId];

		Set<Manga> mangaList = new HashSet<>();

		mangaList = mapToMangaSet(status, user);

		return mangaList;
	}

	private Set<Manga> mapToMangaSet(MangaInUserListStatus status, User user) {

		return user.getMangaList().stream().filter(manga -> manga.getStatus().equals(status))
				.collect(Collectors.toSet()).stream().map(MangaInUserList::getManga).collect(Collectors.toSet());
	}
}
