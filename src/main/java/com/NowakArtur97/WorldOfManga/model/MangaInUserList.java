package com.NowakArtur97.WorldOfManga.model;

import com.NowakArtur97.WorldOfManga.enums.MangaInUserListStatus;
import com.NowakArtur97.WorldOfManga.feature.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "manga_list", schema = "world_of_manga")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MangaInUserList {

	@EmbeddedId
	private MangaInUserListId mangaInUserListId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "manga_id", insertable = false, updatable = false)
	private Manga manga;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private User user;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private MangaInUserListStatus status;

	public MangaInUserList(Manga manga, User user, MangaInUserListStatus status) {
		this.manga = manga;
		this.user = user;
		this.mangaInUserListId = new MangaInUserListId(manga.getId(), user.getId());
		this.status = status;
	}
}
