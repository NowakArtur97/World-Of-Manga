package com.NowakArtur97.WorldOfManga.controller.manga;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.NowakArtur97.WorldOfManga.dto.MangaDTO;
import com.NowakArtur97.WorldOfManga.validation.manga.MangaTranslationValidator;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(path = "/admin")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MangaCreationController {

	private final MangaTranslationValidator mangaTranslationDTOValidator;

	@GetMapping(path = "/addOrUpdateManga")
	public String showAddMangaPage(Model theModel) {

		theModel.addAttribute("mangaDTO", new MangaDTO());

		return "views/manga-form";
	}

	@PostMapping(path = "/addOrUpdateManga")
	public String processAddMangaPage(Model theModel, @ModelAttribute("mangaDTO") @Valid MangaDTO mangaDTO,
			BindingResult result) {

		mangaTranslationDTOValidator.validate(mangaDTO, result);

		if (result.hasErrors()) {

			theModel.addAttribute("mangaDTO", mangaDTO);

			return "views/manga-form";
		}

		return "redirect:/";
	}
}
