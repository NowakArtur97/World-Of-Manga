package com.NowakArtur97.WorldOfManga.dto;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import com.NowakArtur97.WorldOfManga.feature.author.Author;
import com.NowakArtur97.WorldOfManga.model.MangaGenre;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MangaDTO {

	private Long id;

	private MultipartFile image;

	@NotEmpty(message = "{manga.authors.notEmpty}")
	private Set<Author> authors;

	@NotEmpty(message = "{manga.genres.notEmpty}")
	private Set<MangaGenre> genres;

	@Valid
	private MangaTranslationDTO enTranslation = new MangaTranslationDTO();

	@Valid
	private MangaTranslationDTO plTranslation = new MangaTranslationDTO();
}
