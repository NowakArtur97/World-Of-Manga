package com.NowakArtur97.WorldOfManga.controller.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.NowakArtur97.WorldOfManga.service.api.MangaService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(path = "/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MainController {

	private final MangaService mangaService;

	@GetMapping
	public String showMainPage(Model theModel) {

		theModel.addAttribute("mangas", mangaService.findAll());

		return "views/main";
	}
}
