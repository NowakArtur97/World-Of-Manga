package com.NowakArtur97.WorldOfManga.controller.manga;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;

import com.NowakArtur97.WorldOfManga.exception.MangaNotFoundException;
import com.NowakArtur97.WorldOfManga.service.api.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(path = "/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MangaFavouriteController {

	private final UserService userService;

	private final LocaleResolver cookieLocaleResolver;
	
	@GetMapping(path = "/addOrRemoveFromFavourites/{id}")
	public String addOrRemoveFromFavourites(HttpServletRequest request, @PathVariable("id") Long mangaId)
			throws MangaNotFoundException {

		userService.addOrRemoveFromFavourites(mangaId);

		String referer = request.getHeader("Referer");

		return "redirect:" + referer;
	}
	
	@GetMapping(path = "/myMangaWorld")
	public String showUserMangaList(Model theModel, HttpServletRequest request) {

		Locale locale = cookieLocaleResolver.resolveLocale(request);

		theModel.addAttribute("mangas", userService.loadLoggedInUsername().getFavouriteMangas());

		if (locale != null) {
			theModel.addAttribute("locale", locale.getLanguage());
		}

		return "views/main";
	}
}
