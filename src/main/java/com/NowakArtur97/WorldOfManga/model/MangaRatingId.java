package com.NowakArtur97.WorldOfManga.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MangaRatingId implements Serializable {

	private static final long serialVersionUID = -4902130401018115529L;

	@Column(name = "manga_id")
	private Long mangaId;

	@Column(name = "user_id")
	private Long userId;
}
