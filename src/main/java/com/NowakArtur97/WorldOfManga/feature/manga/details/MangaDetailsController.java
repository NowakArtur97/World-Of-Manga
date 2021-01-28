package com.NowakArtur97.WorldOfManga.feature.manga.details;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping(path = "/manga")
@RequiredArgsConstructor
class MangaDetailsController {

    private final MangaService mangaService;

    @GetMapping(path = "/getImage/{id}")
    void showAddMangaPage(HttpServletResponse response, @PathVariable("id") Long id)
            throws MangaNotFoundException {

        response.setContentType("image/jpeg");

        byte[] imageBytes = mangaService.findById(id).getImage();

        InputStream inputStream = new ByteArrayInputStream(imageBytes);

        try {
            IOUtils.copy(inputStream, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
