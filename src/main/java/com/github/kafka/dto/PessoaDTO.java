package com.github.kafka.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.Objects;

public class PessoaDTO {

    @NotBlank(message = "can not be empty or null")
    private String name;

    @NotBlank(message = "can not be empty or null")
    private String surname;

    public PessoaDTO() {
    }

    public PessoaDTO(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PessoaDTO pessoaDTO = (PessoaDTO) o;
        return Objects.equals(name, pessoaDTO.name) && Objects.equals(surname, pessoaDTO.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname);
    }

    @Override
    public String toString() {
        return "PessoaDTO(name=" + name + ", surname=" + surname + ")";
    }

}
