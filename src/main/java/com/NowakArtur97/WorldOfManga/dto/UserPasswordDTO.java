package com.NowakArtur97.WorldOfManga.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPasswordDTO {

	private String password;
	
	private String matchingPassword;
}
