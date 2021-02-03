package com.NowakArtur97.WorldOfManga.feature.user;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
class UserDTO {

    @NotBlank(message = "{user.username.notBlank}")
    @Size(message = "{user.username.size}{max}", max = 40)
    private String username;

    @NotBlank(message = "{user.email.notBlank}")
    @Size(message = "{user.email.size}{max}", max = 100)
    @Email(message = "{user.email.emailFormat}")
    private String email;

    @Valid
    private UserPasswordDTO userPasswordDTO;

    @Size(message = "{user.firstName.size}{max}", max = 40)
    private String firstName;

    @Size(message = "{user.lastName.size}{max}", max = 40)
    private String lastName;

    @AssertTrue(message = "{user.areTermsAccepted.assertTrue}")
    private Boolean areTermsAccepted;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;

        UserDTO userDTO = (UserDTO) o;

        return Objects.equals(getUsername(), userDTO.getUsername()) &&
                Objects.equals(getEmail(), userDTO.getEmail()) &&
                Objects.equals(getUserPasswordDTO(), userDTO.getUserPasswordDTO()) &&
                Objects.equals(getFirstName(), userDTO.getFirstName()) &&
                Objects.equals(getLastName(), userDTO.getLastName()) &&
                Objects.equals(getAreTermsAccepted(), userDTO.getAreTermsAccepted());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getEmail(), getUserPasswordDTO(), getFirstName(), getLastName(), getAreTermsAccepted());
    }
}
