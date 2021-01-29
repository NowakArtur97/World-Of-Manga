package com.NowakArtur97.WorldOfManga.feature.author;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {

    @NotBlank(message = "{author.fullName.notBlank}")
    @Size(message = "{author.fullName.size}{max}", max = 50)
    private String fullName;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof AuthorDTO)) return false;

        AuthorDTO authorDTO = (AuthorDTO) o;

        return Objects.equals(getFullName(), authorDTO.getFullName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFullName());
    }
}
