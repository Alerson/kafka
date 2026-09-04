package com.github.kafka.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Objects;

public class EnderecoDTO {

    @NotBlank(message = "can not be empty or null")
    private String logradouro;

    // numero é opcional (endereços sem numeração/"S/N" são comuns) e aceita zero;
    // apenas valores negativos são inválidos. Ver docs/features/2026-09-fluxo-endereco.md.
    @PositiveOrZero(message = "must not be negative")
    private Integer numero;

    @NotBlank(message = "can not be empty or null")
    private String cidade;

    @NotBlank(message = "can not be empty or null")
    private String estado;

    @NotBlank(message = "can not be empty or null")
    private String cep;

    public EnderecoDTO() {
    }

    public EnderecoDTO(String logradouro, Integer numero, String cidade, String estado, String cep) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnderecoDTO that = (EnderecoDTO) o;
        return Objects.equals(logradouro, that.logradouro)
                && Objects.equals(numero, that.numero)
                && Objects.equals(cidade, that.cidade)
                && Objects.equals(estado, that.estado)
                && Objects.equals(cep, that.cep);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logradouro, numero, cidade, estado, cep);
    }

    @Override
    public String toString() {
        return "EnderecoDTO(logradouro=" + logradouro + ", numero=" + numero
                + ", cidade=" + cidade + ", estado=" + estado + ", cep=" + cep + ")";
    }

}
