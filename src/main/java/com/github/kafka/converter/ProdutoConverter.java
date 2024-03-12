package com.github.kafka.converter;

import com.github.kafka.dto.ProdutoDTO;
import example.avro.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoConverter {

    public ProdutoDTO toDTO(Produto produto){
        return ProdutoDTO.builder()
                .name(valueOrDefault(produto.getName()))
                .valor(produto.getValor() != null ? String.valueOf(produto.getValor()) : null)
                .build();
    }

    private static String valueOrDefault(CharSequence value) {
        return value != null ? String.valueOf(value) : "";
    }

}
