package com.NowakArtur97.WorldOfManga.feature.manga.inUserList;

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
class MangaInUserListId implements Serializable {

    private static final long serialVersionUID = -1934008381091284254L;

    @Column(name = "manga_id")
    private Long mangaId;

    @Column(name = "user_id")
    private Long userId;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof MangaInUserListId)) return false;

        MangaInUserListId that = (MangaInUserListId) o;

        return Objects.equals(getMangaId(), that.getMangaId()) &&
                Objects.equals(getUserId(), that.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMangaId(), getUserId());
    }
}
