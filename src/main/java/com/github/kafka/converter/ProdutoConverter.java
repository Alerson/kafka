package com.github.kafka.converter;

import com.github.kafka.dto.ProdutoDTO;
import com.github.kafka.utils.ConversionUtils;
import example.avro.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoConverter {

    public ProdutoDTO toDto(Produto produto) {
        return new ProdutoDTO(
                ConversionUtils.valueOrDefault(produto.getName()),
                produto.getValor() != null ? String.valueOf(produto.getValor()) : null
        );
    }

}
