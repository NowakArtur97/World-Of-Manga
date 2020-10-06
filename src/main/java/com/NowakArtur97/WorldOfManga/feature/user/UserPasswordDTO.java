package com.NowakArtur97.WorldOfManga.feature.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@UserPasswordsMatch.List({
        @UserPasswordsMatch(password = "password", matchingPassword = "matchingPassword",
                message = "{userPassword.password.matchingFields}")})
public
class UserPasswordDTO {

    @NotBlank(message = "{userPassword.password.notBlank}")
    private String password;

    @NotBlank(message = "{userPassword.password.notBlank}")
    private String matchingPassword;
}
