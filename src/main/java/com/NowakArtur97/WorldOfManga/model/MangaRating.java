package com.NowakArtur97.WorldOfManga.model;

import com.NowakArtur97.WorldOfManga.feature.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
	private int rating;

	public MangaRating(Manga manga, User user, int rating) {
		this.manga = manga;
		this.user = user;
		this.mangaRatingId = new MangaRatingId(manga.getId(), user.getId());
		this.rating = rating;
	}
}
