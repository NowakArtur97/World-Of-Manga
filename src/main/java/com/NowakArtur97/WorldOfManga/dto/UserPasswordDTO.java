package com.NowakArtur97.WorldOfManga.dto;

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
		@PasswordsMatch(password = "password", matchingPassword = "matchingPassword", message = "${userPassword.password.matchingFields}") })
public class UserPasswordDTO {

	private String password;

	private String matchingPassword;
}
