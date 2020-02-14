package com.NowakArtur97.WorldOfManga.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthorDTO {

	@NotBlank(message = "{author.fullName.notBlank}")
	@Size(message = "{author.fullName.size}{max}", max = 50)
	private String fullName;
}
