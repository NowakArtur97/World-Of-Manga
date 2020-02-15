package com.NowakArtur97.WorldOfManga.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MangaDTO {

	private final Set<@Valid AuthorDTO> authors = new HashSet<>();

	@Valid
	private MangaTranslationDTO enTranslation = new MangaTranslationDTO();

	@Valid
	private MangaTranslationDTO plTranslation = new MangaTranslationDTO();

	public void addAuthorDTO(AuthorDTO authorDTO) {

		this.getAuthors().add(authorDTO);
	}
}
