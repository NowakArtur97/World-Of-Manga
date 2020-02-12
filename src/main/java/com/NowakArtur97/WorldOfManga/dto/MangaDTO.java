package com.NowakArtur97.WorldOfManga.dto;

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

	@Valid
	private MangaTranslationDTO enTranslation = new MangaTranslationDTO();
	
	@Valid
	private MangaTranslationDTO plTranslation = new MangaTranslationDTO();
}
