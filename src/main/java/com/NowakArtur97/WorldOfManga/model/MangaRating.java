package com.NowakArtur97.WorldOfManga.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	private MangaRatingId id;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("manga_id")
	private Manga manga;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("user_id")
	private User user;

	@Transient
	private Double rating;

	public MangaRating(Manga manga, User user, Double rating) {
		this.id = new MangaRatingId(manga.getId(), user.getId());
		this.manga = manga;
		this.user = user;
		this.rating = rating;
	}
}
