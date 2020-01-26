package com.NowakArtur97.WorldOfManga.validation.userPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

public class UserPasswordsMatchValidator implements ConstraintValidator<UserPasswordsMatch, Object> {

	private String password;
	private String matchingPassword;
	private String message;

	@Override
	public void initialize(final UserPasswordsMatch passwordMatchAnnotation) {

		password = passwordMatchAnnotation.password();
		matchingPassword = passwordMatchAnnotation.matchingPassword();
		message = passwordMatchAnnotation.message();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {

		boolean isValid = true;

		final Object passwordField = new BeanWrapperImpl(value).getPropertyValue(password);
		final Object matchingPasswordField = new BeanWrapperImpl(value).getPropertyValue(matchingPassword);

		isValid = passwordField != null && matchingPasswordField != null && passwordField.equals(matchingPasswordField);

		if (!isValid) {
			context.buildConstraintViolationWithTemplate(message).addPropertyNode(password).addConstraintViolation()
					.disableDefaultConstraintViolation();
		}

		return isValid;
	}

}