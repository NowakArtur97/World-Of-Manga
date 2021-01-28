package com.NowakArtur97.WorldOfManga.feature.manga.inUserList;

import com.NowakArtur97.WorldOfManga.feature.manga.details.Manga;
import com.NowakArtur97.WorldOfManga.feature.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "manga_list", schema = "world_of_manga")
@Getter
@Setter
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

	@Override
	public boolean equals(Object o) {

		if (this == o) return true;
		if (!(o instanceof MangaInUserList)) return false;

		MangaInUserList that = (MangaInUserList) o;

		return Objects.equals(getMangaInUserListId(), that.getMangaInUserListId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getMangaInUserListId());
	}
}
