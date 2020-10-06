package com.NowakArtur97.WorldOfManga.controller.manga;

import com.NowakArtur97.WorldOfManga.exception.MangaNotFoundException;
import com.NowakArtur97.WorldOfManga.service.MangaRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class MangaRatingController {

    private final MangaRatingService mangaRatingService;

    @GetMapping(path = "/rateManga")
    public String rateManga(HttpServletRequest request, @RequestParam("id") Long mangaId,
                            @RequestParam("rating") int rating) throws MangaNotFoundException {

        mangaRatingService.rateManga(mangaId, rating);

        String referer = request.getHeader("Referer");

        return "redirect:" + referer;
    }
}
