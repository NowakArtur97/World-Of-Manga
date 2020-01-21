package com.NowakArtur97.WorldOfManga.validation.userPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, Object> {

	private String password;
	private String matchingPassword;

	@Override
	public void initialize(final PasswordsMatch passwordMatchAnnotation) {

		password = passwordMatchAnnotation.password();
		matchingPassword = passwordMatchAnnotation.matchingPassword();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {

		boolean isValid = true;

		final Object passwordField = new BeanWrapperImpl(value).getPropertyValue(password);
		final Object matchingPasswordField = new BeanWrapperImpl(value).getPropertyValue(matchingPassword);

		isValid = passwordField != null && matchingPasswordField != null && passwordField.equals(matchingPasswordField);

		return isValid;
	}

}
