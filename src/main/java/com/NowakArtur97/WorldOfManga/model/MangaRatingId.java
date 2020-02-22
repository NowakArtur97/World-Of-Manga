package com.NowakArtur97.WorldOfManga.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MangaRatingId implements Serializable {

	private static final long serialVersionUID = -4902130401018115529L;

	@Column(name = "manga_id")
	private Manga manga;

	@Column(name = "user_id")
	private User user;
}
