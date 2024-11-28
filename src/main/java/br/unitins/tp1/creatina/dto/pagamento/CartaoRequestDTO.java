package br.unitins.tp1.creatina.dto.pagamento;

import java.time.LocalDate;

import br.unitins.tp1.creatina.model.TipoCartao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CartaoRequestDTO(
    @NotBlank(message = "O número do cartão é obrigatório.")
    @Pattern(regexp = "\\d{16}", message = "O número do cartão deve ter 16 dígitos.")
    String numero,

    @NotBlank(message = "O nome do titular é obrigatório.")
    String nomeTitular,

    @NotNull(message = "A validade é obrigatória.")
    LocalDate validade,

    @NotBlank(message = "O CVC é obrigatório.")
    @Pattern(regexp = "\\d{3}", message = "O CVC deve ter 3 dígitos.")
    String cvc,

    @NotNull(message = "O tipo do cartão é obrigatório.")
    TipoCartao tipoCartao
) {}

