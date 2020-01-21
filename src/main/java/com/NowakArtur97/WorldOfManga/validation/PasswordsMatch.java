package com.NowakArtur97.WorldOfManga.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = PasswordMatchValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordsMatch {

	String message() default "${userPassword.password.matchingFields}";
	
	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String firstPasswordField();

	String secondPasswordField();

	@Documented
	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@interface List {
		PasswordsMatch[] value();
	}
}
