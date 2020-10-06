package com.NowakArtur97.WorldOfManga.feature.author;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {

	@NotBlank(message = "{author.fullName.notBlank}")
	@Size(message = "{author.fullName.size}{max}", max = 50)
	private String fullName;
}
