package com.NowakArtur97.WorldOfManga.feature.manga.inUserList;

import com.NowakArtur97.WorldOfManga.feature.manga.details.Manga;
import com.NowakArtur97.WorldOfManga.feature.manga.details.MangaNotFoundException;
import com.NowakArtur97.WorldOfManga.feature.manga.rating.MangaRating;
import com.NowakArtur97.WorldOfManga.feature.user.User;
import com.NowakArtur97.WorldOfManga.feature.user.UserService;
import lombok.RequiredArgsConstructor;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
class MangaInUserListController {

    private final MangaInUserListService mangaInUserListService;

    private final LocaleResolver cookieLocaleResolver;

    private final UserService userService;

    @GetMapping(path = "/addToList")
    String addToList(HttpServletRequest request, @RequestParam("id") Long mangaId,
                     @RequestParam("status") int status) throws MangaNotFoundException {

        mangaInUserListService.addOrRemoveFromList(mangaId, status);

        String referer = request.getHeader("Referer");

        return "redirect:" + referer;
    }

    @GetMapping(path = "/sortMangaList/{status}")
    String sortMangaList(Model theModel, HttpServletRequest request, @PathVariable("status") int status,
                         @PageableDefault(size = 12) Pageable pageable) {

        Locale locale = cookieLocaleResolver.resolveLocale(request);

        Set<Manga> mangaList = mangaInUserListService.getUsersMangaListByStatus(status);

        List<Manga> mangas = new ArrayList<>(mangaList);

        Page<Manga> mangaPages = convertListToPage(pageable, mangas);

        theModel.addAttribute("mangas", mangaPages);

        if (locale != null) {
            theModel.addAttribute("locale", locale.getLanguage());
        }

        User user = userService.loadLoggedInUsername();

        theModel.addAttribute("usersFavourites", new ArrayList<>(user.getFavouriteMangas()));
        theModel.addAttribute("usersRatings",
                user.getMangasRatings().stream().map(MangaRating::getManga).collect(Collectors.toSet()));

        return "views/manga-list";
    }

    private Page<Manga> convertListToPage(Pageable pageable, List<Manga> mangas) {

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), mangas.size());

        return new PageImpl<>(mangas.subList(start, end), pageable, mangas.size());
    }
}
