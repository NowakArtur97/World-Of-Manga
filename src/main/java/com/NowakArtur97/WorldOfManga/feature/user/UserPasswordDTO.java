package com.NowakArtur97.WorldOfManga.feature.user;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@UserPasswordsMatch.List({
        @UserPasswordsMatch(password = "password", matchingPassword = "matchingPassword",
                message = "{userPassword.password.matchingFields}")})
class UserPasswordDTO {

    @NotBlank(message = "{userPassword.password.notBlank}")
    private String password;

    @NotBlank(message = "{userPassword.password.notBlank}")
    private String matchingPassword;
}
