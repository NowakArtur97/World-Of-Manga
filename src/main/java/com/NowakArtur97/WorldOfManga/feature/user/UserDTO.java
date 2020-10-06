package com.NowakArtur97.WorldOfManga.feature.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public
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
}
