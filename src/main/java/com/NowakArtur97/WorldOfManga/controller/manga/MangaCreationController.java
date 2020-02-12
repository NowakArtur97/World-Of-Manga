package com.NowakArtur97.WorldOfManga.controller.manga;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.NowakArtur97.WorldOfManga.dto.MangaDTO;

@Controller
@RequestMapping(path = "/admin")
public class MangaCreationController {

	@GetMapping(path = "/addOrUpdateManga")
	public String showAddMangaPage(Model theModel) {

		theModel.addAttribute("mangaDTO", new MangaDTO());

		return "views/manga-form";
	}

	@PostMapping(path = "/addOrUpdateManga")
	public String processAddMangaPage(Model theModel, @ModelAttribute("mangaDTO") @Valid MangaDTO mangaDTO,
			BindingResult result) {

		if (result.hasErrors()) {

			theModel.addAttribute("mangaDTO", mangaDTO);

			return "views/manga-form";
		}

		return "redirect:/";
	}
}
