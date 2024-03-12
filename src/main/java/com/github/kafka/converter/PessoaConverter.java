package com.github.kafka.converter;

import com.github.kafka.dto.PessoaDTO;
import example.avro.Pessoa;
import org.springframework.stereotype.Component;

@Component
public class PessoaConverter {
    public PessoaDTO toDto(Pessoa pessoa) {
        return PessoaDTO.builder()
                .name(valueOrDefault(pessoa.getName()))
                .surname(valueOrDefault(pessoa.getSurname()))
                .build();
    }

    private static String valueOrDefault(CharSequence value) {
        return value != null ? String.valueOf(value) : "";
    }

}
