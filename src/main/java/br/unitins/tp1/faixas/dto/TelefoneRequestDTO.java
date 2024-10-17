package br.unitins.tp1.faixas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TelefoneRequestDTO(
    @NotBlank(message = "O ddd não pode ser nulo.")
    @Size(min = 2 , max = 2 , message = "O ddd deve conter apenas 2 digitos")
    String ddd, 
    @NotBlank(message = "O número não pode ser nulo.")
    @Size(min = 9 , max = 9, message = "O número deve conter 9 digitos ")
    String numero
) {}
