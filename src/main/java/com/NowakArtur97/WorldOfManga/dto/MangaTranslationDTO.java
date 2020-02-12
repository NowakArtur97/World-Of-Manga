package com.NowakArtur97.WorldOfManga.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MangaTranslationDTO {

	private String title;

	private String description;
}
