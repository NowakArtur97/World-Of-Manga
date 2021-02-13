package com.NowakArtur97.WorldOfManga.feature.manga.details;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
class MangaControllerAdvice {

    @ExceptionHandler(value = MangaNotFoundException.class)
    public String defaultErrorHandler(HttpServletRequest request) {

        String referer = request.getHeader("Referer");

        return "redirect:" + referer;
    }
}
