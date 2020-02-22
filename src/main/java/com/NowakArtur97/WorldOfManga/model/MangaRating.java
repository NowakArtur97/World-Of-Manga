package com.NowakArtur97.WorldOfManga.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "manga_rating", schema = "world_of_manga")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MangaRating {

	@EmbeddedId
	private MangaRatingId mangaRatingId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "manga_id", insertable = false, updatable = false)
	private Manga manga;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private User user;

	@Column(name = "rating")
	private Long rating;

	public MangaRating(Manga manga, User user, Long rating) {
		this.manga = manga;
		this.user = user;
		this.mangaRatingId = new MangaRatingId(manga.getId(), user.getId());
		this.rating = rating;
	}
}
