package com.NowakArtur97.WorldOfManga.controller.main;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;

import com.NowakArtur97.WorldOfManga.service.api.MangaService;
import com.NowakArtur97.WorldOfManga.service.api.RecommendationService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(path = "/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MainController {

	private final MangaService mangaService;
	
	private final RecommendationService recommendationService;

	private final LocaleResolver cookieLocaleResolver;

	@GetMapping
	public String showMainPage(Model theModel, HttpServletRequest request) {

		Locale locale = cookieLocaleResolver.resolveLocale(request);

		theModel.addAttribute("mangas", mangaService.findAll());
		theModel.addAttribute("recommendations", recommendationService.recommendManga());

		if (locale != null) {
			theModel.addAttribute("locale", locale.getLanguage());
		}

		return "views/main";
	}
}
