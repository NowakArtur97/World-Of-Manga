package com.NowakArtur97.WorldOfManga.controller.main;

import java.util.Locale;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.NowakArtur97.WorldOfManga.model.MangaRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;

import com.NowakArtur97.WorldOfManga.model.User;
import com.NowakArtur97.WorldOfManga.service.api.MangaService;
import com.NowakArtur97.WorldOfManga.service.api.RecommendationService;
import com.NowakArtur97.WorldOfManga.service.api.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(path = "/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MainController {

	private final MangaService mangaService;

	private final RecommendationService recommendationService;

	private final UserService userService;

	private final LocaleResolver cookieLocaleResolver;

	@GetMapping
	public String showMainPage(Model theModel, HttpServletRequest request,
			@PageableDefault(size = 12) Pageable pageable) {

		Locale locale = cookieLocaleResolver.resolveLocale(request);

		if (locale != null) {
			theModel.addAttribute("locale", locale.getLanguage());
		}
	
		theModel.addAttribute("mangas", mangaService.findAllDividedIntoPages(pageable));
		theModel.addAttribute("recommendations", recommendationService.recommendManga());

		if (userService.isUserLoggedIn()) {

			loadUserData(theModel);
		}

		return "main";
	}

	private void loadUserData(Model theModel) {

		User user = userService.loadLoggedInUsername();

		theModel.addAttribute("usersFavourites", user.getFavouriteMangas());
		theModel.addAttribute("usersRatings",
				user.getMangasRatings().stream().map(MangaRating::getManga).collect(Collectors.toSet()));
	}
}
