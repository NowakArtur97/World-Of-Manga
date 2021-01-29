package com.NowakArtur97.WorldOfManga.feature.user;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@UserPasswordsMatch.List({
        @UserPasswordsMatch(password = "password", matchingPassword = "matchingPassword")})
class UserPasswordDTO {

    @NotBlank(message = "{userPassword.password.notBlank}")
    private String password;

    @NotBlank(message = "{userPassword.password.notBlank}")
    private String matchingPassword;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof UserPasswordDTO)) return false;

        UserPasswordDTO that = (UserPasswordDTO) o;

        return Objects.equals(getPassword(), that.getPassword()) &&
                Objects.equals(getMatchingPassword(), that.getMatchingPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPassword(), getMatchingPassword());
    }
}
