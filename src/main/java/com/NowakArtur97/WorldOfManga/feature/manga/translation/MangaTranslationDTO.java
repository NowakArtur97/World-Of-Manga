package com.NowakArtur97.WorldOfManga.feature.manga.translation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
