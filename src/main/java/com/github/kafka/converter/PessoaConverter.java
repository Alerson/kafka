package com.github.kafka.converter;

import com.github.kafka.dto.PessoaDTO;
import com.github.kafka.utils.ConversionUtils;
import example.avro.Pessoa;
import org.springframework.stereotype.Component;

@Component
public class PessoaConverter {
    public PessoaDTO toDto(Pessoa pessoa) {
        return new PessoaDTO(
                ConversionUtils.valueOrDefault(pessoa.getName()),
                ConversionUtils.valueOrDefault(pessoa.getSurname())
        );
    }

}
