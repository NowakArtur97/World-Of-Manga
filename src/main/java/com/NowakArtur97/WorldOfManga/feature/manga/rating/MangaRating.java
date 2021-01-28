package com.NowakArtur97.WorldOfManga.feature.manga.rating;

import com.NowakArtur97.WorldOfManga.feature.manga.details.Manga;
import com.NowakArtur97.WorldOfManga.feature.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "manga_rating", schema = "world_of_manga")
@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof MangaRating)) return false;

        MangaRating that = (MangaRating) o;

        return Objects.equals(getMangaRatingId(), that.getMangaRatingId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMangaRatingId());
    }
}
