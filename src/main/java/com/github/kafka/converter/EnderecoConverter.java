package com.github.kafka.converter;

import com.github.kafka.dto.EnderecoDTO;
import com.github.kafka.utils.ConversionUtils;
import example.avro.Endereco;
import org.springframework.stereotype.Component;

@Component
public class EnderecoConverter {

    public EnderecoDTO toDto(Endereco endereco) {
        return new EnderecoDTO(
                ConversionUtils.valueOrDefault(endereco.getLogradouro()),
                endereco.getNumero(),
                ConversionUtils.valueOrDefault(endereco.getCidade()),
                ConversionUtils.valueOrDefault(endereco.getEstado()),
                ConversionUtils.valueOrDefault(endereco.getCep())
        );
    }

}
