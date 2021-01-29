package com.NowakArtur97.WorldOfManga.feature.manga.rating;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
class MangaRatingId implements Serializable {

    private static final long serialVersionUID = -4902130401018115529L;

    @Column(name = "manga_id")
    private Long mangaId;

    @Column(name = "user_id")
    private Long userId;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof MangaRatingId)) return false;
		MangaRatingId that = (MangaRatingId) o;
		return Objects.equals(getMangaId(), that.getMangaId()) &&
				Objects.equals(getUserId(), that.getUserId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getMangaId(), getUserId());
	}
}
