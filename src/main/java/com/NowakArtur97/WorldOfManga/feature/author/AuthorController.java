package com.NowakArtur97.WorldOfManga.feature.author;

import com.NowakArtur97.WorldOfManga.dto.AuthorDTO;
import com.NowakArtur97.WorldOfManga.dto.MangaDTO;
import com.NowakArtur97.WorldOfManga.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/admin")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    private final AuthorValidator authorValidator;

    @GetMapping(path = "/addOrUpdateAuthor")
    public String showAddMangaPage(Model theModel) {

        theModel.addAttribute("mangaDTO", new MangaDTO());
        theModel.addAttribute("authorDTO", new AuthorDTO());
        theModel.addAttribute("authors", authorService.findAll());

        return "views/manga-form";
    }

    @PostMapping(path = "/addOrUpdateAuthor")
    public String processAddAuthorPage(Model theModel, @ModelAttribute("authorDTO") @Valid AuthorDTO authorDTO,
                                       BindingResult result) {

        authorValidator.validate(authorDTO, result);

        if (result.hasErrors()) {

            theModel.addAttribute("authorDTO", authorDTO);
            theModel.addAttribute("mangaDTO", new MangaDTO());
            theModel.addAttribute("authors", authorService.findAll());

            return "views/manga-form";
        }

        authorService.addOrUpdate(authorDTO);

        return "redirect:/admin/addOrUpdateAuthor";
    }
}
