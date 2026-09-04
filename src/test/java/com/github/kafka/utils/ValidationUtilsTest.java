package com.github.kafka.utils;

import com.github.kafka.dto.EnderecoDTO;
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

    @Test
    void shouldPassValidationWhenEnderecoNumeroIsNull() {
        // endereço sem numeração ("S/N") é um caso válido de negócio
        EnderecoDTO dto = new EnderecoDTO("Rua das Flores", null, "Porto Alegre", "RS", "90000-000");

        assertDoesNotThrow(() -> ValidationUtils.validate(dto));
    }

    @Test
    void shouldPassValidationWhenEnderecoNumeroIsZero() {
        EnderecoDTO dto = new EnderecoDTO("Rua das Flores", 0, "Porto Alegre", "RS", "90000-000");

        assertDoesNotThrow(() -> ValidationUtils.validate(dto));
    }

    @Test
    void shouldThrowValidationExceptionWhenEnderecoNumeroIsNegative() {
        EnderecoDTO dto = new EnderecoDTO("Rua das Flores", -1, "Porto Alegre", "RS", "90000-000");

        ValidationException exception = assertThrows(ValidationException.class,
                () -> ValidationUtils.validate(dto));

        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("must not be negative"));
    }
}
