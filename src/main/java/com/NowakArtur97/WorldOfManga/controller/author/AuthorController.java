package com.NowakArtur97.WorldOfManga.controller.author;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.NowakArtur97.WorldOfManga.dto.AuthorDTO;
import com.NowakArtur97.WorldOfManga.dto.MangaDTO;
import com.NowakArtur97.WorldOfManga.exception.LanguageNotFoundException;
import com.NowakArtur97.WorldOfManga.service.api.AuthorService;
import com.NowakArtur97.WorldOfManga.validation.author.AuthorValidator;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(path = "/admin")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorController {

	private final AuthorService authorService;

	private final AuthorValidator authorValidator;

	@GetMapping(path = "/addOrUpdateAuthor")
	public String showAddMangaPage(Model theModel) {

		MangaDTO mangaDTO = new MangaDTO();
		AuthorDTO authorDTO = new AuthorDTO();

		theModel.addAttribute("mangaDTO", mangaDTO);
		theModel.addAttribute("authorDTO", authorDTO);

		return "views/manga-form";
	}
	
	@PostMapping(path = "/addOrUpdateAuthor")
	public String processAddAuthorPage(Model theModel, @ModelAttribute("authorDTO") @Valid AuthorDTO authorDTO,
			BindingResult result) throws LanguageNotFoundException {

		authorValidator.validate(authorDTO, result);

		if (result.hasErrors()) {

			theModel.addAttribute("authorDTO", authorDTO);
			theModel.addAttribute("mangaDTO", new MangaDTO());

			return "views/manga-form";
		}

		authorService.addOrUpdate(authorDTO);

		return "redirect:/admin/addOrUpdateManga";
	}
}
