package com.NowakArtur97.WorldOfManga.feature.author;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class AuthorDTO {

    @NotBlank(message = "{author.fullName.notBlank}")
    @Size(message = "{author.fullName.size}{max}", max = 50)
    private String fullName;
}
