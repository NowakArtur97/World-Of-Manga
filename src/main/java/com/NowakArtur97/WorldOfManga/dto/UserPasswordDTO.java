package com.NowakArtur97.WorldOfManga.dto;

import javax.validation.constraints.NotBlank;

import com.NowakArtur97.WorldOfManga.validation.userPassword.PasswordsMatch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@PasswordsMatch.List({
		@PasswordsMatch(password = "password", matchingPassword = "matchingPassword", message = "{userPassword.password.matchingFields}") })
public class UserPasswordDTO {

	@NotBlank(message = "{userPassword.password.notBlank}")
	private String password;

	@NotBlank(message = "{userPassword.password.notBlank}")
	private String matchingPassword;
}
