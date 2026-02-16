package com.github.kafka.utils;

import com.github.kafka.dto.PessoaDTO;
import com.github.kafka.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilsTest {

    @Test
    void shouldPassValidationWithValidObject() {
        PessoaDTO dto = new PessoaDTO("Alerson", "Rigo");

        assertDoesNotThrow(() -> ValidationUtils.validate(dto));
    }

    @Test
    void shouldThrowValidationExceptionWhenFieldsAreBlank() {
        PessoaDTO dto = new PessoaDTO("", "");

        ValidationException exception = assertThrows(ValidationException.class,
                () -> ValidationUtils.validate(dto));

        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("can not be empty or null"));
    }

    @Test
    void shouldThrowValidationExceptionWhenFieldsAreNull() {
        PessoaDTO dto = new PessoaDTO();

        ValidationException exception = assertThrows(ValidationException.class,
                () -> ValidationUtils.validate(dto));

        assertNotNull(exception.getMessage());
    }
}
