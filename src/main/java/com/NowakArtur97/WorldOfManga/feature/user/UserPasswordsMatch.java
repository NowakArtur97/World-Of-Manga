package com.NowakArtur97.WorldOfManga.feature.user;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserPasswordsMatchValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserPasswordsMatch {

    String message() default "{userPassword.password.matchingFields}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String password();

    String matchingPassword();

    @Documented
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        UserPasswordsMatch[] value();
    }
}
