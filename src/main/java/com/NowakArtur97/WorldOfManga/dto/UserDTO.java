package com.NowakArtur97.WorldOfManga.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

	@NotBlank(message = "${user.userName.notBlank}")
	@Size(message = "${user.userName.size}", max = 40)
	private String userName;

	@NotBlank(message = "${user.email.notBlank}")
	@Size(message = "${user.email.size}", max = 100)
	private String email;

	@Valid
	private UserPasswordDTO password;

	@Size(message = "${user.firstName.size}", max = 40)
	private String firstName;

	@Size(message = "${user.lastName.size}", max = 40)
	private String lastName;
}