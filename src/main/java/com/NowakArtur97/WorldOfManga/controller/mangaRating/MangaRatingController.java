package com.NowakArtur97.WorldOfManga.controller.mangaRating;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.NowakArtur97.WorldOfManga.exception.MangaNotFoundException;
import com.NowakArtur97.WorldOfManga.service.api.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(path = "/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MangaRatingController {

	private final UserService userService;

	@GetMapping(path = "/rateManga")
	public String showAddMangaPage(HttpServletRequest request, @RequestParam(name = "id") Long mangaId,
			@RequestParam(name = "rate") Long rating) throws MangaNotFoundException, UsernameNotFoundException {

		String referer = request.getHeader("Referer");

		userService.rateManga(mangaId, rating);

		return "redirect:" + referer;
	}
}
