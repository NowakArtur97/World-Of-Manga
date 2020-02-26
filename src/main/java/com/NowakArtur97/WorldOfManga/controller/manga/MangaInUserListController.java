package com.NowakArtur97.WorldOfManga.controller.manga;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.NowakArtur97.WorldOfManga.exception.MangaNotFoundException;
import com.NowakArtur97.WorldOfManga.service.api.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(path = "/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MangaInUserListController {

	private final UserService userService;

	@GetMapping(path = "/addToList")
	public String addToList(HttpServletRequest request, @RequestParam("id") Long mangaId,
			@RequestParam("status") int status) throws MangaNotFoundException {

		userService.addToList(mangaId, status);

		String referer = request.getHeader("Referer");

		return "redirect:" + referer;
	}
}
