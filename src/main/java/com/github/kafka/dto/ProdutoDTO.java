package com.github.kafka.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {

    @NotBlank(message = "can not be empty or null")
    private String name;

    @NotBlank(message = "can not be empty or null")
    private String valor;

}
