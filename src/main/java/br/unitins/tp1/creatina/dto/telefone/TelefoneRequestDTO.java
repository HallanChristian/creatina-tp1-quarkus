package br.unitins.tp1.creatina.dto.telefone;

import br.unitins.tp1.creatina.model.Telefone;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// DTO TelefoneCliente
public record TelefoneRequestDTO(
    // DDD do telefone (obrigatório, deve conter 2 dígitos)
    @NotBlank(message = "Informe o DDD do telefone.")
    @Size(min = 2 , max = 2 , message = "O ddd deve conter apenas 2 digitos")
    String ddd, 

    // Número do telefone (obrigatório, deve conter 9 dígitos)
    @NotBlank(message = "Informe o número do telefone.")
    @Size(min = 9 , max = 9, message = "O número deve conter 9 digitos ")
    String numero
) {
    public static Telefone convert(TelefoneRequestDTO dto) {
                Telefone t = new Telefone();
                t.setDdd(dto.ddd);
                t.setNumero(dto.numero);
                return t;
        }
    }
