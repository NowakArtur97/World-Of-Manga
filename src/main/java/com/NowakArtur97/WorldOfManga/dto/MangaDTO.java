package com.NowakArtur97.WorldOfManga.dto;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.NowakArtur97.WorldOfManga.model.Author;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MangaDTO {

	@NotEmpty(message = "{manga.authors.notEmpty}")
	private Set<Author> authors;

	@Valid
	private MangaTranslationDTO enTranslation = new MangaTranslationDTO();

	@Valid
	private MangaTranslationDTO plTranslation = new MangaTranslationDTO();
}
