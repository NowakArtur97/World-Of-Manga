package com.NowakArtur97.WorldOfManga.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordsMatch, Object> {

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {

		return false;
	}

}
