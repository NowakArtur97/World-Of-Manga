package com.NowakArtur97.WorldOfManga.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.service.api.MangaService;
import com.NowakArtur97.WorldOfManga.service.api.RecommendationService;
import com.NowakArtur97.WorldOfManga.service.api.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecommendationServiceImpl implements RecommendationService {

	private final MangaService mangaService;

	private final UserService userService;

	@Override
	public List<Manga> recommendManga() {

		List<Manga> allManga = mangaService.findAll();

		if (userService.isUserLoggedIn()) {

		}

		return allManga;
	}
}
