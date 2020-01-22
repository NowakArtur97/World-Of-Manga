package com.NowakArtur97.WorldOfManga.validation.userPassword;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = PasswordsMatchValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordsMatch {

	String message() default "{userPassword.password.matchingFields}";
	
	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String password();

	String matchingPassword();

	@Documented
	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@interface List {
		PasswordsMatch[] value();
	}
}
