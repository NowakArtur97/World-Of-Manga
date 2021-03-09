package com.NowakArtur97.WorldOfManga.feature.user;

import com.NowakArtur97.WorldOfManga.testUtil.generator.NameWithSpacesGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("User_Tests")
@Tag("Unit_Tests")
@Tag("UserPasswordValidation_Tests")
class UserPasswordValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void when_users_password_is_correct_should_not_have_violations() {

        String password = "password";

        UserPasswordDTO userPasswordDTO = UserPasswordDTO.builder().password(password).matchingPassword(password)
                .build();

        Set<ConstraintViolation<UserPasswordDTO>> violations = validator.validate(userPasswordDTO);

        assertAll(() -> assertTrue(violations.isEmpty(), () -> "shouldn`t have violations, but: "));
    }

    @Test
    void when_users_password_is_null_should_have_violations() {

        String password = null;

        UserPasswordDTO userPasswordDTO = UserPasswordDTO.builder().password(password).matchingPassword(password)
                .build();

        Set<ConstraintViolation<UserPasswordDTO>> violations = validator.validate(userPasswordDTO);

        assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violations, but: "), () -> assertEquals(3,
                violations.size(), () -> "should have three violations, but have: " + violations.size()));
    }

    @Test
    void when_users_password_is_empty_should_have_violations() {

        UserPasswordDTO userPasswordDTO = UserPasswordDTO.builder().password("").matchingPassword("").build();

        Set<ConstraintViolation<UserPasswordDTO>> violations = validator.validate(userPasswordDTO);

        assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violations, but: "), () -> assertEquals(2,
                violations.size(), () -> "should have two violations, but have: " + violations.size()));
    }

    @Test
    void when_users_password_is_blank_should_have_violations() {

        UserPasswordDTO userPasswordDTO = UserPasswordDTO.builder().password("   ").matchingPassword("   ").build();

        Set<ConstraintViolation<UserPasswordDTO>> violations = validator.validate(userPasswordDTO);

        assertAll(() -> assertFalse(violations.isEmpty(), () -> "should have violations, but: "), () -> assertEquals(2,
                violations.size(), () -> "should have two violations, but have: " + violations.size()));
    }
}
