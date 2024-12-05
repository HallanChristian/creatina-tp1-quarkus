package br.unitins.tp1.creatina.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SenhaPatchDTO(
        @NotBlank(message = "A senha não pode ser nula") @Size(min = 5, max = 20, message = "A senha deve ter entre 5 e 20 caracteres") String senha) {

}
