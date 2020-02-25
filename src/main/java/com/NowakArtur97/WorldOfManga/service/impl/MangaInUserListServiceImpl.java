package com.NowakArtur97.WorldOfManga.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaInUserList;
import com.NowakArtur97.WorldOfManga.model.User;
import com.NowakArtur97.WorldOfManga.repository.MangaInUserListRepository;
import com.NowakArtur97.WorldOfManga.service.api.MangaInUserListService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MangaInUserListServiceImpl implements MangaInUserListService {

	private final MangaInUserListRepository mangaInUserListRepository;

	@Override
	public Optional<MangaInUserList> findByUserAndManga(User user, Manga manga) {

		return mangaInUserListRepository.findByUserAndManga(user, manga);
	}
}
