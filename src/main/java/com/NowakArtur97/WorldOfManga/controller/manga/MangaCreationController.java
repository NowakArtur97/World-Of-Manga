package com.NowakArtur97.WorldOfManga.controller.manga;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.NowakArtur97.WorldOfManga.dto.MangaDTO;

@Controller
@RequestMapping(path = "/admin")
public class MangaCreationController {

	@GetMapping(path = "/addManga")
	public String showRegistrationPage(Model theModel) {

		theModel.addAttribute("mangaDTO", new MangaDTO());

		return "views/manga-form";
	}
}
