package com.github.kafka.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.Objects;

public class ProdutoDTO {

    @NotBlank(message = "can not be empty or null")
    private String name;

    @NotBlank(message = "can not be empty or null")
    private String valor;

    public ProdutoDTO() {
    }

    public ProdutoDTO(String name, String valor) {
        this.name = name;
        this.valor = valor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProdutoDTO that = (ProdutoDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, valor);
    }

    @Override
    public String toString() {
        return "ProdutoDTO(name=" + name + ", valor=" + valor + ")";
    }

}
