package com.NowakArtur97.WorldOfManga.feature.manga.translation;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MangaTranslationDTO {

    @NotBlank(message = "{mangaTranslation.title.notBlank}")
    @Size(message = "{mangaTranslation.title.size}{max}", max = 50)
    private String title;

    @NotBlank(message = "{mangaTranslation.description.notBlank}")
    @Size(message = "{mangaTranslation.description.size}{max}", max = 1000)
    private String description;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof MangaTranslationDTO)) return false;

        MangaTranslationDTO that = (MangaTranslationDTO) o;

        return Objects.equals(getTitle(), that.getTitle()) &&
                Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getDescription());
    }
}
