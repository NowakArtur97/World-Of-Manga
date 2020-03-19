package com.NowakArtur97.WorldOfManga.controller.manga;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;

import com.NowakArtur97.WorldOfManga.exception.MangaNotFoundException;
import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.service.api.MangaInUserListService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(path = "/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MangaInUserListController {

	private final MangaInUserListService mangaInUserListService;

	private final LocaleResolver cookieLocaleResolver;

	@GetMapping(path = "/addToList")
	public String addToList(HttpServletRequest request, @RequestParam("id") Long mangaId,
			@RequestParam("status") int status) throws MangaNotFoundException {

		mangaInUserListService.addOrRemoveFromList(mangaId, status);

		String referer = request.getHeader("Referer");

		return "redirect:" + referer;
	}

	@GetMapping(path = "/sortMangaList/{status}")
	public String sortMangaList(Model theModel, HttpServletRequest request, @PathVariable("status") int status,
			@PageableDefault(size = 12) Pageable pageable) {

		Locale locale = cookieLocaleResolver.resolveLocale(request);

		Set<Manga> mangaList = mangaInUserListService.getUsersMangaListByStatus(status);

		List<Manga> mangas = new ArrayList<>(mangaList);

		Page<Manga> mangaPages = convertListToPage(pageable, mangas);

		theModel.addAttribute("mangas", mangaPages);

		if (locale != null) {
			theModel.addAttribute("locale", locale.getLanguage());
		}

		return "views/manga-list";
	}

	private Page<Manga> convertListToPage(Pageable pageable, List<Manga> mangas) {

		int start = (int) pageable.getOffset();
		int end = (start + pageable.getPageSize()) > mangas.size() ? mangas.size() : (start + pageable.getPageSize());

		Page<Manga> mangaPages = new PageImpl<Manga>(mangas.subList(start, end), pageable, mangas.size());

		return mangaPages;
	}
}
