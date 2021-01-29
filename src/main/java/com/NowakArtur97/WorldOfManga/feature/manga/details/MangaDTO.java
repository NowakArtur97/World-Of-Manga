package com.NowakArtur97.WorldOfManga.feature.manga.details;

import com.NowakArtur97.WorldOfManga.feature.author.Author;
import com.NowakArtur97.WorldOfManga.feature.manga.genre.MangaGenre;
import com.NowakArtur97.WorldOfManga.feature.manga.translation.MangaTranslationDTO;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MangaDTO {

    private Long id;

    private MultipartFile image;

    @NotEmpty(message = "{manga.authors.notEmpty}")
    private Set<Author> authors;

    @NotEmpty(message = "{manga.genres.notEmpty}")
    private Set<MangaGenre> genres;

    @Valid
    private MangaTranslationDTO enTranslation = new MangaTranslationDTO();

    @Valid
    private MangaTranslationDTO plTranslation = new MangaTranslationDTO();

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof MangaDTO)) return false;

        MangaDTO mangaDTO = (MangaDTO) o;

        return Objects.equals(getId(), mangaDTO.getId()) &&
                Objects.equals(getAuthors(), mangaDTO.getAuthors()) &&
                Objects.equals(getGenres(), mangaDTO.getGenres()) &&
                Objects.equals(getEnTranslation(), mangaDTO.getEnTranslation()) &&
                Objects.equals(getPlTranslation(), mangaDTO.getPlTranslation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAuthors(), getGenres(), getEnTranslation(), getPlTranslation());
    }
}
