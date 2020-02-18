package com.NowakArtur97.WorldOfManga.controller.main;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;

import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaTranslation;
import com.NowakArtur97.WorldOfManga.service.api.MangaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(path = "/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class MainController {

	private final MangaService mangaService;

	private final LocaleResolver cookieLocaleResolver;

	@GetMapping
	public String showMainPage(Model theModel, HttpServletRequest request) {

		Locale locale = cookieLocaleResolver.resolveLocale(request);

		theModel.addAttribute("mangas", mangaService.findAll());
		theModel.addAttribute("locale", locale);

		for (Manga manga : mangaService.findAll()) {
			for (MangaTranslation translation : manga.getTranslations()) {
				log.info("LOCALE: " + translation.getLanguage().getLocale());
			}
		}

		return "views/main";
	}
}
