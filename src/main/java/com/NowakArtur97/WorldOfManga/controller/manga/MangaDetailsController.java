package com.NowakArtur97.WorldOfManga.controller.manga;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.NowakArtur97.WorldOfManga.exception.MangaNotFoundException;
import com.NowakArtur97.WorldOfManga.service.api.MangaService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(path = "/manga")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MangaDetailsController {

	private final MangaService mangaService;

	@GetMapping(path = "/getImage/{id}")
	public void showAddMangaPage(HttpServletResponse response, @PathVariable("id") Long id)
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

	@GetMapping(path = "/rateManga")
	public String showAddMangaPage(HttpServletRequest request, @RequestParam(name = "id") Long id,
			@RequestParam(name = "rate") Long rating) throws MangaNotFoundException {

		String referer = request.getHeader("Referer");
		
		mangaService.rateManga(id, rating);
		
		return "redirect:" + referer;
	}
}
