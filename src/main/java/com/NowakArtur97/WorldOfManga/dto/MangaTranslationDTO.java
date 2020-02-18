package com.NowakArtur97.WorldOfManga.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MangaTranslationDTO {

	@NotBlank(message = "{mangaTranslation.title.notBlank}")
	@Size(message = "{mangaTranslation.title.size}{max}", max = 50)
	private String title;

	@NotBlank(message = "{mangaTranslation.description.notBlank}")
	@Size(message = "{mangaTranslation.description.size}{max}", max = 1000)
	private String description;
}
